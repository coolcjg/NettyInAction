package chap14;

import javax.net.ssl.SSLEngine;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.ssl.SslHandler;

public class AnpnClientPipelineInitializer  extends ChannelInitializer<Channel>{
	private final SSLEngine clientEngine;
	
	public ApnsClientPipelineFactory(SSLEngine engine) {
		this.clientEngine = engine;
	}
	
	@Override
	public void initChannel(Channel channel) throws Exception{
		final ChannelPipeline pipeline = channel.pipeline();
		final SslHandler handler = new SslHandler(clientEngine);
		handler.setEnableRenegotiation(true);
		pipeline.addLast("ssl", handler);
		pipeline.addLast("decoder", new ApnsResponseDecoder());
	}

}
