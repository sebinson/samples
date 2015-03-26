package net.sample.framework.message.common;

import java.util.ResourceBundle;

import net.sample.framework.message.transport.log.TransportLog;

import org.springframework.util.StringUtils;

public class ConstantTransport {
    /** 通信类型1建连接 */
    public static final byte MINA_PROTOCOL_TYPE_1 = 1;

    /** 通信类型2心跳 */
    public static final byte MINA_PROTOCOL_TYPE_2 = 2;

    /** 通信类型3业务通讯 */
    public static final byte MINA_PROTOCOL_TYPE_3 = 3;

    /** GBK code */
    public static final String CODE_GBK = "GBK";
    /** UTF-8 code */
    public static final String CODE_UTF8 = "UTF-8";

    /** 服务端default ip */
    public static final String MINA_SERVER_DEFAULT_IP = "127.0.0.1";
    /** 服务端default port */
    public static final int MINA_SERVER_DEFAULT_PORT = 9999;

    // Java 虚拟机返回可用处理器的数目
    public static final int THREADS_JVM_AVAILABLEPROCESSORS = Runtime.getRuntime().availableProcessors();

    /** 发送数据包的大小 */
    public static int MINA_MAX_PACKET_SIZE_SEND = 1024 * 5;// 5K,不能小于9

    /** 接收数据包的大小 */
    public static int MINA_MAX_PACKET_SIZE_RECEIV = 1024 * 5;// 5K，即IoBuffer的大小，不能小于9

    /** 接收，发送数据包文件流Binary的最大值 */
    public static int MINA_MAX_SIZE_BINARY = 1024 * 1000 * 100;// 100M

    /** mina通讯空闲最大时间 default 5分钟 */
    public static int MINA_IDLE_SECOND = 300;

    /** 无返回应答计量数 default 2048 */
    public static int MINA_SEMAPHORE_SIZE_UNREPLY = 2048;

    /** 异步返回应答计量数 default 2048 */
    public static int MINA_SEMAPHORE_SIZE_ASYNC = 2048;

    /** mina客户端连接超时时间 default 10秒 */
    public static int MINA_CONNECT_TIMEOUT = 10 * 000;

    /** mina客户，服务端default Java虚拟机返回可用处理器的数目*10 */
    public static int MINA_THREAD_DEFAULT_COUNT = THREADS_JVM_AVAILABLEPROCESSORS * 10;

    /** 异步应答回调的线程池 default Java虚拟机返回可用处理器的数目+1 */
    public static int MINA_NTHREADS_SIZE_CALLBACK = THREADS_JVM_AVAILABLEPROCESSORS + 1;

    /** RequestProcessor处理的线程池 default Java虚拟机返回可用处理器的数目的100倍 */
    public static int MINA_NTHREADS_SIZE_PROCESSOR = THREADS_JVM_AVAILABLEPROCESSORS * 100;

    /** AsyncRequestProcessor处理的线程池 default Java虚拟机返回可用处理器的数目的80倍 */
    public static int MINA_NTHREADS_SIZE_PROCESSOR_ASYNC = THREADS_JVM_AVAILABLEPROCESSORS * 80;

    /** 发，收数据默认超时时间 default=10S */
    public static int MINA_SEND_RECEIVE_DEFAULT_TIMEOUTMILLIS = 10 * 1000;

    static {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("mina_transport");
            if (bundle != null) {
                m1(bundle, "mina_max_packet_size_send", 1, MINA_MAX_PACKET_SIZE_SEND);
                m1(bundle, "mina_max_packet_size_receiv", 1, MINA_MAX_PACKET_SIZE_RECEIV);
                m1(bundle, "mina_max_size_binary", 1, MINA_MAX_SIZE_BINARY);
                m1(bundle, "mina_idle_second", 1, MINA_IDLE_SECOND);
                m1(bundle, "mina_semaphore_size_unreply", 1, MINA_SEMAPHORE_SIZE_UNREPLY);
                m1(bundle, "mina_semaphore_size_async", 1, MINA_SEMAPHORE_SIZE_ASYNC);
                m1(bundle, "mina_connect_timeout", 1, MINA_CONNECT_TIMEOUT);
                m1(bundle, "mina_thread_default_count", 1, MINA_THREAD_DEFAULT_COUNT);
                m1(bundle, "mina_nthreads_size_callback", 1, MINA_NTHREADS_SIZE_CALLBACK);
                m1(bundle, "mina_nthreads_size_processor", 1, MINA_NTHREADS_SIZE_PROCESSOR);
                m1(bundle, "mina_send_receive_default_timeoutmillis", 1, MINA_SEND_RECEIVE_DEFAULT_TIMEOUTMILLIS);
                m1(bundle, "mina_nthreads_size_processor_async", 1, MINA_NTHREADS_SIZE_PROCESSOR_ASYNC);
            }
        } catch (Exception e) {
            TransportLog.warn(String.format("初始化mina_transport.properties异常,e[%s]", e.getMessage()));
        }
    }

    private static void m1(ResourceBundle bundle, String key, int type, Object desc)

    {
        try {
            String v = bundle.getString(key);
            if (type == 0) {
                if (!StringUtils.isEmpty(v)) {
                    desc = v;
                }
            } else if (type == 1) {
                Integer perporties = Integer.parseInt(v);
                if (perporties != null && perporties > 0) {
                    desc = perporties;
                }
            } else if (type == 2) {
                Long perporties = Long.parseLong(v);
                if (perporties != null && perporties > 0) {
                    desc = perporties;
                }
            }
        } catch (Exception e) {
            TransportLog.warn(String.format("Init ResourceBundle exception,key[%s],data type is [%s],e[%s]", key,
                    (type == 1 ? "Integer" : "other"), e.getMessage()));
        }
    }
}
