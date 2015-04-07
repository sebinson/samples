package net.sebinson.framework.message.push;

import net.sebinson.framework.message.push.service.DistributionServer;
import net.sebinson.framework.message.transport.RPCHook;
import net.sebinson.framework.message.transport.processor.LoginProcessor;
import net.sebinson.framework.message.transport.processor.RequestProcessor;

import org.springframework.util.StringUtils;

/**
 * sokect服务端工厂父类
 *
 */
public class TransportServerFactoryAbstract {
    protected DistributionServer distributionServer;

    /**
     * 长连接　注册登录与监控　必选 短连接不用处理，会不注册
     * 
     * @param loginItype
     * @param loginProcessor
     * @param moniterItype
     * @param moniterProcessor
     * @throws IllegalArgumentException
     *             参数有为null
     */
    public void registerLongConnectProcessor(String loginItype, LoginProcessor loginProcessor, String moniterItype,
            RequestProcessor moniterProcessor) {
        if (this.distributionServer.isLong()) {
            if (StringUtils.isEmpty(loginItype) || loginProcessor == null) {
                throw new IllegalArgumentException("loginItype or LoginProcessor is null, loginItype=" + loginItype
                        + ", LoginProcessor=" + loginProcessor);
            }
            if (StringUtils.isEmpty(moniterItype) || moniterProcessor == null) {
                throw new IllegalArgumentException("moniterItype or moniterProcessor is null, moniterItype="
                        + moniterItype + ", RequestProcessor=" + moniterProcessor);
            }
            this.distributionServer.registerLogintProcessor(loginItype, loginProcessor);
            this.distributionServer.registerMoniterProcessor(moniterItype, moniterProcessor);
        }
    }

    /**
     * 注册服务　可选
     * 
     * @param itype
     *            　接口编码
     * @param processor
     *            　处理接口
     * @throws IllegalArgumentException
     *             参数有为null
     */
    public void registerRequestProcessorregisterRequestProcessor(String itype, RequestProcessor processor) {
        if (StringUtils.isEmpty(itype) || processor == null) {
            throw new IllegalArgumentException("itype or RequestProcessor is null, itype=" + itype
                    + ", RequestProcessor=" + processor);
        }
        this.distributionServer.registerRequestProcessor(itype, processor);
    }

    /**
     * 可选 注册勾子，如报文加密校验
     * 
     * @param rpcHook
     */
    public void registerPRCHook(RPCHook rpcHook) {
        this.distributionServer.registerPRCHook(rpcHook);
    }

    public void start() throws Exception {
        this.distributionServer.start();
    }

    public void stop() {
        this.distributionServer.stop();
    }
}
