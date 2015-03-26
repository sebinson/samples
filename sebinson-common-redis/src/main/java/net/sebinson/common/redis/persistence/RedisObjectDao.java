package net.sebinson.common.redis.persistence;

import net.sebinson.common.redis.innerTools.ObjectSerializer;
import net.sebinson.common.redis.log.RedisLog;
import redis.clients.jedis.Jedis;

public class RedisObjectDao extends RedisDatabase {

    public RedisObjectDao(String id) {
        super(id);
    }

    /**
     * 赋值
     * 
     * @param key
     * @param obj
     */
    public void set(String key, Object obj) {
        Jedis jedis = this.getJedis();
        try {
            jedis.set(key.getBytes(), ObjectSerializer.serialize(obj));
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
    }

    @SuppressWarnings("unchecked")
    public <T> T getGenerics(String key, Class<T> Clazz) {
        Jedis jedis = this.getJedis();
        Object obj = null;
        try {
            byte[] byteObj = jedis.get(key.getBytes());
            obj = ObjectSerializer.unserialize(byteObj);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return (T) obj;
    }

    public Object get(String key) {
        Jedis jedis = this.getJedis();
        Object obj = null;
        try {
            byte[] byteObj = jedis.get(key.getBytes());
            obj = ObjectSerializer.unserialize(byteObj);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return obj;
    }

    /**
     * 对key进行++运算
     * 
     * @param key
     * @return
     */
    public long incr(String key) {
        Jedis jedis = this.getJedis();
        long l = 0;
        try {
            l = jedis.incr(key);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return l;
    }

    /**
     * 对key进行--运算
     * 
     * @param key
     * @return
     */
    public long decr(String key) {
        Jedis jedis = this.getJedis();
        long l = 0;
        try {
            l = jedis.decr(key);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return l;
    }

    /**
     * 对key加上指定值
     * 
     * @param key
     * @param add
     * @return
     */
    public long incrby(String key, long add) {
        Jedis jedis = this.getJedis();
        long l = 0;
        try {
            l = jedis.incrBy(key, add);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return l;
    }

    /**
     * 对key减去指定值
     * 
     * @param key
     * @param add
     * @return
     */
    public long decrby(String key, long add) {
        Jedis jedis = this.getJedis();
        long l = 0;
        try {
            l = jedis.decrBy(key, add);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return l;
    }
}
