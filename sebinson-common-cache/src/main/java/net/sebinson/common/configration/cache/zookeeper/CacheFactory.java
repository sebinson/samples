package net.sebinson.common.configration.cache.zookeeper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sebinson.common.configration.cache.type.DictionaryCache;
import net.sebinson.common.configration.cache.type.ICacheType;
import net.sebinson.common.configration.cache.type.MapCache;
import net.sebinson.common.configration.cache.type.TreeCache;

public class CacheFactory {
    private static Map<String, ICacheType> map = new ConcurrentHashMap<String, ICacheType>();

    public static MapCache getMapCache(CacheEnums x) {
        return (MapCache) map.get(x.name());
    }

    @SuppressWarnings("rawtypes")
    public static TreeCache getTreeCache(CacheEnums x) {
        return (TreeCache) map.get(x.name());
    }

    @SuppressWarnings("rawtypes")
    public static DictionaryCache getDictionaryCache(CacheEnums x) {
        return (DictionaryCache) map.get(x.name());
    }

    public static void put(String key, ICacheType value) {
        map.put(key, value);
    }

    public static ICacheType get(String key) {
        return map.get(key);
    }
}
