package net.sebinson.sample.message.collection.common;

import java.util.HashMap;

public class CacheBean {
    private static class CacheInner {
        private static HashMap<String, String> cacheMap = new HashMap<String, String>();
    }

    private CacheBean() {
        super();
    }

    public static HashMap<String, String> getCachemap() {
        return CacheInner.cacheMap;
    }
}
