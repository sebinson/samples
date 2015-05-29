package net.sebinson.sample.message.collection.core.service.impl.processors;

import net.sebinson.framework.message.transport.exception.TransportException;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;
import net.sebinson.sample.message.collection.core.service.processor.VersionControlProcessorService;

public abstract class AbstractVersionControlProcessorService implements VersionControlProcessorService {

    @Override
    public Object[] processLoginRequestSync(RemotingCommand request) throws TransportException {
        //empty
        throw new TransportException(TransportException.EORROR_TRANSPORT, "");
    }

    @Override
    public void processRequestUnreply(RemotingCommand request) throws TransportException {
        //empty
        throw new TransportException(TransportException.EORROR_TRANSPORT, "");
    }

    @Override
    public RemotingCommand processRequestSync(RemotingCommand request) throws TransportException {
        //empty
        throw new TransportException(TransportException.EORROR_TRANSPORT, "");
    }

    @Override
    public void processRequestAsync(RemotingCommand request) throws TransportException {
        //empty
        throw new TransportException(TransportException.EORROR_TRANSPORT, "");
    }

}
