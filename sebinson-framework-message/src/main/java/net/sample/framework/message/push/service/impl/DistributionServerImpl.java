package net.sample.framework.message.push.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sample.framework.message.common.ClientInfoMsg;
import net.sample.framework.message.common.ConstantTransport;
import net.sample.framework.message.push.ConstantDistributed;
import net.sample.framework.message.push.bean.GAGMessage;
import net.sample.framework.message.push.bean.SendResult;
import net.sample.framework.message.push.cache.FutionItypeCache;
import net.sample.framework.message.push.service.DistributionServer;
import net.sample.framework.message.push.service.HistoryDataService;
import net.sample.framework.message.push.service.SendCallBack;
import net.sample.framework.message.push.service.TransportBootstrap;
import net.sample.framework.message.push.util.ServerSendMessageUtil;
import net.sample.framework.message.transport.InvokeCallback;
import net.sample.framework.message.transport.LoginProcessor;
import net.sample.framework.message.transport.RPCHook;
import net.sample.framework.message.transport.RequestProcessor;
import net.sample.framework.message.transport.exception.TransportConnectException;
import net.sample.framework.message.transport.exception.TransportException;
import net.sample.framework.message.transport.exception.TransportSendRequestException;
import net.sample.framework.message.transport.exception.TransportTimeoutException;
import net.sample.framework.message.transport.exception.TransportTooMuchRequestException;
import net.sample.framework.message.transport.log.TransportLog;
import net.sample.framework.message.transport.mina.MinaTransportServer;
import net.sample.framework.message.transport.mina.ResponseFuture;
import net.sample.framework.message.transport.protocol.Header;
import net.sample.framework.message.transport.protocol.RemotingCommand;
import net.sebinson.common.utils.JsonUtil;
import net.sebinson.common.utils.TimeUtil;
import net.sebinson.common.utils.UUIDUtil;

import org.apache.mina.core.session.IoSession;
import org.springframework.util.StringUtils;

import com.google.gson.reflect.TypeToken;

public class DistributionServerImpl implements DistributionServer, TransportBootstrap {

    private int distributedType;// 0=单节点模式，1=分布式多节点模式
    private String name;// 服务名
    private boolean isLong = true;// true,长连接，false短连接。
    private int port;// 端口
    private int nThreads;// mina线程数据
    private int cacheType;// TODO;default=0 cacheType 缓存库: 0=本地EHCache 0=单节点模式,
                          // 1=redis,2...待补充

    // mina服务端
    private MinaTransportServer minaTransportServer;

    // 1:映射数据写入共享 2：历史数据的处理
    private LoginProcessorDistribution loginProcessorDistribution;

    // 处理历史数据
    private HistoryDataService historyDataService;

    public DistributionServerImpl(String serverName, int port, boolean isLong, int nThreads, int distributedType) {
        this(serverName, port, isLong, nThreads, distributedType, 0);
    }

    public DistributionServerImpl(String serverName, int port, boolean isLong, int nThreads, int distributedType,
            int cacheType) {
        this.distributedType = distributedType;
        this.name = serverName;
        this.isLong = isLong;
        this.port = port;
        this.nThreads = nThreads;
        this.cacheType = cacheType;
        this.loginProcessorDistribution = new LoginProcessorDistribution();
        this.minaTransportServer = new MinaTransportServer();
    }

    private void init() {
        if (this.distributedType == 0)// 0=单节点模式
        {
            this.historyDataService = new HistoryDataServiceLocalImpl();
            this.loginProcessorDistribution.setHistoryDataService(this.historyDataService);
            this.loginProcessorDistribution.setShareSessionMappingService(new ShareSessionMappingServiceLocalImpl(
                    this.minaTransportServer));
        } else if (this.cacheType == 1) {
            this.historyDataService = new HistoryDataServiceRedisImpl();
            this.loginProcessorDistribution.setHistoryDataService(this.historyDataService);
            this.loginProcessorDistribution.setShareSessionMappingService(new ShareSessionMappingServiceRedisImpl());
        } else {
            // TODO
        }

        if (this.cacheType != 0) {
            // 1=分布式多节点模式
            // TODO 要启动分布式Socket短连接服务端收数据
            // TODO 本地没有映射时，要把数据发给别的节点处理soket客户端，短连接
        }
        ServerSendMessageUtil.setDistributionServer(this);
    }

