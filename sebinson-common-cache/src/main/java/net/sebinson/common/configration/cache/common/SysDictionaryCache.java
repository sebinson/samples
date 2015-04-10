package net.sebinson.common.configration.cache.common;

import java.util.Map;

import net.sebinson.common.configration.cache.beans.SysDictionary;
import net.sebinson.common.configration.cache.zookeeper.CacheEnums;
import net.sebinson.common.configration.cache.zookeeper.CacheFactory;


public class SysDictionaryCache
{
    @SuppressWarnings("unchecked")
    public static Map<String, SysDictionary> get(String type)
    {
        return CacheFactory.getDictionaryCache(CacheEnums.SYS_DICTIONART_CACHE).get(type);
    }

    public static SysDictionary get(String type, String code)
    {
        return (SysDictionary) CacheFactory.getDictionaryCache(CacheEnums.SYS_DICTIONART_CACHE).get(type).get(code);
    }
}
