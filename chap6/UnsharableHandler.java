package chap6;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class UnsharableHandler extends ChannelInboundHandlerAdapter {
	
	private int count;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		count++;
		System.out.println("channelRead(...) called the " + count + " time");
		ctx.fireChannelRead(msg);
	}

}
