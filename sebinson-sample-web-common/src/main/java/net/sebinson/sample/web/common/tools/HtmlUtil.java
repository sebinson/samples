package net.sebinson.sample.web.common.tools;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class HtmlUtil
{
    public static void writerJson(HttpServletResponse response, String jsonStr)
    {
        writer(response, jsonStr);
    }

    public static void writerJson(HttpServletResponse response, Object object)
    {
        Gson gson = new Gson();
        writer(response, gson.toJson(object));
    }

    public static void writerHtml(HttpServletResponse response, String htmlStr)
    {
        writer(response, htmlStr);
    }

    private static void writer(HttpServletResponse response, String str)
    {
        try
        {
            //设置页面不缓存
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            PrintWriter out = null;
            out = response.getWriter();
            out.print(str);
            out.flush();
            out.close();
        }
        catch (IOException e)
        {
        }
    }
}
