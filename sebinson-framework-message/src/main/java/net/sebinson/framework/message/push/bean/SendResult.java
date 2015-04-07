package net.sebinson.framework.message.push.bean;

import net.sebinson.framework.message.transport.protocol.RemoteCommand;

/**
 * 异步调用返回结果
 * 
 * @author jmb
 *
 */
public class SendResult<T> {
    /** 发送的消息 */
    private GAGMessage<T> requestMsg;

    /** 返回的报文，如没有返回，就为空 */
    private RemoteCommand response;

    public GAGMessage<T> getRequestMsg() {
        return this.requestMsg;
    }

    public void setRequestMsg(GAGMessage<T> requestMsg) {
        this.requestMsg = requestMsg;
    }

    public RemoteCommand getResponse() {
        return this.response;
    }

    public void setResponse(RemoteCommand response) {
        this.response = response;
    }

}
