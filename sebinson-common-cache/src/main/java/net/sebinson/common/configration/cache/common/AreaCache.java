package net.sebinson.common.configration.cache.common;

import java.util.List;

import net.sebinson.common.configration.cache.beans.Area;
import net.sebinson.common.configration.cache.zookeeper.CacheEnums;
import net.sebinson.common.configration.cache.zookeeper.CacheFactory;

public class AreaCache {
    public static Area get(String key) {
        return (Area) CacheFactory.getTreeCache(CacheEnums.AREA_CACHE).get(key);
    }

    @SuppressWarnings("unchecked")
    public static List<Area> getNext(String key) {
        return CacheFactory.getTreeCache(CacheEnums.AREA_CACHE).getNext(key);
    }
}
