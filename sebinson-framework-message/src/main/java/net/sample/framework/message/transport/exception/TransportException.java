package net.sample.framework.message.transport.exception;

public class TransportException extends Exception {
    private static final long serialVersionUID = -7172944271217490301L;

    //unknown exception
    public static final String EORROR_TRANSPORT = "t000";

    //server exception
    public static final String EORROR_TRANSPORT_SERVER = "t001";
    
    //client exception
    public static final String EORROR_TRANSPORT_CLIENT = "t002";
    
    //interface encode not supported
    public static final String EORROR_TRANSPORT_ITYPE = "t003";

    //package validation exception
    public static final String EORROR_COMMAND = "t100";
    
    //package encode exception
    public static final String EORROR_COMMANDPROTOCOL = "t101";

    //connection exception
    public static final String EORROR_CONNECT = "t200";
    
    /** 无效连接异常 */
    public static final String EORROR_CONNECT_ILLEGAL = "t201";

    /** 发送报文异常 */
    public static final String EORROR_SENDREQUSET = "t300";
    /** 短连接，服务端不能主动数据异常 */
    public static final String EORROR_SENDREQUSET_SERVER_ISSHORT_SOCKET = "t301";

    /** 发送报文超时 */
    public static final String EORROR_TIMEOUT = "t400";

    /** 发送报文请求太多 */
    public static final String EORROR_TOOMUCHREQUEST = "t500";

    /** 同步请求返回为null */
    public static final String EORROR_RESPONSE_SYNC_NULL = "t601";
    /** 登录返回为空 */
    public static final String EORROR_RESPONSE_LOGIN_NULL = "t602";

    // 自定义错误编码
    private String errorCode;

    public TransportException(String errorCode, String message) {
        super("errorCode[" + errorCode + "]" + message);
        this.errorCode = errorCode;
    }

    public TransportException(String errorCode, String message, Throwable cause) {
        super("errorCode[" + errorCode + "]" + message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "TransportException [errorCode=" + this.errorCode + ",cause=" + super.toString() + "]";
    }

}
