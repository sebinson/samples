package net.sebinson.sample.message.collection.core.service.message;

import net.sebinson.sample.message.collection.core.service.CollectionBaseService;

public interface MessageProcessService extends CollectionBaseService {

    /**
     * 业务处理入口
     * 
     * @param tag
     * @param message
     */
    void processMessage(String tag, Object message);

}
