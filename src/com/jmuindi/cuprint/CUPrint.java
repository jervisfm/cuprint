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

	
	public static String getFilePath(String filename) {
		return "/data/data/com.jmuindi.cuprint/" + filename;
	}
	
	/**
	 * Loads the test.pdf packageed file
	 * @throws IOException 
	 */
	public static boolean loadTestFile(Context ctx) {
		
		try {
			final String filename = "test.pdf";
			InputStream in = ctx.getAssets().open(filename);
			// write out the test file. 
			String outPath = getFilePath(filename);
			OutputStream out = new FileOutputStream(outPath);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			out.flush();
			out.close();
			in.close();
			return true;
		} catch (IOException e) {
			// TODO: handle exception
			return false;
		}
		
	}
	
	public static void print() {
				
		
	}
	
}
