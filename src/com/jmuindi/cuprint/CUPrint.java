package com.jmuindi.cuprint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * API class that offers interaction with 
 * the Print At CU service for Android 
 * Mobile Devices
 * @author Jervis
 *
 */
public class CUPrint {

	public static final String PRINT_API_URL = "https://printatcu.com/prints";
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
	
	
	/**
	 * Prints the given file.
	 * @param building - Building Printer is located in
	 * @param printer - Name of Printer
	 * @param options - Printers Options to use
	 * @param f - File to print
	 * @param callback - Called Upon when print job is done. 
	 */
	public static void print(String building, String printer, PrinterOptions options, File f, Context callback) {		
		AsyncHttpClient client = new AsyncHttpClient();				
		RequestParams params = new RequestParams(); 
		params.put("print[building]", building);
		params.put("print[printer]", printer);
		params.put("commit", "Print");
		params.put("print[collate]", options.collate ? "1" : "0" );
		params.put("print[double_sided]", options.double_sided ? "1" : "0");
		params.put("print[copies]", String.valueOf(options.copies));
		try {
			params.put("print[documents][]", f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		client.post(PRINT_API_URL, params, new AsyncHttpResponseHandler() {
			public void onSuccess(String response) {
				System.out.println("Print Succeeded"); 
				// callback.done(); 
			}
		});			
	}
	
	/**
	 * Do a test print of a file 
	 */
	public static void testPrint() {
				
		
		File f = new File(getFilePath("test.pdf"));
		System.out.println("Making ASYNC Test PRINT Request X...");		
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://httpbin.org/post";
		url = "https://printatcu.com/prints";
		RequestParams params = new RequestParams(); 
		params.put("print[building]", "Lerner");
		params.put("print[printer]", "lerner200a");
		params.put("commit", "Print");
		params.put("print[collate]", "0");
		params.put("print[double_sided]", "1");
		params.put("print[copies]", "1");
		try {
			params.put("print[documents][]", f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		client.post(url, params, new AsyncHttpResponseHandler() {
			public void onSuccess(String response) {
				System.out.println(response);
			}
		});						
	}
	
}
