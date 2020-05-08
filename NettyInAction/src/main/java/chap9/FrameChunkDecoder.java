package chap9;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

public class FrameChunkDecoder extends ByteToMessageDecoder{
	private final int maxFrameSize;
	
	public FrameChunkDecoder(int maxFrameSize) {
		this.maxFrameSize = maxFrameSize;
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception{
		int redableBytes = in.readableBytes();
		if(redableBytes > maxFrameSize) {
			in.clear();
			throw new TooLongFrameException();
		}
		ByteBuf buf = in.readBytes(redableBytes);
		out.add(buf);
	}

}
