package net.sebinson.common.log;

import java.util.Timer;
import java.util.TimerTask;

import net.sebinson.common.http.HttpClientUtil;

import org.apache.log4j.helpers.LogLog;

public class LogConfiguration {

    public static final String SPLIT_CHAR      = "_";

    private static String      flume           = "";
    private static String      flumeDir        = "flume";
    private static int         intervalSencond = 86400;  //24小时

    static {
        initValues();
        startTimer();
    }

    private static void initValues() {
        try {
            String flume_new = HttpClientUtil.loadFileStringByUrl("http://html.sebinson.net/flume");
            if (flume.endsWith(flume_new)) {
                return;
            }
            flume = flume_new;
            String[] fs = flume.split("@");
            if (!"".equals(fs[0])) {
                intervalSencond = Integer.parseInt(fs[0]);
            }
            if (fs.length > 1 && !"".equals(fs[1])) {
                flumeDir = fs[1];
            }
        } catch (Exception e) {
            intervalSencond = 86400;//24小时
            flumeDir = "flume";
            LogLog.error("Init the flume properties of the TimeRollingAppender fail! flume=" + flume);
        }
    }

    private static void startTimer() {
        try {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    initValues();
                }
            }, 1800000, 1800000);
        } catch (Exception e) {
            LogLog.error("TimeRollingAppender timer start fail!");
        }
    }

    public static int getIntervalSencond() {
        return intervalSencond;
    }

    public static String getFlumeDir() {
        return flumeDir;
    }
}
