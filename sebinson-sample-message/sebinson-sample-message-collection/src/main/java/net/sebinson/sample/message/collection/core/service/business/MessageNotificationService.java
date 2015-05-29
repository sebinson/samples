package net.sebinson.sample.message.collection.core.service.business;

import net.sebinson.framework.message.push.bean.GAGMessage;
import net.sebinson.sample.message.collection.core.service.CollectionBaseService;
import net.sebinson.sample.message.collection.exceptions.CollectionMessageNotificationException;

/**
 * 
 * 
 * @author C
 */
public interface MessageNotificationService extends CollectionBaseService {

    /**
     * @throws CollectionMessageNotificationException 
     * 
     */
    public void notifyMQMessage(GAGMessage<Object> message) throws CollectionMessageNotificationException;
}
