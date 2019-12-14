package com.jira.tool.rest.util;

import java.net.URI;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

/**
 * @author RM067540
 */
public class UserRestClientUtil
{
    private static URI uri;
    private static UserRestClientUtil INSTANCE;
    private static JiraRestClient restclient;

    private UserRestClientUtil()
    {

    }

    public static UserRestClientUtil getInstance(final String username, final String password) throws Exception
    {
        if (INSTANCE == null)
        {
            INSTANCE = new UserRestClientUtil();

            uri = URI.create(StringLoader.load("url", UserRestClientUtil.class));
            final JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
            restclient = factory.createWithBasicHttpAuthentication(uri, username, password);

        }
        return INSTANCE;
    }

    public static UserRestClientUtil getInstance()
    {
        return INSTANCE;
    }

    public JiraRestClient getrestClient()
    {
        return restclient;
    }

    public static void destroyInstance()
    {
        INSTANCE = null;
    }

}
