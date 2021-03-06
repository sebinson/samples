package net.sebinson.framework.message.transport.protocol;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.sebinson.common.utils.JsonUtil;
import net.sebinson.common.utils.ReflectHelper;
import net.sebinson.framework.message.transport.exception.TransportCommandProtocolException;
import net.sebinson.framework.message.transport.exception.TransportException;

public class RemotingCommand implements Serializable {
    private static final long   serialVersionUID = -4101344746952294912L;

    private byte                type             = 3;                    // 通讯协议，1连接、2心跳、3业务
    private byte[]              binary           = new byte[0];          // 通讯字节流
    private String              message          = "";                   // 通讯字符流，JSON串+32位MD5
    private String              messageNoSign    = "";                   // 通讯字符流，JSON串
    private String              sign;                                    // 签名字段
    private Header              header;
    private Map<String, Object> body;

    public void DeProtocol() throws TransportCommandProtocolException {
        try {
            int index = this.message.length() - 32;
            this.sign = this.message.substring(index);
            this.messageNoSign = this.message.substring(0, index);
            RemotingCommand remotingCommand = JsonUtil.toObj(this.messageNoSign, RemotingCommand.class);
            this.header = remotingCommand.getHeader();
            this.body = remotingCommand.getBody();
        } catch (Exception e) {
            throw new TransportCommandProtocolException(TransportException.EORROR_COMMANDPROTOCOL,
                    "Message parse Header and Body Map<String,Object> exception. message=" + this.message, e);
        }
    }

    public void EnProtocol() throws TransportCommandProtocolException {

        if (this.messageNoSign == null || "".equals(this.messageNoSign)) {
            this.messageNoSign = buildMessageNoSign();
        }
        if (this.sign == null || "".equals(this.sign)) {
            throw new TransportCommandProtocolException(TransportException.EORROR_NO_SIGN, "No sign information exception. message=" + this.message);
        }
        this.message = this.messageNoSign + this.sign;
    }

    public String buildMessageNoSign() {

        if (this.header == null) {
            throw new IllegalArgumentException("RemotingCommand header is null.");
        }
        Map<String, Object> h = ReflectHelper.javaBeanToMap(this.header);
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("header", h);
        // TODO 去掉body里的null值
        if (this.body != null && !this.body.isEmpty()) {
            m.put("body", this.body);
        }
        return this.messageNoSign = JsonUtil.toJson(m);
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageNoSign() {
        return messageNoSign;
    }

    public void setMessageNoSign(String messageNoSign) {
        this.messageNoSign = messageNoSign;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public byte[] getBinary() {
        return binary;
    }

    public void setBinary(byte[] binary) {
        this.binary = binary;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "RemotingCommand [type=" + type + ", message=" + message + ", messageNoSign=" + messageNoSign + ", sign=" + sign + ", binary="
                + Arrays.toString(binary) + ", header=" + header + ", body=" + body + "]";
    }
}
