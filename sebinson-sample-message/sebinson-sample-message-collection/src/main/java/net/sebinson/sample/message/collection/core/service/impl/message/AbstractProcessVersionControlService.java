package net.sebinson.sample.message.collection.core.service.impl.message;

import net.sebinson.framework.message.transport.exception.TransportException;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;
import net.sebinson.sample.message.collection.core.service.message.ProcessVersionControlService;

public abstract class AbstractProcessVersionControlService implements ProcessVersionControlService {

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
