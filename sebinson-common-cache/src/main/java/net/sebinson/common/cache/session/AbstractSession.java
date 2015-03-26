package net.sebinson.common.cache.session;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

@SuppressWarnings("deprecation")
public abstract class AbstractSession implements HttpSession {
    private SessionMeta meta;

    public AbstractSession() {
        meta = new SessionMeta();
        meta.setCreateTime(new Date().getTime());
        meta.setLastAccessedTime(meta.getCreateTime());
        meta.setNew(true);
        meta.setMaxInactiveInterval((int) AbstractSessionChangeListener.getTimeout());
    }

    public void setLastAccessedTime(long lastAccessedTime) {
        meta.setLastAccessedTime(lastAccessedTime);
    }

    public long getCreationTime() {
        return meta.getCreateTime();
    }

    public String getId() {
        return meta.getSid();
    }

    public void setId(String sid) {
        meta.setSid(sid);
    }

    public long getLastAccessedTime() {
        return meta.getLastAccessedTime();
    }

    public ServletContext getServletContext() {
        return null;
    }

    public void setMaxInactiveInterval(int interval) {
        meta.setMaxInactiveInterval(interval);

    }

    public int getMaxInactiveInterval() {
        return meta.getMaxInactiveInterval();
    }

    public void invalidate() {

    }

    public boolean isNew() {
        return meta.isNew();
    }

    public SessionMeta getMeta() {
        return meta;
    }

    public void setMeta(SessionMeta meta) {
        this.meta = meta;
    }

    public HttpSessionContext getSessionContext() {
        return null;
    }
}
