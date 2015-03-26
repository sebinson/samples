package net.sebinson.common.cache.zookeeper.common;

import java.util.Map;

import net.sebinson.common.cache.zookeeper.CacheEnums;
import net.sebinson.common.cache.zookeeper.CacheFactory;
import net.sebinson.common.cache.zookeeper.beans.SysDictionary;


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
