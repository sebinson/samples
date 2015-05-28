package net.sebinson.sample.demos.redis.pubsub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Program {

    private static final String CHANNEL_NAME = "CommonChannel";

    public static void main(String[] args) {

        JedisPoolConfig poolConfig = new JedisPoolConfig();

        JedisPool jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6349, 0);

        final Jedis subscriberJedis = jedisPool.getResource();

        final Subscriber subscriber = new Subscriber();

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    System.out.println("Subscribing to \"commonChannel\". This thread will be blocked.");
                    subscriberJedis.subscribe(subscriber, CHANNEL_NAME);
                    System.out.println("Subscription ended.");
                } catch (Exception e) {
                    System.out.println("Subscribing failed.");
                }
            }

        }).start();

        Jedis publisherJedis = jedisPool.getResource();

        new Publisher(publisherJedis, CHANNEL_NAME).start();

        subscriber.unsubscribe();

        jedisPool.returnResource(subscriberJedis);

        jedisPool.returnResource(publisherJedis);

        jedisPool.close();
    }
}
