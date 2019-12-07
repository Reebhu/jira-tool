package org.jira.tool.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jira.tool.rest.process.JQLprocessor;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.jira.tool.model.User;

/**
 * @author RM067540
 */
@Singleton
@Path("jira")
public class JiraResources
{
    private User user;

    @Path("user")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(final User user)
    {
        this.user = user;

        return Response.ok().build();
    }

    @Path("issues")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getIssuesCurrentUser()
    {
        final String jql = " assignee = currentUser() ";

        final Map<String, String> mapIssues = new HashMap<>();

        final List<Issue> issues = JQLprocessor.processJQL(jql, user.getUsername(), user.getPassword());

        for (final Issue issue : issues)
        {
            mapIssues.put(issue.getKey(), issue.getSummary());
        }

        return Response.ok().entity(mapIssues).build();
    }
}
