package net.sebinson.common.cache.zookeeper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sebinson.common.cache.zookeeper.type.DictionaryCache;
import net.sebinson.common.cache.zookeeper.type.Cache;
import net.sebinson.common.cache.zookeeper.type.MapCache;
import net.sebinson.common.cache.zookeeper.type.TreeCache;

public class CacheFactory {
    private static Map<String, Cache> map = new ConcurrentHashMap<String, Cache>();

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

    public static void put(String key, Cache value) {
        map.put(key, value);
    }

    public static Cache get(String key) {
        return map.get(key);
    }
}
