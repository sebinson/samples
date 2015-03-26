package net.sebinson.common.cache.session.zookeeper;

import java.util.Date;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;

import net.sebinson.common.cache.session.SessionMeta;

public class ZkSessionCleaner extends Thread {
    @Override
    public void run() {
        ZkClient client = ZkConnection.getInstance();
        while (true) {
            List<String> sessionList = client.getChildren(ZkSessionHelper.root);
            for (int i = 0, len = sessionList.size(); i < len; i++) {
                String sid = sessionList.get(i);
                SessionMeta meta = client.readData(ZkSessionHelper.root + "/" + sid);
                if ((new Date().getTime() - meta.getLastAccessedTime()) > meta.getMaxInactiveInterval()) {

                    client.deleteRecursive(ZkSessionHelper.root + "/" + sid);
                }
            }
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
            }
        }
    }
}


