package net.sebinson.common.cache.session.zookeeper;

import org.I0Itec.zkclient.ZkClient;

import net.sebinson.common.cache.session.SessionMeta;
import net.sebinson.common.cache.zookeeper.CacheHelper;

public class ZkSessionHelper {
    public static final String root = CacheHelper.CACHE_ROOT_DEFAULT+"/session";

    public static void setAttribute(String sid, String name, Object value) {
        ZkClient client = ZkConnection.getInstance();
        if (!client.exists(root + "/" + sid + "/" + name)) {
            client.createPersistent(root + "/" + sid + "/" + name);
        }
        client.writeData(root + "/" + sid + "/" + name, value);
    }

    public static void removeAttribute(String sid, String name) {
        ZkClient client = ZkConnection.getInstance();
        if (client.exists(root + "/" + sid + "/" + name)) {
            client.delete(root + "/" + sid + "/" + name);
        }
    }

    public static void addSession(SessionMeta meta) {
        ZkClient client = ZkConnection.getInstance();
        client.createPersistent(root + "/" + meta.getSid());
        client.writeData(root + "/" + meta.getSid(), meta);
    }
}
