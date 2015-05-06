package net.sebinson.sample.demos.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOServer {
    //通道管理器
    private Selector selector;

    /**
     * 获取ServerSocket通道，并作初始化工作
     * 
     * @throws IOException
     */
    public void init(int port) throws IOException {

        //打开一个ServerSocket通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        //设置非阻塞
        serverChannel.configureBlocking(false);
        //绑定监听端口
        serverChannel.socket().bind(new InetSocketAddress(port));
        //打开通道管理器
        this.selector = Selector.open();
        //绑定通道到通道管理器，并注册通道Accept事件
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    /**
     * 采用轮询的方式处理selector上注册的事件
     * 
     * @throws IOException
     */
    public void listen() throws IOException {

        System.out.println("服务端启动成功.");

        while (true) {
            //通道阻塞，等待监听的事件到达
            int readyChannels = selector.select();
            if (readyChannels == 0)
                continue;
            //获取通道所有注册的事件
            Iterator<SelectionKey> iter = this.selector.selectedKeys().iterator();
            //遍历事件，进行处理
            while (iter.hasNext()) {

                SelectionKey skey = iter.next();
                //防止重复处理同一事件
                iter.remove();
                //处理连接事件
                if (skey.isAcceptable()) {
                    //获取ServerSocket
                    ServerSocketChannel server = (ServerSocketChannel) skey.channel();
                    //获取客户端连接的通道
                    SocketChannel channel = server.accept();

                    channel.configureBlocking(false);
                    //向客户端发送消息                   
                    channel.write(ByteBuffer.wrap(new String("向客户端发送消息.").getBytes()));
                    //为客户端通道注册读事件
                    channel.register(this.selector, SelectionKey.OP_READ);
                }
                //处理读事件
                if (skey.isReadable()) {

                    read(skey);
                }
            }
        }
    }

    /**
     * 处理读取客户端发送的消息
     * 
     * @param skey
     * @throws IOException
     */
    private void read(SelectionKey skey) throws IOException {

        SocketChannel channel = (SocketChannel) skey.channel();

        ByteBuffer buffer = ByteBuffer.allocate(10);

        channel.read(buffer);

        byte[] data = buffer.array();

        String msg = new String(data).trim();

        System.out.println("服务端收到信息：" + msg);

        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());

        channel.write(outBuffer);// 将消息回送给客户端  
    }

    /**
     * 启动服务端测试
     * 
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        NIOServer server = new NIOServer();

        server.init(8000);

        server.listen();
    }
}
