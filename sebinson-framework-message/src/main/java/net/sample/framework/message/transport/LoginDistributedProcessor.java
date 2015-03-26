package net.sample.framework.message.transport;

import net.sample.framework.message.common.ClientInfoMsg;

/**
 * 处理分布式登录请求
 *
 */
public interface LoginDistributedProcessor
{
    /**
     * 1:映射数据写入共享,监控汇报时也会更新缓存
     * @param ClientInfoMsg ClientInfoMsg
     * */
    public void processShareSession(long sessionIdn, String add, ClientInfoMsg clientInfoMsg);

    /**
     * 2：历史数据的处理
     * @param ClientInfoMsg ClientInfoMsg
     * */
    public void processHistoryData(long sessionId, String add, ClientInfoMsg clientInfoMsg);

    /**
     * 1:删除映射数据
     * @param ClientInfoMsg ClientInfoMsg
     * */
    public void removeShareSession(long sessionId, String add);

}
