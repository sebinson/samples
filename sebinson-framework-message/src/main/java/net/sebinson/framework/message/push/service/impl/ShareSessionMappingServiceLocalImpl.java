package net.sebinson.framework.message.push.service.impl;

import net.sebinson.framework.message.push.service.ShareSessionMappingService;
import net.sebinson.framework.message.transport.mina.MinaTransportServer;

/**
 * 本地处理映射数据 本地的映射没有过期时间，直接复用MinaServerHandler.addsMap
 * 如果要有过期时间，则新加一个EHCache来处理。但MinaServerHandler.addsMap得保留，不然发不了数据
 * 
 * @author jmb
 *
 */
public class ShareSessionMappingServiceLocalImpl implements ShareSessionMappingService {

    private MinaTransportServer minaTransportServer;

    public ShareSessionMappingServiceLocalImpl(MinaTransportServer minaTransportServer) {
        super();
        this.minaTransportServer = minaTransportServer;
    }

    @Override
    public void setMapping(String key, String value, long expireTime) {
        // 本地的不处理，见　MinaServerHandler.addsMap
    }

    @Override
    public String getMapping(String add) {
        Long sessionId = this.minaTransportServer.getSessonId(add);// 返回sessionId
        if (sessionId == null) {
            return null;
        }
        return sessionId + "";
    }

    @Override
    public void removeMaping(String key) {
        // 本地的不处理，见　MinaServerHandler.addsMap
    }

}
