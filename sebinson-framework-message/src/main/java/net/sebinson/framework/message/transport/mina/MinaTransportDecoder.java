package net.sebinson.framework.message.transport.mina;

import net.sebinson.framework.message.common.ConstantTransport;
import net.sebinson.framework.message.transport.exception.TransportCommandProtocolException;
import net.sebinson.framework.message.transport.exception.TransportException;
import net.sebinson.framework.message.transport.protocol.RemotingCommand;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

/**
 * Mina解码 
 * 单个报文首次发送与接收数据的大小至少应该大于9个字节，由[1个字节通信类型（1建连接;2心跳;3业务通讯）][4个字节字符串长度][4个字节byte数组长度]这个而得出来的
 * [1个字节通信类型（1建连接;2心跳;3业务通讯）][4个字节字符串长度][4个字节byte数组长度][字符串][byte数组]
 *
 */
public class MinaTransportDecoder implements MessageDecoder
{

    private final AttributeKey decodeFlage = new AttributeKey(this.getClass(), "decodeFlage");

    @Override
    public MessageDecoderResult decodable(IoSession session, IoBuffer in)
    {
        Object context = session.getAttribute(this.decodeFlage);
        if (context != null)
        {
            return MessageDecoderResult.OK;
        }

        byte type = in.get();
        int jsonStrLenth = in.getInt();
        int binaryLenth = in.getInt();
        //报文校验
        if (type != ConstantTransport.MINA_PROTOCOL_TYPE_1 && type != ConstantTransport.MINA_PROTOCOL_TYPE_2 && type != ConstantTransport.MINA_PROTOCOL_TYPE_3)
        {
            throw new IllegalArgumentException(TransportException.EORROR_COMMANDPROTOCOL + ", Message type is incorrect, type=" + type + ", sessionId=" + session.getId());
        }
        if ((binaryLenth + jsonStrLenth) > ConstantTransport.MINA_MAX_SIZE_BINARY)//字符串与byte[]总大小大于100M
        {
            throw new IllegalArgumentException(TransportException.EORROR_COMMANDPROTOCOL + ", Message string and binary is to large. string size=" + jsonStrLenth + ", binary size=" + binaryLenth + ", sessionId=" + session.getId());
        }
        try
        {
            context = new Context(type, jsonStrLenth, binaryLenth);
        }
        catch (Throwable e)
        {
            throw new IllegalArgumentException(TransportException.EORROR_COMMANDPROTOCOL + ", Message string and binary is to large. string size=" + jsonStrLenth + ", binary size=" + binaryLenth + ", type=" + type + ", sessionId=" + session.getId(), e);
        }

        session.setAttribute(this.decodeFlage, context);
        return MessageDecoderResult.OK;
    }

    @Override
    public MessageDecoderResult decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception
    {
        Context context = (Context) session.getAttribute(this.decodeFlage);
        if (context == null)
        {
            throw new TransportCommandProtocolException(TransportException.EORROR_COMMANDPROTOCOL, "IoSession's attribute not has context. session id=" + session.getId());
        }

        byte type = context.type;
        if (type != ConstantTransport.MINA_PROTOCOL_TYPE_1 && type != ConstantTransport.MINA_PROTOCOL_TYPE_2 && type != ConstantTransport.MINA_PROTOCOL_TYPE_3)
        {
            throw new TransportCommandProtocolException(TransportException.EORROR_COMMANDPROTOCOL, "Message type is incorrect, type=" + type + ", sessionId=" + session.getId());
        }
        if ((context.binaryLenth + context.jsonLenth) > ConstantTransport.MINA_MAX_SIZE_BINARY)//字符串与byte[]总大小大于100M
        {
            throw new TransportCommandProtocolException(TransportException.EORROR_COMMANDPROTOCOL, "Message string and binary is to large. string size=" + context.jsonLenth + ", binary size=" + context.binaryLenth + ", sessionId=" + session.getId());
        }

        if (context.inItCount > 0)//没初始化
        {
            while (in.hasRemaining())
            {
                in.get();
                context.inItCount--;
                if (context.inItCount <= 0)//初始化完成
                {
                    break;
                }
            }
        }

        while (in.hasRemaining())
        {
            if (context.jsonStrcount < context.jsonLenth)
            {
                context.jsonStr[context.jsonStrcount] = in.get();
                context.jsonStrcount++;
                if (context.jsonStrcount < context.jsonLenth)
                {
                    continue;
                }
            }

            if (context.binarycount < context.binaryLenth)
            {
                context.binary[context.binarycount] = in.get();
                context.binarycount++;
                if (context.binarycount < context.binaryLenth)
                {
                    continue;
                }
            }

            context.isOK = true;
            RemotingCommand remotingCommand = new RemotingCommand();
            remotingCommand.setType(context.type);
            remotingCommand.setBaseinfo(new String(context.jsonStr, ConstantTransport.CODE_UTF8));
            remotingCommand.setBinary(context.binary);
            if (context.type != ConstantTransport.MINA_PROTOCOL_TYPE_2)//不是心跳
            {
                remotingCommand.DeProtocol();
            }
            out.write(remotingCommand);
            session.removeAttribute(this.decodeFlage);
            break;
        }

        if (!context.isOK)
        {
            return MessageDecoderResult.NEED_DATA;
        }

        context.reset();
        return MessageDecoderResult.OK;
    }

    @Override
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception
    {

    }

    //解码处理类
    class Context
    {
        byte type;
        int jsonLenth = 0;
        int binaryLenth = 0;
        int jsonStrcount = 0;
        int binarycount = 0;
        boolean isOK = false;
        int inItCount = 9;//9个报文字节
        byte[] jsonStr;
        byte[] binary;

        Context(byte type, int jsonLenth, int binaryLenth)
        {
            super();
            this.type = type;
            this.jsonLenth = jsonLenth;
            this.binaryLenth = binaryLenth;
            this.jsonStr = new byte[jsonLenth];
            this.binary = new byte[binaryLenth];
        }

        void reset()//便于垃圾回收 处理
        {
            this.jsonLenth = 0;
            this.binaryLenth = 0;
            this.jsonStrcount = 0;
            this.binarycount = 0;
            this.isOK = false;
            this.inItCount = 9;
            this.jsonStr = null;
            this.binary = null;
        }
    }
}
