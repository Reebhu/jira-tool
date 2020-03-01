package com.jira.tool.rest.config;

import org.glassfish.jersey.jettison.JettisonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.jira.tool.rest.JiraResources;

/**
 * @author RM067540
 */
public class MyApplication extends ResourceConfig
{
    /* Register JAX-RS application components. */
    public MyApplication()
    {
        register(JiraResources.class);
        register(new JettisonFeature());
    }
}
