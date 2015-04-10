package net.sebinson.common.cache.encache;

import java.io.InputStream;
import java.net.URL;

import net.sebinson.common.cache.AbstractCacheManager;
import net.sebinson.common.cache.Cache;
import net.sebinson.common.cache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.Configuration;

public class EhCacheCacheManager extends AbstractCacheManager {

    private net.sf.ehcache.CacheManager cacheManager;

    public EhCacheCacheManager() throws CacheException {
        this.cacheManager = new CacheManager();
    }

    public EhCacheCacheManager(InputStream configurationInputStream) throws CacheException {
        this.cacheManager = new CacheManager(configurationInputStream);
    }

    public EhCacheCacheManager(String configurationFileName) throws CacheException {
        this.cacheManager = new CacheManager(configurationFileName);
    }

    public EhCacheCacheManager(URL configurationURL) throws CacheException {
        this.cacheManager = new CacheManager(configurationURL);
    }

    public EhCacheCacheManager(Configuration configuration) throws CacheException {
        this.cacheManager = new CacheManager(configuration);
    }

    public EhCacheCacheManager(net.sf.ehcache.CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public net.sf.ehcache.CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(net.sf.ehcache.CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = super.getCache(name);
        if (cache == null) {
            Ehcache ehcache = this.cacheManager.getEhcache(name);
            if (ehcache != null) {
                addCache(name, new EhCacheCache(ehcache));
                cache = super.getCache(name); // potentially decorated
            }
        }
        return cache;
    }

}
