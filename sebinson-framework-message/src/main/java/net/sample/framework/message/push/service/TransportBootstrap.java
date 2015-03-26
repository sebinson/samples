package net.sample.framework.message.push.service;

public interface TransportBootstrap
{
    /**
     * 启动服务
     */
    public void start() throws Exception;

    /**
     *　关闭服务 
     */
    public void stop();
}
