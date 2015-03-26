package net.sebinson.common.redis.persistence;

import java.util.List;
import java.util.Set;

import net.sebinson.common.redis.log.RedisLog;
import redis.clients.jedis.Jedis;

public class RedisSetDao extends RedisDatabase {

    public RedisSetDao(String id) {
        super(id);
    }

    /**
     * 这个命令类似于 SUNION 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集。 如果 destination
     * 已经存在，则将其覆盖。 destination 可以是 key 本身。
     * 
     * @param dstkey
     * @param keys
     * @return
     */
    public Long sunionstore(String dstkey, String... keys) {
        Jedis jedis = this.getJedis();
        Long value = null;
        try {
            value = jedis.sunionstore(dstkey, keys);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 返回一个集合的全部成员，该集合是所有给定集合的并集。 不存在的 key 被视为空集。
     * 
     * @param keys
     * @return
     */
    public Set<String> sunion(String... keys) {
        Jedis jedis = this.getJedis();
        Set<String> value = null;
        try {
            value = jedis.sunion(keys);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 如果命令执行时，只提供了 key 参数，那么返回集合中的一个随机元素。 从 Redis 2.6 版本开始， SRANDMEMBER 命令接受可选的
     * count 参数：
     * 
     * @param key
     * @param count
     * @return
     */
    public List<String> srandmember(String key, int count) {
        Jedis jedis = this.getJedis();
        List<String> value = null;
        try {
            value = jedis.srandmember(key, count);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 如果命令执行时，只提供了 key 参数，那么返回集合中的一个随机元素。 从 Redis 2.6 版本开始， SRANDMEMBER 命令接受可选的
     * count 参数：
     * 
     * @param key
     * @return
     */
    public String srandmember(String key) {
        Jedis jedis = this.getJedis();
        String value = null;
        try {
            value = jedis.srandmember(key);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 移除并返回集合中的一个随机元素。 如果只想获取一个随机元素，但不想该元素从集合中被移除的话，可以使用 SRANDMEMBER 命令。
     * 
     * @param key
     * @return
     */
    public String spop(String key) {
        Jedis jedis = this.getJedis();
        String value = null;
        try {
            value = jedis.spop(key);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 将 member 元素从 source 集合移动到 destination 集合。
     * 
     * @param srckey
     * @param dstkey
     * @param member
     * @return
     */

    public Long smove(String srckey, String dstkey, String member) {
        Jedis jedis = this.getJedis();
        Long value = 0L;
        try {
            value = jedis.smove(srckey, dstkey, member);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 判断 member 元素是否集合 key 的成员。
     * 
     * @param key
     * @param member
     * @return
     */
    public Boolean sismember(String key, String member) {
        Jedis jedis = this.getJedis();
        Boolean value = false;
        try {
            value = jedis.sismember(key, member);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 这个命令类似于 SINTER 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集。 如果 destination
     * 集合已经存在，则将其覆盖。 destintion 可以是 key 本身。
     * 
     * @param dstkey
     * @param keys
     * @return
     */
    public Long sinterstore(String dstkey, String... keys) {
        Jedis jedis = this.getJedis();
        Long value = null;
        try {
            value = jedis.sinterstore(dstkey, keys);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 返回一个集合的全部成员，该集合是所有给定集合的交集。 不存在的 key 被视为空集 当给定集合当中有一个空集时，结果也为空集(根据集合运算定律)。
     * 
     * @param keys
     * @return
     */
    public Set<String> sinter(String... keys) {
        Jedis jedis = this.getJedis();
        Set<String> value = null;
        try {
            value = jedis.sinter(keys);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 这个命令的作用和 SDIFF 类似，但它将结果保存到 destination 集合，而不是简单地返回结果集。 如果 destination
     * 集合已经存在，则将其覆盖。 destination 可以是 key 本身。
     * 
     * @param dstkey
     * @param keys
     * @return
     */
    public Long sdiffstore(String dstkey, String... keys) {
        Jedis jedis = this.getJedis();
        Long value = null;
        try {
            value = jedis.sdiffstore(dstkey, keys);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 返回一个集合的全部成员，该集合是所有给定集合之间的差集。 不存在的 key 被视为空集。
     * 
     * @param keys
     * @return
     */
    public Set<String> sdiff(String... keys) {
        Jedis jedis = this.getJedis();
        Set<String> set = null;
        try {
            set = jedis.sdiff(keys);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return set;
    }

    /**
     * 向set中添加元素
     * 
     * @param key
     * @param members
     */
    public void put(String key, String... members) {
        Jedis jedis = this.getJedis();
        try {
            jedis.sadd(key, members);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
    }

    /**
     * 返回set中，所有的域和值
     * 
     * @param key
     * @return
     */
    public Set<String> get(String key) {
        Jedis jedis = this.getJedis();
        Set<String> set = null;
        try {
            set = jedis.smembers(key);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return set;
    }

    /**
     * 删除名称为key的set中的元素member
     * 
     * @param key
     *            List别名
     * @param field
     *            键
     */
    public void delElement(String key, String... members) {
        Jedis jedis = this.getJedis();
        try {
            jedis.srem(key, members);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
    }

    /**
     * 返回集合的元素个数
     * 
     * @param key
     * @return
     */
    public long countSet(String key) {
        Jedis jedis = this.getJedis();
        Long scard = null;
        try {
            scard = jedis.scard(key);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return scard;
    }
}
