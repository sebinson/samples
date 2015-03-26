package net.sebinson.common.cache.session;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface SessionManager {

    public void addSession(HttpSession session, String sid);

    public HttpSession getSession(String sid);

    public Map<String, Object> getAllSession();

    public String getSidByCookie(HttpServletRequest request);

    public void loadSession();

}
