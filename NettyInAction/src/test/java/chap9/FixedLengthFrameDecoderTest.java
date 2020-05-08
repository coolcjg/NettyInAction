package chap9;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

public class FixedLengthFrameDecoderTest {
	
	public void testFramesDecoded() {
		ByteBuf buf = Unpooled.buffer();
		for(int i=0; i<9; i++) {
			buf.writeByte(i);
		}
		
		ByteBuf input = buf.duplicate();
		
		EmbeddedChannel channel  = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
		
		//바이트를 기록
		assertTrue(channel.writeInbound(input.retain()));
		assertTrue(channel.finish());
		
		//메시지를 읽음
		ByteBuf read = (ByteBuf) channel.readInbound();
		assertEquals(buf.readSlice(3), read);
		read.release();
		
		read = (ByteBuf)channel.readInbound();
		assertEquals(buf.readSlice(3), read);
		read.release();
		
		read=(ByteBuf) channel.readInbound();
		assertEquals(buf.readSlice(3), read);
		read.release();
		
		assertNull(channel.readInbound());
		buf.release();
	}
	
	
	
	public void testFramesDecoded2() {
		ByteBuf buf = Unpooled.buffer();
		for(int i=0; i<9; i++) {
			buf.writeByte(i);
		}
		
		ByteBuf input = buf.duplicate();
		
		EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
		
		assertFalse(channel.writeInbound(input.readBytes(2)));
		assertTrue(channel.writeInbound(input.readBytes(7)));
		assertTrue(channel.finish());
		
		
		ByteBuf read = (ByteBuf) channel.readInbound();
		assertEquals(buf.readSlice(3), read);
		read.release();
		
		read = (ByteBuf) channel.readInbound();
		assertEquals(buf.readSlice(3), read);
		read.release();
		
		read=(ByteBuf) channel.readInbound();
		assertEquals(buf.readSlice(3), read);
		read.release();
		
		assertNull(channel.readInbound());
		buf.release();
		
		
		
	}

}
