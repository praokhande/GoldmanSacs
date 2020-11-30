package com.gs.assignment;

import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.Receiver;

public class ServiceMain {

	public ServiceMain() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws InterruptedException {
		
		DataReceiverImpl receiver = DataReceiverImpl.getInstance();
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		
		Thread t1 = new Thread(new Runnable() { 
            @Override
            public void run() {
            	int count1 = 0;
            	int nextCount = 0;
            	int count = 0;
            	while(count < 10) {
            		setMap(map1, count);
            		receiver.onDataReceived(map1);
            		setMap1(map1, count);
            		if(nextCount%3 == 0) {
            			map1.put(PubConstants.PUB_SOURCE, PubConstants.PUB_SOURCE+count1);
            			count1++;
            		}
            		receiver.onDataReceived(map1);

            		nextCount++;
            		count++;
            	}
            }
		});
		t1.start();
		Thread.sleep(165000);
		receiver.stop();
	}

	public static void setMap(Map<String, Object> data, int count) {
		data.put(PubConstants.PUB_NAME, PubConstants.PUB_NAME + count);
		data.put(PubConstants.PUB_TYPE, PubConstants.PUB_TYPE + count);
		data.put(PubConstants.PUB_SOURCE, PubConstants.PUB_SOURCE + count);

		data.put("A", "A" + count);
		data.put("B", "B" + count);
	}

	public static void setMap1(Map<String, Object> data, int count) {
		data.put(PubConstants.PUB_NAME, PubConstants.PUB_NAME + count);
		data.put(PubConstants.PUB_TYPE, PubConstants.PUB_TYPE + count);
		data.put(PubConstants.PUB_SOURCE, PubConstants.PUB_SOURCE + count);

		data.put("A", "A" + count);
		data.put("B", "B" + count);

		data.put("X", "X" + count);
		data.put("Y", "Y" + count);
	}
}
