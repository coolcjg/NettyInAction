package chap7;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ScheduleExamples {
	private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();
	
	public static void schedule() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
		
		ScheduledFuture<?> furture = executor.schedule(new Runnable() {
			@Override
			public void run() {
				System.out.println("Now it is 60 seconds later");
			}
		}, 5, TimeUnit.SECONDS);
		
		executor.shutdown();
	}
	
	public static void scheduleFixedViaEventLoop() {
		Channel ch = CHANNEL_FROM_SOMEWHERE;
		ScheduledFuture<?> future = ch.eventLoop().scheduleAtFixedRate(
				new Runnable() {
					@Override
					public void run() {
						System.out.println("Run every 60 seconds");
					}
				}, 3, 3, TimeUnit.SECONDS);
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		scheduleFixedViaEventLoop();
	}
	
	

}
