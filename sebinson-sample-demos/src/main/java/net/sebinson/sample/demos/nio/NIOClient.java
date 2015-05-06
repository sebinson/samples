package net.sebinson.sample.demos.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOClient {

    private Selector selector;

    public void init(String ip, int port) throws IOException {

        SocketChannel channel = SocketChannel.open();

        channel.configureBlocking(false);

        channel.connect(new InetSocketAddress(ip, port));

        this.selector = Selector.open();

        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void listen() throws IOException {

        while (true) {

            selector.select();

            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();

            while (iter.hasNext()) {

                SelectionKey skey = iter.next();

                if (skey.isConnectable()) {

                    SocketChannel channel = (SocketChannel) skey.channel();
                    
                    // 如果正在连接，则完成连接  
                    if (channel.isConnectionPending()) {                       
                        channel.finishConnect();                       
                    }                  
                    // 设置成非阻塞  
                    channel.configureBlocking(false);
                    //在这里可以给服务端发送信息哦  
                    channel.write(ByteBuffer.wrap(new String("向服务端发送了一条信息").getBytes()));
                    //在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。  
                    channel.register(this.selector, SelectionKey.OP_READ);
                }

                if (skey.isReadable()) {
                    read(skey);
                }
            }
        }

    }

    private void read(SelectionKey skey) throws IOException {

        SocketChannel channel = (SocketChannel) skey.channel();

        ByteBuffer buffer = ByteBuffer.allocate(10);

        channel.read(buffer);

        byte[] data = buffer.array();

        String msg = new String(data).trim();

        System.out.println("客户端收到信息：" + msg);

        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());

        channel.write(outBuffer);// 将消息回送给服务端
    }

    /**
     * 启动客户端测试
     * 
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        NIOClient client = new NIOClient();

        client.init("localhost", 8000);

        client.listen();
    }
}
