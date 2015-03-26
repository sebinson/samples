package net.sebinson.common.cache.zookeeper;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import net.sebinson.common.cache.zookeeper.log.CacheLog;

@Service
public class CacheBootstrap implements InitializingBean {
    private static boolean flag = false;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!flag) {
            flag = true;
            CacheThread ct = new CacheThread();
            ct.start();
        } else {
            CacheLog.warn("Cache task has been started, this belongs to the repetition priming!");
        }
    }
}
