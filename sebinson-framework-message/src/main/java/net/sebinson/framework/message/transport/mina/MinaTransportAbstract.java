package net.sebinson.framework.message.transport.mina;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import net.sebinson.common.utils.SpringBeanUtil;
import net.sebinson.common.utils.TimeUtil;
import net.sebinson.framework.message.common.ClientInfoMsg;
import net.sebinson.framework.message.common.SemaphoreOnce;
import net.sebinson.framework.message.transport.InvokeCallback;
import net.sebinson.framework.message.transport.RPCHook;
import net.sebinson.framework.message.transport.exception.TransportCommandException;
import net.sebinson.framework.message.transport.exception.TransportConnectException;
import net.sebinson.framework.message.transport.exception.TransportException;
import net.sebinson.framework.message.transport.exception.TransportSendRequestException;
import net.sebinson.framework.message.transport.exception.TransportTimeoutException;
import net.sebinson.framework.message.transport.exception.TransportTooMuchRequestException;
import net.sebinson.framework.message.transport.log.TransportLog;
import net.sebinson.framework.message.transport.processor.LoginDistributedProcessor;
import net.sebinson.framework.message.transport.processor.LoginProcessor;
import net.sebinson.framework.message.transport.processor.RequestProcessor;
import net.sebinson.framework.message.transport.protocol.Header;
import net.sebinson.framework.message.transport.protocol.RemoteCommand;

import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;

/**
 * 发送接收数据抽象类
 *
 */
public abstract class MinaTransportAbstract {
    /** 信号量，Unreply情况会使用，防止本地Mina缓存请求过多 */
    protected final Semaphore                                             semaphoreUnreply;

    /** 信号量，异步调用情况会使用，防止本地Mina缓存请求过多 */
    protected final Semaphore                                             semaphoreAsync;

    /** 缓存同，异步应答，主要是异步应该 */
    protected final ConcurrentHashMap<String/* serial */, ResponseFuture> responseMap  = new ConcurrentHashMap<String, ResponseFuture>(256);

    /** 缓存注册的接口。优化使用注册的接口，如没有找到，就用SpringBeanUtils.getBean(itype) */
    protected final HashMap<String/* itype */, RequestProcessor>          processorMap = new HashMap<String, RequestProcessor>(32);

    /** 勾子，子类也会用到 */
    protected RPCHook                                                     RpcHook;

    /** 登录接口 */
    protected LoginProcessor                                              loginProcessor;

    /** 登录接口的接口编码 */
    protected String                                                      loginItype   = "";

    /** 监控接口的接口编码 */
    protected String                                                      moniterItype = "";

    /** 异步应答回调的线程池 */
    protected abstract ExecutorService getCallbackExecutor();

    /** RequestProcessor处理的线程池 */
    protected abstract ExecutorService getProcessorExecutor();

    /** AsyncProcessor处理的线程池 */
    protected abstract ExecutorService getAsyncProcessorExecutor();

    /** LoginDistributedProcessor 处理的线程池 */
    // protected ExecutorService
    // loginDistributedProcessorExecutorService;//为了资源，改用getAsyncProcessorExecutor()

    /** 获取客户端的Iosession */
    protected abstract IoSession getIoSession(String add) throws TransportConnectException;

    /** 是否是长连接 */
    protected abstract boolean isLong();

    /** 登录成功后处理登录信息 */
    protected abstract void invokeLoginAdd(IoSession session, ClientInfoMsg clientInfoMsg, String add);

    public MinaTransportAbstract(int semaphoreUnreplySize, int semaphoreAsyncSize) {
        super();
        this.semaphoreUnreply = new Semaphore(semaphoreUnreplySize);
        this.semaphoreAsync = new Semaphore(semaphoreAsyncSize);
    }

    public String getLoginItype() {
        return this.loginItype;
    }

