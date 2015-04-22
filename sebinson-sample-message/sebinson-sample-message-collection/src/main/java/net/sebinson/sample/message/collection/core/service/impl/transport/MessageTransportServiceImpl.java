package net.sebinson.sample.message.collection.core.service.impl.transport;

import net.sebinson.framework.message.push.bean.GAGMessage;
import net.sebinson.framework.message.push.bean.SendResult;
import net.sebinson.framework.message.push.service.SendCallBack;
import net.sebinson.framework.message.push.util.ServerSendMessageUtil;
import net.sebinson.sample.message.collection.core.service.transport.MessageTransportService;

import org.springframework.stereotype.Service;

@Service("messageTransportService")
public class MessageTransportServiceImpl implements MessageTransportService {

    @Override
    public void processMessage(String tag, Object message) {
        if (message instanceof GAGMessage) {
            try {
                GAGMessage<?> msg = (GAGMessage<?>) message;
                //异步推送数据给采集终端
                ServerSendMessageUtil.sendMessageAsync(msg.getReceiveid()[0], msg, new SendCallBack() {
                    @Override
                    public <T> void onSuccess(SendResult<T> requset) {
                        //消息发送成功后实现业务处理
                    }

                    @Override
                    public <T> void onException(SendResult<T> requset, Throwable e) {
                        // 消息发送出现异常后实现业务处理
                    }
                });
            } catch (Exception e) {
            }
        } else {
        }
    }
}
