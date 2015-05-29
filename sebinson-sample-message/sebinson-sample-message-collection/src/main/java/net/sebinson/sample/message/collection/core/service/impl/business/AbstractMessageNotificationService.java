package net.sebinson.sample.message.collection.core.service.impl.business;

import net.sebinson.framework.message.push.bean.GAGMessage;
import net.sebinson.sample.message.collection.core.service.business.MessageNotificationService;
import net.sebinson.sample.message.collection.exceptions.CollectionMessageNotificationException;

public abstract class AbstractMessageNotificationService implements MessageNotificationService {

    @Override
    public void notifyMQMessage(GAGMessage<Object> message) throws CollectionMessageNotificationException {

        throw new CollectionMessageNotificationException("", "");
    }

}
