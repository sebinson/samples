package net.sebinson.sample.web.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sebinson.sample.web.common.beans.Message;
import net.sebinson.sample.web.common.tools.HtmlUtil;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

public class BaseController {

    HttpServletRequest request;
    HttpServletResponse response;

    // 请求线程
    private static ThreadLocal<HttpServletRequest> request_threadLocal = new ThreadLocal<HttpServletRequest>();

    // 响应线程
    private static ThreadLocal<HttpServletResponse> reponse_threadLocal = new ThreadLocal<HttpServletResponse>();

    public static void setRequest(HttpServletRequest request) {
        request_threadLocal.set(request);
    }

    public static HttpServletRequest getRequest() {
        return request_threadLocal.get();
    }

    public static void removeRequest() {
        request_threadLocal.remove();
    }

    public static void setResponse(HttpServletResponse response) {
        reponse_threadLocal.set(response);
    }

    public static HttpServletResponse getResponse() {
        return reponse_threadLocal.get();
    }

    public static void removeResponse() {
        reponse_threadLocal.remove();
    }

    public static ModelAndView forword(String viewName, Map<String, Object> context) {
        return new ModelAndView(viewName, context);
    }

    public static void resultOperation(Message messageData, HttpServletResponse response) {
        if (messageData != null && !StringUtils.hasText(messageData.getMsg())) {
            if (messageData.isSuccess()) {
                messageData.setMsg("操作成功");
            } else {
                messageData.setMsg("操作失败");
            }
        }

        HtmlUtil.writerJson(response, messageData);
    }

}
