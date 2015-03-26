package net.sample.framework.message.transport;

import net.sample.framework.message.transport.exception.TransportException;
import net.sample.framework.message.transport.protocol.RemotingCommand;

/**
 * 登录接口
 *
 */
public interface LoginProcessor
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

}
