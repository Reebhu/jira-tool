package com.jira.tool.rest.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.MissingResourceException;

/**
 * @author RM067540
 */
public class StringLoader
{
    /**
     * Utility class to help load externalized strings.
     * @param str
     * @param clazz
     * @return
     */
    public static String load(final String str, final Class<?> clazz)
    {
        String stmt = null;
        final StringBuilder resourceName = new StringBuilder(32);
        resourceName.append(str).append(".txt");

        final InputStream stream = clazz.getClassLoader().getResourceAsStream(resourceName.toString());

        // If the resource could not be found, throw an exception.
        if (stream == null)
        {
            throw new MissingResourceException("Unable to load given resource", clazz.getName(), str);
        }

        // The SQL stream was found, so read it.
        final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        try
        {
            String line = reader.readLine();
            if (line != null)
            {
                final StringBuilder builder = new StringBuilder(128);

                // Include a comment denoting the name of the query.

                while (line != null)
                {
                    builder.append(line);

                    // Preserve the new lines in the externalized SQL.
                    builder.append('\n');

                    line = reader.readLine();
                }
                stmt = builder.toString();
            }
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
        return stmt.trim();
    }
}
