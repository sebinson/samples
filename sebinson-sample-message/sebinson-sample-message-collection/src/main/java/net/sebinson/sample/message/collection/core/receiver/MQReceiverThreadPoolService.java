package net.sebinson.sample.message.collection.core.receiver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import net.sebinson.sample.message.collection.common.CollectConstants;
import net.sebinson.sample.message.collection.core.service.transport.IMessageTransportService;

public class MQReceiverThreadPoolService extends AbstractMQReceiverThreadService {

    private ExecutorService         mqExector = null;

    @Resource(name = "messageTransportService")
    private IMessageTransportService messageTransportService;

    public MQReceiverThreadPoolService() {
        this.mqExector = Executors.newFixedThreadPool(CollectConstants.THREAD_SIZE_RECEIIVE_MQ, new ThreadFactory() {
            private final AtomicInteger ai = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "MQReceiverServiceThread_" + ai.incrementAndGet());
            }
        });
    }

    @Override
    public void process(String tag, Object message) {
        try {
            this.mqExector.submit(new Runnable() {

                @Override
                public void run() {
                    MQReceiverThreadPoolService.this.messageTransportService.processMessage(tag, message);
                }
            });
        } catch (RejectedExecutionException e) {

        }
    }
}
