package net.sebinson.common.cache.session.zookeeper;

import org.I0Itec.zkclient.ZkClient;

import net.sebinson.common.configration.cache.zookeeper.CacheHelper;

public class ZkConnection {
        
    private volatile static ZkClient zkClient = null;

    public static ZkClient getInstance() {
        if (zkClient == null) {
            synchronized (ZkConnection.class) {
                if (zkClient == null) {
                    zkClient = new ZkClient(CacheHelper.CACHE_ZK_SERVER_DEFAULT);
                }
            }
        }
        return zkClient;
    }
}
