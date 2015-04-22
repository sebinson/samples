package net.sebinson.sample.message.collection.core.receiver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import net.sebinson.sample.message.collection.common.CollectConstants;
import net.sebinson.sample.message.collection.core.service.message.MessageProcessService;

public class MQMessageReceiverThreadPoolService extends AbstractMQMessageReceiverThreadService {

    private ExecutorService   mqExector = null;

    @Resource(name = "messageProcessService")
    private MessageProcessService messageProcessService;

    public MQMessageReceiverThreadPoolService() {
        this.mqExector = Executors.newFixedThreadPool(CollectConstants.THREAD_SIZE_RECEIIVE_MQ, new ThreadFactory() {
            private final AtomicInteger ai = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "MQMessageReceiverServiceThread_" + ai.incrementAndGet());
            }
        });
    }

    @Override
    public void process(String tag, Object message) {
        try {
            this.mqExector.submit(new Runnable() {

                @Override
                public void run() {
                    MQMessageReceiverThreadPoolService.this.messageProcessService.processMessage(tag, message);
                }
            });
        } catch (RejectedExecutionException e) {

        }
    }
}