    @Override
    public void start() throws Exception {
        this.init();
        this.minaTransportServer.startServer(this.name, this.isLong, this.port, this.nThreads);
    }

    @Override
    public void stop() {
        this.minaTransportServer.stop();
        if (this.distributedType != 0) {

            // 1=分布式多节点模式
            // TODO 要启动分布式Socket短连接服务端收数据
            // TODO 本地没有映射时，要把数据发给别的节点处理soket客户端，短连接
        }
    }

    @Override
    public <T> RemotingCommand sendSync(String add, GAGMessage<T> request) {
        TransportLog.debug("sendSync：发送报文到<" + add + ">开始...,发送 GAGMessage=" + request);
        this.checkParamer(add, request);
        RemotingCommand remotingCommand = this.prepareCommand(request, 0);
        if (this.checkMsgTimeout(request)) {
            TransportLog.debug("sendSync：发送报文到<" + add + ">已经过了有效期，数据丢弃..., GAGMessage=" + request);
            return null;
        }
        if (this.checkMsgHasSend(request)) {
            TransportLog.debug("sendSync：发送报文到<" + add + ">已经状态是(1-消息发送成功),数据丢弃..., GAGMessage=" + request);
            return null;
        }

        boolean hasSendSend = false;
        int allpush = request.getAllpush();// 0从接收者里随机取一个发送;1给所有接收者群发中的情况
        String[] receiveid = request.getReceiveid();
        Set<String> receiveidSet = new HashSet<String>(Arrays.asList(receiveid));
        receiveidSet.add(add);
        String serial = request.getSerial();

        RemotingCommand response = null;
        int tooMuchExceptionCount = 5;// TransportTooMuchRequestException 一共重试5次
        boolean isTooMuchException = false;// TransportTooMuchRequestException
                                           // true
        int hasSendCount = 0;// 所有已发送的次数
        for (Iterator<String> iterator = receiveidSet.iterator(); iterator.hasNext();) {
            if (hasSendSend)// 发送成功
            {
                if (allpush == 0)// 0从接收者里随机取一个发送
                {
                    break;
                }// 1给所有接收者群发中的情况
            }

            if (!isTooMuchException)// 不是TransportTooMuchRequestException
            {
                add = iterator.next();
            }

            if (!this.checkAddMapping(add))// 本节点有无此映射 false 无， true 有
            {
                continue;
            }
            try {
                hasSendCount++;
                remotingCommand.getHeader().setAdd(add);// 用客户端的标识
                response = this.minaTransportServer.invokeSync(add, remotingCommand,
                        ConstantTransport.MINA_SEND_RECEIVE_DEFAULT_TIMEOUTMILLIS);
                hasSendSend = true;
                request.setFlag(1);
                isTooMuchException = false;
            } catch (TransportConnectException e) {
                // 删除映射与断开本地Iosession
                this.removeMappingAddIosession(add);
                isTooMuchException = false;
                TransportLog.debug("连接异常.adds=" + add + ", serial=" + serial + ", e=" + e.getMessage());
            } catch (TransportTimeoutException e) {
                hasSendSend = true;
                request.setFlag(1);
                isTooMuchException = false;
                TransportLog.debug("发送返回结果超时异常.adds=" + add + ", serial=" + serial + ", e=" + e.getMessage());
            } catch (TransportSendRequestException e) {
                isTooMuchException = false;
                TransportLog.debug("发送异常.adds=" + add + ", serial=" + serial + ", e=" + e.getMessage());
            } catch (TransportTooMuchRequestException e) {
                tooMuchExceptionCount--;
                if (tooMuchExceptionCount <= 0)// 到了一定次数就解除此add的提交
                {
                    isTooMuchException = false;
                } else {
                    try {
                        Thread.sleep(100);// 等待100毫秒
                    } catch (InterruptedException e1) {
                    }
                    isTooMuchException = true;
                }
                TransportLog.debug("发送报文请求太多异常.adds=" + add + ", serial=" + serial + ", e=" + e.getMessage());
            } catch (InterruptedException e) {
                isTooMuchException = false;
                TransportLog.debug("发送线程中断异常.adds=" + add + ", serial=" + serial + ", e=" + e.getMessage());
            }
        }

        if (!hasSendSend) {// 发送不成功
            request.setFlag(2);
            if (hasSendCount <= 0) {// 一次都没有发送
                TransportLog.debug("sendSync：发送报文到<" + receiveidSet + ">失败,所有连接都无效..., serial=" + serial);
            }

            if (!this.cheakMsgTimeoutAndRsend(request))// 数据舍丢
            {
                TransportLog.debug("sendSync：发送报文到<" + receiveidSet + ">失败,数据过了有效期或者发送失败后不重发..., serial=" + serial);
                return response;
            }

            // 处理历史数据
            this.adapterGAGMessage(request);
            TransportLog.debug("sendSync：发送报文到<" + receiveidSet + ">失败,保存于历史数据中..., serial=" + serial);
            return response;
        }

        TransportLog.debug("sendSync：发送报文到<" + add + ">完成..., serial=" + serial + ", 返回 RemotingCommand=" + response);
        return response;
    }

