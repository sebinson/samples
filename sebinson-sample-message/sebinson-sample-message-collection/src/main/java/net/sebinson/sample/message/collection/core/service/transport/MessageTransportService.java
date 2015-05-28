package net.sebinson.sample.message.collection.core.service.transport;

import net.sebinson.sample.message.collection.core.service.CollectionBaseService;

public interface MessageTransportService extends CollectionBaseService {

    void processMessage(String tag, Object message);
}
