package net.sebinson.sample.message.collection.common;

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
        if (remotingCommand.getHeader() != null && remotingCommand.getHeader().getItype().equals("")) {

        }
        if (remotingCommand.getHeader() != null && remotingCommand.getHeader().getItype().startsWith("")) {
            remotingCommand.setBody(null);
            session.write(remotingCommand);
        }
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        
    }
}
