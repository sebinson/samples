package net.sample.base.redis.persistence;

import java.util.Map;
import java.util.Set;

import net.sample.base.redis.log.RedisLog;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ZParams;

public class RedisSortedSetDao extends RedisDatabase {

    public RedisSortedSetDao(String id) {
        super(id);
    }

    /**
     * 算给定的一个或多个有序集的交集，其中给定 key 的数量必须以 numkeys 参数指定，并将该交集(结果集)储存到 destination 。
     * 默认情况下，结果集中某个成员的 score 值是所有给定集下该成员 score 值之和.
     * 
     * @param dstkey
     * @param sets
     * @return
     */
    public Long zinterstore(String dstkey, String... sets) {
        Jedis jedis = this.getJedis();
        Long value = null;
        try {
            value = jedis.zinterstore(dstkey, sets);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 算给定的一个或多个有序集的交集，其中给定 key 的数量必须以 numkeys 参数指定，并将该交集(结果集)储存到 destination 。
     * 默认情况下，结果集中某个成员的 score 值是所有给定集下该成员 score 值之和.
     * 
     * @param dstkey
     * @param params
     * @param sets
     * @return
     */
    public Long zinterstore(String dstkey, ZParams params, String... sets) {
        Jedis jedis = this.getJedis();
        Long value = null;
        try {
            value = jedis.zinterstore(dstkey, params, sets);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 计算给定的一个或多个有序集的并集，其中给定 key 的数量必须以 numkeys 参数指定，并将该并集(结果集)储存到 destination 。
     * 默认情况下，结果集中某个成员的 score 值是所有给定集下该成员 score 值之 和 。
     * 
     * @param dstkey
     * @param params
     * @param sets
     * @return
     */
    public Long zunionstore(String dstkey, ZParams params, String... sets) {
        Jedis jedis = this.getJedis();
        Long value = null;
        try {
            value = jedis.zunionstore(dstkey, params, sets);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 计算给定的一个或多个有序集的并集，其中给定 key 的数量必须以 numkeys 参数指定，并将该并集(结果集)储存到 destination 。
     * 默认情况下，结果集中某个成员的 score 值是所有给定集下该成员 score 值之 和 。
     * 
     * @param dstkey
     * @param sets
     * @return
     */
    public Long zunionstore(String dstkey, String... sets) {
        Jedis jedis = this.getJedis();
        Long value = null;
        try {
            value = jedis.zunionstore(dstkey, sets);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序。 排名以 0 为底，也就是说， score
     * 值最大的成员排名为 0 。
     * 
     * @param key
     * @param member
     * @return
     */
    public Long zrevrank(String key, String member) {
        Jedis jedis = this.getJedis();
        Long value = null;
        try {
            value = jedis.zrevrank(key, member);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。有序集成员按 score
     * 值递减(从大到小)的次序排列。
     * 
     * @param key
     * @param max
     * @param min
     * @param offset
     * @param count
     * @return
     */
    public Set<String> zrevrangebyscore(String key, String max, String min, int offset, int count) {
        Jedis jedis = this.getJedis();
        Set<String> value = null;
        try {
            value = jedis.zrevrangeByScore(key, max, min, offset, count);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。有序集成员按 score
     * 值递减(从大到小)的次序排列。
     * 
     * @param key
     * @param max
     * @param min
     * @param offset
     * @param count
     * @return
     */
    public Set<String> zrevrangebyscore(String key, Double max, Double min, int offset, int count) {
        Jedis jedis = this.getJedis();
        Set<String> value = null;
        try {
            value = jedis.zrevrangeByScore(key, max, min, offset, count);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。有序集成员按 score
     * 值递减(从大到小)的次序排列。
     * 
     * @param key
     * @param max
     * @param min
     * @return
     */
    public Set<String> zrevrangebyscore(String key, Double max, Double min) {
        Jedis jedis = this.getJedis();
        Set<String> value = null;
        try {
            value = jedis.zrevrangeByScore(key, max, min);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。有序集成员按 score
     * 值递减(从大到小)的次序排列。
     * 
     * @param key
     * @param max
     * @param min
     * @return
     */
    public Set<String> zrevrangebyscore(String key, String max, String min) {
        Jedis jedis = this.getJedis();
        Set<String> value = null;
        try {
            value = jedis.zrevrangeByScore(key, max, min);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zremrangebyscore(String key, String start, String end) {
        Jedis jedis = this.getJedis();
        Long value = null;
        try {
            value = jedis.zremrangeByScore(key, start, end);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zremrangebyscore(String key, Double start, Double end) {
        Jedis jedis = this.getJedis();
        Long value = null;
        try {
            value = jedis.zremrangeByScore(key, start, end);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 移除有序集 key 中，指定排名(rank)区间内的所有成员。 区间分别以下标参数 start 和 stop 指出，包含 start 和 stop
     * 在内。
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zremrangebyrank(String key, Long start, Long end) {
        Jedis jedis = this.getJedis();
        Long value = null;
        try {
            value = jedis.zremrangeByRank(key, start, end);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列。 排名以 0 为底，也就是说，
     * score 值最小的成员排名为 0 。
     * 
     * @param key
     * @param member
     * @return
     */
    public Long zrank(String key, String member) {
        Jedis jedis = this.getJedis();
        Long value = null;
        try {
            value = jedis.zrank(key, member);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 向有序集合中添加元素
     * 
     * @param key
     *            键
     * @param score
     *            分数
     * @param value
     *            元素
     */
    public void put(String key, double score, String value) {
        Jedis jedis = this.getJedis();
        try {
            jedis.zadd(key, score, value);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
    }

    /**
     * 向有序集合中添加元素
     * 
     * @param key
     *            键
     * @param score
     *            分数
     * @param value
     *            元素
     */
    public void put(String key, Map<String, Double> scoreMembers) {
        Jedis jedis = this.getJedis();
        try {
            jedis.zadd(key, scoreMembers);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
    }

    /**
     * 返回有序集 key 中，成员 member 的 score 值。 如果 member 元素不是有序集 key 的成员，或 key 不存在，返回
     * nil 。
     * 
     * @param key
     * @param member
     * @return
     */
    public Double getScore(String key, String member) {
        Jedis jedis = this.getJedis();
        Double value = null;
        try {
            value = jedis.zscore(key, member);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        if (value != null) {
            return value;
        } else {
            return 0.0;
        }
    }

    /**
     * 返回有序集 key 中，指定区间内的成员。 其中成员的位置按 score 值递增(从小到大)来排序。 具有相同 score
     * 值的成员按字典序(lexicographical order )来排列。
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> get(String key, long start, long end) {
        Jedis jedis = this.getJedis();
        Set<String> value = null;
        try {
            value = jedis.zrange(key, start, end);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 返回有序集 key 中，指定区间内的成员。 其中成员的位置按 score 值递减(从大到小)来排列。 具有相同 score
     * 值的成员按字典序的逆序(reverse lexicographical order)排列。
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> getDesc(String key, long start, long end) {
        Jedis jedis = this.getJedis();
        Set<String> value = null;
        try {
            value = jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 返回集合在score指定区间内的元素
     * 
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<String> zrangeByScore(String key, long min, long max) {
        Jedis jedis = this.getJedis();
        Set<String> value = null;
        try {
            value = jedis.zrangeByScore(key, min, max);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 返回集合内元素的个数
     * 
     * @param key
     * @return
     */
    public long zcard(String key) {
        Jedis jedis = this.getJedis();
        long value = 0;
        try {
            value = jedis.zcard(key);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 删除score在指定区间内的元素
     * 
     * @param key
     * @param start
     * @param end
     */
    @SuppressWarnings("unused")
    public void zremrangeByScore(String key, double start, double end) {
        Jedis jedis = this.getJedis();
        Set<String> value = null;
        try {
            jedis.zremrangeByScore(key, start, end);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
    }

    /**
     * 删除set内单个元素
     * 
     * @param key
     * @param members
     */
    public void delElement(String key, String... members) {
        Jedis jedis = this.getJedis();
        try {
            jedis.zrem(key, members);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
    }

    /**
     * 编辑有序集合value元素的分数
     * 
     * @param key
     * @param score
     * @param value
     */
    public void edit(String key, double score, String value) {
        Jedis jedis = this.getJedis();
        try {
            jedis.zincrby(key, score, value);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
    }

    /**
     * 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。
     * 
     * @param key
     * @param min
     * @param max
     * @return
     */
    public long zcount(String key, double min, double max) {
        Jedis jedis = this.getJedis();
        long count = 0;
        try {
            count = jedis.zcount(key, min, max);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return count;
    }
}
