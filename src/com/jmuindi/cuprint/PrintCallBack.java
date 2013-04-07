/**
 * 
 */
package com.jmuindi.cuprint;

/**
 * @author Jervis
 *
 */
public interface PrintCallBack {

	/**
	 * Called after an attempt to send a print job to the 
	 * server. 
	 *  
	 * @param success - only true if the job was successfully sent; 
	 * false otherwise. 
	 */
	public void done(boolean success); 
}
