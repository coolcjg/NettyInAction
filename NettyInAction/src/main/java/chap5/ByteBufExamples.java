package chap5;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Random;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ByteProcessor;
import static io.netty.channel.DummyChannelHandlerContext.DUMMY_INSTANCE;

public class ByteBufExamples {
	private final static Random random = new Random();
	
	private static final ByteBuf BYTE_BUF_FROM_SOMEWHERE = Unpooled.buffer(1024);
	
	private static void handleArray(byte[] array, int offset, int len) {};
	
	private static final ChannelHandlerContext CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE = DUMMY_INSTANCE;
	
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
	
	//5.6
	public static void byteBufRelativeAccess() {
		ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE;
		for(int i=0; i<buffer.capacity(); i++) {
			byte b = buffer.getByte(i);
			System.out.println((char) b);
		}
	}
	
	//5.7
	public static void readAllData() {
		ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE;
		while(buffer.isReadable()) {
			System.out.println(buffer.readByte());
		}
	}
	
	//5.8
	public static void write() {
		ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE;
		while(buffer.maxFastWritableBytes()>=4) {
			buffer.writeInt(random.nextInt());
		}
	}
	
	//5.9
	public static void byteProcesor() {
		ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE;
		int index = buffer.forEachByte(ByteProcessor.FIND_CR);
	}
	
	//5.10
	public static void byteBufSlice() {
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
		ByteBuf sliced = buf.slice(0,15);
		System.out.println(sliced.toString(utf8));
		buf.setByte(0,  (byte)'J');
		assert buf.getByte(0) == sliced.getByte(0);
	}
	
	//5.11
	public static void byteBufCopy() {
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
		ByteBuf copy = buf.copy(0, 15);
		System.out.println(copy.toString(utf8));
		buf.setByte(0,  (byte)'J');
		System.out.println(copy.toString(utf8));
		assert buf.getByte(0) != copy.getByte(0);
	}
	
	//5.12
	public static void byteBufSetGet() {
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
		System.out.println((char)buf.getByte(0));
		int readerIndex = buf.readerIndex();
		int writerIndex = buf.writerIndex();
		buf.setByte(0,  (byte)'B');
		System.out.println((char)buf.getByte(0));
		assert readerIndex == buf.readerIndex();
		assert writerIndex == buf.writerIndex();
	}
	
	//5.13
	public static void byteBufWriteRead() {
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
		System.out.println((char)buf.readByte());
		int readerIndex = buf.readerIndex();
		int writerIndex = buf.writerIndex();
		buf.writeByte((byte)'?');
		System.out.println((char)buf.readByte());
		assert readerIndex == buf.readerIndex();
		assert writerIndex != buf.writerIndex();
	}
	
	//5.14
	public static void obtainingByteBufAllocatorReference() {
		Channel channel = CHANNEL_FROM_SOMEWHERE;
		ByteBufAllocator allocator = channel.alloc();
		
		ChannelHandlerContext ctx = CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE;
		ByteBufAllocator allocator2 = ctx.alloc();
	}
	
	
	
	public static void main(String[] args) {
		byteBufWriteRead();
	}
	
	
	

}
