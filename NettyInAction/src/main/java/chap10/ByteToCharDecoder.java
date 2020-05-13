package chap10;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ByteToCharDecoder extends ByteToMessageDecoder{
	
	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception{
		if(in.readableBytes() >=2) {
			out.add(in.readChar());
		}
	}

}