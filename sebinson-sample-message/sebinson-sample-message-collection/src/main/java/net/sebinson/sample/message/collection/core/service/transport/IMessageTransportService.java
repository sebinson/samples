package net.sebinson.sample.message.collection.core.service.transport;

import net.sebinson.sample.message.collection.core.service.ICollectionBaseService;

public interface IMessageTransportService extends ICollectionBaseService {

    void processMessage(String tag, Object message);
}
