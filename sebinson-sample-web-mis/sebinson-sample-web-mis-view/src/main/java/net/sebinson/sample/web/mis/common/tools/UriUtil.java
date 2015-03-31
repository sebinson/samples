package net.sebinson.sample.web.mis.common.tools;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import net.sebinson.common.utils.StringUtil;

public class UriUtil {

    public static void joinOperAccessUrls(String menuUri, String operActions, List<String> accessUrls) {

        if (menuUri == null || operActions == null || accessUrls == null) {
            return;
        }
        String[] actions = operActions.split(",");
        String menuUrL = StringUtil.substringBeforeLast(menuUri, "/");
        for (String action : actions) {
            if (action.startsWith("/"))
                accessUrls.add(action);
            else
                accessUrls.add(menuUrL + "/" + action);
        }
    }

    public static String getReqUri(String reqUri) throws MalformedURLException {
        URL url = new URL(reqUri);
        String path = url.getPath();
        return path;
    }

}