    @Override
    public <T> void sendAsync(String add, final GAGMessage<T> request, final SendCallBack callBack) {
        TransportLog.debug("sendAsync：发送报文到<" + add + ">开始...,发送 GAGMessage=" + request);
        this.checkParamer(add, request);
        RemotingCommand remotingCommand = this.prepareCommand(request, 1);
        if (this.checkMsgTimeout(request)) {
            TransportLog.debug("sendAsync：发送报文到<" + add + ">已经过了有效期，数据丢弃..., GAGMessage=" + request);
            return;
        }
        if (this.checkMsgHasSend(request)) {
            TransportLog.debug("sendAsync：发送报文到<" + add + ">已经状态是(1-消息发送成功),数据丢弃..., GAGMessage=" + request);
            return;
        }

        boolean hasSendSend = false;
        int allpush = request.getAllpush();// 0从接收者里随机取一个发送;1给所有接收者群发中的情况
        String[] receiveid = request.getReceiveid();
        Set<String> receiveidSet = new HashSet<String>(Arrays.asList(receiveid));
        receiveidSet.add(add);
        final String serial = request.getSerial();

        final List<String> haveSends = new ArrayList<String>(receiveidSet.size());
        int tooMuchExceptionCount = 5;// TransportTooMuchRequestException 一共重试5次
        boolean isTooMuchException = false;// TransportTooMuchRequestException
                                           // true
        int hasSendCount = 0;// 所有已发送的次数
        for (Iterator<String> iterator = receiveidSet.iterator(); iterator.hasNext();) {
            if (hasSendSend)// 发送成功
            {
                if (allpush == 0)// 0从接收者里随机取一个发送
                {
                    break;
                }// 1给所有接收者群发中的情况
            }

            if (!isTooMuchException)// 不是TransportTooMuchRequestException
            {
                add = iterator.next();
            }

            if (!this.checkAddMapping(add))// 本节点有无此映射 false 无， true 有
            {
                continue;
            }
            try {
                hasSendCount++;
                haveSends.add(add);
                remotingCommand.getHeader().setAdd(add);// 用客户端的标识
                this.minaTransportServer.invokeASync(add, remotingCommand,
                        ConstantTransport.MINA_SEND_RECEIVE_DEFAULT_TIMEOUTMILLIS, new InvokeCallback() {
                            @Override
                            public void operationComplete(ResponseFuture responseFuture) {
                                if (callBack == null) {
                                    return;
                                }
                                RemotingCommand responseCommand = responseFuture.getResponseCommand();
                                SendResult<T> sendResult = new SendResult<T>();
                                sendResult.setRequestMsg(request);
                                if (responseCommand != null) {
                                    try {
                                        sendResult.setResponse(responseCommand);
                                        callBack.onSuccess(sendResult);
                                    } catch (Exception e) {
                                        callBack.onException(sendResult, e);
                                    }
                                } else {
                                    if (!responseFuture.isSendRequestOK()) {
                                        callBack.onException(sendResult, new TransportSendRequestException(
                                                TransportException.EORROR_SENDREQUSET, haveSends.toString(), "serial="
                                                        + serial, responseFuture.getThrowable()));
                                    } else if (responseFuture.isTimeout()) {
                                        callBack.onException(sendResult,
                                                new TransportTimeoutException(TransportException.EORROR_TIMEOUT,
                                                        haveSends.toString(), responseFuture.getSendTimeoutMillis(),
                                                        "serial=" + serial, responseFuture.getThrowable()));
                                    } else {
                                        callBack.onException(
                                                sendResult,
                                                new TransportException(TransportException.EORROR_TRANSPORT,
                                                        "unknow exception reseaon. serial=" + serial, responseFuture
                                                                .getThrowable()));
                                    }
                                }
                            }
                        });

                hasSendSend = true;
                request.setFlag(1);
                isTooMuchException = false;
            } catch (TransportConnectException e) {
                // 删除映射与断开本地Iosession
                this.removeMappingAddIosession(add);
                isTooMuchException = false;
                TransportLog.debug("连接异常.adds=" + add + ", serial=" + serial + ", e=" + e.getMessage());
            } catch (TransportTimeoutException e) {
                hasSendSend = true;
                request.setFlag(1);
                isTooMuchException = false;
                TransportLog.debug("发送返回结果超时异常.adds=" + add + ", serial=" + serial + ", e=" + e.getMessage());
            } catch (TransportSendRequestException e) {
                isTooMuchException = false;
                TransportLog.debug("发送异常.adds=" + add + ", serial=" + serial + ", e=" + e.getMessage());
            } catch (TransportTooMuchRequestException e) {
                tooMuchExceptionCount--;
                if (tooMuchExceptionCount <= 0)// 到了一定次数就解除此add的提交
                {
                    isTooMuchException = false;
                } else {
                    try {
                        Thread.sleep(100);// 等待100毫秒
                    } catch (InterruptedException e1) {
                    }
                    isTooMuchException = true;
                }

                TransportLog.debug("发送报文请求太多异常.adds=" + add + ", serial=" + serial + ", e=" + e.getMessage());
            } catch (InterruptedException e) {
                isTooMuchException = false;
                TransportLog.debug("发送线程中断异常.adds=" + add + ", serial=" + serial + ", e=" + e.getMessage());
            }
        }

        if (!hasSendSend) {// 发送不成功
            request.setFlag(2);
            if (hasSendCount <= 0) {// 一次都没有发送
                TransportLog.debug("sendAsync：发送报文到<" + receiveidSet + ">失败,所有连接都无效..., serial=" + serial);
            }

            if (!this.cheakMsgTimeoutAndRsend(request))// 数据舍丢
            {
                TransportLog.debug("sendAsync：发送报文到<" + receiveidSet + ">失败,数据过了有效期或者发送失败后不重发..., serial=" + serial);
                return;
            }

            // 处理历史数据
            this.adapterGAGMessage(request);
            TransportLog.debug("sendAsync：发送报文到<" + receiveidSet + ">失败,保存于历史数据中..., serial=" + serial);
            return;
        }

        TransportLog.debug("sendAsync：发送报文到<" + add + ">结束..., serial=" + serial);
    }

