package chap6;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SimpleDiscardHandler extends SimpleChannelInboundHandler<Object>{
	@Override
	public void channelRead0(ChannelHandlerContext ctx, Object msg) {
		
	}

}
