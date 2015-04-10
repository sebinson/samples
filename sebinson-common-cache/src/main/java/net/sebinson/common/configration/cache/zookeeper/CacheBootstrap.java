package net.sebinson.common.configration.cache.zookeeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class CacheBootstrap implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(CacheBootstrap.class);
    private static boolean      flag   = false;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!flag) {
            flag = true;
            CacheThread ct = new CacheThread();
            ct.start();
        } else {
            logger.warn("Cache task has been started, this belongs to the repetition priming!");
        }
    }
}
