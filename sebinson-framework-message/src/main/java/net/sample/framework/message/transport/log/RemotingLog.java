package net.sample.framework.message.transport.log;

import org.apache.log4j.Logger;

public class RemotingLog
{
    protected static final Logger log = Logger.getLogger("pushlog");

    public static void debug(Object message)
    {
        if (log.isDebugEnabled())
        {
            log.debug(message);
        }
    }

    public static void info(Object message)
    {
        if (log.isInfoEnabled())
        {
            log.info(message);
        }
    }

    public static void warn(Object message)
    {
        log.warn(message);
    }

    public static void error(Object message, Throwable e)
    {
        log.error(message, e);
    }

    public static boolean getLevel(int level)
    {
        return log.getLoggerRepository().isDisabled(level);
    }

}
