package com.jira.tool.rest.process;

import java.util.ArrayList;
import java.util.List;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;

/**
 * @author RM067540
 */
public class JQLprocessor
{
    private final JiraRestClient restclient;

    private JQLprocessor(final JiraRestClient restclient)
    {
        this.restclient = restclient;
    }

    public static JQLprocessor create(final JiraRestClient restclient)
    {
        return new JQLprocessor(restclient);
    }

    /**
     * Takes a jql and returns corresponding issue list.
     * @param jql
     * @return
     */
    public List<Issue> processJQL(final String jql)
    {
        final List<Issue> issueList = new ArrayList<>();

        final SearchResult search = restclient.getSearchClient().searchJql(jql).claim();

        for (final Issue issue : search.getIssues())
        {
            issueList.add(issue);
        }

        return issueList;
    }
}
