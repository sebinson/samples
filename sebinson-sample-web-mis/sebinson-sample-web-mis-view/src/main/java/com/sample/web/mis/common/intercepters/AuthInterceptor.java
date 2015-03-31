package com.sample.web.mis.common.intercepters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sebinson.sample.web.common.annotations.Auth;
import net.sebinson.sample.web.common.beans.Message;
import net.sebinson.sample.web.common.tools.HtmlUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sample.common.tools.StringUtil;
import com.sample.web.mis.common.tools.AuthUtil;

/**
 * 认证授权拦截器
 *
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler != null && handler instanceof HandlerMethod) {

            Auth auth = ((HandlerMethod) handler).getMethod().getAnnotation(Auth.class);
            String cp = request.getContextPath();
            String rp = request.getRequestURI();
            // 验证登陆
            if (auth == null || auth.verifyLogin()) {
                if (AuthUtil.getUser(request) == null) {// 没登录就要求登录
                    response.sendRedirect("index.do");
                    return false;
                }
            }
            // 验证授权
            if (auth == null || auth.verifyURL()) {
                if (!AuthUtil.isAccessUrl(request, StringUtil.removeStart(rp, cp).trim())) {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    Message message = new Message(false, "没有权限访问,请联系管理员.");
                    logger.info("URL权限验证不通过:[url={}][user ={}]");
                    HtmlUtil.writerJson(response, message);
                    return false;
                }
            }
        }
        return super.preHandle(request, response, handler);
    }
}
