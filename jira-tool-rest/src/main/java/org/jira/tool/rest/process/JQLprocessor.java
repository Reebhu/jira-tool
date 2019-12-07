/**
 * 
 */
package org.jira.tool.rest.process;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

/**
 * @author RM067540
 *
 */
public class JQLprocessor {
	
	
	public static List<Issue> processJQL(String jql, String username, String password) 
	{
		
		List<Issue> doneIssueList = new ArrayList<Issue>();
		
		URI jiraserverUri = URI.create("https://jira2.cerner.com");
		
		JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
		JiraRestClient restclient = factory.createWithBasicHttpAuthentication(jiraserverUri, username, password);
		
		SearchResult search = restclient.getSearchClient().searchJql(jql).claim();
		
		for (Issue issue : search.getIssues()) 
		{
			doneIssueList.add(issue);
		}
		return doneIssueList;
	}
}
