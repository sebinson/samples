package net.sebinson.common.cache.redis;

import net.sebinson.common.cache.AbstractCacheManager;
import net.sebinson.common.cache.Cache;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisCacheManager extends AbstractCacheManager {

	private JedisPool jedisPool;

	public RedisCacheManager() {
		this("localhost", 6379, 2000);
	}

	public RedisCacheManager(String host, int port, int timeout) {
		this(new JedisPoolConfig(), host, port, timeout);
	}

	public RedisCacheManager(JedisPoolConfig poolConfig, String host, int port,
			int timeout) {
		jedisPool = new JedisPool(poolConfig, host, port, timeout);
	}

	public RedisCacheManager(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	@Override
	public Cache getCache(String name) {

		if (name == null || name.length() == 0) {
			throw new IllegalArgumentException("error name");
		}

		Cache cache = super.getCache(name);

		if (cache == null) {
			addCache(name, new RedisCache(name, jedisPool));
			cache = super.getCache(name); // potentially decorated
		}

		return cache;
	}

}
