package com.gs.assignment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;



/**
 * This class will facilitate Timeout checks for publishers who do not send messages for 150 seconds. 
 *
 */
public class ScheduledExecutorRepeat {

	private AtomicBoolean isStop = new AtomicBoolean(false);
	
	private DataReceiverImpl dataReciver;
	private ScheduledExecutorService ses;
	ScheduledFuture<?> scheduledFuture;
	
	private ScheduledExecutorRepeat() {
		ses = Executors.newScheduledThreadPool(1);
		
		Runnable task1 = () -> {
			checknStopPublisher(dataReciver);
		};

		// init Delay = 5, repeat the task every 1 second
		ScheduledFuture<?> scheduledFuture = ses.scheduleAtFixedRate(task1, 1, PubConstants.TIMEOUT_CHK_INTERVAL, TimeUnit.SECONDS);
	}
	
	public static void checknStopPublisher(DataReceiverImpl dataReciver) {
		
		for (Map.Entry<String, AtomicInteger> entry : dataReciver.timeoutMap.entrySet()) {
//			int count = entry.getValue() - PubConstants.TIMEOUT_CHK_INTERVAL;
			AtomicInteger count = new AtomicInteger();
			if((entry.getValue().addAndGet(-1 * PubConstants.TIMEOUT_CHK_INTERVAL) ) <= 0) {
				dataReciver.timeoutMap.remove(entry.getKey());
				dataReciver.stopPublisher(entry.getKey(), dataReciver.publisherMap.get(entry.getKey()));
			} 
//			else {
//				dataReciver.timeoutMap.put(entry.getKey(), (entry.getValue() - PubConstants.TIMEOUT_CHK_INTERVAL));
//			}
		}
	}

	// Inner class to provide instance of class
	private static class StaticSingleton {
		private static final ScheduledExecutorRepeat INSTANCE = new ScheduledExecutorRepeat();
	}

	public static ScheduledExecutorRepeat getInstance(DataReceiver dataReciver) {
		StaticSingleton.INSTANCE.dataReciver = (DataReceiverImpl)dataReciver;
		return StaticSingleton.INSTANCE;
	}
	
	public void stop() {
		if(scheduledFuture != null) {
			scheduledFuture.cancel(true);
		}
		if(ses != null) {
			ses.shutdown();
		}
	}
	
	
}
