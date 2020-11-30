/**
 * 
 */
package com.gs.assignment;

import java.util.Map;

/**
 * 
 *
 */
public interface Recorder {

	public boolean publisherStarted(Publisher publisher);

	public boolean publisherStopped(Publisher publisher, Map data);

	public boolean publisherChangedSource(Publisher publisher, String source);
	
}
