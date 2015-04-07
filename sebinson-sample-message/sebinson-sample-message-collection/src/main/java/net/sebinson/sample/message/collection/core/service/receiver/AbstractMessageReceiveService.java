package net.sebinson.sample.message.collection.core.service.receiver;

import java.util.Set;

import net.sebinson.common.utils.ObjectUtil;
import net.sebinson.common.utils.UUIDUtil;

import com.alibaba.rocketmq.client.consumer.DefaultMQPullConsumer;
import com.alibaba.rocketmq.client.consumer.MessageQueueListener;
import com.alibaba.rocketmq.client.consumer.PullResult;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.alibaba.rocketmq.remoting.exception.RemotingConnectException;
import com.alibaba.rocketmq.remoting.exception.RemotingTimeoutException;

/**
 * MQ接收处理服务
 * 
 * @author C
 */
public abstract class AbstractMessageReceiveService implements Runnable {

    private String namesrvAddr = "mq.sebinson.net:9876";
    private String topicName;
    private String subExpression;
    private int    threadNum;
    private String consumerGroupName;

    /**
     * 消息接收处理
     */
    public abstract void process(String tag, Object message);

    @Override
    public void run() {
        if (this.consumerGroupName == null || this.topicName == null) {
            return;
        }
        try {
            this.clusterMessage();
        } catch (Exception e) {
            // TODO logger
        }
    }

    public void start() {
        for (int i = 0; i < this.threadNum; i++) {
            Thread thread = new Thread(this);
            thread.start();
        }
    }

    public int getThreadNum() {
        return this.threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public String getTopicName() {
        return this.topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getSubExpression() {
        return this.subExpression;
    }

    public void setSubExpression(String subExpression) {
        this.subExpression = subExpression;
    }

    public String getConsumerGroupName() {
        return this.consumerGroupName;
    }

    public void setConsumerGroupName(String consumerGroupName) {
        this.consumerGroupName = consumerGroupName;
    }

    private void clusterMessage() throws Exception {

        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer(this.consumerGroupName);
        consumer.setNamesrvAddr(this.namesrvAddr);
        consumer.setInstanceName("Consumer InstanceName-" + UUIDUtil.getUUID());
        MessageQueueListener listener = new MessageQueueListener() {
            @Override
            public void messageQueueChanged(String topic, Set<MessageQueue> mqAll, Set<MessageQueue> mqDivided) {
                // TODO messageQueueChanged
            }
        };
        consumer.registerMessageQueueListener(this.topicName, listener);
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.start();
        long offset;
        while (true) {
            try {
                Set<MessageQueue> mqs;
                mqs = consumer.fetchMessageQueuesInBalance(this.topicName);
                for (MessageQueue mq : mqs) {

                    offset = consumer.fetchConsumeOffset(mq, false);
                    offset = (offset < 0 ? 0 : offset);
                    PullResult pullResult = consumer.pull(mq, this.subExpression, offset, 2);

                    switch (pullResult.getPullStatus()) {
                    case FOUND:
                        this.firePullResult(pullResult);
                        consumer.updateConsumeOffset(mq, pullResult.getNextBeginOffset());
                        consumer.getDefaultMQPullConsumerImpl().persistConsumerOffset();
                        break;
                    case NO_MATCHED_MSG:
                        break;
                    case NO_NEW_MSG:
                        break;
                    case OFFSET_ILLEGAL:
                        break;
                    default:
                        break;
                    }
                }
            } catch (RemotingTimeoutException e) {
                Thread.sleep(15000);
            } catch (RemotingConnectException e) {
                Thread.sleep(15000);
            } catch (Exception e) {
            }
            Thread.sleep(10);
        }

    }

    private void firePullResult(PullResult pullResult) {
        for (MessageExt mex : pullResult.getMsgFoundList()) {
            this.process(mex.getTags(), ObjectUtil.unserialize(mex.getBody()));
        }
    }
}
