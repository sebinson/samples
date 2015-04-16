package net.sebinson.sample.message.collection;

import java.util.HashMap;

import net.sebinson.framework.message.transport.protocol.Header;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;
import net.sebinson.sample.message.collection.common.MessageClient;

public class MessageTest {

    public void testMessageSent() {

        RemotingCommand[] commands = new RemotingCommand[2];

        commands[0] = MessageTest.getXxxRemotingCommand();

        MessageClient.sent(commands);

    }

    public static RemotingCommand getXxxRemotingCommand() {

        RemotingCommand command = new RemotingCommand();

        command.setHeader(new Header());
        command.setBody(new HashMap<String, Object>());
        command.setSign(MessageClient.sign(command));
        
        return command;
    }
}
