package net.sebinson.common.redis.innerTools;

import java.util.HashMap;
import java.util.Map;

import org.I0Itec.zkclient.ZkClient;

import net.sebinson.common.redis.log.RedisLog;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class RedisPoolHelper {
    final public static String ZK_SERVER = "zk.sebinson.net:2181";
    final public static String CACHE_PATH = "/cache/redis/pools";
    private static Map<String, JedisPool> pools = new HashMap<String, JedisPool>();
    private static int maxIdle = 20;
    private static int maxTotal = 1000;
    private static long maxWaitMillis = 10000;
    private static long timeBetweenEvictionRunsMillis = 30000;
    private static long minEvictableIdleTimeMillis = 30000;
    private static int timeout = 3000;

    static {
        try {
            ZkClient zk = new ZkClient(ZK_SERVER);
            String temp = zk.readData(CACHE_PATH, true);
            Map<String, String> map = new Gson().fromJson(temp, new TypeToken<HashMap<String, String>>() {
            }.getType());
            maxIdle = Integer.parseInt(map.get("system.maxIdle"));
            maxTotal = Integer.parseInt(map.get("system.maxTotal"));
            maxWaitMillis = Long.parseLong(map.get("system.maxWaitMillis"));
            timeBetweenEvictionRunsMillis = Long.parseLong(map.get("system.timeBetweenEvictionRunsMillis"));
            minEvictableIdleTimeMillis = Long.parseLong(map.get("system.minEvictableIdleTimeMillis"));
            timeout = Integer.parseInt(map.get("system.timeout"));
        } catch (Exception e) {
            RedisLog.error("Failed to initialize, Redis configuration exception, will use the default parameters", e);
        }
    }

    /**
     * 获取连接池
     * 
     * @param id
     *            例：redis.sebinson.net:6369:2
     * @return
     */
    public static synchronized JedisPool getPool(String id) {
        if (!pools.containsKey(id)) {
            RedisLog.info("JedisPool [ " + id + " ] does not exist，creating...");
            String[] temp = id.split(":");
            JedisPoolConfig config = new JedisPoolConfig();// Jedis池配置
            config.setMaxIdle(maxIdle);// 最大空闲对象个数
            config.setMaxTotal(maxTotal);// 最大连接数
            config.setMaxWaitMillis(maxWaitMillis);// 获取对象时最大等待时间
            config.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);// 多长时间检查一次连接池中空闲的连接
            config.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);// 空闲连接多长时间后会被收回
            JedisPool pool = new JedisPool(config, temp[0], Integer.parseInt(temp[1]), timeout, null,
                    Integer.parseInt(temp[2]));
            pools.put(id, pool);
            return pool;
        } else {
            return pools.get(id);
        }
    }

    /**
     * release connection
     * 
     * @param jedis
     * @param pool
     */
    public static void release(Jedis jedis, JedisPool pool) {
        if (jedis == null || pool == null) {
            return;
        }
        try {
            pool.returnResource(jedis);
        } catch (Exception e) {
            RedisLog.error("JedisPool connection release exception", e);
            if (jedis != null) {
                try {
                    pool.returnBrokenResource(jedis);
                } catch (Exception e2) {
                    RedisLog.error("JedisPool connection release exception ", e2);
                }
            }
        }
    }
}
