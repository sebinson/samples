package net.sebinson.sample.message.collection.main;

import javax.servlet.ServletContextEvent;

import net.sebinson.framework.message.push.SingleTransportServerFactory;
import net.sebinson.framework.message.transport.RPCHook;
import net.sebinson.framework.message.transport.processor.LoginProcessor;
import net.sebinson.framework.message.transport.processor.RequestProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

public class CollectionBootstrap extends ContextLoaderListener {

    private static final Logger logger = LoggerFactory.getLogger(CollectionBootstrap.class);

    @Override
    public void contextInitialized(final ServletContextEvent event) {
        logger.info("采集服务启动...");
        long from = System.currentTimeMillis();
        super.contextInitialized(event);
        WebApplicationContext wac = getCurrentWebApplicationContext();
        initBillCollect(wac, event, this, from);
    }

    private void initBillCollect(WebApplicationContext wac, ServletContextEvent event, CollectionBootstrap _this, long from) {
        final SingleTransportServerFactory singleTransportServer = new SingleTransportServerFactory("采集服务", 8888, true, 0);
        try {
            LoginProcessor loginProcessor = (LoginProcessor) wac.getBean("gtsa01");
            RequestProcessor minitorRequestProcessor = (RequestProcessor) wac.getBean("gtsa06");
            singleTransportServer.registerLongConnectProcessor("gtsa01", loginProcessor, "gtsa06", minitorRequestProcessor);
            singleTransportServer.registerPRCHook(wac.getBean("socketHookRpc", RPCHook.class));
            singleTransportServer.start();
            long to = System.currentTimeMillis();
            logger.info((String.format("采集服务启动成功,耗时[%s]毫秒.", (from - to))));
        } catch (Throwable e) {
            // System.exit(0);
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                logger.info("账单采集服务停止开始...");
                if (singleTransportServer != null) {
                    singleTransportServer.stop();
                }
                if (event != null && wac != null) {
                    _this.contextDestroyed(event);
                }
                logger.info("账单采集服务停止成功.");
            }
        });
    }

}
