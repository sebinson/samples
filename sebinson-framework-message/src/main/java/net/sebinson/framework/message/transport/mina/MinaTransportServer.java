package net.sebinson.framework.message.transport.mina;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import net.sebinson.common.utils.TimeUtil;
import net.sebinson.framework.message.common.ClientInfoMsg;
import net.sebinson.framework.message.common.ConstantTransport;
import net.sebinson.framework.message.transport.InvokeCallback;
import net.sebinson.framework.message.transport.LoginDistributedProcessor;
import net.sebinson.framework.message.transport.LoginProcessor;
import net.sebinson.framework.message.transport.RPCHook;
import net.sebinson.framework.message.transport.RequestProcessor;
import net.sebinson.framework.message.transport.TransportServer;
import net.sebinson.framework.message.transport.TransportService;
import net.sebinson.framework.message.transport.exception.TransportConnectException;
import net.sebinson.framework.message.transport.exception.TransportException;
import net.sebinson.framework.message.transport.exception.TransportSendRequestException;
import net.sebinson.framework.message.transport.exception.TransportTimeoutException;
import net.sebinson.framework.message.transport.exception.TransportTooMuchRequestException;
import net.sebinson.framework.message.transport.log.TransportLog;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;

import org.apache.mina.core.session.IoSession;
import org.springframework.util.StringUtils;

/**
 * 服务端发送数据
 *
 */
public class MinaTransportServer extends MinaTransportAbstract implements TransportServer, TransportService
{
    // 处理Callback应答器
    private ExecutorService callBackExecutor;
    // 处理RequestProcessor的请求
    private ExecutorService processorExecutor;
    // 处理异步应答的请求
    private ExecutorService asyncProcessorExecutor;
    //每隔1秒扫描下异步调用超时情况
    private final Timer timer;

    private MinaServerHandler minaServerHandler;

    public MinaTransportServer()
    {
        super(ConstantTransport.MINA_SEMAPHORE_SIZE_UNREPLY, ConstantTransport.MINA_SEMAPHORE_SIZE_ASYNC);
        this.timer = new Timer("AsyncResponseTimer", true);
        this.minaServerHandler = new MinaServerHandler(this);
    }

