package net.sebinson.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author C
 *
 */
public class UUIDUtil {

    private static final Logger logger = LoggerFactory.getLogger(UUIDUtil.class);

    private final static String mac = NUM32(Long.valueOf(GetMacs(), 16), 10);
    private final static String pid = NUM32(PID(), 3);
    private static int point1 = new Random(new Object().hashCode()).nextInt(1023);
    private static int point2 = new Random(new Object().hashCode()).nextInt(32766);

    public static String getUUID() {
        String millis = NUM32(System.currentTimeMillis(), 9);
        String nanoTime = NUM32(System.nanoTime(), 5);
        String srtPoint1 = NUM32(getPoint1(), 2);
        String srtPoint2 = NUM32(getPoint2(), 3);
        return millis + nanoTime + mac + srtPoint1 + pid + srtPoint2;
    }

    private static String NUM32(long l, int wide) {
        String or = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] cs = new char[wide];

        while (wide > 0) {
            int surplus = (int) (l & 31);
            cs[wide - 1] = or.charAt(surplus);
            l = l >> 5;
            wide--;
        }
        return new String(cs);
    }

    private static synchronized int getPoint1() {
        if (point1 > 1023) {
            point1 = 0;
        }
        point1++;
        return point1;
    }

    private static synchronized int getPoint2() {
        if (point2 > 32766) {
            point2 = 0;
        }
        point2++;
        return point2;
    }

    private static String getMACAddress(String commond, String regular) throws Exception {
        Pattern pattern = Pattern.compile(regular); // windows ..-..-..-..-..-..
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(commond);
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    mac = matcher.group().replaceAll("\\W", "");
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("UUID get mac error!", e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e1) {
                logger.error("UUID get mac error!", e1);

            } finally {
                bufferedReader = null;
                process = null;
            }
        }
        if (mac == null || mac.isEmpty()) {
            throw new Exception("cat get the mac");
        }
        return mac;
    }

    private static long PID() {
        long pid = 0;
        try {
            String runtime = ManagementFactory.getRuntimeMXBean().getName();
            String strPid = runtime.split("@")[0];
            pid = Long.parseLong(strPid);
        } catch (Exception e) {
            pid = new Random().nextInt(new Object().hashCode());
        }
        return pid;
    }

    private static String GetMacs() {
        String mac = null;
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.startsWith("windows")) {
                mac = getMACAddress("getmac", "..-..-..-..-..-..");
            } else {
                mac = getMACAddress("ifconfig", "..:..:..:..:..:..");
            }
        } catch (Exception e) {
            InetAddress addr;
            try {
                addr = InetAddress.getLocalHost();
                mac = addr.getHostAddress().replaceAll("\\W", "");
            } catch (UnknownHostException u) {
                logger.error("UUID get mac error!", u);
            }
        }
        return mac;
    }
}