    /** 扫描下异步调用超时情况 */
    public void scanResponseMap() {
        Iterator<Entry<String, ResponseFuture>> it = this.responseMap.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, ResponseFuture> next = it.next();
            ResponseFuture rep = next.getValue();
            if ((rep.getBeginSendTime() + rep.getSendTimeoutMillis() + 1000) <= System.currentTimeMillis()) {
                it.remove();
                try {
                    rep.excuteInvokeCallback();
                } catch (Throwable e) {
                    TransportLog.warn("scanResponseMap, operationComplete Exception,serial=" + rep.getSerial() + ",e=" + e);
                } finally {
                    rep.release();
                }

                TransportLog.warn("remove timeout request,serial=" + rep.getSerial() + ", ResponseFuture=" + rep);
            }
        }
    }

    protected void processRequestCommandImpl(final IoSession session, final String add, final RemoteCommand request) {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    if (MinaTransportAbstract.this.RpcHook != null) {
                        MinaTransportAbstract.this.RpcHook.doBeforeRequest(add, request);
                    }
                    // 业务处理
                    String itype = request.getHeader().getItype();
                    RemoteCommand response = null;
                    if (!MinaTransportAbstract.this.loginItype.equals(itype))// 非登录
                    {
                        RequestProcessor requestProcessorTemp = MinaTransportAbstract.this.processorMap.get(itype);
                        if (requestProcessorTemp == null)// 优化使用注册的接口
                        {
                            // Spring配置文件中配置com.gooagoo.common.utils.SpringBeanUtils
                            requestProcessorTemp = SpringBeanUtil.getBean(itype, RequestProcessor.class);
                            if (requestProcessorTemp == null) {
                                // 接口编码不正确
                                TransportLog.warn("itype[" + itype + "] is incorrent, request=" + request);
                                throw new TransportException(TransportException.EORROR_TRANSPORT_ITYPE, "Request itype is incorrent, itype=" + itype);
                            }
                        }

                        final RequestProcessor requestProcessor = requestProcessorTemp;
                        if (MinaTransportAbstract.this.moniterItype.equals(itype)) {// 监控接口，更新映射数据
                            if (MinaTransportAbstract.this.isLong()) {// 长连接才处理
                                if (MinaTransportAbstract.this.loginProcessor instanceof LoginDistributedProcessor) {
                                    final LoginDistributedProcessor l = (LoginDistributedProcessor) MinaTransportAbstract.this.loginProcessor;
                                    ClientInfoMsg clientInfoMsg = (ClientInfoMsg) session.getAttribute("add");
                                    MinaTransportAbstract.this.processDistributedlogin(l, clientInfoMsg, session, add, request, 0);
                                }
                            }
                        }

                        int rpc = request.getHeader().getRpc();// 整数:处理完数据后再应答（同步应答）为0，接收完数据没有处理数据直接应答（异步应答）为1，接收数据后不应答为2
                        if (rpc == 0)// （同步应答）为0
                        {
                            response = requestProcessor.processRequestSync(request);
                            if (response == null) {
                                TransportLog.warn("Sync response is null,Request=" + request);
                                throw new TransportException(TransportException.EORROR_RESPONSE_SYNC_NULL, "同步应答返回为null, Request Serial="
                                        + request.getHeader().getSerial() + ", add=" + add + ", itype=" + request.getHeader().getItype());
                            }
                        } else if (rpc == 1)// （异步应答）为1
                        {
                            try {
                                MinaTransportAbstract.this.getAsyncProcessorExecutor().submit(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            requestProcessor.processRequestAsync(request);
                                        } catch (TransportException e) {
                                            String infoString = "RequestProcessor.processRequestAsync Exception. request=" + request;
                                            TransportLog.error(infoString, e);// 异步请求抛的异常
                                        } catch (Throwable e) {
                                            TransportLog.error(String.format("RequestProcessor.processRequestAsync Throwable. request is [%s]", request), e);
                                        }
                                    }
                                });
                            } catch (RejectedExecutionException e) {
                                String info = "Too many requests and AsyncProcessorThread pool busy,processRequestAsync RejectedExecutionException. request="
                                        + request;
                                TransportLog.error(info, e);
                                throw new TransportTooMuchRequestException(TransportException.EORROR_TOOMUCHREQUEST, "请求大多,服务器忙. Request Serial="
                                        + request.getHeader().getSerial() + ", add=" + add + ", itype=" + request.getHeader().getItype());
                            }

                            Header requestHeader = request.getHeader();
                            response = new RemoteCommand();
                            Header header = new Header();
                            response.setHeader(header);
                            header.setCode(1);
                            header.setRpc(requestHeader.getRpc());
                            header.setSerial(requestHeader.getSerial());
                            header.setItype(requestHeader.getItype());
                            header.setAdd(requestHeader.getAdd());
                            header.setResult("0");
                            header.setMsg("接收数据成功.");
                        } else
                        // 接收数据后不应答为2
                        {
                            requestProcessor.processRequestUnreply(request);
                        }
                    } else {// 登录
                        Object[] responseLogin = null;// 登录
                        try {
                            responseLogin = MinaTransportAbstract.this.loginProcessor.processLoginRequestSync(request);
                        } catch (TransportException e)// TransportException.EORROR_CONNECT_ILLEGAL
                        {
                            TransportLog.warn("loginProcessor processLoginRequestSync exception,Request Serial=" + request.getHeader().getSerial() + ", add="
                                    + add + ", Request=" + request + ", e=" + e);
                            throw e;
                        }

                        if (responseLogin == null || responseLogin.length < 2 || responseLogin[0] == null || responseLogin[1] == null)// TransportException.EORROR_RESPONSE_LOGIN_NULL
                        {
                            TransportLog.warn("Login response is null, Request Serial=" + request.getHeader().getSerial() + ", add=" + add + ", Request="
                                    + request);
                            throw new TransportException(TransportException.EORROR_RESPONSE_LOGIN_NULL, "无效连接. Request Serial="
                                    + request.getHeader().getSerial() + ", add=" + add);
                        }

                        ClientInfoMsg clientInfoMsg = (ClientInfoMsg) responseLogin[1];
                        String adds = clientInfoMsg.getAdd();
                        if (adds == null || adds.trim().length() == 0)// 不校验“blank”等情况
                        {
                            clientInfoMsg.setAdd(add);
                        }
                        // 登录成功后把1:mac存入到addsMap中, 2：IoSession中放入
                        // add=ClientInfoMsg
                        // session.setAttribute("add", responseLogin[1]);//存入mac
                        MinaTransportAbstract.this.invokeLoginAdd(session, clientInfoMsg, add);
                        response = (RemoteCommand) responseLogin[0];
                        // 先处理历史等数据
                        if (MinaTransportAbstract.this.loginProcessor instanceof LoginDistributedProcessor) {
                            final LoginDistributedProcessor l = (LoginDistributedProcessor) MinaTransportAbstract.this.loginProcessor;
                            // 处理另一个业务object[1]=ClientInfoMsg
                            MinaTransportAbstract.this.processDistributedlogin(l, (ClientInfoMsg) responseLogin[1], session, add, request, 1);
                        }
                    }

                    if (response != null)// 应答
                    {
                        response.getHeader().setCode(1);
                        response.getHeader().setCtime(TimeUtil.getCurrentDateTime("yyyyMMddHHmmssSSS"));
                        if (MinaTransportAbstract.this.RpcHook != null) {
                            MinaTransportAbstract.this.RpcHook.doBeforeResponse(add, response);
                        }
                        session.write(response);
                    }
                    MinaTransportAbstract.this.isClose(session);
                } catch (TransportException e) {
                    TransportLog.warn(String.format("processRequestCommandImpl:processRequest is TransportException. request is [%s], e is [%s]", request,
                            e.getMessage()));
                    MinaTransportAbstract.this.writeErrorReqsponse(session, request, add, e);
                    String errorCode = e.getErrorCode();
                    if (TransportException.EORROR_RESPONSE_LOGIN_NULL.equals(errorCode) || TransportException.EORROR_CONNECT_ILLEGAL.equals(errorCode))// 登录失败
                    {
                        if (MinaTransportAbstract.this.isLong()) {// 长连接登录失败断开连接
                            session.close(true);
                        }
                    }
                } catch (Throwable e) {
                    TransportLog.error(String.format("processRequestCommandImpl:processRequest is Throwable. request is [%s]", request), e);
                    MinaTransportAbstract.this.writeErrorReqsponse(session, request, add, new TransportException(TransportException.EORROR_TRANSPORT,
                            "processRequestCommandImpl:processRequest is Throwable. Request Serial=" + request.getHeader().getSerial() + ", add=" + add
                                    + ", itype=" + request.getHeader().getItype() + ", e=" + e.getMessage(), e));
                }
            }
        };

        try {
            // 做流控
            this.getProcessorExecutor().submit(run);
        } catch (RejectedExecutionException e) {
            String info = "Too many requests and ProcessorThread pool busy,processRequestCommandImpl RejectedExecutionException.request=" + request;
            TransportLog.error(info, e);
            TransportException te = new TransportTooMuchRequestException(TransportException.EORROR_TOOMUCHREQUEST, "请求大多,服务器忙. Request Serial="
                    + request.getHeader().getSerial() + ", add=" + add + ", itype=" + request.getHeader().getItype());
            this.writeErrorReqsponse(session, request, add, te);
        }
    }

    // 处理失败，返回结果
    private void writeErrorReqsponse(IoSession session, RemoteCommand request, String add, TransportException e) {
        if (request.getHeader().getRpc() != 2) {
            request.getHeader().setCode(1);
            request.getHeader().setResult(e.getErrorCode());
            request.getHeader().setMsg(e.getMessage());
            request.getHeader().setCtime(TimeUtil.getCurrentDateTime("yyyyMMddHHmmssSSS"));
            request.setBody(null);
            request.setBinary(null);
            if (MinaTransportAbstract.this.RpcHook != null) {
                MinaTransportAbstract.this.RpcHook.doBeforeResponse(add, request);
            }
            session.write(request);
        }
        MinaTransportAbstract.this.isClose(session);
    }

    // 短连接关闭连接
    private void isClose(IoSession session) {
        if (!this.isLong()) {
            session.close(true);
        }
    }

    protected void processResponseCommandImpl(IoSession session, String add, final RemoteCommand response) {
        final ResponseFuture responseFuture = this.responseMap.get(response.getHeader().getSerial());
        if (responseFuture == null) {
            TransportLog.info("Receive response, but not matched any request, response may be timeout, response Serial=" + response.getHeader().getSerial()
                    + ", add=" + add + ", itype=" + response.getHeader().getItype() + ", response=" + response);
            this.isClose(session);
            return;
        }

        if (MinaTransportAbstract.this.RpcHook != null) {// 校验报文，由于是应答，出错只记录日志即可
            try {
                MinaTransportAbstract.this.RpcHook.doBeforeRequest(add, response);
            } catch (TransportCommandException e) {
                TransportLog.warn("Receive response, but RpcHook.doBeforeRequest transportCommandException. response=" + response
                        + ", transportCommandException=" + e);
            }
        }

        responseFuture.release();
        // 异步回调
        if (responseFuture.getCallback() != null) {
            responseFuture.setResponseCommand(response);
            boolean notHasCallback = true;
            ExecutorService executor = this.getCallbackExecutor();
            if (executor != null) {
                try {
                    executor.submit(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                responseFuture.excuteInvokeCallback();
                            } catch (Throwable e) {
                                TransportLog.debug("Excute InvokeCallback in CallbackExecutor exception, and excuteInvokeCallback exception, responseFuture="
                                        + responseFuture + ", e=" + e.getMessage());
                            }
                        }
                    });
                    notHasCallback = false;// 已经处理了
                } catch (RejectedExecutionException e) {
                    TransportLog.debug("Excute InvokeCallback in CallbackExecutor exception, maybe CallbackExecutor is busy, responseFuture=" + responseFuture
                            + ", e=" + e.getMessage());
                }
            }

            if (notHasCallback) {
                try {
                    responseFuture.excuteInvokeCallback();
                } catch (Throwable e) {
                    TransportLog.debug("Excute excuteInvokeCallback exception, e=" + e.getMessage());
                }
            }
        } else {// 同步返回
            responseFuture.putResponseCommand(response);
        }

        this.responseMap.remove(response.getHeader().getSerial());
        this.isClose(session);
    }

    // 短连接不能主动发数据，长连接发数据是否登录等不用这这儿处理
    protected RemoteCommand invokeSyncImpl(IoSession session, final String add, final RemoteCommand request, long timeoutMillis) throws InterruptedException,
            TransportTimeoutException, TransportSendRequestException {
        final String serial = request.getHeader().getSerial();
        try {
            if (MinaTransportAbstract.this.RpcHook != null) {
                MinaTransportAbstract.this.RpcHook.doBeforeResponse(add, request);
            }

            final ResponseFuture responseFuture = new ResponseFuture(serial, timeoutMillis, null, null);
            this.responseMap.put(serial, responseFuture);

            session.write(request).addListener(new IoFutureListener<IoFuture>() {
                @Override
                public void operationComplete(IoFuture future) {
                    if (future.isDone())// 发送成功
                    {
                        responseFuture.setSendRequestOK(true);
                        return;
                    }
                    // 发送失败
                    MinaTransportAbstract.this.responseMap.remove(serial);
                    responseFuture.setSendRequestOK(false);
                    responseFuture.putResponseCommand(null);
                    if (future instanceof WriteFuture) {
                        responseFuture.setThrowable(((WriteFuture) future).getException());
                    }
                    TransportLog.info("invokeSyncImpl send exception. serial=" + serial + ", itype=" + request.getHeader().getItype() + ", add=" + add
                            + ", request=" + request);
                }
            });
            RemoteCommand response = responseFuture.waitResponse(timeoutMillis);
            if (response == null) {
                if (responseFuture.isSendRequestOK())// session.write(request)异步发送的，不能与responseFuture.waitResponse(timeoutMillis)换位置
                { // 发送报文成功，读取应答超时
                    throw new TransportTimeoutException(TransportException.EORROR_TIMEOUT, add, timeoutMillis, "invokeSyncImpl: serial=" + serial + ", itype="
                            + request.getHeader().getItype() + ", add=" + add, responseFuture.getThrowable());
                } else {// 发送报文失败
                    throw new TransportSendRequestException(TransportException.EORROR_SENDREQUSET, add, "invokeSyncImpl: serial=" + serial + ", itype="
                            + request.getHeader().getItype() + ", add=" + add, responseFuture.getThrowable());
                }
            }

            return response;
        } finally {
            this.responseMap.remove(serial);
        }
    }

    // 短连接不能主动发数据，长连接发数据是否登录等不用这这儿处理
    protected void invokeASyncImpl(IoSession session, final String add, final RemoteCommand request, long timeoutMillis, InvokeCallback invokeCallback)
            throws InterruptedException, TransportTimeoutException, TransportSendRequestException, TransportTooMuchRequestException {
        final String serial = request.getHeader().getSerial();
        boolean required = this.semaphoreAsync.tryAcquire(timeoutMillis, TimeUnit.MILLISECONDS);
        if (required) {
            final SemaphoreOnce sOnce = new SemaphoreOnce(this.semaphoreAsync);
            // todo
            if (MinaTransportAbstract.this.RpcHook != null) {
                MinaTransportAbstract.this.RpcHook.doBeforeResponse(add, request);
            }

            final ResponseFuture responseFuture = new ResponseFuture(serial, timeoutMillis, invokeCallback, sOnce);
            this.responseMap.put(serial, responseFuture);
            try {
                session.write(request).addListener(new IoFutureListener<IoFuture>() {
                    @Override
                    public void operationComplete(IoFuture future) {
                        if (future.isDone()) {
                            responseFuture.setSendRequestOK(true);
                            return;
                        }
                        // 发送失败
                        MinaTransportAbstract.this.responseMap.remove(serial);
                        responseFuture.setSendRequestOK(false);
                        responseFuture.putResponseCommand(null);
                        if (future instanceof WriteFuture) {
                            responseFuture.setThrowable(((WriteFuture) future).getException());
                        }

                        try {
                            responseFuture.excuteInvokeCallback();
                        } catch (Exception e) {
                            TransportLog.debug("Excute excuteInvokeCallback exception in write addListener, and InvokeCallback throw e=" + e);
                        } finally {
                            responseFuture.release();
                        }
                        TransportLog.info("invokeASyncImpl send exception. serial=" + serial + ", itype=" + request.getHeader().getItype() + ", add=" + add
                                + ", request=" + request);

                    }
                });
            } catch (Exception e) {
                responseFuture.release();
                TransportLog.warn(String.format("InvokeASyncImpl invoke. write send a request to [%s] failed. request serial is [%s]", add, serial));
                throw new TransportSendRequestException(TransportException.EORROR_SENDREQUSET, add, request.toString(), e);
            }
        } else {
            if (timeoutMillis <= 0) {
                throw new TransportTooMuchRequestException(TransportException.EORROR_TOOMUCHREQUEST, "invokeASyncImpl invoke too fast, timeoutMills="
                        + timeoutMillis);
            } else {
                String info = String
                        .format("invokeASyncImpl require semaphore timeout,requset serial is [%s], timeoutMills is [%d](ms), waiting thread nums is [%d], semaphoreAsync value is [%d].",
                                serial, timeoutMillis, this.semaphoreAsync.getQueueLength(), this.semaphoreAsync.availablePermits());
                TransportLog.warn(info + ". request is [" + request + "].");
                throw new TransportTimeoutException(TransportException.EORROR_TIMEOUT, info);
            }
        }
    }

    protected void invokeUnreplyImpl(IoSession session, final String add, final RemoteCommand request, long timeoutMillis) throws InterruptedException,
            TransportTooMuchRequestException, TransportTimeoutException, TransportSendRequestException {
        boolean required = this.semaphoreUnreply.tryAcquire(timeoutMillis, TimeUnit.MILLISECONDS);
        if (required) {
            final SemaphoreOnce sOnce = new SemaphoreOnce(this.semaphoreUnreply);
            if (this.RpcHook != null)// rpchook
            {
                this.RpcHook.doBeforeResponse(add, request);
            }

            try {
                session.write(request).addListener(new IoFutureListener<IoFuture>() {
                    @Override
                    public void operationComplete(IoFuture future) {
                        sOnce.release();
                        if (!future.isDone()) {
                            TransportLog.warn(String.format(
                                    "InvokeUnreplyImpl invoke IoFuture.isDone() is false. write send a request to [%s] failed. request is [%s]", add,
                                    request.toString()));
                        } else {
                            TransportLog.debug(String.format("InvokeUnreplyImpl invoke . write send a request to [%s] success. request is [%s]", add,
                                    request.toString()));
                        }
                    }
                });

            } catch (Exception e) {
                sOnce.release();
                TransportLog.warn(String.format("invokeUnreplyImpl invoke. write send a request to [%s] failed. request serial is [%s]", add, request
                        .getHeader().getSerial()));
                throw new TransportSendRequestException(TransportException.EORROR_SENDREQUSET, add, request.toString(), e);
            }
        } else {
            if (timeoutMillis <= 0) {
                throw new TransportTooMuchRequestException(TransportException.EORROR_TOOMUCHREQUEST, "invokeUnreplyImpl invoke too fast, timeoutMills="
                        + timeoutMillis);
            } else {
                String info = String
                        .format("invokeUnreplyImpl require semaphore timeout,requset serial is [%s], timeoutMills is [%d](ms), waiting thread nums is [%d], semaphoreUnreply value is [%d].",
                                request.getHeader().getSerial(), timeoutMillis, this.semaphoreUnreply.getQueueLength(),
                                this.semaphoreUnreply.availablePermits());
                TransportLog.warn(info + ". request is [" + request + "].");
                throw new TransportTimeoutException(TransportException.EORROR_TIMEOUT, info);
            }
        }
    }

    // 1:映射数据写入共享 2：历史数据的处理
    private void processDistributedlogin(final LoginDistributedProcessor l, final ClientInfoMsg clientInfoMsg, final IoSession session, final String add,
            final RemoteCommand request, final int type) throws TransportTooMuchRequestException {
        try {
            // MinaTransportAbstract.this.loginDistributedProcessorExecutorService.submit(new
            // Runnable()
            MinaTransportAbstract.this.getAsyncProcessorExecutor().submit(new Runnable()// 改用getAsyncProcessorExecutor
                    {
                        @Override
                        public void run() {
                            try {
                                l.processShareSession(session.getId(), add, clientInfoMsg);
                            } catch (Throwable e) {
                                TransportLog.error("处理映射失败.add=" + add + " ,sessionId=" + session.getId() + ", clientInfoMsg is " + clientInfoMsg, e);
                            }

                            if (type == 0)// type==0不再处理历史数据
                            {
                                return;
                            }

                            try {
                                l.processHistoryData(session.getId(), add, clientInfoMsg);
                            } catch (Throwable e) {
                                TransportLog.error("登录请求处理历史数据失败.add=" + add + " ,sessionId=" + session.getId() + ", clientInfoMsg is " + clientInfoMsg, e);
                            }
                        }
                    });
        } catch (RejectedExecutionException e) {
            String info = "Too many requests and loginDistributedProcessorThread pool busy, processShareSession and processHistoryData RejectedExecutionException. request="
                    + request;
            TransportLog.error("处理映射或历史数据失败.add=" + add + " ,sessionId=" + session.getId() + ", clientInfoMsg is " + clientInfoMsg + ", " + info, e);
            throw new TransportTooMuchRequestException(TransportException.EORROR_TOOMUCHREQUEST, "请求大多,服务器忙. Request Serial=" + request.getHeader().getSerial()
                    + ", add=" + add + ", itype=" + request.getHeader().getItype());
        }
    }
}
