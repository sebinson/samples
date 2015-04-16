package net.sebinson.sample.message.collection.core.service.message.transport;

import com.gooagoo.bill.collect.core.service.BillCollectBaseService;
import com.gooagoo.bill.collect.core.service.impl.transport.aware.ProcessVersionControlService;
import com.gooagoo.common.utils.SpringBeanUtils;
import com.gooagoo.message.remoting.transport.LoginProcessor;
import com.gooagoo.message.remoting.transport.exception.TransportException;
import com.gooagoo.message.remoting.transport.protocol.RemotingCommand;

/**
 * 采集终端交互-登录
 * @author zsl
 *
 */
public abstract class AbstractLoginRequestProcessorService implements LoginProcessor, BillCollectBaseService
{

    /**
     * 处理同步登陆请求
     * @param request 对应请求报文
     * @return 处理成功返回Object[0]=RemotingCommand返回给客户端的数据，object[1]=ClientInfoMsg
     * 处理失败直接抛TransportException异常即可
     * @throws TransportException 处理失败返回  
     * TransportException.errorCode即为返回对方报文Header的resutl, TransportException.message即为返回对方报文Header的msg
     */

    @Override
    public Object[] processLoginRequestSync(RemotingCommand request) throws TransportException
    {
        int version = request.getHeader().getVersion();
        String itype = request.getHeader().getItype();
        ProcessVersionControlService loginProcessor = null;
        try
        {
            loginProcessor = SpringBeanUtils.getBean(itype + "_" + version, ProcessVersionControlService.class);
        }
        catch (Exception e)
        {
            throw new TransportException(TransportException.EORROR_TRANSPORT_ITYPE, "LoginProcessor 子类不存在,itype=" + itype + ",version=" + version);
        }
        return loginProcessor.processLoginRequestSync(request);
    }

}
