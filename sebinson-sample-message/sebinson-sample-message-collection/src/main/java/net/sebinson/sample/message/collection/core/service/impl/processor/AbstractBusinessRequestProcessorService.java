package net.sebinson.sample.message.collection.core.service.impl.processor;

import net.sebinson.common.utils.SpringBeanUtil;
import net.sebinson.framework.message.transport.exception.TransportException;
import net.sebinson.framework.message.transport.processor.RequestProcessor;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;
import net.sebinson.sample.message.collection.core.service.CollectionBaseService;
import net.sebinson.sample.message.collection.core.service.processor.VersionControlProcessorService;

public abstract class AbstractBusinessRequestProcessorService implements RequestProcessor, CollectionBaseService {

    @Override
    public void processRequestUnreply(RemotingCommand request) throws TransportException {
        this.getProcessVersionControlService(request).processRequestUnreply(request);
    }

    @Override
    public RemotingCommand processRequestSync(RemotingCommand request) throws TransportException {
        return this.getProcessVersionControlService(request).processRequestSync(request);
    }

    @Override
    public void processRequestAsync(RemotingCommand request) throws TransportException {
        this.getProcessVersionControlService(request).processRequestAsync(request);
    }

    private VersionControlProcessorService getProcessVersionControlService(RemotingCommand request) throws TransportException {
        String version = request.getHeader().getVersion();
        String itype = request.getHeader().getItype();
        VersionControlProcessorService requestProcessor = null;
        try {
            requestProcessor = SpringBeanUtil.getBean(itype + "_" + version, VersionControlProcessorService.class);

        } catch (Exception e) {
            throw new TransportException(TransportException.EORROR_TRANSPORT_ITYPE, "" + e.getMessage(), e);
        }

        return requestProcessor;
    }

}
