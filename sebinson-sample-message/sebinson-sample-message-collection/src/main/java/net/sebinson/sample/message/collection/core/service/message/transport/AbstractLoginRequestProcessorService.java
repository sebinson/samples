package net.sebinson.sample.message.collection.core.service.message.transport;

import net.sebinson.common.utils.SpringBeanUtil;
import net.sebinson.framework.message.transport.exception.TransportException;
import net.sebinson.framework.message.transport.processor.LoginProcessor;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;
import net.sebinson.sample.message.collection.core.service.CollectionBaseService;
import net.sebinson.sample.message.collection.core.service.ProcessVersionControlService;

public abstract class AbstractLoginRequestProcessorService implements LoginProcessor, CollectionBaseService {
    
    @Override
    public Object[] processLoginRequestSync(RemotingCommand request) throws TransportException {
        String version = request.getHeader().getVersion();
        String itype = request.getHeader().getItype();
        ProcessVersionControlService loginProcessor = null;
        try {
            loginProcessor = SpringBeanUtil.getBean(itype + "_" + version, ProcessVersionControlService.class);
        } catch (Exception e) {
            throw new TransportException(TransportException.EORROR_TRANSPORT_ITYPE, "");
        }
        return loginProcessor.processLoginRequestSync(request);
    }
}
