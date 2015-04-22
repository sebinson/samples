package net.sebinson.sample.message.collection.core.service.impl.message;

import net.sebinson.common.utils.SpringBeanUtil;
import net.sebinson.framework.message.transport.exception.TransportException;
import net.sebinson.framework.message.transport.processor.LoginProcessor;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;
import net.sebinson.sample.message.collection.core.service.ICollectionBaseService;
import net.sebinson.sample.message.collection.core.service.message.IProcessVersionControlService;

public abstract class AbstractLoginRequestProcessorService implements LoginProcessor, ICollectionBaseService {
    
    @Override
    public Object[] processLoginRequestSync(RemotingCommand request) throws TransportException {
        String version = request.getHeader().getVersion();
        String itype = request.getHeader().getItype();
        IProcessVersionControlService loginProcessor = null;
        try {
            loginProcessor = SpringBeanUtil.getBean(itype + "_" + version, IProcessVersionControlService.class);
        } catch (Exception e) {
            throw new TransportException(TransportException.EORROR_TRANSPORT_ITYPE, "");
        }
        return loginProcessor.processLoginRequestSync(request);
    }
}
