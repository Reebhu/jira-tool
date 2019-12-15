package com.jira.tool.rest.util.logger;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggingManager
{

    public static void log(final String msg, final Level level, final Class<?> clazz)
    {
        final Logger logger = Logger.getLogger(clazz.getName());

        try
        {
            LogManager.getLogManager().readConfiguration(LoggingManager.class.getClassLoader().getResourceAsStream("mylogging.properties"));
        }
        catch (SecurityException | IOException e1)
        {
            e1.printStackTrace();
        }

        logger.setLevel(Level.FINE);
        logger.addHandler(new ConsoleHandler());
        // adding custom handler
        logger.addHandler(new MyHandler());
        try
        {
            // FileHandler file name with max size and number of log files limit
            final Handler fileHandler = new FileHandler("C:\\dev\\projects\\jira-tool\\jira-tool-rest\\src\\main\\resources\\log\\logger.log", 2000,
                    5);
            fileHandler.setFormatter(new MyFormatter());
            // setting custom filter for FileHandler
            fileHandler.setFilter(new MyFilter());
            logger.addHandler(fileHandler);
            logger.log(level, msg);
        }
        catch (SecurityException | IOException e)
        {
            e.printStackTrace();
        }

    }

}