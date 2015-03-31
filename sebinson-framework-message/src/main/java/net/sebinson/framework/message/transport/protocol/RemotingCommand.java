package net.sebinson.framework.message.transport.protocol;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.sebinson.common.utils.JsonUtil;
import net.sebinson.framework.message.transport.exception.TransportCommandProtocolException;
import net.sebinson.framework.message.transport.exception.TransportException;

import org.springframework.util.StringUtils;

/**
 * Remoting 客户端与服务端通讯通过Remotingcommand来交互 协议 [1个字节通信类型（type
 * 1建连接;2心跳;3业务通讯）][4个字节字符串长度][4个字节byte数组长度][字符串（header+body）][byte数组(binary)]
 * 
 *
 */
public class RemotingCommand implements Serializable {
    private static final long serialVersionUID = -4101344746952294912L;

    private byte type = 3;// 通讯协议 1建连接;2心跳;3业务通讯
    private String baseinfo = "";// 原始字符串
    private byte[] binary = new byte[0];// byte数据流

    private Header header;// 报文头部
    private Map<String, Object> body;// 业务数据

    /** 解码用 */
    public void DeProtocol() throws TransportCommandProtocolException {
        try {
            RemotingCommand remotingCommand = JsonUtil.toObj(this.baseinfo, RemotingCommand.class);       
            this.header = remotingCommand.getHeader();
            this.body = remotingCommand.getBody();
        } catch (Exception e) {
            throw new TransportCommandProtocolException(TransportException.EORROR_COMMANDPROTOCOL,
                    "jsonStr parse Header and body Map<String,Object> exception. jsonStr=" + this.baseinfo, e);
        }
    }

    /** 编码码用 */
    public void EnProtocol() throws TransportCommandProtocolException {
        try {
            if (this.header == null) {
                throw new IllegalArgumentException("RemotingCommand header is null.");
            }

            Map<String, Object> h = this.javaBeanToMap(this.header);
            Map<String, Object> f = new HashMap<String, Object>();
            f.put("header", h);
            // TODO 去掉body里的null值
            if (this.body != null && !this.body.isEmpty()) {
                f.put("body", this.body);
            }
            this.baseinfo = JsonUtil.toJson(f);
        } catch (Exception e) {
            throw new TransportCommandProtocolException(TransportException.EORROR_COMMANDPROTOCOL,
                    "RemotingCommand to JSON and binary bytes exception. RemotingCommand=" + this, e);
        }
    }

    private Map<String, Object> javaBeanToMap(Object javaBean) {
        Map<String, Object> result = new HashMap<String, Object>();
        Method[] methods = javaBean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            try {
                if (method.getName().startsWith("get")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);
                    Object value = method.invoke(javaBean, new Object[0]);
                    if (value == null || !StringUtils.isEmpty(value.toString())) {
                        continue;
                    }
                    result.put(field, value);
                }

            } catch (Exception e) {
            }

        }
        return result;
    }

    public byte getType() {
        return this.type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public Header getHeader() {
        return this.header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Map<String, Object> getBody() {
        return this.body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

    public byte[] getBinary() {
        return this.binary;
    }

    public void setBinary(byte[] binary) {
        if (binary == null) {
            this.binary = new byte[0];
            return;
        }
        this.binary = binary;
    }

    public String getBaseinfo() {
        return this.baseinfo;
    }

    public void setBaseinfo(String baseinfo) {
        this.baseinfo = baseinfo;
    }

    @Override
    public String toString() {
        return "RemotingCommand [type=" + this.type + ", baseinfo=" + this.baseinfo + ",header=" + this.header
                + ", body=" + this.body + ", binary.size=" + (this.binary == null ? 0 : this.binary.length) + "]";
    }
}
