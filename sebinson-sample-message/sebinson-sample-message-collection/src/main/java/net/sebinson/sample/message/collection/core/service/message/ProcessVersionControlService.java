package net.sebinson.sample.message.collection.core.service.message;

import net.sebinson.framework.message.transport.exception.TransportException;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;

public interface ProcessVersionControlService {
    /**
     * 同步登陆请求
     * 
     * @return Object[0] = RemotingCommand，object[1] = ClientInfoMsg
     */
    public Object[] processLoginRequestSync(RemotingCommand request) throws TransportException;

    /**
     * 处理没有应答的请求
     * 
     */
    public void processRequestUnreply(RemotingCommand request) throws TransportException;

    /**
     * 处理同步应答的请求
     * 
     */
    public RemotingCommand processRequestSync(RemotingCommand request) throws TransportException;

    /**
     * 处理异步应答的请求
     * 
     */
    public void processRequestAsync(RemotingCommand request) throws TransportException;
}
