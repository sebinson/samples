package net.sebinson.framework.message.transport.mina;

import net.sebinson.framework.message.common.ConstantTransport;
import net.sebinson.framework.message.transport.protocol.RemoteCommand;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

/**
 * Mina编码
 * [1个字节通信类型（1建连接;2心跳;3业务通讯）][4个字节字符串长度][4个字节byte数组长度][字符串][byte数组]
 *
 */
public class MinaTransportEncoder implements MessageEncoder<RemoteCommand>
{
    @Override
    public void encode(IoSession session, RemoteCommand command, ProtocolEncoderOutput out) throws Exception
    {
        if (command.getType() != ConstantTransport.MINA_PROTOCOL_TYPE_2)
        {//不是心跳
            command.EnProtocol();
        }

        IoBuffer buffer = IoBuffer.allocate(ConstantTransport.MINA_MAX_PACKET_SIZE_SEND);
        buffer.setAutoExpand(true);
        buffer.put(command.getType());//1个字节通信类型
        byte[] jsonStr = command.getBaseinfo().getBytes("utf-8");
        buffer.putInt(jsonStr.length);//4个字节字符串长度
        buffer.putInt(command.getBinary().length);//4个字节byte数组长度
        buffer.put(jsonStr);//字符串
        buffer.put(command.getBinary());//byte数组
        buffer.flip();
        out.write(buffer);
        out.flush();
        buffer.free();
    }
}
