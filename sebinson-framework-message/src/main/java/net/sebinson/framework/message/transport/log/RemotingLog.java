package net.sebinson.framework.message.transport.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemotingLog {
    protected static final Logger log = LoggerFactory.getLogger("PUSH_LOG");

    public static void debug(String message) {
        if (log.isDebugEnabled()) {
            log.debug(message);
        }
    }

    public static void info(String message) {
        if (log.isInfoEnabled()) {
            log.info(message);
        }
    }

    public static void warn(String message) {
        log.warn(message);
    }

    public static void error(String message, Throwable e) {
        log.error(message, e);
    }
}
