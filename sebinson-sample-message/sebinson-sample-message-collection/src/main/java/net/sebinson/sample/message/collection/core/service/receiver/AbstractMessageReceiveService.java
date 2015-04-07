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

public abstract class AbstractMessageReceiveService implements Runnable {

    private String namesrvAddr = "mq.goo.com:9876";
    private String topicName;
    private String subExpression;
    private int    threadNum;
    private String consumerGroupName;

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

    @Override
    public void run() {
        if (this.consumerGroupName == null || this.topicName == null) {
            return;
        }
        try {
            this.clusterMessage();
        } catch (Exception e) {
        }
    }

    /**
     * 消费集群消息
     */
    private void clusterMessage() throws Exception {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer(this.consumerGroupName);

        consumer.setNamesrvAddr(this.namesrvAddr);
        consumer.setInstanceName("Consumer InstanceName-" + UUIDUtil.getUUID());

        MessageQueueListener listener = new MessageQueueListener() {
            @Override
            public void messageQueueChanged(String topic, Set<MessageQueue> mqAll, Set<MessageQueue> mqDivided) {
            }
        };
        consumer.registerMessageQueueListener(this.topicName, listener);
        consumer.setMessageModel(MessageModel.CLUSTERING);

        consumer.start();

        long offset;
        while (true) {
            try {
                // 根据topic获取MessageQueue，以均衡方式在组内多个消费者之间分配
                Set<MessageQueue> mqs;
                mqs = consumer.fetchMessageQueuesInBalance(this.topicName);
                for (MessageQueue mq : mqs) {

                    /**
                     * 取得队列offset 第二个参数为true时表示直接从存储层读取，为false时表示先从缓存内读取，
                     * 如缓存内不存在该队列offset则再从存储层读取 RocketMQ定时任务每5秒写入一次存储层offset，
                     * 因此读取间隔快于5秒并遇到broker宕机时会造成重复消息问题，所以最好自己强制持久化
                     */
                    offset = consumer.fetchConsumeOffset(mq, false);
                    // offset不能小于0
                    offset = (offset < 0 ? 0 : offset);
                    // 拉取消息，最后一个参数代表每次拉取的最大数量，该参数有BUG，实际拉取最大数量为该参数值减1
                    PullResult pullResult = consumer.pull(mq, this.subExpression, offset, 2);

                    // 只有发现消息时才进行消费活动
                    switch (pullResult.getPullStatus()) {
                    case FOUND:// 发现消息，进行消费活动
                        // 消息处理逻辑
                        this.firePullResult(pullResult);

                        // 更新offset到缓存
                        consumer.updateConsumeOffset(mq, pullResult.getNextBeginOffset());

                        // 强制更新offset到存储层
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
                // 服务器连接失败，15秒后重试
                Thread.sleep(15000);
            } catch (RemotingConnectException e) {
                // 服务器连接失败，15秒后重试
                Thread.sleep(15000);
            } catch (Exception e) {
            }
            // 降低单CPU服务器的CPU占用率
            Thread.sleep(10);
        }

    }

    /**
     * 消息处理
     * 
     * @param pullResult
     *            拉取的消息集合
     */
    private void firePullResult(PullResult pullResult) {
        for (MessageExt mex : pullResult.getMsgFoundList()) {
            this.process(mex.getTags(), ObjectUtil.unserialize(mex.getBody()));
        }
    }

    /**
     * 消息处理逻辑
     * 
     * @param tag
     *            消息标签
     * @param msgObject
     *            消息对象
     */
    public abstract void process(String tag, Object msgObject);

    /**
     * 初始化线程、开始接收消息
     */
    public void start() {
        for (int i = 0; i < this.threadNum; i++) {

            Thread thread = new Thread(this);
            thread.start();

        }
    }

}
