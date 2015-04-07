package net.sebinson.framework.message.transport.processor;

import net.sebinson.framework.message.transport.exception.TransportException;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;

/**
 * 接收到的请求处理,非登陆 报文用到那个方法就实现那个方法即可
 * "rpc":[必填,主要用于应答]整数:处理完数据后再应答（同步应答）为0，接收完数据没有处理数据直接应答（异步应答）为1，接收数据后不应答为2
 * 0：实现processRequestSync 1：实现processRequestAsync 2：实现processRequestUnreply即可
 *
 */
public interface RequestProcessor {
    /**
     * 处理没有应答的请求
     * 
     * @param request
     *            对应请求报文
     * @throws TransportException
     *             处理失败返回 TransportException.errorCode即为返回对方报文Header的resutl,
     *             TransportException.message即为返回对方报文Header的msg
     */
    public void processRequestUnreply(RemotingCommand request) throws TransportException;

    /**
     * 处理同步应答的请求
     * 
     * @param request
     *            对应请求报文
     * @return 处理成功返回对方的报文，处理失败直接抛TransportException异常即可
     * @throws TransportException
     *             处理失败返回 TransportException.errorCode即为返回对方报文Header的resutl,
     *             TransportException.message即为返回对方报文Header的msg
     */
    public RemotingCommand processRequestSync(RemotingCommand request) throws TransportException;

    /**
     * 处理异步应答的请求
     * 
     * @param request
     *            对应请求报文
     * @throws TransportException
     *             处理失败返回 TransportException.errorCode即为返回对方报文Header的resutl,
     *             TransportException.message即为返回对方报文Header的msg
     */
    public void processRequestAsync(RemotingCommand request) throws TransportException;
}