    @Override
    public <T> void sendUnreply(String add, GAGMessage<T> request) {
        TransportLog.debug("sendUnreply：发送报文到<" + add + ">开始..., 发送 GAGMessage=" + request);
        this.checkParamer(add, request);
        RemotingCommand remotingCommand = this.prepareCommand(request, 2);
        if (this.checkMsgTimeout(request)) {
            TransportLog.debug("sendUnreply：发送报文到<" + add + ">已经过了有效期，数据丢弃..., GAGMessage=" + request);
            return;
        }
        if (this.checkMsgHasSend(request)) {
            TransportLog.debug("sendUnreply：发送报文到<" + add + ">已经状态是(1-消息发送成功),数据丢弃..., GAGMessage=" + request);
            return;
        }

        boolean hasSendSend = false;
        int allpush = request.getAllpush();// 0从接收者里随机取一个发送;1给所有接收者群发中的情况
        String[] receiveid = request.getReceiveid();
        Set<String> receiveidSet = new HashSet<String>(Arrays.asList(receiveid));
        receiveidSet.add(add);
        String serial = request.getSerial();

        int tooMuchExceptionCount = 5;// TransportTooMuchRequestException 一共重试5次
        boolean isTooMuchException = false;// TransportTooMuchRequestException
                                           // true
        int hasSendCount = 0;// 所有已发送的次数
        for (Iterator<String> iterator = receiveidSet.iterator(); iterator.hasNext();) {
            if (hasSendSend)// 发送成功
            {
                if (allpush == 0)// 0从接收者里随机取一个发送
                {
                    break;
                }// 1给所有接收者群发中的情况
            }

            if (!isTooMuchException)// 不是TransportTooMuchRequestException
            {
                add = iterator.next();
            }

            if (!this.checkAddMapping(add))// 本节点有无此映射 false 无， true 有
            {
                continue;
            }
            try {
                hasSendCount++;
                remotingCommand.getHeader().setAdd(add);// 用客户端的标识
                this.minaTransportServer.invokeUnreply(add, remotingCommand,
                        ConstantTransport.MINA_SEND_RECEIVE_DEFAULT_TIMEOUTMILLIS);
                hasSendSend = true;
                request.setFlag(1);
                isTooMuchException = false;
            } catch (TransportConnectException e) {
                // 删除映射与断开本地Iosession
                this.removeMappingAddIosession(add);
                isTooMuchException = false;
                TransportLog.debug("发送数据,连接异常.adds=" + add + ", serial=" + serial + ", e=" + e.getMessage());
            } catch (TransportTimeoutException e) {
                hasSendSend = true;
                request.setFlag(1);
                isTooMuchException = false;
                TransportLog.debug("发送返回结果超时异常.adds=" + add + ", serial=" + serial + ", e=" + e.getMessage());
            } catch (TransportSendRequestException e) {
                isTooMuchException = false;
                TransportLog.debug("发送异常.adds=" + add + ", serial=" + serial + ", e=" + e.getMessage());
            } catch (TransportTooMuchRequestException e) {
                tooMuchExceptionCount--;
                if (tooMuchExceptionCount <= 0)// 到了一定次数就解除此add的提交
                {
                    isTooMuchException = false;
                } else {
                    try {
                        Thread.sleep(100);// 等待100毫秒
                    } catch (InterruptedException e1) {
                    }
                    isTooMuchException = true;
                }

                TransportLog.debug("发送报文请求太多异常.adds=" + add + ", serial=" + serial + ", e=" + e.getMessage());
            } catch (InterruptedException e) {
                isTooMuchException = false;
                TransportLog.debug("发送线程中断异常.adds=" + add + ", serial=" + serial + ", e=" + e.getMessage());
            }
        }

        if (!hasSendSend) {// 发送不成功
            request.setFlag(2);
            if (hasSendCount <= 0) {// 一次都没有发送
                TransportLog.debug("sendUnreply：发送报文到<" + receiveidSet + ">失败,所有连接都无效..., serial=" + serial);
            }

            if (!this.cheakMsgTimeoutAndRsend(request))// 数据舍丢
            {
                TransportLog.debug("sendUnreply：发送报文到<" + receiveidSet + ">失败,数据过了有效期或者发送失败后不重发..., serial=" + serial);
                return;
            }

            // 处理历史数据
            this.adapterGAGMessage(request);
            TransportLog.debug("sendUnreply：发送报文到<" + receiveidSet + ">失败,保存于历史数据中..., serial=" + serial);
            return;
        }

        TransportLog.debug("sendUnreply：发送报文到<" + add + ">结束..., serial=" + serial);
    }

