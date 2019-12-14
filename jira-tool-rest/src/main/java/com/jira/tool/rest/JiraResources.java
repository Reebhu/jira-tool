package com.jira.tool.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.google.gson.Gson;
import com.jira.tool.model.User;
import com.jira.tool.model.WorklogResponseSet;
import com.jira.tool.rest.process.JQLprocessor;
import com.jira.tool.rest.process.WorklogProcessor;
import com.jira.tool.rest.util.StringLoader;

/**
 * @author RM067540
 */
@Singleton
@Path("jira")
public class JiraResources
{
    private User user;
    private JiraRestClient restclient;

    /**
     * Allows creation of a user object used for basic authentication.
     * @param user
     *            populated using POST request.
     * @return {@link Response} with user details.
     */
    @Path("user")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(final User user)
    {
        this.user = user;
        try
        {
            user.setURI(StringLoader.load("url", getClass()));

            final JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
            restclient = factory.createWithBasicHttpAuthentication(user.getURI(), user.getUsername(), user.getPassword());
            return Response.ok().entity(user).build();
        }
        catch (final RestClientException exception)
        {
            return Response.status(exception.getStatusCode().get()).entity("Invalid username or password").build(); //$NON-NLS-1$
        }
    }

    /**
     * Retrieves the issues for current user
     * @param beginDate
     *            date from which the issues are to be retrieved (may be null).
     * @param endDate
     *            date to which the issues are to be retrieved (may be null).
     * @return
     */
    @Path("issues")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getIssuesCurrentUser(@QueryParam(value = "from") final String beginDate, @QueryParam(value = "to") final String endDate)
    {
        if (!validateUser())
        {
            return badResponseInvalidUser();
        }
        String jql = " assignee = currentUser() ";
        if (beginDate != null)
        {
            jql = jql + " AND updatedDate >= " + beginDate;
        }
        if (endDate != null)
        {
            jql = jql + " AND updatedDate <= " + endDate;
        }

        final Map<String, String> mapIssues = new HashMap<>();

        final List<Issue> issues = JQLprocessor.create(restclient).processJQL(jql);

        for (final Issue issue : issues)
        {
            mapIssues.put(issue.getKey(), issue.getSummary());
        }

        return Response.ok().entity(mapIssues).build();
    }

    /**
     * Retrieves the worklog data from JIRA according to the date.
     * @return List of {@link WorklogResponseSet}.
     */
    @Path("worklog")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getWorklogCurrentUser()
    {
        if (!validateUser())
        {
            return badResponseInvalidUser();
        }
        final String jql = " assignee = currentUser() AND worklogAuthor = currentUser()";
        final List<Issue> issues = JQLprocessor.create(restclient).processJQL(jql);

        final WorklogProcessor worklogProcessor = WorklogProcessor.getInstance(restclient);
        final List<WorklogResponseSet> map = worklogProcessor.processWorklog(issues);
        if (map == null)
        {
            return Response.status(Status.BAD_GATEWAY).build();
        }
        final Gson gson = new Gson();
        final String jsonString = gson.toJson(map);
        return Response.status(Status.OK).entity(jsonString).build();

    }

    /**
     * Validates a user once he has logged in.
     * @return
     */
    private boolean validateUser()
    {
        if (user == null || (user.getUsername() == null) || (user.getPassword() == null))
        {
            return false;
        }
        return true;
    }

    /**
     * Is returned when trying to access the endpoints without logging in.
     * @return
     */
    private Response badResponseInvalidUser()
    {
        return Response.status(Status.FORBIDDEN).entity("Please login ").build(); //$NON-NLS-1$
    }
}
