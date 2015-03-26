package net.sample.framework.message.push.util;

import net.sample.framework.message.common.ClientInfoMsg;
import net.sample.framework.message.push.bean.GAGMessage;
import net.sample.framework.message.push.service.DistributionServer;
import net.sample.framework.message.push.service.SendCallBack;
import net.sample.framework.message.transport.log.TransportLog;
import net.sample.framework.message.transport.protocol.RemotingCommand;

/**
 * 服务端主动发送报文
 * @author jmb
 *
 */
public class ServerSendMessageUtil
{
    /**主动给长连接发送数据*/
    private static DistributionServer distributionServer = null;

    /**
     * 客户端不用有应答报文
     * @param add Socket对应地址 
     * @param msg 报文
     * @throws IllegalArgumentException add或者msg为空,GAGMessage必填项错误
     */
    public static <T> void sendMessageUnreply(String add, GAGMessage<T> msg)
    {
        distributionServer.sendUnreply(add, msg);
        TransportLog.debug("SendMessageUnreply send to [" + add + "] GAGMessage, GAGMessage=" + msg);
    }

    /**客户端处理完请求后返回应答报文 
     * @param add Socket对应地址 
     * @param msg 报文
     * @return 对方的应答报文，如超时，报文过有效期，没有接收者连接等情况，返回为null
     * @throws IllegalArgumentException add，msg为空,GAGMessage必填项错误
     */
    public static <T> RemotingCommand sendMessageSync(String add, GAGMessage<T> msg)
    {
        RemotingCommand sendSync = distributionServer.sendSync(add, msg);
        //TransportLog.debug("sendMessageSync send to [" + add + "] GAGMessage, GAGMessage=" + msg + ", return RemotingCommand=" + sendSync);
        return sendSync;
    }

    /**客户端接完数据后，返回应答报文 
     * @param add Socket对应地址 
     * @param msg 报文
     * @param callBack 回调函数，可以为null
     * @throws IllegalArgumentException add或者msg为空, GAGMessage必填项错误
     */
    public static <T> void sendMessageAsync(String add, GAGMessage<T> msg, SendCallBack callBack)
    {
        distributionServer.sendAsync(add, msg, callBack);
        //TransportLog.debug("sendMessageAync send to [" + add + "] GAGMessage, GAGMessage=" + msg);
    }

    public static void setDistributionServer(DistributionServer distributionServer)
    {
        ServerSendMessageUtil.distributionServer = distributionServer;
    }

    /**
     * 获取客户信息
     * @param adds 客户端标识，如mac
     * @return ClientInfoMsg,无 null
     */
    public static ClientInfoMsg getClientInfoMsg(String adds)
    {
        return distributionServer.getClientInfoMsg(adds);
    }

}
