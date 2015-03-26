package net.sample.framework.message.transport.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import net.sample.framework.message.common.ClientInfoMsg;
import net.sample.framework.message.common.ConstantTransport;
import net.sample.framework.message.transport.exception.TransportCommandException;
import net.sample.framework.message.transport.exception.TransportCommandProtocolException;
import net.sample.framework.message.transport.exception.TransportConnectException;
import net.sample.framework.message.transport.exception.TransportException;
import net.sample.framework.message.transport.log.TransportLog;
import net.sample.framework.message.transport.protocol.Header;
import net.sample.framework.message.transport.protocol.RemotingCommand;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoderException;
import org.apache.mina.filter.codec.ProtocolEncoderException;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * 客户端发送登录接口成功后,
 * 一：addsMap：key=mac,value=sessionId
 *            IOSession add=ClientInfoMsg
 * 二：发心跳时会校验   1：IoSession中有无 add。无：断开此连接;有:则不处理。防止恶意连接 
 *                2： 如连接后一直不发数据，过5分钟后会断开   
 * Mina服务端
 *
 */
public class MinaServerHandler extends IoHandlerAdapter
{
    private int nThreads = ConstantTransport.MINA_THREAD_DEFAULT_COUNT;
    private int port = ConstantTransport.MINA_SERVER_DEFAULT_PORT;
    private boolean isLong = true;
    private String name = "推送系统服务端";
    //key,客户端的地址，value IoSessionId，只存登录成功的mac
    private ConcurrentMap<String, Long> addsMap = new ConcurrentHashMap<String, Long>(256);

    private MinaTransportServer minaTransportServer = null;
    private MinaServer minaServer = null;

    public MinaServerHandler(MinaTransportServer minaTransportServer)
    {
        this.minaTransportServer = minaTransportServer;
        this.minaServer = new MinaServer();
    }

    public void start(String name, boolean isLong, int port, int nThreads) throws TransportException
    {
        if (name != null && name.trim().length() > 0)
        {
            this.name = name.trim();
        }
        if (port > 1)
        {
            this.port = port;
        }
        if (nThreads > 1)
        {
            this.nThreads = nThreads;
        }
        this.isLong = isLong;
        this.minaServer.start();
    }

