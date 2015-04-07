package net.sebinson.framework.message.push.service.impl;

import net.sebinson.common.utils.TimeUtil;
import net.sebinson.framework.message.common.ClientInfoMsg;
import net.sebinson.framework.message.push.ConstantDistributed;
import net.sebinson.framework.message.push.service.HistoryDataService;
import net.sebinson.framework.message.push.service.ShareSessionMappingService;
import net.sebinson.framework.message.transport.exception.TransportException;
import net.sebinson.framework.message.transport.log.TransportLog;
import net.sebinson.framework.message.transport.processor.LoginDistributedProcessor;
import net.sebinson.framework.message.transport.processor.LoginProcessor;
import net.sebinson.framework.message.transport.protocol.RemoteCommand;

/**
 *  登录的处理
 *  1:映射数据写入共享 2：历史数据的处理
 * @author jmb
 *
 */
public class LoginProcessorDistribution implements LoginProcessor, LoginDistributedProcessor
{
    //处理登录
    private LoginProcessor loginProcessor;

    //处理历史数据 
    private HistoryDataService historyDataService;

    //处理映射
    private ShareSessionMappingService shareSessionMappingService;

    @Override
    public Object[] processLoginRequestSync(RemoteCommand request) throws TransportException
    {
        return this.loginProcessor.processLoginRequestSync(request);
    }

    @Override
    public void processShareSession(long sessionId, String add, ClientInfoMsg clientInfoMsg)
    {
        //处理映射
        //key=客户端用户ID_服务器IP_sessionId value=yyyyyMMddHHmmssSSS 分布式模式 key要模糊查询，否则，就不能加sessionId
        //key=客户端用户ID 本地模式
        String key = add + "_" + ConstantDistributed.LOCAL_IP + "_" + sessionId;
        this.shareSessionMappingService.setMapping(key, TimeUtil.getCurrentDateTime("yyyyMMddHHmmssSSS"), clientInfoMsg.getMonitertime() * 5);//监控时间的5倍
        TransportLog.info("写入到库的映射数据key是[" + key + "]");
    }

    @Override
    public void processHistoryData(long sessionId, String add, ClientInfoMsg clientInfoMsg)
    {
        //处理历史数据
        String key = add + "_" + ConstantDistributed.LOCAL_IP + "_" + sessionId;
        this.historyDataService.invokeHistoryData(sessionId, add, clientInfoMsg);
        TransportLog.info("处理历史数据信息是[" + key + "]");
    }

    @Override
    public void removeShareSession(long sessionId, String add)
    {
        //删除映射
        //key=客户端用户ID_服务器IP_sessionId value=yyyyyMMddHHmmssSSS
        String key = add + "_" + ConstantDistributed.LOCAL_IP + "_" + sessionId;
        this.shareSessionMappingService.removeMaping(key);
        TransportLog.info("删除映射key是[" + key + "]");
    }

    public LoginProcessor getLoginProcessor()
    {
        return this.loginProcessor;
    }

    public void setLoginProcessor(LoginProcessor loginProcessor)
    {
        this.loginProcessor = loginProcessor;
    }

    public HistoryDataService getHistoryDataService()
    {
        return this.historyDataService;
    }

    public void setHistoryDataService(HistoryDataService historyDataService)
    {
        this.historyDataService = historyDataService;
    }

    public ShareSessionMappingService getShareSessionMappingService()
    {
        return this.shareSessionMappingService;
    }

    public void setShareSessionMappingService(ShareSessionMappingService shareSessionMappingService)
    {
        this.shareSessionMappingService = shareSessionMappingService;
    }

}
