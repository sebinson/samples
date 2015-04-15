package net.sebinson.sample.message.collection.common;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import net.sebinson.framework.message.transport.mina.MinaTransportProtocolCodecFacotry;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MessageClient {

    private static String       MAC          = "A1B2C3D4E5F6";
    private static String       ENCRYPT_KEY  = "0123456789";
    private static final int    IDLE_TIMEOUT = 3000;
    private static final String DOMAIN       = "127.0.0.1";
    private static final int    PORT         = 8765;

    public static void sent(RemotingCommand[] commands) {

        IoConnector connector = new NioSocketConnector();
        connector.getSessionConfig().setReadBufferSize(1024);
        connector.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE, IDLE_TIMEOUT);
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MinaTransportProtocolCodecFacotry()));
        connector.getFilterChain().addLast("threadPool", new ExecutorFilter(Executors.newCachedThreadPool()));
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.setHandler(new MessageClientHandler());

        new Thread(new Distributor(connector, commands)).start();
    };

    private static class Distributor implements Runnable {

        IoConnector       connector = null;
        IoSession         session   = null;
        RemotingCommand[] commands  = null;

        public Distributor(IoConnector connector, RemotingCommand[] commands) {
            this.connector = connector;
            this.commands = commands;
        }

        @Override
        public void run() {

            ConnectFuture future = this.connector.connect(new InetSocketAddress(DOMAIN, PORT));

            future.awaitUninterruptibly();

            this.session = future.getSession();

            new Thread(new Heart(this.session)).start();

            transmit(this.session, this.commands);

        }

    }

    private static class Heart implements Runnable {

        IoSession session = null;

        public Heart(IoSession session) {
            this.session = session;
        }

        @Override
        public void run() {

            while (true) {

                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {

                }

                RemotingCommand heart = new RemotingCommand();
                heart.setMessage("Y");
                heart.setType((byte) 2);
                this.session.write(heart);
            }

        }
    }

    private static void transmit(IoSession session, RemotingCommand[] commands) {
        for (int i = 0; i < commands.length; i++) {
            session.write(commands[i]);
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
