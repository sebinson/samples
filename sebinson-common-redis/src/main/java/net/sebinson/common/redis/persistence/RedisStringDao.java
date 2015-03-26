package net.sebinson.common.redis.persistence;

import java.util.List;

import net.sebinson.common.redis.log.RedisLog;
import redis.clients.jedis.Jedis;

public class RedisStringDao extends RedisDatabase {

    public RedisStringDao(String id) {
        super(id);
    }

    /**
     * 返回 key 所储存的字符串值的长度。 当 key 储存的不是字符串值时，返回一个错误。
     * 
     * @param key
     * @return
     */
    public Long strlen(String key) {
        Jedis jedis = this.getJedis();
        Long value = 0L;
        try {
            value = jedis.strlen(key);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始。 不存在的 key
     * 当作空白字符串处理。
     * 
     * @param key
     * @param offset
     * @param value
     * @return
     */
    public Long setrange(String key, long offset, String value) {
        Jedis jedis = this.getJedis();
        Long result = 0L;
        try {
            result = jedis.setrange(key, offset, value);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return result;
    }

    /**
     * 将 key 的值设为 value ，当且仅当 key 不存在。 若给定的 key 已经存在，则 SETNX 不做任何动作。
     * 
     * @param key
     * @param value
     * @return
     */
    public Long setnx(String key, String value) {
        Jedis jedis = this.getJedis();
        Long result = 0L;
        try {
            result = jedis.setnx(key, value);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return result;
    }

    /**
     * 对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。 位的设置或清除取决于 value 参数，可以是 0 也可以是 1 。 当
     * key 不存在时，自动生成一个新的字符串值。
     * 
     * @param key
     * @param offset
     * @param value
     * @return
     */
    public Boolean setbit(String key, long offset, Boolean value) {
        Jedis jedis = this.getJedis();
        boolean result = false;
        try {
            result = jedis.setbit(key, offset, value);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return result;
    }

    /**
     * 对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。 位的设置或清除取决于 value 参数，可以是 0 也可以是 1 。 当
     * key 不存在时，自动生成一个新的字符串值。
     * 
     * @param key
     * @param offset
     * @param value
     * @return
     */
    public Boolean setbit(String key, long offset, String value) {
        Jedis jedis = this.getJedis();
        boolean result = false;
        try {
            result = jedis.setbit(key, offset, value);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return result;
    }

    /**
     * 这个命令和 SETEX 命令相似，但它以毫秒为单位设置 key 的生存时间，而不是像 SETEX 命令那样，以秒为单位
     * 
     * @param key
     * @param milliseconds
     * @param value
     * @return
     */
    public String psetex(String key, int milliseconds, String value) {
        Jedis jedis = this.getJedis();
        String result = null;
        try {
            result = jedis.psetex(key, milliseconds, value);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return result;
    }

    /**
     * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在。 即使只有一个给定 key 已存在， MSETNX
     * 也会拒绝执行所有给定 key 的设置操作。
     * 
     * @param keysvalues
     * @return
     */
    public Long msetnx(String... keysvalues) {
        Jedis jedis = this.getJedis();
        Long value = 0L;
        try {
            jedis.msetnx(keysvalues);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 同时设置一个或多个 key-value 对。 如果某个给定 key 已经存在，那么 MSET 会用新值覆盖原来的旧值，如果这不是你所希望的效果，
     * 请考虑使用 MSETNX 命令：它只会在所有给定 key 都不存在的情况下进行设置操作。
     * 
     * @param keysvalues
     * @return
     */
    public String mset(String... keysvalues) {
        Jedis jedis = this.getJedis();
        String value = null;
        try {
            value = jedis.mset(keysvalues);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 返回所有(一个或多个)给定 key 的值。 如果给定的 key 里面，有某个 key 不存在，那么这个 key 返回特殊值 nil
     * 。因此，该命令永不失败。
     * 
     * @param keys
     * @return
     */
    public List<String> mget(String... keys) {
        Jedis jedis = this.getJedis();
        List<String> value = null;
        try {
            value = jedis.mget(keys);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 为 key 中所储存的值加上浮点数增量 increment 。 如果 key 不存在，那么 INCRBYFLOAT 会先将 key 的值设为 0
     * ，再执行加法操作。 如果命令执行成功，那么 key 的值会被更新为（执行加法之后的）新值，并且新值会以字符串的形式返回给调用者。
     * 
     * @param key
     * @param increment
     * @return
     */
    public Double incrbyfloat(String key, Double increment) {
        Double value = null;
        Jedis jedis = this.getJedis();
        try {
            value = jedis.incrByFloat(key, increment);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 返回 key 中字符串值的子字符串，字符串的截取范围由 start 和 end 两个偏移量决定(包括 start 和 end 在内)。
     * 负数偏移量表示从字符串最后开始计数， -1 表示最后一个字符， -2 表示倒数第二个，以此类推。
     * 
     * @param key
     * @param startOffset
     * @param endOffset
     * @return
     */
    public String getrange(String key, Long startOffset, Long endOffset) {
        String value = null;
        Jedis jedis = this.getJedis();
        try {
            value = jedis.getrange(key, startOffset, endOffset);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。 当 key 存在但不是字符串类型时，返回一个错误。
     * 
     * @param key
     * @param value
     * @return
     */
    public String getset(String key, String value) {
        String result = null;
        Jedis jedis = this.getJedis();
        try {
            result = jedis.getSet(key, value);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return result;
    }

    /**
     * 对 key 所储存的字符串值，获取指定偏移量上的位(bit)。 当 offset 比字符串值的长度大，或者 key 不存在时，返回 0 。
     * 
     * @param key
     * @param offset
     * @return
     */
    public Boolean getbit(String key, long offset) {
        Boolean value = false;
        Jedis jedis = this.getJedis();
        try {
            value = jedis.getbit(key, offset);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 计算给定字符串中，被设置为 1 的比特位的数量。 一般情况下，给定的整个字符串都会被进行计数，通过指定额外的 start 或 end
     * 参数，可以让计数只在特定的位上进行。 start 和 end 参数的设置和 GETRANGE 命令类似，都可以使用负数值：比如 -1
     * 表示最后一个位，而 -2 表示倒数第二个位，以此类推。 不存在的 key 被当成是空字符串来处理，因此对一个不存在的 key 进行
     * BITCOUNT 操作，结果为 0 。
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long bitcount(String key, long start, long end) {
        Long value = 0L;
        Jedis jedis = this.getJedis();
        try {
            value = jedis.bitcount(key, start, end);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 计算给定字符串中，被设置为 1 的比特位的数量。 一般情况下，给定的整个字符串都会被进行计数，通过指定额外的 start 或 end
     * 参数，可以让计数只在特定的位上进行。 start 和 end 参数的设置和 GETRANGE 命令类似，都可以使用负数值：比如 -1
     * 表示最后一个位，而 -2 表示倒数第二个位，以此类推。 不存在的 key 被当成是空字符串来处理，因此对一个不存在的 key 进行
     * BITCOUNT 操作，结果为 0 。
     * 
     * @param key
     * @return
     */
    public Long bitcount(String key) {
        Long value = 0L;
        Jedis jedis = this.getJedis();
        try {
            value = jedis.bitcount(key);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 将字符串值 value 关联到 key 。 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
     * 
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        Jedis jedis = this.getJedis();
        try {
            jedis.set(key, value);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
    }

    /**
     * 添加有生命周期的元素
     * 
     * @param key
     * @param seconds
     *            生命周期 秒为单位
     * @param value
     */
    public void set(String key, int seconds, String value) {
        Jedis jedis = this.getJedis();
        try {
            jedis.setex(key, seconds, value);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
    }

    /**
     * 获取key的值
     * 
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = this.getJedis();
        String value = null;
        try {
            value = jedis.get(key);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 如果key已经存在并且是一个字符串，将value追加到key原来的值之后
     * 
     * @param key
     * @param value
     */
    public void append(String key, String value) {
        Jedis jedis = this.getJedis();
        try {
            jedis.append(key, value);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
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
