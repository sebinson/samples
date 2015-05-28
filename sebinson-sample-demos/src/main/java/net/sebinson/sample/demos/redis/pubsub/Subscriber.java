package net.sebinson.sample.demos.redis.pubsub;

import redis.clients.jedis.Client;
import redis.clients.jedis.JedisPubSub;

public class Subscriber extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        System.out.println("Message received. Channel: " + channel + ", Msg: " + message);
        super.onMessage(channel, message);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        // TODO Auto-generated method stub
        super.onPMessage(pattern, channel, message);
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        // TODO Auto-generated method stub
        super.onPSubscribe(pattern, subscribedChannels);
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        // TODO Auto-generated method stub
        super.onSubscribe(channel, subscribedChannels);
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        // TODO Auto-generated method stub
        super.onUnsubscribe(channel, subscribedChannels);
    }

    @Override
    public void proceedWithPatterns(Client client, String... patterns) {
        // TODO Auto-generated method stub
        super.proceedWithPatterns(client, patterns);
    }
}
