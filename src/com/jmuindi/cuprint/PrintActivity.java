package com.jmuindi.cuprint;

import android.app.Activity;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class PrintActivity extends Activity {

	
	public static final String TAG = "PrintActivity";
	
	public void d(String msg) {
		Log.d(TAG,msg);
	}
	
	public void sm(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_print);		
		
		d("test debug msg");
		sm("Hello world");
		test(); 
	}

	
	public void test() {
		boolean success = CUPrint.loadTestFile(this);
		// sm("Load File was Successful? " + success);
		asyncHttpTest();
		sm("async done ");
	}
	
	
	public void asyncHttpTest() {
		System.out.println("Making ASYNC Request...");
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://www.google.com", new AsyncHttpResponseHandler() {							
			@Override
		    public void onSuccess(String response) {
		        
		    	System.out.println(response);
		    }
		});
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.print, menu);
		return true;
	}

}
