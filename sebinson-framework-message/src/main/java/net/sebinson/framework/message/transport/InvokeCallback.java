package net.sebinson.framework.message.transport;

import net.sebinson.framework.message.transport.mina.ResponseFuture;

/**
 * 异步调用应答回调接口
 *
 */
public interface InvokeCallback {
    /**
     * 异步数据发送成功后，回调此接口
     * 
     * @param responseFuture
     */
    public void operationComplete(ResponseFuture responseFuture);
}
