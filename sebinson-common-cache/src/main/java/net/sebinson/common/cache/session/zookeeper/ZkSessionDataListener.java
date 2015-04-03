package net.sebinson.common.cache.session.zookeeper;

import net.sebinson.common.cache.session.SessionMeta;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class ZkSessionDataListener implements IZkDataListener {

    @Override
    public void handleDataChange(String arg0, Object arg1) throws Exception {
        ZkClient client = ZkConnection.getInstance();
        String sid = arg0.substring(arg0.lastIndexOf("/") + 1);
        ZkSession session = (ZkSession) ZkSessionManager.getInstance().getSession(sid);
        SessionMeta meta = client.readData(arg0);
        session.setMeta(meta);

    }

    @Override
    public void handleDataDeleted(String arg0) throws Exception {

    }
}
