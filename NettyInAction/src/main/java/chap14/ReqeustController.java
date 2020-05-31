package chap14;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;

public class ReqeustController extends IdleStateAwareChannelUpstreamHandler{
	
	@Override
	public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception{
		//클라이언트에 대한 연결을 닫고 모든 항목을 롤백함.
	}
	
	@Override 
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception{
		if(!acquireConnectionSlot()) {
			//허용되는 최대 서버 연결 횟수에 도달
			//503 service unavailable로 응답
			//연결을 종료.
		}else {
			//연결의 요청 파이프라인을 설정함
		}
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception{
		if(isDone()){
			return;
		}
		
		if(e.getMessage() instanceof HttpReqeust) {
			handleHttpRequest((HttpReqeust) e.getMessage());
		}else if(e.getMessage() instanceof HttpChunk) {
			handleHttpChunk((HttpChunk)e.getMessage());
		}
	}
	

}
