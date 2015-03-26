package net.sebinson.common.cache.session.zookeeper;

import java.util.List;

import org.I0Itec.zkclient.ZkClient;

import net.sebinson.common.cache.session.AbstractSessionManager;
import net.sebinson.common.cache.session.SessionManager;
import net.sebinson.common.cache.session.SessionMeta;

public class ZkSessionManager extends AbstractSessionManager {

    private static class ZkSessionManagerGet {
        private static final SessionManager instance = new ZkSessionManager();
    }

    public static SessionManager getInstance() {
        return ZkSessionManagerGet.instance;
    }

    @Override
    public void loadSession() {
        ZkClient client = ZkConnection.getInstance();
        List<String> sessionList = client.getChildren(ZkSessionHelper.root);
        for (int i = 0, len = sessionList.size(); i < len; i++) {
            String sid = sessionList.get(i);
            SessionMeta meta = client.readData(ZkSessionHelper.root + "/" + sid);
            ZkSession session = new ZkSession();
            session.setId(sid);
            session.setMeta(meta);
            List<String> attributeList = client.getChildren(ZkSessionHelper.root + "/" + sid);
            for (int j = 0, size = attributeList.size(); j < size; j++) {
                String name = attributeList.get(j);
                Object value = client.readData(ZkSessionHelper.root + "/" + sid + "/" + name);
                session.setAttributeLocal(name, value);
            }
            SessionManager sessionManager = ZkSessionManager.getInstance();
            sessionManager.addSession(session, sid);
        }
    }
}