    /** true，数据已经失效，false没有失败 */
    private <T> boolean checkMsgTimeout(GAGMessage<T> request) {
        long timeout = request.getTimeout();
        long n = System.currentTimeMillis();
        long c = request.getCtime();
        if (n - c - 1000 > timeout)// 1秒为缓冲
        {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String ctimeStr = df.format(new Date(c));
            String ntimeStr = df.format(new Date(n));
            TransportLog.debug("报文过了有效期,超时时间＝" + timeout + "(ms), 创建时间＝" + ctimeStr + ", 当前时间＝" + ntimeStr
                    + ", serial=" + request.getSerial());
            return true;
        }
        return false;
    }

    /** true，数据已经发送成功，false没有发送成功 */
    private <T> boolean checkMsgHasSend(GAGMessage<T> request) {
        int hasSend = request.getFlag();// 操作结果,0-消息没有发送，1-消息发送成功，2-消息发送失败....
        if (hasSend == 1) {
            TransportLog.debug("报文已发送成功,不再重复发送,GAGMessage.flag＝" + hasSend + ", serial=" + request.getSerial());
            return true;
        }
        return false;
    }

    /** 本节点有无此映射 false 无， true 有 */
    private boolean checkAddMapping(String add) {
        /*
         * 方案一 //查共享方式 if (this.distributedType != 1) {//分布式模式 add = add + "_" +
         * ConstantDistributed.LOCAL_IP; } String mapping =
         * this.loginProcessorDistribution
         * .getShareSessionMappingService().getMapping(add); if (mapping ==
         * null) { return false; } return true;
         */

        // 方案二,查服务端有无此iosession 这样最快速
        boolean hasSession = true;
        IoSession ioSession = null;
        try {
            ioSession = this.minaTransportServer.getIoSession(add);
        } catch (TransportConnectException e) {
            TransportLog.info(add + "的连接无效,e=" + e.getMessage());
        }
        if (ioSession == null) {
            hasSession = false;
        }
        return hasSession;
    }

