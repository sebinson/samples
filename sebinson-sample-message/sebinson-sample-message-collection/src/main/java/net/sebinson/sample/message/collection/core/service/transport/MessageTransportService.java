package net.sebinson.sample.message.collection.core.service.transport;

import net.sebinson.sample.message.collection.core.service.CollectionBaseService;

public interface MessageTransportService extends CollectionBaseService {

    /**
     * 业务处理入口
     * 
     * @param tag
     * @param message
     */
    void processMessage(String tag, Object message);

}
