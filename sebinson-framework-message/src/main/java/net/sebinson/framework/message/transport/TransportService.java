package net.sebinson.framework.message.transport;

import net.sebinson.framework.message.transport.exception.TransportConnectException;
import net.sebinson.framework.message.transport.exception.TransportSendRequestException;
import net.sebinson.framework.message.transport.exception.TransportTimeoutException;
import net.sebinson.framework.message.transport.exception.TransportTooMuchRequestException;
import net.sebinson.framework.message.transport.processor.LoginProcessor;
import net.sebinson.framework.message.transport.processor.RequestProcessor;
import net.sebinson.framework.message.transport.protocol.RemoteCommand;

/**
 * 发送数据
 * 注册hook,RequestProcessor,LoginProcessor
 *
 */
public interface TransportService
{

    /**
     *发送数据，等待同步应答 
     */
    public RemoteCommand invokeSync(final String add, final RemoteCommand request, final long timeoutMillis) throws InterruptedException, TransportConnectException, TransportTimeoutException, TransportSendRequestException, TransportTooMuchRequestException;

    /**
     *发送数据，等待异步应答 
     */
    public void invokeASync(final String add, final RemoteCommand request, final long timeoutMillis, final InvokeCallback invokeCallback) throws InterruptedException, TransportConnectException, TransportTimeoutException, TransportSendRequestException, TransportTooMuchRequestException;

    /**
     *发送数据，不等待应答 
     */
    public void invokeUnreply(final String add, final RemoteCommand request, final long timeoutMillis) throws InterruptedException, TransportConnectException, TransportTimeoutException, TransportSendRequestException, TransportTooMuchRequestException;

    /**
     *注册请求处理器，优化使用注册的接口，如没有找到，就用SpringBeanUtils.getBean(itype)
     * @param itype 接口名
     * @param processor RequestProcessor
     */
    public void registerRequestProcessor(final String itype, final RequestProcessor processor);

    /**
     *注册登陆接口 LoginProcessor 长连接用
     * @param itype 登陆接口名
     * @param processor LoginProcessor
     */
    public void registerLogintProcessor(final String itype, final LoginProcessor processor);

    /**
     *注册监控接口 LoginProcessor 长连接用
     * @param itype 监控接口接口名
     * @param processor RequestProcessor
     */
    public void registerMoniterProcessor(final String itype, final RequestProcessor processor);

    /**
     * 通信层勾子，如报文的校验处理
     */
    public void registerPRCHook(RPCHook rpcHook);

}
