package net.sebinson.sample.web.common.tools;

import javax.servlet.http.HttpServletRequest;

/**
 * Session 工具类
 * 
 * @author C
 */
public final class SessionUtil {
    /**
     * 设置session的值
     * 
     * @param request
     * @param key
     * @param value
     */
    public static void setAttribute(HttpServletRequest request, String key, Object value) {
        request.getSession(true).setAttribute(key, value);
    }

    /**
     * 获取session的值
     * 
     * @param request
     * @param key
     * @param value
     */
    public static Object getAttribute(HttpServletRequest request, String key) {
        return request.getSession(true).getAttribute(key);
    }

    /**
     * 删除Session值
     * 
     * @param request
     * @param key
     */
    public static void removeAttribute(HttpServletRequest request, String key) {
        request.getSession(true).removeAttribute(key);
    }
}