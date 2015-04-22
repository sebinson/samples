package net.sebinson.sample.message.collection.core.service.message;

import net.sebinson.common.utils.Md5Util;
import net.sebinson.framework.message.common.ClientInfoMsg;
import net.sebinson.framework.message.push.util.ServerSendMessageUtil;
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
            response.getMessageNoSign();
            String sign = this.getSecuritySign(response, add);
            response.setSign(sign);
        } catch (Exception e) {
        }
    };

    protected boolean checkSecurityRequestMessage(RemotingCommand request, String adds, String encryptKey) throws TransportCommandException {

        String sign = new Md5Util().encrypt(request.getMessageNoSign() + "^" + encryptKey).toUpperCase();
        if (request.getSign().equals(sign)) {
            return true;
        }
        return false;
    }

    protected String getSecuritySign(RemotingCommand request, String adds) throws TransportCommandException {
        ClientInfoMsg clientInfoMsg = ServerSendMessageUtil.getClientInfoMsg(adds);
        if (clientInfoMsg == null) {
            return "";
        }
        String encryptKey = (String) clientInfoMsg.getParam().get("encryptKey");
        return new Md5Util().encrypt(request.getMessageNoSign() + "^" + encryptKey).toUpperCase();
    }
}
