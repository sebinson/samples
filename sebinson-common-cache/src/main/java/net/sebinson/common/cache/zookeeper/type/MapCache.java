package net.sebinson.common.cache.zookeeper.type;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapCache implements Cache {
    private Map<String, String> map = new ConcurrentHashMap<String, String>();

    /**
     * 根据key值获取value
     * 
     * @param key
     * @return
     */
    public String get(String key) {
        synchronized (this.map) {
            return this.map.get(key);
        }
    }

    @SuppressWarnings("unchecked")
    public void reload(Object data) {
        synchronized (this.map) {
            this.map.clear();
            this.map.putAll((Map<String, String>) data);
        }
    }
}
