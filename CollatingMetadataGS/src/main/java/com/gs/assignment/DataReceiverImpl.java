/**
 * 
 */
package com.gs.assignment;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 
 *
 */
public class DataReceiverImpl implements DataReceiver {
	
	ConcurrentHashMap<String, Map<String, Object>> publisherMap = new ConcurrentHashMap<String, Map<String,Object>>();
	ConcurrentHashMap<String, AtomicInteger> timeoutMap = new ConcurrentHashMap<String, AtomicInteger>();

	Recorder recorder;
	ScheduledExecutorRepeat schedExecutor;
	/**
	 * 
	 */
	private DataReceiverImpl() {
		schedExecutor = ScheduledExecutorRepeat.getInstance(this);
		this.setRecorder(new RecorderImpl()); //Setting default Recorder.
	}
	private DataReceiverImpl(Recorder recorder) {
		this.setRecorder(recorder);
		schedExecutor = ScheduledExecutorRepeat.getInstance(this);
	}
	
	// Inner class to provide instance of class
	private static class StaticSingleton {
		private static final DataReceiverImpl INSTANCE = new DataReceiverImpl();
	}
	
	public static DataReceiverImpl getInstance() {
		return StaticSingleton.INSTANCE;
	}

	//User can set recorder using this function
	public void setRecorder(Recorder recorder) {
		this.recorder = recorder;
	}
	
	/* (non-Javadoc)
	 * @see com.gs.assignment.DataReceiver#onDataReceived(java.util.Map)
	 */
	@Override
	public void onDataReceived(Map data) {
		String publisherName = (String)data.get(PubConstants.PUB_NAME);
		String publisherType = (String)data.get(PubConstants.PUB_TYPE);
		String publisherSource = (String)data.get(PubConstants.PUB_SOURCE);
		
		String key = publisherName + publisherType;
		if(publisherMap.containsKey(key)) {
			Map<String, Object> oldData = publisherMap.get(key);
			
//			System.out.println("Old Source: "+ oldData.get(PubConstants.PUB_SOURCE) + " New Source: " + publisherSource);
			
			if(!publisherSource.equals(oldData.get(PubConstants.PUB_SOURCE))) {
//				System.out.println("Source changed for: " + publisherName);
				publisherChangedSource(key, data);
			} else {
				oldData.putAll(data);
				publisherMap.put(key, oldData);
			}
		} else {
			startPublisher(key, data);
		}
		timeoutMap.put(key, new AtomicInteger(PubConstants.PUB_TIMEOUT));
	}
	
	public void stopPublisher(String key, Map<String, Object> data) {
		publisherMap.remove(key);
		Publisher publisher = getPublisher(data);
		recorder.publisherStopped(publisher, data);
	}

	public void startPublisher(String key, Map<String, Object> data) {
		Map<String, Object> localData = new HashMap<>();
		localData.putAll(data);
		publisherMap.put(key, localData);
		Publisher publisher = getPublisher(data);
		recorder.publisherStarted(publisher);
	}
	
	public void publisherChangedSource(String key, Map<String, Object> data) {
		Map<String, Object> localData = new HashMap<>();
		localData.putAll(data);
		publisherMap.put(key, localData);
		Publisher publisher = getPublisher(data);
		recorder.publisherChangedSource(publisher, (String)data.get(PubConstants.PUB_SOURCE));
	}
	
	public Publisher getPublisher(Map<String, Object> data) {
		return new Publisher((String)data.get(PubConstants.PUB_NAME), (String)data.get(PubConstants.PUB_TYPE), 
				(String)data.get(PubConstants.PUB_SOURCE));
	}
	
	public void stop() {
		schedExecutor.stop();
	}
}
