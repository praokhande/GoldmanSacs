/**
 * 
 */
package com.gs.assignment;

import java.util.Map;

/**
 * 
 *
 */
public class RecorderImpl implements Recorder {

	/**
	 * 
	 */
	public RecorderImpl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.gs.assignment.Recorder#publisherStarted(com.gs.assignment.Publisher)
	 */
	@Override
	public boolean publisherStarted(Publisher publisher) {
		System.out.println("Started: "+ publisher.getPublisherName() + " Type: " + publisher.getPublisherType());
		return true;
	}

	/* (non-Javadoc)
	 * @see com.gs.assignment.Recorder#publisherStopped(com.gs.assignment.Publisher, java.util.Map)
	 */
	@Override
	public boolean publisherStopped(Publisher publisher, Map data) {
		System.out.println("Stopped: "+ publisher.getPublisherName() + " Type: " + publisher.getPublisherType());
		return true;
	}

	/* (non-Javadoc)
	 * @see com.gs.assignment.Recorder#publisherChangedSource(com.gs.assignment.Publisher, java.lang.String)
	 */
	@Override
	public boolean publisherChangedSource(Publisher publisher, String source) {
		System.out.println("Source Changed: "+ publisher.getPublisherName() + " Type: " + publisher.getPublisherType() + " Old Source: " + publisher.publisherSource 
				+ " New Source: " + source);
		return true;
	}

}
