package com.jira.tool.model;

import java.util.Collection;
import java.util.Objects;

/**
 * @author RM067540
 */
public class WorklogResponseSet
{
    private String date;
    private Collection<WorklogResponse> worklogResponses;

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "WorklogResponseList [date=" + date + ", worklogResponseSet=" + worklogResponses + "]";
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(date, worklogResponses);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof WorklogResponseSet))
        {
            return false;
        }
        final WorklogResponseSet other = (WorklogResponseSet) obj;
        return Objects.equals(date, other.date) && Objects.equals(worklogResponses, other.worklogResponses);
    }

    /**
     * @return the date
     */
    public String getDate()
    {
        return date;
    }

    /**
     * @return the worklogResponseSet
     */
    public Collection<WorklogResponse> getWorklogResponses()
    {
        return worklogResponses;
    }

    /**
     * @param date
     *            the date to set
     */
    public void setDate(final String date)
    {
        this.date = date;
    }

    /**
     * @param collection
     *            the worklogResponseSet to set
     */
    public void setWorklogResponses(final Collection<WorklogResponse> collection)
    {
        this.worklogResponses = collection;
    }
}
