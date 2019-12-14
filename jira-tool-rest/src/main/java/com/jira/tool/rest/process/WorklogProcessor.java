package com.jira.tool.rest.process;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Worklog;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.jira.tool.model.WorklogResponse;
import com.jira.tool.model.WorklogResponseSet;

/**
 * @author RM067540
 */
public class WorklogProcessor
{
    private static WorklogProcessor INSTANCE;
    private final JiraRestClient restclient;
    private final Set<WorklogResponse> worklogResponseList = new HashSet<>();

    private WorklogProcessor(final JiraRestClient restclient)
    {
        this.restclient = restclient;
    }

    /**
     * @param restclient
     *            Jira rest client created during logging in.
     * @return An instance of {@link WorklogProcessor}.
     */
    public static WorklogProcessor getInstance(final JiraRestClient restclient)
    {
        INSTANCE = new WorklogProcessor(restclient);
        return INSTANCE;
    }

    /**
     * Processes the list of issue to extract worklogs and return them according to date.
     * @param issues
     *            List of {@link Issue}.
     * @return List of {@link WorklogResponseSet}.
     */
    public List<WorklogResponseSet> processWorklog(final List<Issue> issues)
    {
        final List<Issue> issueList = Collections.synchronizedList(new ArrayList<>());
        final List<Iterable<Worklog>> iterList = new ArrayList<>();
        final Map<Long, Issue> issueMap = new HashMap<>();
        final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        final IssueRestClient issueClient = restclient.getIssueClient();
        for (final Issue issue : issues)
        {
            issueMap.put(issue.getId(), issue);
        }
        int retry = 0;
        while (true)
        {
            try
            {
                issues.parallelStream().forEach(issue -> issueList.add(issueClient.getIssue(issue.getKey()).claim()));
                break;
            }
            catch (final RuntimeException exception)
            {
                retry++;
                if (retry > 3)
                {
                    return null;
                }
            }
        }
        for (final Issue issue : issueList)
        {
            iterList.add(issue.getWorklogs());
        }
        for (final Iterable<Worklog> iter : iterList)
        {
            for (final Worklog work : iter)
            {
                final WorklogResponse worklogResponse = new WorklogResponse();
                final String[] strArr = work.getIssueUri().toString().split("/");
                final Issue issue = issueMap.get(Long.parseLong(strArr[strArr.length - 1]));
                if (issue != null)
                {
                    worklogResponse.setIssueKey(issue.getKey());
                    worklogResponse.setIssueSummary(issue.getSummary());
                }
                worklogResponse.setComment(work.getComment());
                worklogResponse.setDate(formatter.format(work.getStartDate().toDate()));
                worklogResponse.setHoursLogged(work.getMinutesSpent() / 60);
                worklogResponseList.add(worklogResponse);
            }
        }
        final Multimap<String, WorklogResponse> map = ArrayListMultimap.create();
        for (final WorklogResponse worklog : worklogResponseList)
        {
            map.put(worklog.getDate(), worklog);
        }

        final List<WorklogResponseSet> responseList = new ArrayList<>();
        for (final String key : map.keySet())
        {
            final WorklogResponseSet set = new WorklogResponseSet();
            set.setDate(key);
            set.setWorklogResponses(map.get(key));
            responseList.add(set);
        }
        responseList.sort(Comparator.comparing(e -> e.getDate()));
        return responseList;
    }

}
