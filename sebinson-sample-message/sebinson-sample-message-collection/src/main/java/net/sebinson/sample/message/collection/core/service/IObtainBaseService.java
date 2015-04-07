package net.sebinson.sample.message.collection.core.service;

public interface IObtainBaseService {

    /**
     * 业务处理入口
     * 
     * @param tag
     * @param message
     */
    void processMessage(String tag, Object message);

}
