package com.jira.tool.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.google.gson.Gson;
import com.jira.tool.model.UserInput;
import com.jira.tool.model.WorklogResponseSet;
import com.jira.tool.rest.process.JQLprocessor;
import com.jira.tool.rest.process.WorklogProcessor;
import com.jira.tool.rest.util.UserRestClientUtil;
import com.jira.tool.rest.util.logger.LoggingManager;

/**
 * @author RM067540
 */
@Path("jira")
public class JiraResources
{

    /**
     * Allows creation of a user object used for basic authentication.
     * @param user
     *            populated using POST request.
     * @return {@link Response} with user details.
     */
    @Path("login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(final UserInput userInput)
    {
        try
        {
            UserRestClientUtil.getInstance(userInput.getUsername(), userInput.getPassword());
            final JiraRestClient restClient = UserRestClientUtil.getInstance().getrestClient();
            restClient.getUserClient().getUser(userInput.getUsername()).claim();
        }
        catch (final Exception exception)
        {
            LoggingManager.log(ExceptionUtils.getRootCauseMessage(exception), Level.SEVERE, getClass());

            return Response.status(Status.UNAUTHORIZED).entity(exception.getMessage()).build(); // $NON-NLS-1$
        }
        return Response.ok().build();
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
        String jql = " assignee = currentUser() ";
        if (beginDate != null)
        {
            jql = jql + " AND updatedDate >= " + beginDate;
        }
        if (endDate != null)
        {
            jql = jql + " AND updatedDate <= " + endDate;
        }
        if (UserRestClientUtil.getInstance() == null)
        {
            LoggingManager.log("UserRestClientUtil.getInstance() = null", Level.SEVERE, getClass());
            return Response.status(Status.FORBIDDEN).entity("Please login").build();
        }
        final JiraRestClient restClient = UserRestClientUtil.getInstance().getrestClient();

        final Map<String, String> mapIssues = new HashMap<>();

        final List<Issue> issues = JQLprocessor.create(restClient).processJQL(jql);

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
        final String jql = " assignee = currentUser() AND worklogAuthor = currentUser()";

        if (UserRestClientUtil.getInstance() == null)
        {
            LoggingManager.log("UserRestClientUtil.getInstance() = null", Level.SEVERE, getClass());
            return Response.status(Status.FORBIDDEN).entity("Please login").build();
        }

        final JiraRestClient restClient = UserRestClientUtil.getInstance().getrestClient();

        final List<Issue> issues = JQLprocessor.create(restClient).processJQL(jql);

        final WorklogProcessor worklogProcessor = WorklogProcessor.getInstance(restClient);
        try
        {
            final List<WorklogResponseSet> map = worklogProcessor.processWorklog(issues);
            final Gson gson = new Gson();
            final String jsonString = gson.toJson(map);
            return Response.status(Status.OK).entity(jsonString).build();
        }
        catch (final Exception exception)
        {
            LoggingManager.log(ExceptionUtils.getRootCauseMessage(exception), Level.SEVERE, getClass());
            return Response.status(Status.BAD_GATEWAY).build();
        }

    }

    /**
     * Allows creation of a user object used for basic authentication.
     * @param user
     *            populated using POST request.
     * @return {@link Response} with user details.
     */
    @Path("logout")
    @DELETE
    public Response logout()
    {
        UserRestClientUtil.destroyInstance();
        return Response.status(Status.NO_CONTENT).build();
    }

}
