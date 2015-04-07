package net.sebinson.framework.message.push.service;

import net.sebinson.framework.message.common.ClientInfoMsg;
import net.sebinson.framework.message.push.bean.GAGMessage;
import net.sebinson.framework.message.transport.RPCHook;
import net.sebinson.framework.message.transport.processor.LoginProcessor;
import net.sebinson.framework.message.transport.processor.RequestProcessor;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;

public interface DistributionServer extends TransportBootstrap
{
    /**
     *发送数据，等待同步应答 
     */
    public <T> RemotingCommand sendSync(final String add, final GAGMessage<T> request);

    /**
     *发送数据，等待异步应答 
     */
    public <T> void sendAsync(final String add, final GAGMessage<T> request, final SendCallBack callBack);

    /**
     *发送数据，不等待应答 
     */
    public <T> void sendUnreply(final String add, final GAGMessage<T> request);

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
    public void registerPRCHook(final RPCHook rpcHook);

    /**
     * true 长连接，flase　短连接
     */
    public boolean isLong();

    /**
     * 获取客户端信息
     * @param adds 客户端标识，如mac
     * @return ClientInfoMsg,无 null
     */
    public ClientInfoMsg getClientInfoMsg(String adds);
}
