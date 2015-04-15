package net.sebinson.sample.message.collection.common;

import java.util.Map;

import net.sebinson.framework.message.transport.protocol.RemotingCommand;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class MessageClientHandler extends IoHandlerAdapter {

    @Override
    public void messageSent(IoSession ioSession, Object message) throws Exception {
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        RemotingCommand remotingCommand = (RemotingCommand) message;
        if (remotingCommand.getHeader() != null && remotingCommand.getHeader().getItype().equals("gtsa01")) {
            Map<String, Object> s = remotingCommand.getBody();
            CacheBean.getCachemap().put("shopid", s.get("shopid").toString());
            CacheBean.getCachemap().put("shopname", s.get("shopname").toString());
            CacheBean.getCachemap().put("shopentityid", s.get("shopentityid").toString());
            CacheBean.getCachemap().put("shopentityname", s.get("shopentityname").toString());
        }
        if (remotingCommand.getHeader() != null && remotingCommand.getHeader().getItype().startsWith("gtsf")) {
            remotingCommand.setBody(null);
            session.write(remotingCommand);
        }
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
    }
}
