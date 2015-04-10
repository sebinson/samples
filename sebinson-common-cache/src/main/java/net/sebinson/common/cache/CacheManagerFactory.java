package net.sebinson.common.cache;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.sebinson.common.cache.encache.EhCacheCacheManager;
import net.sebinson.common.cache.memcached.MemcachedManager;
import net.sebinson.common.cache.redis.RedisCacheManager;

/**
 * 生成各种缓存管理器的工厂方法。其中：<br/>
 * 1:EhCache <li>默认的配置为/ehcache.xml <li>
 * 默认只能创建一个EhCacheManager实例，且只创建第一个被实例化的EhCacheManager 2:Redis <br/>
 * 3:Memcached<br/>
 * 
 */
public final class CacheManagerFactory {

    // 配置文件路径
    private final static String                    CONFIG_FILE     = "/cache.properties";

    // 缓存管理器Map。这个地方主要是为了实现多个缓存
    private final static Map<String, CacheManager> cacheManagerMap = new HashMap<String, CacheManager>();

    // 单例
    private static CacheManagerFactory             instance        = new CacheManagerFactory();

    // 配置文件信息
    private static Properties                      props;

    private CacheManagerFactory() {
        init();
    }

    public static final CacheManagerFactory getInstance() {
        return instance;
    }

    /**
     * 默认的缓存管理器
     * 
     * @return
     */
    public CacheManager getCacheManager() {
        return getCacheManager("default");
    }

    /**
     * 根据管理器名称获得缓存管理器
     * 
     * @param managerName
     * @return
     */
    public CacheManager getCacheManager(String managerName) {

        if (cacheManagerMap.get(managerName) != null) {
            return cacheManagerMap.get(managerName);
        }

        // 根据配置文件信息实例化不同的CacheManager
        CacheManager cacheManager = getCacheManagerInstance(managerName);
        cacheManagerMap.put(managerName, cacheManager);

        return cacheManager;
    }

    /**
     * 初始化各个CacheManager
     * 
     * @param managerName
     * @return
     */
    private CacheManager getCacheManagerInstance(String managerName) {

        CacheManager cacheManager = null;

        if (managerName == null) {
            throw new CacheException("managerName is null !");
        }
        String cache_class = props.getProperty("cache." + managerName); // 获得缓存类型

        // 获得缓存配置地址
        String configLocation = props.getProperty("cache.config." + managerName);

        if ("redis".equalsIgnoreCase(cache_class)) {
            cacheManager = createRedisCacheManager(configLocation);
        } else if ("ehcache".equalsIgnoreCase(cache_class)) {
            cacheManager = createEhCacheCacheManager(configLocation);
        } else if ("memcached".equalsIgnoreCase(cache_class)) {
            cacheManager = new MemcachedManager();
        } else if (cache_class == null) { // 在没有找到相关属性的情况
            throw new CacheException("Not found cache type!");
        }

        cacheManagerMap.put(managerName, cacheManager);

        return cacheManager;
    }

    /**
     * 插件RedisCacheManager实例
     * 
     * @param configLocation
     * @return
     */
    private CacheManager createRedisCacheManager(String configLocation) {

        CacheManager cacheManager;
        try {
            Properties props = new Properties();
            props.load(getConfigFileStream(configLocation));
            String host = props.getProperty("host");
            int port = Integer.valueOf(props.getProperty("port"));
            int timeout = Integer.valueOf(props.getProperty("timeout"));

            cacheManager = new RedisCacheManager(host, port, timeout);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CacheException("Can't load  properties");
        }

        return cacheManager;
    }

    /**
     * 创建EhCacheCacheManager实例
     * 
     * @return
     */
    private CacheManager createEhCacheCacheManager(String configLocation) {
        // 在EhCache中要确保只有一个被实例化
        Set<String> keySet = cacheManagerMap.keySet();
        Object[] keys = keySet.toArray();

        for (int i = 0; i < keys.length; i++) {
            String key = keys[i].toString();
            CacheManager cm = cacheManagerMap.get(key);
            if (cm instanceof EhCacheCacheManager) { // 如果cacheManagerMap已经存在EhCacheCacheManager的缓存，则直接返回
                return cm;
            }
        }
        InputStream configStream = getConfigFileStream(configLocation);
        return new EhCacheCacheManager(configStream);
    }

    /**
     * 初始化
     */
    private void init() {
        InputStream configStream = getConfigFileStream(CONFIG_FILE);

        props = new Properties();
        try {
            props.load(configStream);
            configStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获得配置文件流
     * 
     * @param configFile
     * @return
     */
    private InputStream getConfigFileStream(String configFile) {
        InputStream configStream = CacheManager.class.getClassLoader().getParent().getResourceAsStream(configFile);

        if (configStream == null) {
            configStream = CacheManager.class.getResourceAsStream(configFile);
        }
        if (configStream == null) {
            throw new CacheException("Cannot find " + configFile + " !");
        }
        return configStream;
    }

}
