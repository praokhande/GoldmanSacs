package com.gs.assignment;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DataRecieverTest {
	
	DataReceiverImpl receiver = DataReceiverImpl.getInstance();
	Map<String, Object> map1 = new HashMap<String, Object>();
	Map<String, Object> map2 = new HashMap<String, Object>();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void testDataRecieverService() throws InterruptedException{
		
		Thread t1 = new Thread(new Runnable() { 
            @Override
            public void run() {
            	int count1 = 0;
            	int nextCount = 0;
            	int count = 0;
            	while(count < 20) {
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
	
	
//	@Test
//	void DataRecieverServiceStoptest() {
//		
//		receiver.stop();
//	}
	
	
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
