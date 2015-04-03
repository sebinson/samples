package net.sebinson.common.cache.session.zookeeper;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sebinson.common.cache.session.AbstractSession;
import net.sebinson.common.cache.session.AbstractSessionChangeListener;
import net.sebinson.common.cache.session.AbstractSessionFilter;
import net.sebinson.common.cache.session.SessionManager;
import net.sebinson.common.cache.session.SidGenerator;

import org.I0Itec.zkclient.ZkClient;

public class ZkSessionFilter extends AbstractSessionFilter {

    private void newSession(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = new ZkSession();
        String sid = SidGenerator.generateSid();
        ((AbstractSession) session).setId(sid);
        ZkSessionManager.getInstance().addSession(session, sid);
        ZkSessionHelper.addSession(((AbstractSession) session).getMeta());
        Cookie cookie = new Cookie("sid", sid);
        cookie.setMaxAge((int) AbstractSessionChangeListener.getTimeout());
        response.addCookie(cookie);
        request.setAttribute("sid", sid);

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response) {
        SessionManager sessionManager = ZkSessionManager.getInstance();
        String sid = sessionManager.getSidByCookie(request);
        if (sid == null || sid.trim().equals("")) {
            newSession(request, response);
        } else {
            AbstractSession session = (AbstractSession) sessionManager.getSession(sid);
            if (session != null) {
                session.setLastAccessedTime(new Date().getTime());
                ZkClient client = ZkConnection.getInstance();
                client.writeData(ZkSessionHelper.root + "/" + sid, session.getMeta());
            } else {
                newSession((HttpServletRequest) request, (HttpServletResponse) response);
            }
        }
    }
}
