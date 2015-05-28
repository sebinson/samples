package net.sebinson.sample.message.collection.core.service.message;

import net.sebinson.framework.message.transport.exception.TransportException;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;

public interface ProcessVersionControlService
{
    /**
     * 处理同步登陆请求
     * @param request 对应请求报文
     * @return 处理成功返回Object[0]=RemotingCommand返回给客户端的数据，object[1]=ClientInfoMsg
     * 处理失败直接抛TransportException异常即可
     * @throws TransportException 处理失败返回  
     * TransportException.errorCode即为返回对方报文Header的resutl, TransportException.message即为返回对方报文Header的msg
     */
    public Object[] processLoginRequestSync(RemotingCommand request) throws TransportException;

    /**
     * 处理没有应答的请求
     * @param request 对应请求报文
     * @throws TransportException 处理失败返回  
     * TransportException.errorCode即为返回对方报文Header的resutl, TransportException.message即为返回对方报文Header的msg
     */
    public void processRequestUnreply(RemotingCommand request) throws TransportException;

    /**
     * 处理同步应答的请求
     * @param request 对应请求报文
     * @return 处理成功返回对方的报文，处理失败直接抛TransportException异常即可
     * @throws TransportException 处理失败返回  
     * TransportException.errorCode即为返回对方报文Header的resutl, TransportException.message即为返回对方报文Header的msg
     */
    public RemotingCommand processRequestSync(RemotingCommand request) throws TransportException;

    /**
     * 处理异步应答的请求
     * @param request 对应请求报文
     * @throws TransportException 处理失败返回  
     * TransportException.errorCode即为返回对方报文Header的resutl, TransportException.message即为返回对方报文Header的msg
     */
    public void processRequestAsync(RemotingCommand request) throws TransportException;
}
