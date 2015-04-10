package net.sebinson.common.configration.cache.zookeeper.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheLog {

    private static Logger log = LoggerFactory.getLogger("log_cache");

    public static synchronized void error(String message, Throwable e) {
        log.error(message, e);
    }

    public static synchronized void warn(String message) {
        log.warn(message);
    }

    public static synchronized void info(String message) {
        log.info(message);
    }

    public static synchronized void debug(String message) {
        log.debug(message);
    }
}
