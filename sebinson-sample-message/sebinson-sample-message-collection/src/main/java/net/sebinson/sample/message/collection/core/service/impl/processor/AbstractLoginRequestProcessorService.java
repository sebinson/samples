package net.sebinson.sample.message.collection.core.service.impl.processor;

import net.sebinson.common.utils.SpringBeanUtil;
import net.sebinson.framework.message.transport.exception.TransportException;
import net.sebinson.framework.message.transport.processor.LoginProcessor;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;
import net.sebinson.sample.message.collection.core.service.CollectionBaseService;
import net.sebinson.sample.message.collection.core.service.processor.VersionControlProcessorService;

public abstract class AbstractLoginRequestProcessorService implements LoginProcessor, CollectionBaseService {
    
    @Override
    public Object[] processLoginRequestSync(RemotingCommand request) throws TransportException {
        String version = request.getHeader().getVersion();
        String itype = request.getHeader().getItype();
        VersionControlProcessorService loginProcessor = null;
        try {
            loginProcessor = SpringBeanUtil.getBean(itype + "_" + version, VersionControlProcessorService.class);
        } catch (Exception e) {
            throw new TransportException(TransportException.EORROR_TRANSPORT_ITYPE, "");
        }
        return loginProcessor.processLoginRequestSync(request);
    }
}
