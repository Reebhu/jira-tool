package org.jira.tool.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Filter;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.User;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

/**
 * @author RM067540
 *
 */
@Path("jira")
public class JiraResources {
	@Path("issues")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getIssues() {
        List<Issue> doneIssueList = new ArrayList<Issue>();
        JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        URI jiraserverUri = URI.create("https://jira2.cerner.com");
        JiraRestClient restclient = factory.createWithBasicHttpAuthentication(jiraserverUri, "RM067540", "Reebhu1206");
        
        
       SearchResult search = restclient.getSearchClient().searchJql(" assignee = \"KG032427\" ").claim();
      for(Issue issue: search.getIssues())
      {
          if(issue.getStatus().getName().equals("Closed"))
          {
              doneIssueList.add(issue);
              System.out.println(issue.getKey()+" "+issue.getSummary());
          }
      }
		return Response.ok().build();
	}
}
