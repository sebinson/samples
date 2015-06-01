package net.sebinson.sample.message.collection.core.service.impl.processor;

import org.springframework.stereotype.Service;

import net.sebinson.framework.message.transport.exception.TransportException;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;

@Service("xxx000")
public class Xxx000LoginRequestProcessorServiceImpl extends AbstractLoginRequestProcessorService {
    
    @Override
    public Object[] processLoginRequestSync(RemotingCommand request) throws TransportException {
        // TODO Auto-generated method stub
        return super.processLoginRequestSync(request);
    }

}
