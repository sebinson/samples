package net.sebinson.sample.message.collection.core.service.impl.processor;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sebinson.framework.message.transport.exception.TransportCommandException;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;

public class CommonRPCHookProcessorServiceImpl extends AbstractRPCHookProcessorService {

    @Resource(name = "versionMap")
    private Map<String, List<?>> versionMap;

    @Override
    public void doBeforeRequest(String add, RemotingCommand request) throws TransportCommandException {
        
        //验密
        this.encrypCheck(add, request);
        
        //版本
        this.versionCheck(add, request);
    }

    private void versionCheck(String cid, RemotingCommand request) {
        // TODO Auto-generated method stub
        
    }

    private void encrypCheck(String cid, RemotingCommand request) {
        // TODO Auto-generated method stub
        
    }

}
