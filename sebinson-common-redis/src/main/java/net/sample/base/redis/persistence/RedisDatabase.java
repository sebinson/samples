package net.sample.base.redis.persistence;

import java.util.List;
import java.util.Set;

import net.sample.base.redis.innerTools.RedisPoolHelper;
import net.sample.base.redis.log.RedisLog;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;

/**
 * 
 * {@link http://redis.readthedocs.org/en/latest}
 * @author C
 *
 */
public class RedisDatabase {
    private JedisPool pool;

    /**
     * @param id
     *            <p>
     *            数据库ID</br> 例：redis.sebinson.com:6369:2
     */
    public RedisDatabase(String id) {
        this.pool = RedisPoolHelper.getPool(id);
    }

    /**
     * 获取jedis 常用于操作较多的地方 用完之后，需要自行关闭
     * 
     * @return
     */
    public Jedis getJedis() {
        return this.pool.getResource();
    }

    /**
     * 释放jedis
     * 
     * @param jedis
     */
    public void releaseJedis(Jedis jedis) {
        RedisPoolHelper.release(jedis, this.pool);
    }

    public String getRedisError() {
        return "Redis操作异常【" + Thread.currentThread().getStackTrace()[2].getMethodName() + "】";
    }

    /**
     * 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)。
     * 
     * @param key
     * @return
     */
    public Long ttl(String key) {
        Jedis jedis = this.getJedis();
        Long value = null;
        try {
            value = jedis.ttl(key);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 返回或保存给定列表、集合、有序集合 key 中经过排序的元素。 排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较。
     * 
     * @param dstkey
     * @param sortingParameters
     * @return
     */
    public List<String> sort(String dstkey, SortingParams sortingParameters) {
        Jedis jedis = this.getJedis();
        List<String> value = null;
        try {
            value = jedis.sort(dstkey, sortingParameters);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 返回或保存给定列表、集合、有序集合 key 中经过排序的元素。 排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较。
     * 
     * @param key
     * @param sortingParameters
     * @param dstkey
     * @return
     */
    public Long sort(String key, SortingParams sortingParameters, String dstkey) {
        Jedis jedis = this.getJedis();
        Long value = 0L;
        try {
            jedis.sort(key, sortingParameters, dstkey);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 返回或保存给定列表、集合、有序集合 key 中经过排序的元素。 排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较。
     * 
     * @param key
     * @param dstkey
     * @return
     */
    public Long sort(String key, String dstkey) {
        Jedis jedis = this.getJedis();
        Long value = 0L;
        try {
            value = jedis.sort(key, dstkey);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 当且仅当 newkey 不存在时，将 key 改名为 newkey 。 当 key 不存在时，返回一个错误。
     * 
     * @param oldkey
     * @param newkey
     * @return
     */
    public Long renamenx(String oldkey, String newkey) {
        Jedis jedis = this.getJedis();
        Long value = 0L;
        try {
            value = jedis.renamenx(oldkey, newkey);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 将 key 改名为 newkey 。 当 key 和 newkey 相同，或者 key 不存在时，返回一个错误。 当 newkey 已经存在时，
     * RENAME 命令将覆盖旧值。
     * 
     * @param oldkey
     * @param newkey
     * @return
     */
    public String rename(String oldkey, String newkey) {
        Jedis jedis = this.getJedis();
        String value = null;
        try {
            value = jedis.rename(oldkey, newkey);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 从当前数据库中随机返回(不删除)一个 key 。
     * 
     * @return
     */
    public String randomkey() {
        Jedis jedis = this.getJedis();
        String value = null;
        try {
            value = jedis.randomKey();
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 这个命令类似于 TTL 命令，但它以毫秒为单位返回 key 的剩余生存时间，而不是像 TTL 命令那样，以秒为单位。
     * 
     * @param key
     * @return
     */
    public Long pttl(String key) {
        Jedis jedis = this.getJedis();
        Long value = 0L;
        try {
            value = jedis.pttl(key);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 这个命令和 EXPIREAT 命令类似，但它以毫秒为单位设置 key 的过期 unix 时间戳，而不是像 EXPIREAT 那样，以秒为单位。
     * 
     * @param key
     * @param millisecondsTimestamp
     * @return
     */
    public Long pexpireAt(String key, long millisecondsTimestamp) {
        Jedis jedis = this.getJedis();
        Long value = 0L;
        try {
            value = jedis.pexpireAt(key, millisecondsTimestamp);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 这个命令和 EXPIRE 命令的作用类似，但是它以毫秒为单位设置 key 的生存时间，而不像 EXPIRE 命令那样，以秒为单位
     * 
     * @param key
     * @param milliseconds
     * @return
     */
    public Long pexpire(String key, long milliseconds) {
        Jedis jedis = this.getJedis();
        Long value = 0L;
        try {
            value = jedis.pexpire(key, milliseconds);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 移除给定 key 的生存时间，将这个 key 从『易失的』(带生存时间 key )转换成『持久的』(一个不带生存时间、永不过期的 key )。
     * 
     * @param key
     * @return
     */

    public Long persist(String key) {
        Jedis jedis = this.getJedis();
        Long value = 0L;
        try {
            value = jedis.persist(key);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 将当前数据库的 key 移动到给定的数据库 db 当中。 如果当前数据库(源数据库)和给定数据库(目标数据库)有相同名字的给定 key ， 或者
     * key 不存在于当前数据库，那么 MOVE 没有任何效果。
     * 
     * @param key
     * @param dbIndex
     * @return
     */
    public Long move(String key, int dbIndex) {
        Jedis jedis = this.getJedis();
        Long value = 0L;
        try {
            value = jedis.move(key, dbIndex);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 将 key 原子性地从当前实例传送到目标实例的指定数据库上，一旦传送成功， key 保证会出现在目标实例上，而当前实例上的 key 会被删除。
     * 
     * @param host
     * @param port
     * @param key
     * @param destinationDb
     * @param timeout
     * @return
     */
    public String migrate(String host, int port, String key, int destinationDb, int timeout) {
        Jedis jedis = this.getJedis();
        String value = null;
        try {
            value = jedis.migrate(host, port, key, destinationDb, timeout);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * EXPIREAT 的作用和 EXPIRE 类似，都用于为 key 设置生存时间。 不同在于 EXPIREAT 命令接受的时间参数是 UNIX
     * 时间戳(unix timestamp)。
     * 
     * @param key
     * @param unixTime
     * @return
     */
    public Long expireat(String key, Long unixTime) {
        Jedis jedis = this.getJedis();
        long count = 0L;
        try {
            jedis.expireAt(key, unixTime);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return count;
    }

    public Long count() {
        Jedis jedis = this.getJedis();
        long count = 0L;
        try {
            count = jedis.dbSize();
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return count;
    }

    public Set<String> keys(String pattern) {
        Jedis jedis = this.getJedis();
        Set<String> keys = null;
        try {
            keys = jedis.keys(pattern);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return keys;
    }

    /**
     * 检查key是否存在
     * 
     * @param key
     * @return
     */
    public boolean exists(String key) {
        Jedis jedis = this.getJedis();
        boolean bool = false;
        try {
            bool = jedis.exists(key);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return bool;
    }

    /**
     * 返回key值的类型 none(key不存在),string(字符串),list(列表),set(集合),zset(有序集),hash(哈希表)
     * 
     * @param key
     * @return
     */
    public String type(String key) {
        Jedis jedis = this.getJedis();
        String type = null;
        try {
            type = jedis.type(key);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return type;
    }

    /**
     * 扫描key
     * 
     * @param cursor
     * @param params
     * @return
     */
    public ScanResult<String> scan(String cursor, ScanParams params) {
        Jedis jedis = this.getJedis();
        ScanResult<String> result = null;
        try {
            result = jedis.scan(cursor, params);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return result;
    }

    /**
     * 清空数据库
     */
    public void flushDB() {
        Jedis jedis = this.getJedis();
        try {
            jedis.flushDB();
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
    }

    /**
     * 为给定key设置生命周期
     * 
     * @param key
     * @param seconds
     *            生命周期 秒为单位
     * @see redis.clients.jedis.Jedis#expire(String, int)
     */
    public void setExpire(String key, int seconds) {
        Jedis jedis = this.getJedis();
        try {
            jedis.expire(key, seconds);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
    }

    /**
     * @see redis.clients.jedis.Jedis#del(String[])
     * @param keys
     */
    public void del(String... keys) {
        Jedis jedis = this.getJedis();
        try {
            jedis.del(keys);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
    }
}
