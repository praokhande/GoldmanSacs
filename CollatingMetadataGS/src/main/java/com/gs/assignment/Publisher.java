package com.gs.assignment;

import java.util.Map;

public class Publisher {

	String publisherName;
	String publisherType;
	String publisherSource;
	
//	DataReceiver receiver = DataReceiverImpl.getInstance();
	
	public Publisher(String publisherName, String publisherType, String publisherSource) {
		super();
		this.publisherName = publisherName;
		this.publisherType = publisherType;
		this.publisherSource = publisherSource;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	public String getPublisherType() {
		return publisherType;
	}
	public void setPublisherType(String publisherType) {
		this.publisherType = publisherType;
	}
	public String getPublisherSource() {
		return publisherSource;
	}
	public void setPublisherSource(String publisherSource) {
		this.publisherSource = publisherSource;
	}
	
//	public void publishMetaData(Map data) {
//		receiver.onDataReceived(data);
//	}
	
}
