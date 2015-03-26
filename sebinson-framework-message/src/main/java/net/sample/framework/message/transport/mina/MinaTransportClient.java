package net.sample.framework.message.transport.mina;

import java.util.concurrent.ExecutorService;

import net.sample.framework.message.common.ClientInfoMsg;
import net.sample.framework.message.common.ConstantTransport;
import net.sample.framework.message.transport.InvokeCallback;
import net.sample.framework.message.transport.LoginProcessor;
import net.sample.framework.message.transport.RPCHook;
import net.sample.framework.message.transport.TransportClient;
import net.sample.framework.message.transport.TransportService;
import net.sample.framework.message.transport.exception.TransportConnectException;
import net.sample.framework.message.transport.exception.TransportSendRequestException;
import net.sample.framework.message.transport.exception.TransportTimeoutException;
import net.sample.framework.message.transport.exception.TransportTooMuchRequestException;
import net.sample.framework.message.transport.protocol.RemotingCommand;

import org.apache.mina.core.session.IoSession;
import org.apache.zookeeper.server.RequestProcessor;

/**
 * 客户端发送数据 　sync后，recicemessage（应该只有processResponse这种情况）后
 * session.clolse(true),只有在processResponse中体现 　暂没有实现，后期有时间再考虑
 * 
 *
 */
@Deprecated
public class MinaTransportClient extends MinaTransportAbstract implements TransportClient, TransportService {

    public MinaTransportClient() {
        super(ConstantTransport.MINA_SEMAPHORE_SIZE_UNREPLY, ConstantTransport.MINA_SEMAPHORE_SIZE_ASYNC);
        // TODO
    }

    @Override
    public RemotingCommand invokeSync(String add, RemotingCommand request, long timeoutMillis)
            throws InterruptedException, TransportConnectException, TransportTimeoutException,
            TransportSendRequestException, TransportTooMuchRequestException {
        // TODO 短连接客户端 sync后，recicemessage后 session.clolse(true),
        return null;
    }

    @Override
    public void invokeASync(String add, RemotingCommand request, long timeoutMillis, InvokeCallback invokeCallback)
            throws InterruptedException, TransportConnectException, TransportTimeoutException,
            TransportSendRequestException, TransportTooMuchRequestException {
        // TODO 短连接客户端,没有此方法

    }

    @Override
    public void invokeUnreply(String add, RemotingCommand request, long timeoutMillis) throws InterruptedException,
            TransportConnectException, TransportTimeoutException, TransportSendRequestException,
            TransportTooMuchRequestException {
        // TODO 短连接客户端session.write发数据upreply后 直接断开session.closee(true)

    }

    public void registerRequestProcessor(String itype, RequestProcessor processor) {

    }

    @Override
    public void registerPRCHook(RPCHook rpcHook) {

    }

    @Override
    public void startClient(String name, boolean isLong, String ip, int port, int nThreads) {

    }

    @Override
    public void stop() {
    }

    @Override
    protected ExecutorService getCallbackExecutor() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected ExecutorService getProcessorExecutor() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected IoSession getIoSession(String add) throws TransportConnectException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void registerLogintProcessor(String itype, LoginProcessor processor) {
        // 客户端可以不用实现
    }

    public void registerMoniterProcessor(String itype, RequestProcessor processor) {
        // 客户端可以不用实现

    }

    @Override
    protected boolean isLong() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected ExecutorService getAsyncProcessorExecutor() {

        return null;
    }

    @Override
    protected void invokeLoginAdd(IoSession session, ClientInfoMsg clientInfoMsg, String add) {
        // TODO Auto-generated method stub
    }

    @Override
    public void registerRequestProcessor(String itype,
            net.sample.framework.message.transport.RequestProcessor processor) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void registerMoniterProcessor(String itype,
            net.sample.framework.message.transport.RequestProcessor processor) {
        // TODO Auto-generated method stub
        
    }

}
