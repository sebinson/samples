package net.sebinson.framework.message.transport.mina;

import java.io.Serializable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import net.sebinson.framework.message.common.SemaphoreOnce;
import net.sebinson.framework.message.transport.InvokeCallback;
import net.sebinson.framework.message.transport.protocol.RemoteCommand;

/**
 * 异步请求应答数据
 *
 */
public class ResponseFuture implements Serializable {
    private static final long      serialVersionUID        = -9080809215794478434L;

    private volatile RemoteCommand responseCommand;                                      // 应答数据
                                                                                         // 定时任务与应答同时处理
    private volatile boolean       sendRequestOK           = true;                       // 是否发送成功
                                                                                         // 定时任务与应答同时处理
    private volatile Throwable     throwable;                                            // 异常信息
                                                                                         // 定时任务与应答同时处理

    private final String           serial;                                               // 流水号
    private final long             sendTimeoutMillis;
    private final long             beginSendTime           = System.currentTimeMillis();
    private final InvokeCallback   callback;                                            // 回调函数

    private final CountDownLatch   countDownLatch          = new CountDownLatch(1);      // 同步处理用
    private final SemaphoreOnce    semaphoreOnce;                                        // 保证信号量至多至少只被释放一次
    private final AtomicBoolean    executeCallbackOnlyOnce = new AtomicBoolean(false);  // 保证回调的callback方法至多至少只被执行一次

    public ResponseFuture(String serial, long sendTimeoutMillis, InvokeCallback callback, SemaphoreOnce semaphoreOnce) {
        super();
        this.serial = serial;
        this.sendTimeoutMillis = sendTimeoutMillis;
        this.callback = callback;
        this.semaphoreOnce = semaphoreOnce;
    }

    /**
     * 执行InvokeCallback回调函数
     */
    public void excuteInvokeCallback() {
        if (this.callback != null) {
            if (this.executeCallbackOnlyOnce.compareAndSet(false, true)) {
                this.callback.operationComplete(this);
            }
        }
    }

    /**
     * 释放计数信号量资源
     */
    public void release() {
        if (this.semaphoreOnce != null) {
            this.semaphoreOnce.release();
        }
    }

    /**
     * 应答是否超时
     * 
     * @return true 超时，false 没超时
     */
    public boolean isTimeout() {
        long diff = this.beginSendTime + this.sendTimeoutMillis;
        return System.currentTimeMillis() > diff;
    }

    /**
     * 同步应答，线程阻塞一定时间
     */
    public RemoteCommand waitResponse(long waitMills) throws InterruptedException {
        this.countDownLatch.await(waitMills, TimeUnit.MILLISECONDS);
        return this.responseCommand;
    }

    /**
     * 同步应答，线程阻塞 发送失败时，不用再线程阻塞了
     */
    public void putResponseCommand(RemoteCommand responseCommand) {
        this.responseCommand = responseCommand;
        this.countDownLatch.countDown();
    }

    public long getBeginSendTime() {
        return this.beginSendTime;
    }

    public boolean isSendRequestOK() {
        return this.sendRequestOK;
    }

    public void setSendRequestOK(boolean sendRequestOK) {
        this.sendRequestOK = sendRequestOK;
    }

    public long getSendTimeoutMillis() {
        return this.sendTimeoutMillis;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public RemoteCommand getResponseCommand() {
        return this.responseCommand;
    }

    public void setResponseCommand(RemoteCommand responseCommand) {
        this.responseCommand = responseCommand;
    }

    public String getSerial() {
        return this.serial;
    }

    public InvokeCallback getCallback() {
        return this.callback;
    }

    @Override
    public String toString() {
        return "ResponseFuture [responseCommand=" + this.responseCommand + ", sendRequestOK=" + this.sendRequestOK + ", throwable=" + this.throwable
                + ", serial=" + this.serial + ", sendTimeoutMillis=" + this.sendTimeoutMillis + ", callback=" + this.callback + ", beginSendTime="
                + this.beginSendTime + ", countDownLatch=" + this.countDownLatch + "]";
    }

}
