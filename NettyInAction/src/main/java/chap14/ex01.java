package chap14;

import org.hamcrest.Factory;

public class ex01 {
	
	
	
	pipelineFactory = new ChannelPipelineFactory() {
		@Override
		public ChannelPipeline getPipeline() throws Exception{
			ChannelPipeline pipeline = Channel.pipeline();
			pipeline.addLast("idleStateHandler", new IdleStateHandler());
			pipeline.addLast("httpServerCodec", new HttpServerCodec());
			pipeline.addLast("requestController", new RequestController());
			
			return pipeline;
		}
	}

}
