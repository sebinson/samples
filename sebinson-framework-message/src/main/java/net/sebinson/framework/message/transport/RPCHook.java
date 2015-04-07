package net.sebinson.framework.message.transport;

import net.sebinson.framework.message.transport.exception.TransportCommandException;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;

/**
 * 接收报文后预处理，发送报文前预处理 主用要用于报文校验
 *
 */
public interface RPCHook {

    /**
     * 接收报文后预处理 请求报文校验
     * 
     * @param add
     *            Socket对应地址
     * @param request
     *            对应请求报文
     * @throws TransportCommandException
     *             校验报文失败
     */
    public void doBeforeRequest(final String add, final RemotingCommand request) throws TransportCommandException;

    /**
     * 发送报文前预处理 发送报文处理
     * 
     * @param add
     *            Socket对应地址
     * @param response
     *            对应发送报文
     */
    public void doBeforeResponse(final String add, final RemotingCommand response);
}
