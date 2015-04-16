package net.sebinson.sample.message.collection.core.service.message;

import net.sebinson.sample.message.collection.core.service.ICollectionBaseService;

public interface IObtainBaseService extends ICollectionBaseService {

    /**
     * 业务处理入口
     * 
     * @param tag
     * @param message
     */
    void processMessage(String tag, Object message);

}
