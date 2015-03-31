package net.sebinson.framework.message.push;

import net.sebinson.framework.message.push.service.impl.DistributionServerImpl;


/**
 * 集群sokect服务端工厂
 * 创建socket,注入RequestProcessor,RPCHook
 *
 */
public class ClusterTransportServerFactory extends TransportServerFactoryAbstract
{

    /**
     * 创建单节点服务端
     * @param serverName　服务端名
     * @param port　端口 小于1，则用默认 9999
     * @param isLong　是否是长连接
     * @param nThreads　mina线程数据: 小于 1,则用默认虚拟机返回可用处理器的数目*10
     * @param TODO cacheType　缓存库: 0=本地EHCache,1=redis,2...待补充
     */
    public ClusterTransportServerFactory(String serverName, int port, boolean isLong, int nThreads, int cacheType)
    {
        this.distributionServer = new DistributionServerImpl(serverName, port, isLong, nThreads, 1, cacheType);
    }
}
