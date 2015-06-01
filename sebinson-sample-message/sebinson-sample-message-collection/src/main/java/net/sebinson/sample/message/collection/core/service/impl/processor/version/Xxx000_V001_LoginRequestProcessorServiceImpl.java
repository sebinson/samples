package net.sebinson.sample.message.collection.core.service.impl.processor.version;

import net.sebinson.framework.message.transport.exception.TransportException;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;
import net.sebinson.sample.message.collection.core.service.impl.processor.AbstractLoginRequestProcessorService;

import org.springframework.stereotype.Service;

@Service("Xxx000_" + "V001")
public class Xxx000_V001_LoginRequestProcessorServiceImpl extends AbstractLoginRequestProcessorService{

    @Override
    public Object[] processLoginRequestSync(RemotingCommand request) throws TransportException {
        // TODO Auto-generated method stub
        return super.processLoginRequestSync(request);
    }
}
