package net.sebinson.sample.message.collection;

import net.sebinson.framework.message.transport.protocol.RemotingCommand;
import net.sebinson.sample.message.collection.common.MessageClient;

public class MessageTest {

    public void testMessageSent() {

        RemotingCommand[] commands = new RemotingCommand[2];
        MessageClient.sent(commands);
    }
}