    /**
     * 校验GAGMessage rsend;//0发送失败后保留重发;1发送失败后不重发而舍弃 timeout =
     * 259200000;//消息过期时间,毫秒 默认为3天
     * 
     * @return true 数据保留 false数据舍丢
     */
    private <T> boolean cheakMsgTimeoutAndRsend(GAGMessage<T> request) {
        if (this.checkMsgTimeout(request))// true，数据已经失效
        {
            return false;
        }

        int rsend = request.getRsend();
        if (0 != rsend)// 0发送失败后保留重发;1发送失败后不重发而舍弃
        {
            return false;
        }
        return true;
    }

    /**
     * //发送失败，单节点与多节点的处理 A： 1：单节点模式，放入历史数据中 2－1：分布式模式，从共享中找到有效的接收者，然后把数据发送给对应节点
     * 2－2：分布式模式，从共享中没找到任何一个有效接收接者，放入历史数据中
     * 
     * B:放入到历史中有如下规则 c1:FutionItypeCache.getValue(itype)=N..., 保存数据到历史库中
     * N_shopentityid_mmdd(ctime得到),List<GAGMessage> ,过期时间timeout
     * c2:FutionItypeCache.getValue(itype)=null, 保存数据到历史库中
     * receivied[0]_mmdd(ctime得到),List<GAGMessage> ,过期时间timeout
     * receivied[1]_mmdd(ctime得到),List<GAGMessage> ,过期时间timeout ....
     * 对于又c2,allpush;//0从接收者里随机取一个发送;1给所有接收者群发中的1情况
     * 当历史数据有一个发送成功后，则应该删除eceivied[1]_mmdd其它对应的数据
     */
    private <T> void adapterGAGMessage(GAGMessage<T> request) {
        if (this.distributedType == 0) {// 0=单节点模式，1=分布式多节点模式
                                        // 1：单节点模式，放入历史数据中
            this.processSingleHistory(request);
        } else {// 1=分布式多节点模式
                // TODO 2－1：分布式模式，从共享中找到有效的接收者，然后把数据发送给对应节点
                // TODO 2－2：分布式模式，从共享中没找到任何一个有效接收接者，放入历史数据中
                // processDistributedHistory(request);
        }
    }

