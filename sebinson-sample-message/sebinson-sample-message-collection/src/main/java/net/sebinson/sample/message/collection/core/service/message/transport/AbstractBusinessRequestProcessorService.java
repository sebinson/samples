package net.sebinson.sample.message.collection.core.service.message.transport;

import net.sebinson.framework.message.transport.exception.TransportException;
import net.sebinson.framework.message.transport.processor.RequestProcessor;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;
import net.sebinson.sample.message.collection.core.service.ICollectionBaseService;

public abstract class AbstractBusinessRequestProcessorService implements RequestProcessor, ICollectionBaseService {

    @Override
    public void processRequestUnreply(RemotingCommand request) throws TransportException {
        String version = request.getHeader().getVersion();
        String itype = request.getHeader().getItype();
        ProcessVersionControlService requestProcessor = null;
        try {
            requestProcessor = SpringBeanUtils.getBean(itype + "_" + version, ProcessVersionControlService.class);
        } catch (Exception e) {
            throw new TransportException(TransportException.EORROR_TRANSPORT, "子类[" + this.getClass() + "]没有重写["
                    + Thread.currentThread().getStackTrace()[1].getMethodName() + "]方法.业务逻辑没有实现.");
        }
        requestProcessor.processRequestUnreply(request);
    }

    /**
     * 处理同步应答的请求
     * 
     * @param request 对应请求报文
     * @return 处理成功返回对方的报文，处理失败直接抛TransportException异常即可
     * @throws TransportException 处理失败返回 TransportException.errorCode即为返回对方报文Header的resutl, TransportException.message即为返回对方报文Header的msg
     */

    @Override
    public RemotingCommand processRequestSync(RemotingCommand request) throws TransportException {
        String version = request.getHeader().getVersion();
        String itype = request.getHeader().getItype();
        ProcessVersionControlService requestProcessor = null;
        try {
            requestProcessor = SpringBeanUtils.getBean(itype + "_" + version, ProcessVersionControlService.class);
        } catch (Exception e) {
            throw new TransportException(TransportException.EORROR_TRANSPORT, "子类[" + this.getClass() + "]没有重写["
                    + Thread.currentThread().getStackTrace()[1].getMethodName() + "]方法.业务逻辑没有实现.");
        }
        return requestProcessor.processRequestSync(request);
    }

    /**
     * 处理异步应答的请求
     * 
     * @param request 对应请求报文
     * @throws TransportException 处理失败返回 TransportException.errorCode即为返回对方报文Header的resutl, TransportException.message即为返回对方报文Header的msg
     */

    @Override
    public void processRequestAsync(RemotingCommand request) throws TransportException {
        int version = request.getHeader().getVersion();
        String itype = request.getHeader().getItype();
        ProcessVersionControlService requestProcessor = null;
        try {
            requestProcessor = SpringBeanUtils.getBean(itype + "_" + version, ProcessVersionControlService.class);
        } catch (Exception e) {
            throw new TransportException(TransportException.EORROR_TRANSPORT, "子类[" + this.getClass() + "]没有重写["
                    + Thread.currentThread().getStackTrace()[1].getMethodName() + "]方法.业务逻辑没有实现.");
        }
        requestProcessor.processRequestAsync(request);
    }

}
