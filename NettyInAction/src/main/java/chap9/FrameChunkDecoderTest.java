package chap9;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

public class FrameChunkDecoderTest {
	@Test
	public void testFramesDecoded() {
		ByteBuf buf = Unpooled.buffer();
		for(int i=0; i<9; i++) {
			buf.writeByte(i);
		}
		ByteBuf input = buf.duplicate();
		
		EmbeddedChannel channel = new EmbeddedChannel(new FrameChunkDecoder(3));
		
		assertTrue(channel.writeInbound(input.readBytes(2)));
	}

}
