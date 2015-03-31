package com.sample.web.mis.common.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sebinson.sample.web.common.tools.SessionUtil;

import com.sample.web.mis.constants.WebConstants;
import com.sample.web.mis.persistence.domain.SampleUser;

public class AuthUtil {

    public static SampleUser getUser(HttpServletRequest request) {

        Object obj = SessionUtil.getAttribute(request, WebConstants.SESSION_SAMPLE_USER);
        if (obj != null && obj instanceof SampleUser) {
            return (SampleUser) obj;
        }
        return null;
    }

    public static boolean isAccessUrl(HttpServletRequest request, String url) {
        List<?> accessUrls = null;
        Object object = SessionUtil.getAttribute(request, WebConstants.SESSION_ACCESS_URLS);
        if (object != null && object instanceof ArrayList<?>) {
            accessUrls = (ArrayList<?>) object;
        }
        if (accessUrls == null || accessUrls.isEmpty() || !accessUrls.contains(url)) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public static List<String> getMemuOperList(HttpServletRequest request, String url) {
        Object object = SessionUtil.getAttribute(request, WebConstants.SESSION_ACCESS_OPER_MAP);
        if (object == null) {
            return null;
        }
        Map<?, ?> map = (HashMap<?, ?>) object;
        if (map.isEmpty()) {
            return null;
        }
        return (List<String>) map.get(url);
    }
}
