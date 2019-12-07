package org.jira.tool.rest.process;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

/**
 * @author RM067540
 */
public class JQLprocessor
{

    public static List<Issue> processJQL(final String jql, final String username, final String password)
    {

        final List<Issue> doneIssueList = new ArrayList<>();

        final URI jiraserverUri = URI.create("https://jira2.cerner.com");

        final JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        final JiraRestClient restclient = factory.createWithBasicHttpAuthentication(jiraserverUri, username, password);

        final SearchResult search = restclient.getSearchClient().searchJql(jql).claim();

        for (final Issue issue : search.getIssues())
        {
            doneIssueList.add(issue);
        }
        return doneIssueList;
    }
}