    /** 单节点模式，放入历史数据中 */
    private <T> void processSingleHistory(GAGMessage<T> request) {
        long ctime = request.getCtime();
        Date cDate = new Date(ctime);
        Date nDate = new Date();
        int diff = TimeUtil.dateDiff(3, cDate, nDate);
        if (diff < 0) {// 创建时间比当前时间大，数据有bug，丢弃
            TransportLog.warn("创建时间比当前时间大,msg创建时间不正确,Msg丢弃.Msg创建时间＝"
                    + TimeUtil.convertDateToString(cDate, "yyyy-MM-dd HH:mm:ss.SSS") + ", 当前时间="
                    + TimeUtil.convertDateToString(nDate, "yyyy-MM-dd HH:mm:ss.SSS") + ", GAGMessage=" + request);
            return;
        }
        if (diff > 2) {// 创建时间比当前时间相比，超过3天，丢弃
            TransportLog.warn("创建时间比当前时间比对，已超过3天,Msg丢弃.Msg创建时间＝"
                    + TimeUtil.convertDateToString(cDate, "yyyy-MM-dd HH:mm:ss.SSS") + ", 当前时间="
                    + TimeUtil.convertDateToString(nDate, "yyyy-MM-dd HH:mm:ss.SSS") + ", GAGMessage=" + request);
            return;
        }
        String L = FutionItypeCache.getValue(request.getItype());
        if (!StringUtils.isEmpty(L)) {
        }
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date cDate = df.parse("20141231");
        Date nDate = df.parse("20150203");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nDate);
        int nDay = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTime(cDate);
        int cDay = calendar.get(Calendar.DAY_OF_YEAR);

        int diff = nDay - cDay + 1;
        System.out.println(diff);
        diff = TimeUtil.dateDiff(3, cDate, nDate);
        System.out.println(diff);
        System.out.println(TimeUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
    }

    /** 删除映射与断开本地Iosession */
    private void removeMappingAddIosession(String add) {
        // 1删除映射
        String adds = add;
        if (this.distributedType != 1) {// 分布式模式
            adds = add + "_" + ConstantDistributed.LOCAL_IP;
        }
        this.loginProcessorDistribution.getShareSessionMappingService().removeMaping(adds);

        // 2删除本地的Iosession
        IoSession ioSession = null;
        try {
            ioSession = this.minaTransportServer.getIoSession(add);
        } catch (TransportConnectException e) {
        }
        if (ioSession != null) {
            ioSession.close(true);
        }
    }

