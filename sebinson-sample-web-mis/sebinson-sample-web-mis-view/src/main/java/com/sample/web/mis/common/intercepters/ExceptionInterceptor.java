package com.sample.web.mis.common.intercepters;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <b>异常处理拦截器,处理经处理器（控制器）抛出的所有异常
 */
public class ExceptionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex != null) {
            this.logger(request, handler, ex);
        } else {
            super.afterCompletion(request, response, handler, ex);
        }
    }

    /**
     * 记录异常日志
     */
    private void logger(HttpServletRequest request, Object handler, Exception ex) {
        StringBuffer msg = new StringBuffer();
        msg.append("异常拦截日志:");
        msg.append("[uri＝").append(request.getRequestURI()).append("]");
        Enumeration<String> enumer = request.getParameterNames();
        while (enumer.hasMoreElements()) {
            String name = enumer.nextElement();
            String[] values = request.getParameterValues(name);
            msg.append("[").append(name).append("=");
            if (values != null) {
                int i = 0;
                for (String value : values) {
                    i++;
                    msg.append(value);
                    if (i < values.length) {
                        msg.append("｜");
                    }
                }
            }
            msg.append("]");
        }
    }
}
