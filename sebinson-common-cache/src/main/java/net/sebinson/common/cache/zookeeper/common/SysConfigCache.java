package net.sebinson.common.cache.zookeeper.common;

import net.sebinson.common.cache.zookeeper.CacheEnums;
import net.sebinson.common.cache.zookeeper.CacheFactory;

public class SysConfigCache
{
    public static String get(String key)
    {
        return CacheFactory.getMapCache(CacheEnums.SYS_CONFIG_CACHE).get(key);
    }
}
