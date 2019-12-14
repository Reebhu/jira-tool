/**
 *
 */
package com.jira.tool.model;

import java.util.Objects;

/**
 * @author RM067540
 */
public class WorklogResponse
{
    private String issueKey;
    private String issueSummary;
    private double hoursLogged;
    private String comment;
    private String date;

    /**
     * @return the hours_logged
     */
    public double getHoursLogged()
    {
        return hoursLogged;
    }

    /**
     * @return the comment
     */
    public String getComment()
    {
        return comment;
    }

    /**
     * @return the date
     */
    public String getDate()
    {
        return date;
    }

    /**
     * @return the issueKey
     */
    public String getIssueKey()
    {
        return issueKey;
    }

    /**
     * @return the issueSummary
     */
    public String getIssueSummary()
    {
        return issueSummary;
    }

    /**
     * @param issueKey
     *            the issueKey to set
     */
    public void setIssueKey(final String issueKey)
    {
        this.issueKey = issueKey;
    }

    /**
     * @param issueSummary
     *            the issueSummary to set
     */
    public void setIssueSummary(final String issueSummary)
    {
        this.issueSummary = issueSummary;
    }

    /**
     * @param hours_logged
     *            the hours_logged to set
     */
    public void setHoursLogged(final double hoursLogged)
    {
        this.hoursLogged = hoursLogged;
    }

    /**
     * @param comment
     *            the comment to set
     */
    public void setComment(final String comment)
    {
        this.comment = comment;
    }

    /**
     * @param date
     *            the date to set
     */
    public void setDate(final String date)
    {
        this.date = date;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(comment, date, hoursLogged, issueKey, issueSummary);
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
        if (!(obj instanceof WorklogResponse))
        {
            return false;
        }
        final WorklogResponse other = (WorklogResponse) obj;
        return Objects.equals(comment, other.comment) && Objects.equals(date, other.date)
                && Double.doubleToLongBits(hoursLogged) == Double.doubleToLongBits(other.hoursLogged) && Objects.equals(issueKey, other.issueKey)
                && Objects.equals(issueSummary, other.issueSummary);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {

        final StringBuilder sb = new StringBuilder();
        sb.append("class WorklogResponse {\n");

        sb.append("    issueKey: ").append(toIndentedString(issueKey)).append(" ");
        sb.append("    issueSummary: ").append(toIndentedString(issueSummary)).append(" ");
        sb.append("    hoursLogged: ").append(toIndentedString(hoursLogged)).append(" ");
        sb.append("    comment: ").append(toIndentedString(comment)).append(" ");
        sb.append("    date: ").append(toIndentedString(date)).append(" ");

        sb.append("}");
        return sb.toString();

    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private String toIndentedString(final java.lang.Object o)
    {
        if (o == null)
        {
            return "null";
        }
        return o.toString();
    }
}
