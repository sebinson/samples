package net.sample.framework.message.push.service;

import net.sample.framework.message.push.bean.SendResult;

public interface SendCallBack
{
    /**发送成功*/
    public <T> void onSuccess(final SendResult<T> requset);

    /**发生了异常exception*/
    public <T> void onException(final SendResult<T> requset, final Throwable e);
}