    @Override
    public void startServer(String name, boolean isLong, int port, int nThreads) throws TransportException
    {
        if (isLong)//长连接校验
        {
            if (this.loginProcessor == null)
            {
                throw new TransportException(TransportException.EORROR_TRANSPORT_SERVER, "没有注册登录接口.");
            }
            if (!StringUtils.isEmpty(this.moniterItype))
            {
                throw new TransportException(TransportException.EORROR_TRANSPORT_SERVER, "没有注册监控接口.");
            }

            //长连接才有异步请求
            this.asyncProcessorExecutor = Executors.newFixedThreadPool(ConstantTransport.MINA_NTHREADS_SIZE_PROCESSOR_ASYNC, new ThreadFactory()
            {
                private AtomicInteger n = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r)
                {
                    return new Thread(r, "MinaTransportServerAsyncProcessorThread_" + this.n.decrementAndGet());
                }
            });
        }
        this.callBackExecutor = Executors.newFixedThreadPool(ConstantTransport.MINA_NTHREADS_SIZE_CALLBACK, new ThreadFactory()
        {
            private AtomicInteger n = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r)
            {
                return new Thread(r, "MinaTransportServerCallBackThread_" + this.n.decrementAndGet());
            }
        });
        this.processorExecutor = Executors.newFixedThreadPool(ConstantTransport.MINA_NTHREADS_SIZE_CALLBACK, new ThreadFactory()
        {
            private AtomicInteger n = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r)
            {
                return new Thread(r, "MinaTransportServerProcessorThread_" + this.n.decrementAndGet());
            }
        });

        this.minaServerHandler.start(name, isLong, port, nThreads);

        //每隔1秒扫描下异步调用超时情况
        this.timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                try
                {
                    MinaTransportServer.this.scanResponseMap();
                }
                catch (Exception e)
                {
                    TransportLog.error("MinaTransprotServer scanResponseMap exception", e);
                }
            }
        }, 1000 * 3, 1000);
    }

    @Override
    public void stop()
    {
        try
        {
            if (this.minaServerHandler != null)
            {
                this.minaServerHandler.stop();
            }
            if (this.timer != null)
            {
                this.timer.cancel();
            }
            if (this.callBackExecutor != null)
            {
                this.callBackExecutor.shutdown();
            }
            if (this.processorExecutor != null)
            {
                this.processorExecutor.shutdown();
            }
            if (this.asyncProcessorExecutor != null)
            {
                this.asyncProcessorExecutor.shutdown();
            }
            /*if (this.loginDistributedProcessorExecutorService != null)
            {
                this.loginDistributedProcessorExecutorService.shutdown();
            }*/
        }
        catch (Exception e)
        {
            TransportLog.error("MinaTransportServer stop exception, ", e);
        }
    }

    @Override
    public RemotingCommand invokeSync(String add, RemotingCommand request, long timeoutMillis) throws InterruptedException, TransportConnectException, TransportTimeoutException, TransportSendRequestException, TransportTooMuchRequestException
    {
        IoSession session = this.sendRequestCommandCheakSessionAndLogin(add, request, timeoutMillis);
        return this.invokeSyncImpl(session, add, request, timeoutMillis);
    }

    /**只用于长连接*/
    @Override
    public void invokeASync(String add, RemotingCommand request, long timeoutMillis, InvokeCallback invokeCallback) throws InterruptedException, TransportConnectException, TransportTimeoutException, TransportSendRequestException, TransportTooMuchRequestException
    {
        IoSession session = this.sendRequestCommandCheakSessionAndLogin(add, request, timeoutMillis);
        this.invokeASyncImpl(session, add, request, timeoutMillis, invokeCallback);
    }

    /**只用于长连接*/
    @Override
    public void invokeUnreply(String add, RemotingCommand request, long timeoutMillis) throws InterruptedException, TransportConnectException, TransportTimeoutException, TransportSendRequestException, TransportTooMuchRequestException
    {
        IoSession session = this.sendRequestCommandCheakSessionAndLogin(add, request, timeoutMillis);
        this.invokeUnreplyImpl(session, add, request, timeoutMillis);
    }

    public void processRequsetCommand(IoSession session, String add, RemotingCommand request)
    {
        try
        {
            this.processRequsetCommandCheckLogin(session, add, request, ConstantTransport.MINA_SEND_RECEIVE_DEFAULT_TIMEOUTMILLIS);
            this.processRequestCommandImpl(session, add, request);
        }
        catch (Throwable e)
        {
            TransportLog.error("Check Login exception sessionId=" + session.getId() + ", request=" + request, e);
        }
    }

    public void processResponseCommand(IoSession session, String add, RemotingCommand response)
    {
        this.processResponseCommandImpl(session, add, response);
    }

    /**receive请求报文，短连接不用校验，长连接接收数据，校验session客户端是否已登录*/
    private void processRequsetCommandCheckLogin(IoSession session, final String add, RemotingCommand request, long timeoutMills) throws TransportTooMuchRequestException, TransportTimeoutException, TransportSendRequestException, InterruptedException, TransportConnectException
    {
        if (!this.isLong())//短连接不用校验登录情况
        {
            return;
        }
        this.checkLogin(session, add, request, timeoutMills, 1);
    }

    /**send报文，短连接不能发送。长连接发数据校验session客户端是否已登录*/
    private IoSession sendRequestCommandCheakSessionAndLogin(final String add, RemotingCommand request, long timeoutMills) throws TransportConnectException, TransportTooMuchRequestException, TransportTimeoutException, TransportSendRequestException, InterruptedException
    {
        if (!this.isLong())
        {//短连接不发数据
            throw new TransportSendRequestException(TransportException.EORROR_SENDREQUSET_SERVER_ISSHORT_SOCKET, add, "ISshortSocket, SocketServer can't send request", new IllegalArgumentException("短连接服务端不能主动发送数据."));
        }

        IoSession session = this.getIoSession(add);
        this.checkLogin(session, add, request, timeoutMills, 0);
        //ioSession已注册
        return session;
    }

    /**长连接用校验登录情况*/
    private void checkLogin(IoSession session, final String add, RemotingCommand request, long timeoutMills, int code) throws TransportTooMuchRequestException, TransportTimeoutException, TransportSendRequestException, InterruptedException, TransportConnectException
    {
        //登录请求
        if (this.loginItype.equals(request.getHeader().getItype()))
        {
            return;
        }
        //非登录请求
        ClientInfoMsg v = (ClientInfoMsg) session.getAttribute("add");//客户端地址
        //ioSession没有登陆
        if (v == null || !v.getAdd().equals(add))
        {
            request.getHeader().setCode(code);
            if (code == 0)//请求
            {
                request.getHeader().setRpc(2);
            }
            else
            {//应答
                request.getHeader().setCtime(TimeUtil.getCurrentDateTime("yyyyMMddHHmmssSSS"));
            }
            request.getHeader().setResult(TransportException.EORROR_CONNECT_ILLEGAL);
            request.getHeader().setMsg(add + "没有登陆,无效连接.");
            request.setBody(null);
            request.setBinary(null);
            this.invokeUnreplyImpl(session, add, request, timeoutMills);
            session.close(true);//断开连接
            TransportLog.warn("Long connect is true, but [" + add + "] is not Login, so session is close, sessionId  is [" + session.getId() + "]");
            throw new TransportConnectException(TransportException.EORROR_CONNECT_ILLEGAL, add, new IllegalArgumentException("此[" + add + "]没有登陆."));
        }
    }

    /**删除共享映射*/
    public void removeShareSession(IoSession session, String add)
    {
        this.removeShareSession(session.getId(), add);
    }

    /**删除共享映射*/
    public void removeShareSession(long sessionId, String add)
    {
        if (this.isLong())
        {//长连接才删除
            if (this.loginProcessor instanceof LoginDistributedProcessor)
            {
                final LoginDistributedProcessor l = (LoginDistributedProcessor) this.loginProcessor;
                l.removeShareSession(sessionId, add);
            }
        }
        TransportLog.info("remove Iosession,sessionid=" + sessionId + ",add=" + add);
    }

    @Override
    public void registerRequestProcessor(String itype, RequestProcessor processor)
    {
        this.processorMap.put(itype, processor);
    }

    /**
     *注册登陆接口 LoginProcessor　 长连接用
     */
    @Override
    public void registerLogintProcessor(final String itype, final LoginProcessor processor)
    {
        this.loginItype = itype;
        this.loginProcessor = processor;
        if (this.loginProcessor instanceof LoginDistributedProcessor)//多线程处理登情况
        {
            //改用getAsyncProcessorExecutor
            /* this.loginDistributedProcessorExecutorService = Executors.newFixedThreadPool(ConstantTransport.THREADS_JVM_AVAILABLEPROCESSORS, new ThreadFactory()
             {
                 private AtomicInteger n = new AtomicInteger(0);

                 @Override
                 public Thread newThread(Runnable r)
                 {
                     return new Thread(r, "MinaTransportLoginDistributedProcessorThread_" + this.n.decrementAndGet());
                 }
             });*/
        }
    }

    /**
     *注册监控接口 LoginProcessor 长连接用
     * @param itype 监控接口接口名
     * @param processor RequestProcessor
     */
    @Override
    public void registerMoniterProcessor(final String itype, final RequestProcessor processor)
    {
        this.moniterItype = itype;
        this.processorMap.put(itype, processor);
    }

    /**得到add对应的session 没有就返回null*/
    public Long getSessonId(String add)
    {
        return this.minaServerHandler.getAddsMap().get(add);
    }

    @Override
    public void registerPRCHook(RPCHook rpcHook)
    {
        this.RpcHook = rpcHook;
    }

    @Override
    protected ExecutorService getCallbackExecutor()
    {
        return this.callBackExecutor;
    }

    @Override
    protected ExecutorService getProcessorExecutor()
    {
        return this.processorExecutor;
    }

    @Override
    protected ExecutorService getAsyncProcessorExecutor()
    {
        return this.asyncProcessorExecutor;
    }

    @Override
    protected boolean isLong()
    {
        return this.minaServerHandler.isLong();
    }

    @Override
    public IoSession getIoSession(String add) throws TransportConnectException
    {
        return this.minaServerHandler.getIoSession(add);
    }

    @Override
    public void invokeLoginAdd(IoSession session, ClientInfoMsg clientInfoMsg, String add)
    {
        this.minaServerHandler.invokeLoginAdd(session, clientInfoMsg, add);
    }
}
