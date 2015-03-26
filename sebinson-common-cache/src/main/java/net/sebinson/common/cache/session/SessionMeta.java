package net.sebinson.common.cache.session;

import java.io.Serializable;

public class SessionMeta implements Serializable {

    private static final long serialVersionUID = -1097400058462899313L;

    private String sid;
    private long createTime;
    private long lastAccessedTime;
    private int maxInactiveInterval;
    private boolean isNew;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    public void setLastAccessedTime(long lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    public void setMaxInactiveInterval(int maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }
}
