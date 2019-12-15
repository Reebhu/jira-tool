package com.jira.tool.rest.util.logger;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MyFormatter extends Formatter
{

    @Override
    public String format(final LogRecord record)
    {
        return record.getThreadID() + "::" + record.getSourceClassName() + "::" + record.getSourceMethodName() + "::" + new Date(record.getMillis())
                + "::" + record.getMessage() + "\n";
    }

}