package com.jira.tool.rest;

import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.google.gson.Gson;
import com.jira.tool.model.UserInput;
import com.jira.tool.model.WorklogResponseSet;
import com.jira.tool.rest.process.JQLprocessor;
import com.jira.tool.rest.process.WorklogProcessor;
import com.jira.tool.rest.util.StringLoader;
import com.jira.tool.rest.util.logger.LoggingManager;

/**
 * @author RM067540
 */
@Path("jira")
public class JiraResources
{

    @Context
    UriInfo urlInfo;

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
            final URI uri = URI.create(StringLoader.load("url", JiraResources.class));
            final JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
            final JiraRestClient restClient = factory.createWithBasicHttpAuthentication(uri, userInput.getUsername(), userInput.getPassword());
            restClient.getUserClient().getUser(userInput.getUsername()).claim();
            return Response.ok().entity(userInput).build();
        }
        catch (final Exception e)
        {
            return Response.status(Status.FORBIDDEN).entity("Invalid username or password").build();
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
    public Response getIssuesCurrentUser(@HeaderParam("authorization") final String auth, @QueryParam(value = "from") final String beginDate,
            @QueryParam(value = "to") final String endDate)
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
        final JiraRestClient restClient = createJIRArestClient(auth);
        if (restClient == null)
        {
            LoggingManager.log("Tried to access without login", Level.SEVERE, getClass());
            return Response.status(Status.BAD_REQUEST).entity("Please login").build();
        }

        final Map<String, String> mapIssues = new HashMap<>();

        List<Issue> issues;
        issues = JQLprocessor.create(restClient).processJQL(jql);
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
    public Response getWorklogCurrentUser(@HeaderParam("authorization") final String auth)
    {
        final String jql = " assignee = currentUser() AND worklogAuthor = currentUser()";

        final JiraRestClient restClient = createJIRArestClient(auth);

        if (restClient == null)
        {
            LoggingManager.log("Tried to access without login", Level.SEVERE, getClass());
            return Response.status(Status.BAD_REQUEST).entity("Please login").build();
        }

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

    private Pair<String, String> base64Decode(final String encoded) throws IOException
    {
        byte[] decodedByte;
        if (encoded.contains("Basic"))
        {
            decodedByte = Base64.getDecoder().decode(encoded.split(" ")[1]);
        }
        else
        {
            decodedByte = Base64.getDecoder().decode(encoded);
        }
        final String info = new String(decodedByte, "utf8");
        final String[] usernameAndPassword = info.split(":");
        return Pair.of(usernameAndPassword[0], usernameAndPassword[1]);
    }

    private JiraRestClient createJIRArestClient(final String auth)
    {
        String username = null;
        String password = null;
        try
        {
            final Pair<String, String> usernameAndPassword = base64Decode(auth);
            username = usernameAndPassword.getLeft();
            password = usernameAndPassword.getRight();
            final URI uri = URI.create(StringLoader.load("url", JiraResources.class));
            final JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
            return factory.createWithBasicHttpAuthentication(uri, username, password);
        }
        catch (final Exception e)
        {
            LoggingManager.log(e.getMessage(), Level.SEVERE, getClass());
            return null;
        }
    }
}
