package net.sebinson.framework.message.transport.mina;

import net.sebinson.framework.message.transport.protocol.RemoteCommand;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

/**
 * mina编解码工厂
 *
 */
public class MinaTransportProtocolCodecFacotry extends DemuxingProtocolCodecFactory {

    public MinaTransportProtocolCodecFacotry() {
        this.addMessageDecoder(MinaTransportDecoder.class);
        this.addMessageEncoder(RemoteCommand.class, MinaTransportEncoder.class);
    }
}
