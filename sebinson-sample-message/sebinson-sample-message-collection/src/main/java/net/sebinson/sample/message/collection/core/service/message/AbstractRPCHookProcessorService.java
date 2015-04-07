package net.sebinson.sample.message.collection.core.service.message;

import net.sebinson.framework.message.transport.RPCHook;
import net.sebinson.framework.message.transport.exception.TransportCommandException;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;

public abstract class AbstractRPCHookProcessorService implements RPCHook {

    @Override
    public void doBeforeRequest(final String add, final RemotingCommand request) throws TransportCommandException {

    };

    @Override
    public void doBeforeResponse(final String add, final RemotingCommand response) {
        try {
            // 创建无签名报文
            response.createBaseInfoNoSign();
            String sign = this.getSecuritySign(response, add);
            response.setSign(sign);
        } catch (Exception e) {
        }
    };

    protected boolean checkSecurityRequestMessage(RemotingCommand request, String adds, String encryptKey) throws TransportCommandException {

        String sign = SignUtils.emcryptSign(request.getBaseInfoNoSign(), encryptKey);
        if (request.getSign().equals(sign)) {
            return true;
        }
        GooagooLog.debug("校验数据签名不一致,平台计算数据签名[" + sign + "],终端上传数据签名[" + request.getSign() + "]");
        return false;
    }
}
