package net.sebinson.sample.message.collection.core.service.impl.message;

import net.sebinson.common.utils.SpringBeanUtil;
import net.sebinson.framework.message.transport.exception.TransportException;
import net.sebinson.framework.message.transport.processor.RequestProcessor;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;
import net.sebinson.sample.message.collection.core.service.CollectionBaseService;
import net.sebinson.sample.message.collection.core.service.message.ProcessVersionControlService;

public abstract class AbstractBusinessRequestProcessorService implements RequestProcessor, CollectionBaseService {

    @Override
    public void processRequestUnreply(RemotingCommand request) throws TransportException {
        String version = request.getHeader().getVersion();
        String itype = request.getHeader().getItype();
        ProcessVersionControlService requestProcessor = null;
        try {
            requestProcessor = SpringBeanUtil.getBean(itype + "_" + version, ProcessVersionControlService.class);
        } catch (Exception e) {
            throw new TransportException(TransportException.EORROR_TRANSPORT, "");
        }
        requestProcessor.processRequestUnreply(request);
    }

    @Override
    public RemotingCommand processRequestSync(RemotingCommand request) throws TransportException {
        String version = request.getHeader().getVersion();
        String itype = request.getHeader().getItype();
        ProcessVersionControlService requestProcessor = null;
        try {
            requestProcessor = SpringBeanUtil.getBean(itype + "_" + version, ProcessVersionControlService.class);
        } catch (Exception e) {
            throw new TransportException(TransportException.EORROR_TRANSPORT, "");
        }
        return requestProcessor.processRequestSync(request);
    }

    @Override
    public void processRequestAsync(RemotingCommand request) throws TransportException {
        String version = request.getHeader().getVersion();
        String itype = request.getHeader().getItype();
        ProcessVersionControlService requestProcessor = null;
        try {
            requestProcessor = SpringBeanUtil.getBean(itype + "_" + version, ProcessVersionControlService.class);
        } catch (Exception e) {
            throw new TransportException(TransportException.EORROR_TRANSPORT, "");
        }
        requestProcessor.processRequestAsync(request);
    }

}
