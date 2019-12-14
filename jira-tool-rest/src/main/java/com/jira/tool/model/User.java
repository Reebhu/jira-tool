package com.jira.tool.model;

import java.net.URI;

/**
 * @author RM067540
 */
public class User
{
    private String username;
    private String password;
    private String uri;

    /**
     * @return the username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(final String username)
    {
        this.username = username;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(final String password)
    {
        this.password = password;
    }

    /**
     * @return the uRI
     */
    public URI getURI()
    {
        return URI.create(uri);
    }

    /**
     * @param uRI
     *            the uRI to set
     */
    public void setURI(final String uRI)
    {
        uri = uRI;
    }
}