    private <T> RemotingCommand prepareCommand(GAGMessage<T> request, int rpc) {
        String serial = request.getSerial();
        if (serial == null)// 只判断空
        {
            serial = UUIDUtil.getUUID();
            TransportLog.debug("GAGMessage serial is null, serial use UUID.getUUID().");
        }
        String[] receiveid = request.getReceiveid();
        if (receiveid == null || receiveid.length == 0) {
            throw new IllegalArgumentException("GAGMessage receiveid is null or receiveid.length is 0.");
        }
        String shopentityid = request.getShopentityid();
        if (shopentityid == null) {
            throw new IllegalArgumentException("GAGMessage shopentityid is null.");
        }
        String itype = request.getItype();
        if (itype == null)// 只判断空
        {
            throw new IllegalArgumentException("GAGMessage itype is null.");
        }
        long timeout = request.getTimeout();
        if (timeout < 100)// 小于100ms
        {
            throw new IllegalArgumentException("GAGMessage timeout < 100(ms). timeout=" + timeout);
        }
        if (timeout > 259200000) {// 大于3天
            TransportLog.info("GAGMessage timeout > 3 天，使用默认值3天. timeout原始值是=" + timeout);
            timeout = 259200000;
        }
        long ctime = request.getCtime();
        if (ctime < 100)// 小于100ms
        {
            throw new IllegalArgumentException("GAGMessage ctime < 100(ms). ctime=" + ctime);
        }
        request.setRpc(rpc);

        Header header = new Header();
        header.setRpc(rpc);
        header.setSerial(serial);
        header.setItype(itype);
        header.setCtimeLong(ctime);
        RemotingCommand remotingCommand = new RemotingCommand();
        remotingCommand.setHeader(header);

        T t = request.getData();
        if (t != null) {
            String json = JsonUtil.toJson(t);
            Map<String, Object> body = JsonUtil.json2Genericity(json, new TypeToken<Map<String, Object>>() {
            });
            remotingCommand.setBody(body);
        }
        byte[] binary = request.getBinary();
        if (binary == null || binary.length <= 0) {
            remotingCommand.setBinary(binary);
        }

        return remotingCommand;
    }

    private <T> void checkParamer(String add, GAGMessage<T> request) {
        if (StringUtils.isEmpty(add)) {
            throw new IllegalArgumentException("add is null, add=" + add);
        }
        if (request == null) {
            throw new IllegalArgumentException("GAGMessage is null, GAGMessage=" + request);
        }
    }

    @Override
    public void registerRequestProcessor(String itype, RequestProcessor processor) {
        this.minaTransportServer.registerRequestProcessor(itype, processor);
    }

    @Override
    public void registerLogintProcessor(String itype, LoginProcessor processor) {
        this.loginProcessorDistribution.setLoginProcessor(processor);
        this.minaTransportServer.registerLogintProcessor(itype, this.loginProcessorDistribution);
    }

    @Override
    public void registerMoniterProcessor(String itype, RequestProcessor processor) {
        this.minaTransportServer.registerMoniterProcessor(itype, processor);
    }

    @Override
    public void registerPRCHook(RPCHook rpcHook) {
        this.minaTransportServer.registerPRCHook(rpcHook);
    }

    @Override
    public boolean isLong() {
        return this.isLong;
    }

    @Override
    public ClientInfoMsg getClientInfoMsg(String adds) {
        try {
            IoSession session = this.minaTransportServer.getIoSession(adds);
            ClientInfoMsg clientInfoMsg = ((ClientInfoMsg) session.getAttribute("add"));
            if (clientInfoMsg != null) {
                return clientInfoMsg;
            }

            TransportLog.info("getClientInfoMsg ClientInfoMsg is null, adds=" + adds + ", sid=" + session.getId());
        } catch (TransportConnectException e) {
            TransportLog.info("getClientInfoMsg exception, adds=" + adds + ", e=" + e);
        }

        return null;
    }

}
