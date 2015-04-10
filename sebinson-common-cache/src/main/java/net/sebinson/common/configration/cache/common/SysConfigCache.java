package net.sebinson.common.configration.cache.common;

import net.sebinson.common.configration.cache.zookeeper.CacheEnums;
import net.sebinson.common.configration.cache.zookeeper.CacheFactory;

public class SysConfigCache
{
    public static String get(String key)
    {
        return CacheFactory.getMapCache(CacheEnums.SYS_CONFIG_CACHE).get(key);
    }
}
