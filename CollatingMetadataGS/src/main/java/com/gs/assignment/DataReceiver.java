/**
 * 
 */
package com.gs.assignment;

import java.util.Map;

/**
 * B2CMessage
 * Ordermanagement system
 * observer pattern will let t register.
 *
 */
public interface DataReceiver {
	public void onDataReceived(Map data);
}
