package net.sebinson.common.log;

import org.apache.log4j.Logger;

public class Log {

    private static Logger log = Logger.getLogger("rootLog");

    public static synchronized void error(Object message, Throwable e) {
        log.error(message, e);
    }

    public static synchronized void warn(Object message) {
        log.warn(message);
    }

    public static synchronized void info(Object message) {
        log.info(message);
    }

    public static synchronized void debug(Object message) {
        log.debug(message);
    }
}
