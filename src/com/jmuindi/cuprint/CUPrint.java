package com.jmuindi.cuprint;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;

/**
 * API class that offers interaction with 
 * the Print At CU service for Android 
 * Mobile Devices
 * @author Jervis
 *
 */
public class CUPrint {

	/**
	 * Loads the test.pdf packageed file
	 * @throws IOException 
	 */
	public static void loadTestFile(Context ctx) throws IOException {
		
		final String fileName = "test.pdf";
		InputStream in = ctx.getAssets().open(fileName);
		// write out the test file. 
		String outPath = "/data/data/com.jmuindi.cuprint/" + fileName;
		OutputStream out = new FileOutputStream(outPath); 
		
		byte[] buffer = new byte[1024];
		int length;
		
		while ((length = in.read(buffer)) > 0) {
			out.write(buffer, 0, length);
		}
		out.flush();
		out.close();
		in.close();
	}
	
	public static void print() {
		
	}
	
}
