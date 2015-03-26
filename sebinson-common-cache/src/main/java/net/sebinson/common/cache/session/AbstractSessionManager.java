package net.sebinson.common.cache.session;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class AbstractSessionManager implements SessionManager {
    private Map<String, Object> sessions = new HashMap<String, Object>();

    public AbstractSessionManager() {
    }

    @Override
    public HttpSession getSession(String sid) {
        return (HttpSession) sessions.get(sid);
    }

    @Override
    public void addSession(HttpSession session, String sid) {
        this.sessions.put(sid, session);
    }
    
    @Override
    public abstract void loadSession();

    @Override
    public Map<String, Object> getAllSession() {
        return sessions;
    }

    @Override
    public String getSidByCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if ("sid".equals(cookie.getName())) {
                String sid = cookie.getValue();
                return sid;
            }
        }
        return null;
    }
}
