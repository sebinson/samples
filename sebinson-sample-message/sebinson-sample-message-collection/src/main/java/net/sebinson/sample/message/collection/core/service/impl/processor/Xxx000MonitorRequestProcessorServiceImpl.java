package net.sebinson.sample.message.collection.core.service.impl.processor;

import net.sebinson.framework.message.transport.exception.TransportException;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;

import org.springframework.stereotype.Service;

@Service("xxx000Monitor")
public class Xxx000MonitorRequestProcessorServiceImpl extends AbstractBusinessRequestProcessorService {

    @Override
    public void processRequestAsync(RemotingCommand request) throws TransportException {
        // TODO Auto-generated method stub
        super.processRequestAsync(request);
    }

}
