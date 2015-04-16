package net.sebinson.sample.message.collection.core.receiver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import net.sebinson.sample.message.collection.common.CollectConstants;
import net.sebinson.sample.message.collection.core.service.message.IObtainBaseService;

public class MQMessageReceiverService extends AbstractMessageReceiveService {

    private ExecutorService    mqExector = null;

    /* 业务处理服务 */
    @Resource(name = "obtainBaseService")
    private IObtainBaseService obtainBaseService;

    public MQMessageReceiverService() {
        this.mqExector = Executors.newFixedThreadPool(CollectConstants.THREADS_SIZE_OBTAIN_MQ, new ThreadFactory() {
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
                    MQMessageReceiverService.this.obtainBaseService.processMessage(tag, message);
                }
            });
        } catch (RejectedExecutionException e) {

        }
    }
}
