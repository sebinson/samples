package net.sebinson.common.redis.persistence;

import java.util.List;
import java.util.Map;

import net.sebinson.common.redis.log.RedisLog;
import redis.clients.jedis.Jedis;

public class RedisHashDao extends RedisDatabase {

    public RedisHashDao(String id) {
        super(id);
    }

    /**
     * 返回哈希表 key 中域的数量。 哈希表中域的数量。当 key 不存在时，返回 0 。
     * 
     * @param key
     * @return
     */
    public Long len(String key) {
        Jedis jedis = this.getJedis();
        Long value = 0L;
        try {
            value = jedis.hlen(key);

        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 返回哈希表 key 中的所有域。 一个包含哈希表中所有域的表。当 key 不存在时，返回一个空表。
     * 
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<String> key(String key) {

        Jedis jedis = this.getJedis();
        List<String> value = null;
        try {
            value = (List<String>) jedis.hkeys(key);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }

        this.releaseJedis(jedis);
        return value;

    }

    /**
     * 删除哈希表 key 中的一个或多个指定域，不存在的域(field)将被忽略。 被成功移除的域的数量，不包括被忽略的域。
     * 
     * @param key
     * @param fields
     */
    public Long del(String key, String... fields) {
        Jedis jedis = this.getJedis();
        Long value = 0L;
        try {
            value = jedis.hdel(key, fields);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 查看哈希表 key 中，给定域 field 是否存在 如果哈希表含有给定域，返回 1 。如果哈希表不含有给定域，或 key 不存在，返回 0
     * 
     * @param key
     * @param field
     */
    public boolean exists(String key, String field) {
        Jedis jedis = this.getJedis();
        boolean value = false;
        try {
            value = jedis.hexists(key, field);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 为哈希表 key 中的域 field 的值加上增量 increment 。 增量也可以为负数，相当于对给定域进行减法操作。 如果 key
     * 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。 如果域 field 不存在，那么在执行命令前，域的值被初始化为 0 。
     * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误。 本操作的值被限制在 64 位(bit)有符号数字表示之内。
     * 
     * @param key
     * @param field
     * @param increment
     * @return 执行 HINCRBY 命令之后，哈希表 key 中域 field 的值。
     */
    public Long incrby(String key, String field, Long increment) {
        Jedis jedis = this.getJedis();
        Long value = null;
        try {
            value = jedis.hincrBy(key, field, increment);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 为哈希表 key 中的域 field 加上浮点数增量 increment 。 如果哈希表中没有域 field ，那么 HINCRBYFLOAT
     * 会先将域 field 的值设为 0 ，然后再执行加法操作。 如果键 key 不存在，那么 HINCRBYFLOAT 会先创建一个哈希表，再创建域
     * field ，最后再执行加法操作。 当以下任意一个条件发生时，返回一个错误： 域 field 的值不是字符串类型(因为 redis
     * 中的数字和浮点数都以字符串的形式保存，所以它们都属于字符串类型） 域 field 当前的值或给定的增量 increment
     * 不能解释(parse)为双精度浮点数(double precision floating point number)
     * 
     * @param key
     * @param field
     * @param increment
     * @return 执行加法操作之后 field 域的值。
     */
    public Double incrbyfloat(String key, String field, Long increment) {
        Jedis jedis = this.getJedis();
        Double value = null;
        try {
            value = jedis.hincrByFloat(key, field, increment);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在。 若域 field 已经存在，该操作无效。 如果
     * key 不存在，一个新哈希表被创建并执行 HSETNX 命令。
     * 
     * @param key
     * @param field
     * @param value
     * @return 设置成功，返回 1。如果给定域已经存在且没有操作被执行，返回 0。
     */
    public Long setnx(String key, String field, String value) {
        Jedis jedis = this.getJedis();
        long result = 0;
        try {
            result = jedis.hsetnx(key, field, value);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return result;
    }

    /**
     * 返回哈希表 key 中所有域的值。
     * 
     * @param key
     * @return 一个包含哈希表中所有值的表。当 key 不存在时，返回一个空表。
     */
    public List<String> vals(String key) {
        Jedis jedis = this.getJedis();
        List<String> value = null;
        try {
            value = jedis.hvals(key);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 将哈希表 key 中的域 field 的值设为 value 。 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。 如果域
     * field 已经存在于哈希表中，旧值将被覆盖。
     * 
     * @param key
     * @param map
     */
    public void set(String key, Map<String, String> map) {
        Jedis jedis = this.getJedis();
        try {
            jedis.hmset(key, map);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
    }

    /**
     * 将哈希表 key 中的域 field 的值设为 value 。 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。 如果域
     * field 已经存在于哈希表中，旧值将被覆盖。
     * 
     * @param key
     * @param field
     * @param value
     */
    public void put(String key, String field, String value) {
        Jedis jedis = this.getJedis();
        try {
            jedis.hset(key, field, value);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
    }

    /**
     * 返回哈希表 key 中，一个或多个给定域的值。 如果给定的域不存在于哈希表，那么返回一个 nil 值。 因为不存在的 key
     * 被当作一个空哈希表来处理，所以对一个不存在 的 key进行 HMGET操作将返回一个只带有 nil 值的表。
     * 
     * @param key
     * @param fields
     * @return
     */
    public List<String> get(String key, String... fields) {
        Jedis jedis = this.getJedis();
        List<String> value = null;
        try {
            value = jedis.hmget(key, fields);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 返回哈希表key中，所有的域和值
     * 
     * @param key
     * @return
     */
    public Map<String, String> get(String key) {
        Jedis jedis = this.getJedis();
        Map<String, String> map = null;
        try {
            map = jedis.hgetAll(key);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return map;
    }

    /**
     * 返回哈希表 key 中给定域 field 的值。
     * 
     * @param key
     * @param field
     * @return给定域的值。当给定域不存在或是给定 key 不存在时，返回 nil
     */
    public String getMapElement(String key, String field) {
        Jedis jedis = this.getJedis();
        String value = null;
        try {
            value = jedis.hget(key, field);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        return value;
    }

}
