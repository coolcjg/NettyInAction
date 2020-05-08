package chap9;

import static org.junit.Assert.*;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import junit.framework.Assert;

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
		try {
			channel.writeInbound(input.readBytes(4));
			Assert.fail();
		}catch(TooLongFrameException e) {
			
		}
		
		assertTrue(channel.writeInbound(input.readBytes(3)));
		assertTrue(channel.finish());
		
		ByteBuf read = (ByteBuf) channel.readInbound();
		assertEquals(buf.readSlice(2), read);
		read.release();
		
		read = (ByteBuf) channel.readInbound();
		assertEquals(buf.skipBytes(4).readSlice(3), read);
		read.release();
		buf.release();
		
	}

}
