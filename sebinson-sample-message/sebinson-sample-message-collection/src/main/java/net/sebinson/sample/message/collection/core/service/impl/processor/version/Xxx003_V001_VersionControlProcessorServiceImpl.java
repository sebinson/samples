package net.sebinson.sample.message.collection.core.service.impl.processor.version;

import net.sebinson.framework.message.transport.exception.TransportException;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;
import net.sebinson.sample.message.collection.core.service.impl.processor.AbstractVersionControlProcessorService;

import org.springframework.stereotype.Service;

@Service("Xxx001_" + "V001")
public class Xxx003_V001_VersionControlProcessorServiceImpl extends AbstractVersionControlProcessorService{

    @Override
    public void processRequestAsync(RemotingCommand request) throws TransportException {
        // TODO Auto-generated method stub
        super.processRequestAsync(request);
    }
}
