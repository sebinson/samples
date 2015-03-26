package net.sample.base.redis.persistence;

import java.util.Iterator;
import java.util.List;

import net.sample.base.redis.innerTools.ObjectSerializer;
import net.sample.base.redis.log.RedisLog;

import redis.clients.jedis.Jedis;

public class RedisListDao extends RedisDatabase {

    public RedisListDao(String id) {
        super(id);
    }

    /**
     * 将值 value 插入到列表 key 的表尾，当且仅当 key 存在并且是一个列表。 和 RPUSH 命令相反，当 key 不存在时，
     * RPUSHX 命令什么也不做。
     * 
     * @param key
     * @param strings
     * @return
     */
    public Long rpushx(String key, String... strings) {
        Jedis jedis = this.getJedis();
        Long value = null;
        try {
            value = jedis.rpushx(key, strings);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }

        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 将列表 source 中的最后一个元素(尾元素)弹出，并返回给客户端。 将 source 弹出的元素插入到列表 destination ，作为
     * destination 列表的的头元素。
     * 
     * @param srckey
     * @param dstkey
     * @return
     */
    public String rpoplpush(String srckey, String dstkey) {
        Jedis jedis = this.getJedis();
        String result = null;
        try {
            result = jedis.rpoplpush(srckey, dstkey);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }

        this.releaseJedis(jedis);
        return result;
    }

    /**
     * 移除并返回列表 key 的尾元素。
     * 
     * @param key
     * @return
     */
    public String rpop(String key) {
        Jedis jedis = this.getJedis();
        String result = null;
        try {
            result = jedis.rpop(key);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }

        this.releaseJedis(jedis);
        return result;
    }

    /**
     * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。 举个例子，执行命令 LTRIM list
     * 0 2 ，表示只保留列表 list 的前三个元素，其余元素全部删除。 下标(index)参数 start 和 stop 都以 0
     * 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。 你也可以使用负数下标，以 -1 表示列表的最后一个元素，
     * -2 表示列表的倒数第二个元素，以此类推。 当 key 不是列表类型时，返回一个错误。
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public String trim(String key, long start, long end) {
        Jedis jedis = this.getJedis();
        String result = null;
        try {
            result = jedis.ltrim(key, start, end);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }

        this.releaseJedis(jedis);
        return result;
    }

    /**
     * 将列表 key 下标为 index 的元素的值设置为 value 。 当 index 参数超出范围，或对一个空列表( key 不存在)进行
     * LSET 时，返回一个错误。 关于列表下标的更多信息，请参考 LINDEX 命令。
     * 
     * @param key
     * @param index
     * @param value
     * @return
     */
    public String set(String key, long index, String value) {
        Jedis jedis = this.getJedis();
        String result = null;
        try {
            result = jedis.lset(key, index, value);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }

        this.releaseJedis(jedis);
        return result;
    }

    /**
     * 根据参数 count 的值，移除列表中与参数 value 相等的元素。 count 的值可以是以下几种： count > 0 :
     * 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。 count < 0 : 从表尾开始向表头搜索，移除与 value
     * 相等的元素，数量为 count 的绝对值。 count = 0 : 移除表中所有与 value 相等的值。
     * 
     * @param key
     * @param count
     * @param value
     * @return
     */
    public Long rem(String key, long count, String value) {
        Jedis jedis = this.getJedis();
        Long result = null;
        try {
            result = jedis.lrem(key, count, value);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }

        this.releaseJedis(jedis);
        return result;
    }

    /**
     * 将值 value 插入到列表 key 的表头，当且仅当 key 存在并且是一个列表。 和 LPUSH 命令相反，当 key 不存在时，
     * LPUSHX 命令什么也不做。
     * 
     * @param key
     * @param strings
     * @return
     */
    public Long pushx(String key, String... strings) {
        Jedis jedis = this.getJedis();
        Long value = null;
        try {
            value = jedis.lpushx(key, strings);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }

        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表头 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头：
     * 比如说，对空列表 mylist 执行命令 LPUSH mylist a b c ，列表的值将是 c b a ，这等同于原子性地执行 LPUSH
     * mylist a 、 LPUSH mylist b 和 LPUSH mylist c 三个命令。 如果 key 不存在，一个空列表会被创建并执行
     * LPUSH 操作。 当 key 存在但不是列表类型时，返回一个错误。
     * 
     * @param key
     * @param strings
     * @return
     */
    public Long push(String key, String... strings) {
        Jedis jedis = this.getJedis();
        Long value = null;
        try {
            value = jedis.lpush(key, strings);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }

        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 返回列表 key 中，下标为 index 的元素。 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0
     * 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2
     * 表示列表的倒数第二个元素，以此类推。 如果 key 不是列表类型，返回一个错误。
     * 
     * @param key
     * @param index
     * @return
     */
    public String index(String key, long index) {
        Jedis jedis = this.getJedis();
        String value = null;
        try {
            value = jedis.lindex(key, index);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }

        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 移除并返回列表 key 的头元素。
     * 
     * @param strings
     * @return
     */
    public List<String> lpop(String... strings) {
        Jedis jedis = this.getJedis();
        List<String> value = null;
        try {
            value = jedis.blpop(strings);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }

        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 移除并返回列表 key 的头元素。
     * 
     * @param timeout
     * @param strings
     * @return
     */
    public List<String> lpop(int timeout, String... strings) {
        Jedis jedis = this.getJedis();
        List<String> value = null;
        try {
            value = jedis.blpop(timeout, strings);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }

        this.releaseJedis(jedis);
        return value;
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。 如果有多个 value 值，那么各个 value
     * 值按从左到右的顺序依次插入到表尾：比如对一个空列表 mylist 执行 RPUSH mylist a b c ，得出的结果列表为 a b c
     * ，等同于执行命令 RPUSH mylist a 、 RPUSH mylist b 、 RPUSH mylist c 。 如果 key
     * 不存在，一个空列表会被创建并执行 RPUSH 操作。 当 key 存在但不是列表类型时，返回一个错误。
     * 
     * @param key
     * @param list
     */
    public void set(String key, List<String> list) {
        Jedis jedis = this.getJedis();
        try {
            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                jedis.rpush(key, iterator.next());
            }
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }

        this.releaseJedis(jedis);
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。 如果有多个 value 值，那么各个 value
     * 值按从左到右的顺序依次插入到表尾：比如对一个空列表 mylist 执行 RPUSH mylist a b c ，得出的结果列表为 a b c
     * ，等同于执行命令 RPUSH mylist a 、 RPUSH mylist b 、 RPUSH mylist c 。 如果 key
     * 不存在，一个空列表会被创建并执行 RPUSH 操作。 当 key 存在但不是列表类型时，返回一个错误。
     * 
     * @param key
     * @param strings
     */
    public void put(String key, String... strings) {
        Jedis jedis = this.getJedis();
        try {
            jedis.rpush(key, strings);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
    }

    /**
     * 获取整个list
     * 
     * @param key
     * @return
     */
    public List<String> get(String key) {
        Jedis jedis = this.getJedis();
        List<String> list = null;
        try {
            list = jedis.lrange(key, 0, -1);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return list;
    }

    /**
     * 获取List中指定范围的元素 LRANGE key start stop返回列表key中指定区间内的元素，区间以偏移量start和stop指定。
     * 下标(index)参数start和stop都以0为底，也就是说，以0表示列表的第一个元素，以1表示列表的第二个元素，以此类推。
     * 也可以使用负数下标，以-1表示列表的最后一个元素，-2表示列表的倒数第二个元素，以此类推。
     * 
     * @param key
     *            List别名
     * @param start
     *            开始下标
     * @param end
     *            结束下标
     * @return
     */
    public List<String> getRange(String key, long start, long end) {
        Jedis jedis = this.getJedis();
        List<String> list = null;
        try {
            list = jedis.lrange(key, start, end);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return list;
    }

    /**
     * 返回列表 key 的长度。 如果 key 不存在，则 key 被解释为一个空列表，返回 0 . 如果 key 不是列表类型，返回一个错误。
     * 
     * @param key
     * @return
     */
    public long len(String key) {
        Jedis jedis = this.getJedis();
        long l = 0;
        try {
            l = jedis.llen(key);
        } catch (Exception e) {
            l = 0;
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return l;
    }

    /**
     * 移除并返回列表 key 的头元素。
     * 
     * @param key
     * @return
     */
    public String pop(String key) {
        Jedis jedis = this.getJedis();
        String pop = null;
        try {
            pop = jedis.lpop(key);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
        }
        this.releaseJedis(jedis);
        return pop;
    }

    /**
     * 尾部添加对象元素
     * 
     * @param key
     * @param value
     * @return
     */
    public long rpushObject(String key, Object value) {
        Jedis jedis = this.getJedis();
        try {
            return jedis.rpush(key.getBytes(), ObjectSerializer.serialize(value));
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
            return 0L;
        } finally {
            if (jedis != null) {
                this.releaseJedis(jedis);
            }
        }
    }

    /**
     * 头部添加对象元素
     * 
     * @param key
     * @param value
     * @return
     */
    public long lpushObject(String key, Object value) {
        Jedis jedis = this.getJedis();
        try {
            return jedis.lpush(key.getBytes(), ObjectSerializer.serialize(value));
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
            return 0L;
        } finally {
            if (jedis != null) {
                this.releaseJedis(jedis);
            }
        }
    }

    /**
     * 尾部删除对象元素
     * 
     * @param key
     * @return
     */
    public Object rpopObject(String key) {

        Jedis jedis = this.getJedis();
        try {
            byte[] byteObj = jedis.rpop(key.getBytes());
            if (byteObj == null) {
                return null;
            }
            return ObjectSerializer.unserialize(byteObj);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
            return null;
        } finally {
            if (jedis != null) {
                this.releaseJedis(jedis);
            }
        }
    }

    /**
     * 头部删除对象元素
     * 
     * @param key
     * @return
     */
    public Object lpopObject(String key) {

        Jedis jedis = this.getJedis();
        try {
            byte[] byteObj = jedis.lpop(key.getBytes());
            if (byteObj == null) {
                return null;
            }
            return ObjectSerializer.unserialize(byteObj);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
            return null;
        } finally {
            if (jedis != null) {
                this.releaseJedis(jedis);
            }
        }
    }

    /**
     * 尾部删除对象元素
     * 
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T rpopObjectGenerics(String key, Class<T> clazz) {

        Jedis jedis = this.getJedis();
        try {
            byte[] byteObj = jedis.rpop(key.getBytes());
            if (byteObj == null) {
                return null;
            }
            return (T) ObjectSerializer.unserialize(byteObj);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
            return null;
        } finally {
            if (jedis != null) {
                this.releaseJedis(jedis);
            }
        }
    }

    /**
     * 头部删除对象元素
     * 
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T lpopObjectGenerics(String key, Class<T> clazz) {

        Jedis jedis = this.getJedis();
        try {
            byte[] byteObj = jedis.lpop(key.getBytes());
            if (byteObj == null) {
                return null;
            }
            return (T) ObjectSerializer.unserialize(byteObj);
        } catch (Exception e) {
            RedisLog.error(this.getRedisError(), e);
            return null;
        } finally {
            if (jedis != null) {
                this.releaseJedis(jedis);
            }
        }
    }

}
