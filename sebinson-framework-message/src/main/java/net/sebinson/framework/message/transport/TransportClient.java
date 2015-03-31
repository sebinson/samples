package net.sebinson.framework.message.transport;

import net.sebinson.framework.message.transport.exception.TransportException;

/**
 * Socket客户端的启动与停止
 *
 */
public interface TransportClient
{

    /**
     * Socket客户端的启动,长连接
     * @param name 服务名，可为空
     * @param isLong true 长连接，false短连接
     * @param ip 服务端ip或者域名，如果为空,null;用默认127.0.0.1
     * @param port 服务端端口，如果小于1，用默认 9999
     * @param nThreads mina线程池数据，如果小于1，用默认 Java虚拟机返回可用处理器的数目+1
     * @throws TransportException 启动失败
     */
    public void startClient(String name, boolean isLong, String ip, int port, int nThreads) throws TransportException;

    /**
     * Socket客户端的停止
     */
    public void stop();
}