    public void stop()
    {
        this.minaServer.stop();

        //客户终端add与服务端对应的映射数据全删除
        Set<String> keySet = this.addsMap.keySet();
        if (keySet == null || keySet.isEmpty())
        {
            TransportLog.debug(String.format("[%s]停止,端口[%s],没有需要删除的关联映射的数据.", this.name, this.port));
            return;
        }
        TransportLog.debug(String.format("[%s]停止,端口[%s],删除的关联映射数据是[%s]", this.name, this.port, this.addsMap));
        for (String g : keySet)
        {
            Long sessionId = this.addsMap.get(g);
            if (sessionId != null)
            {
                this.minaTransportServer.removeShareSession(sessionId, g);
            }
        }
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception
    {
        super.sessionCreated(session);
        TransportLog.info(String.format("客户端开始连接...ip[%s],sid[%s],连接总数[%s],有效连接总数[%s]", session.getRemoteAddress(), session.getId(), this.minaServer.acceptor.getManagedSessionCount(), this.addsMap.size()));
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception
    {//iosession.close回调
        ClientInfoMsg clientInfoMsg = (ClientInfoMsg) session.getAttribute("add");
        String add = "";
        Long id = Long.valueOf(session.getId());
        if (clientInfoMsg != null)
        {
            add = clientInfoMsg.getAdd();
            Long long1 = this.addsMap.get(add);
            long1 = long1 == null ? -10000 : long1;
            if (id.equals(long1))
            {//由于sessionClosed此方法是异步操作，在只允许单连接，但有多个连接时的处理
                this.addsMap.remove(add);
            }
            session.removeAttribute("add");
            this.minaTransportServer.removeShareSession(session, add);
        }
        boolean flage = "".equals(add);
        session.close(true);
        TransportLog.info(String.format((flage ? "无效" : "有效") + "连接关闭,sid[%s],adds[%s],当前连接总数[%s],有效连接总数[%s]", id, (flage ? "null" : add), this.minaServer.acceptor.getManagedSessionCount(), this.addsMap.size()));
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception
    {
        ClientInfoMsg clientInfoMsg = ((ClientInfoMsg) session.getAttribute("add"));
        if (clientInfoMsg != null)
        {
            TransportLog.info("add=" + clientInfoMsg.getAdd() + ", sid=" + session.getId() + " is idle, close Iosession.");
        }
        else
        {
            TransportLog.info("sid=" + session.getId() + " is idle, close Iosession.");
        }
        session.close(true);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception
    {
        if (cause instanceof TransportCommandProtocolException)//捕获解码异常
        {
            TransportCommandProtocolException t = (TransportCommandProtocolException) cause;
            String info = "Transport TransportCommandProtocolException: sessionid=" + session.getId() + ", 客户端报文不正确, e=" + cause.getMessage();
            this.writeExceptionCommand(session, t.getErrorCode(), info);
            TransportLog.error("【返回】错误信息报文到客户端. " + info, cause);
        }
        else if (cause instanceof ProtocolDecoderException)//捕获解码异常
        {
            String info = "Mina ProtocolDecoderException: sessionid=" + session.getId() + ", 客户端编码不正确, e=" + cause.getMessage();
            this.writeExceptionCommand(session, TransportException.EORROR_TRANSPORT, info);
            TransportLog.error("【返回】错误信息报文到客户端. " + info, cause);
        }
        else if (cause instanceof ProtocolEncoderException)//捕获编码异常
        {
            String info = "Mina ProtocolEncoderException: sessionid=" + session.getId() + ", 服务端编码不正确, e=" + cause.getMessage();
            TransportLog.error("【不发送】错误信息报文到客户端. " + info, cause);
        }
        else if (cause instanceof TransportException)//通信异常
        {
            TransportException t = (TransportException) cause;
            String info = "Transport TransportException: sessionid=" + session.getId() + ", 通信异常, e=" + cause.getMessage();
            this.writeExceptionCommand(session, t.getErrorCode(), info);
            TransportLog.error("【发送】错误信息报文到客户端. " + info, cause);
        }
        else if (cause instanceof IOException)//捕获IO异常
        {
            String info = "Mina IOException: sessionid=" + session.getId() + ", IO不正确, e=" + cause.getMessage();
            TransportLog.error("【不返回】错误信息报文到客户端. " + info, cause);
        }
        else if (cause instanceof IllegalArgumentException && cause.getMessage().startsWith(TransportException.EORROR_COMMANDPROTOCOL))
        {//主要解析客户端编码问题 见MinaTransportDecoder.decodable中的IllegalArgumentException
            String info = "Mina ProtocolDecoderException: sessionid=" + session.getId() + ", 客户端编码不正确, e=" + cause.getMessage();
            this.writeExceptionCommand(session, TransportException.EORROR_COMMANDPROTOCOL, info);
            TransportLog.error("【返回】错误信息报文到客户端. " + info, cause);
        }
        else
        {//其他异常
            String info = "Mina Exception: sessionid=" + session.getId() + ", 未知异常, e=" + cause.getMessage();
            TransportLog.error("【不发送】错误信息报文到客户端. " + info, cause);
        }

    }

    private void writeExceptionCommand(IoSession session, String errorCode, String msg)
    {
        RemotingCommand response = new RemotingCommand();
        Header header = new Header();
        response.setHeader(header);
        header.setCode(1);
        header.setRpc(2);
        header.setItype("异常itype");
        header.setAdd("客户端Adds:" + session.getRemoteAddress());
        header.setResult(errorCode);
        header.setMsg(msg);
        session.write(response);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception
    {
        RemotingCommand request = (RemotingCommand) message;
        TransportLog.debug("Received msg, sessionId=" + session.getId() + ", msg=" + message);
        if (request.getType() == ConstantTransport.MINA_PROTOCOL_TYPE_2)
        {//心跳直接返回
            session.write(request);
            this.checkHeartbeat(session, request);
            return;
        }

        this.checkRemotingCommand(session, request);

        int code = request.getHeader().getCode();
        if (code == 0)
        {
            this.minaTransportServer.processRequsetCommand(session, request.getHeader().getAdd(), request);
        }
        else if (code == 1)
        {
            this.minaTransportServer.processResponseCommand(session, request.getHeader().getAdd(), request);
        }
        else
        {
            TransportLog.warn("RemotingCommand request code incorrect. code=" + code);
        }
    }

    /**IoSession中有无 add. 无，断开此连接;有，则不处理。防止恶意连接*/
    private void checkHeartbeat(IoSession session, RemotingCommand request)
    {
        ClientInfoMsg clientInfoMsg = (ClientInfoMsg) session.getAttribute("add");
        if (clientInfoMsg != null)//有数据，则此为有效连接
        {
            return;
        }
        //无数据，则此为无效连接，断开
        TransportLog.info(String.format("客户端发送心跳数据，但连接无效,关闭此连接。sid[%s],adds[%s]", session.getId(), session.getRemoteAddress()));
        session.close(true);
    }

    /**报文header必须项与正确性校验*/
    private void checkRemotingCommand(IoSession session, RemotingCommand requset) throws TransportCommandException
    {
        Header header = requset.getHeader();
        if (header == null)
        {
            throw new TransportCommandException(TransportException.EORROR_COMMAND, "header is null.");
        }
        int code = header.getCode();
        if (code != 0 && code != 1)
        {
            throw new TransportCommandException(TransportException.EORROR_COMMAND, "header's code is error. code=" + code);
        }
        if (header.getSerial() == null || header.getSerial().trim().length() <= 0)
        {
            throw new TransportCommandException(TransportException.EORROR_COMMAND, "header's serial is null.");
        }
        if (header.getItype() == null || header.getItype().trim().length() <= 0)
        {
            throw new TransportCommandException(TransportException.EORROR_COMMAND, "header's itype is null.");
        }
        if (header.getAdd() == null || header.getAdd().trim().length() <= 0)
        {
            throw new TransportCommandException(TransportException.EORROR_COMMAND, "header's add is null.");
        }

        //"rpc":[必填,主要用于应答]整数:处理完数据后再应答（同步应答）为0，接收完数据没有处理数据直接应答（异步应答）为1，接收数据后不应答为2 校验略 目前处理为，不为1或者0的其它rpc都当成2处理
    }

    /**
     * 处理登录信息，缓存起来
     * @see #checkRemotingCommand()
     * @ses #MinaTransportAbstract.invokeLoginAdd(...)
     */
    public void invokeLoginAdd(IoSession session, ClientInfoMsg clientInfoMsg, String add)
    {
        //参数暂不校验
        try
        {
            IoSession ioSession = this.getIoSession(add);
            if (ioSession != null)
            {
                //TODO 只许充单连接情况 ,多连接暂不考虑
                TransportLog.info("客户端连接重复,adds=" + add + " is connected, oldSessionId=" + ioSession.getId() + ", oldSession is close now. newSessionId=" + session.getId());
                ioSession.close(true);
            }
        }
        catch (TransportConnectException e)
        {
        }

        this.addsMap.put(add, new Long(session.getId()));
        session.setAttribute("add", clientInfoMsg);
        TransportLog.info(String.format("客户端连接有效...ip[%s],sid[%s],adds[%s],连接总数[%s],有效连接总数[%s]", session.getRemoteAddress(), session.getId(), add, this.minaServer.acceptor.getManagedSessionCount(), this.addsMap.size()));
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception
    {
        super.messageSent(session, message);
        TransportLog.debug("Response msg, sessionId=" + session.getId() + ", msg=" + message);
    }

    public IoSession getIoSession(String add) throws TransportConnectException
    {
        Long sessionId = this.addsMap.get(add);
        if (sessionId == null)
        {
            throw new TransportConnectException(TransportException.EORROR_CONNECT, add);
        }
        Map<Long, IoSession> managedSessions = this.minaServer.acceptor.getManagedSessions();
        if (managedSessions == null || managedSessions.isEmpty())
        {
            throw new TransportConnectException(TransportException.EORROR_CONNECT, add);
        }
        IoSession ioSession = managedSessions.get(sessionId);
        //ioSession不存在
        if (ioSession == null)
        {
            throw new TransportConnectException(TransportException.EORROR_CONNECT, add);
        }
        return ioSession;
    }

    public boolean isLong()
    {
        return this.isLong;
    }

    public MinaServer getMinaServer()
    {
        return this.minaServer;
    }

    public ConcurrentMap<String, Long> getAddsMap()
    {
        return this.addsMap;
    }

    class MinaServer
    {
        ExecutorService excecutor = null;
        NioSocketAcceptor acceptor = null;
        boolean isStart = false;

        void start() throws TransportException
        {
            if (!this.isStart)
            {
                try
                {
                    TransportLog.info(String.format("[%s]启动开始---,端口[%s]", MinaServerHandler.this.name, MinaServerHandler.this.port));
                    this.excecutor = Executors.newFixedThreadPool(MinaServerHandler.this.nThreads, new ThreadFactory()
                    {
                        private AtomicInteger n = new AtomicInteger(0);

                        @Override
                        public Thread newThread(Runnable r)
                        {
                            return new Thread(r, "MinaTransportServerThread_" + this.n.decrementAndGet());
                        }
                    });

                    this.acceptor = new NioSocketAcceptor();
                    this.acceptor.getSessionConfig().setSendBufferSize(ConstantTransport.MINA_MAX_PACKET_SIZE_SEND);
                    this.acceptor.getSessionConfig().setReceiveBufferSize(ConstantTransport.MINA_MAX_PACKET_SIZE_RECEIV);
                    this.acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, ConstantTransport.MINA_IDLE_SECOND);
                    this.acceptor.getFilterChain().addLast("codecTF", new ProtocolCodecFilter(new MinaTransportProtocolCodecFacotry()));
                    this.acceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(this.excecutor));
                    this.acceptor.getFilterChain().addLast("logger", new LoggingFilter());

                    this.acceptor.setHandler(MinaServerHandler.this);
                    this.acceptor.bind(new InetSocketAddress(MinaServerHandler.this.port));

                    this.isStart = true;
                    TransportLog.info(String.format("[%s]启动成功---,端口[%s]", MinaServerHandler.this.name, MinaServerHandler.this.port));
                }
                catch (Exception e)
                {
                    String error = String.format("[%s]启动异常---,端口[%s]", MinaServerHandler.this.name, MinaServerHandler.this.port);
                    TransportLog.error(error, e);
                    throw new TransportException(TransportException.EORROR_TRANSPORT_SERVER, error, e);
                }
            }
            else
            {
                TransportLog.info(String.format("[%s]已经启动,不能重复启动,端口[%s]", MinaServerHandler.this.name, MinaServerHandler.this.port));
            }
        }

        void stop()
        {
            TransportLog.info(String.format("[%s]停止开始---,端口[%s]", MinaServerHandler.this.name, MinaServerHandler.this.port));
            if (this.excecutor != null)
            {
                this.excecutor.shutdownNow();
            }
            if (this.acceptor != null)
            {
                this.acceptor.unbind();
                this.acceptor.dispose();
            }
            TransportLog.info(String.format("[%s]停止成功---,端口[%s]", MinaServerHandler.this.name, MinaServerHandler.this.port));
        }

        public NioSocketAcceptor getAcceptor()
        {
            return this.acceptor;
        }

    }

}
