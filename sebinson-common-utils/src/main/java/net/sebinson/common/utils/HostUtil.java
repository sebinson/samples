package net.sebinson.common.utils;

import java.net.InetAddress;

public class HostUtil {
    private static InetAddress inetAddress = null;
    private static String hostAddress = null;
    private static String hostName = null;

    static {
        try {
            inetAddress = PubUtil.getLocalAddress();
            hostAddress = inetAddress.getHostAddress();
            hostName = inetAddress.getHostName();
        } catch (Exception e) {
        }
    }

    public static void init(InetAddress address) {
        inetAddress = address;
        try {
            hostAddress = inetAddress.getHostAddress();
            hostName = inetAddress.getHostName();
        } catch (Exception e) {
        }
    }

    public static InetAddress getInetAddress() {
        return inetAddress;
    }

    public static String getHostAddress() {
        return hostAddress;
    }

    public static String getHostName() {
        return hostName;
    }

}
