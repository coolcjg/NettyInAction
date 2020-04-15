package chap5;

import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

public class ByteBufExamples {
	private static final ByteBuf BYTE_BUF_FROM_SOMEWHERE = Unpooled.buffer(1024);
	
	private static void handleArray(byte[] array, int offset, int len) {};
	
	//5.1
	public static void heapBuffer() {
		ByteBuf heapBuf = BYTE_BUF_FROM_SOMEWHERE;
		if(heapBuf.hasArray()) {
			byte[] array = heapBuf.array();
			int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
			int length = heapBuf.readableBytes();
			handleArray(array, offset, length);
		}
	}
	
	//5.2
	public static void directBuffer() {
		ByteBuf directBuf = BYTE_BUF_FROM_SOMEWHERE;
		if(!directBuf.hasArray()) {
			int length = directBuf.readableBytes();
			byte[] array = new byte[length];
			directBuf.getBytes(directBuf.readerIndex(), array);
			handleArray(array, 0, length);
		}
	}
	
	//5.3
	public static void byteBufferComposite(ByteBuffer header, ByteBuffer body) {
		ByteBuffer[] message = new ByteBuffer[] {header, body};
		
		ByteBuffer message2 = ByteBuffer.allocate(header.remaining() + body.remaining());
		
		message2.put(header);
		message2.put(body);
		message2.flip();
	}
	
	//5.4
	public static void byteBufComposite() {
		CompositeByteBuf messageBuf = Unpooled.compositeBuffer();
		ByteBuf headerBuf = BYTE_BUF_FROM_SOMEWHERE;
		ByteBuf bodyBuf = BYTE_BUF_FROM_SOMEWHERE;
		messageBuf.addComponents(headerBuf, bodyBuf);
		
		messageBuf.removeComponent(0);
		for(ByteBuf buf : messageBuf) {
			System.out.println(buf.toString());
		}
	}
	
	//5.5
	public static void byteBufCompositeArray() {
		CompositeByteBuf compBuf = Unpooled.compositeBuffer();
		int length = compBuf.readableBytes();
		byte[] array = new byte[length];
		compBuf.getBytes(compBuf.readerIndex(), array);
		handleArray(array, 0, array.length);
	}
	
	

}
