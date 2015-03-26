package net.sebinson.common.cache.zookeeper.common;

import java.util.List;

import net.sebinson.common.cache.zookeeper.CacheEnums;
import net.sebinson.common.cache.zookeeper.CacheFactory;
import net.sebinson.common.cache.zookeeper.beans.Area;

public class AreaCache {
    public static Area get(String key) {
        return (Area) CacheFactory.getTreeCache(CacheEnums.AREA_CACHE).get(key);
    }

    @SuppressWarnings("unchecked")
    public static List<Area> getNext(String key) {
        return CacheFactory.getTreeCache(CacheEnums.AREA_CACHE).getNext(key);
    }
}
