package net.sebinson.sample.message.collection.core.service.impl.processors.versions;

import net.sebinson.framework.message.transport.exception.TransportException;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;
import net.sebinson.sample.message.collection.core.service.impl.processors.AbstractVersionControlProcessorService;

import org.springframework.stereotype.Service;

@Service("Xxx002_" + "V001")
public class Xxx002_V001_VersionControlProcessorServiceImpl extends AbstractVersionControlProcessorService{

    @Override
    public void processRequestAsync(RemotingCommand request) throws TransportException {
        // TODO Auto-generated method stub
        super.processRequestAsync(request);
    }
}
