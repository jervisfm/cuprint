package com.jmuindi.cuprint;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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

	
	public void onClick(View v) {
		
		int id = v.getId(); 
		switch (id) {
		case R.id.ImageButtonMinus: {			
			ImageButton ib = (ImageButton) v;
			EditText et = (EditText) findViewById(R.id.editTextCopies);
			int current = Integer.parseInt(et.getText().toString());
			if (current > 1) {
				String newValue = String.valueOf(current - 1);
				et.setText(newValue);				
			}						
			break;
		}
		case R.id.ImageButtonPlus: {			
			ImageButton ib = (ImageButton) v; 
			EditText et = (EditText) findViewById(R.id.editTextCopies);
			int current = Integer.parseInt(et.getText().toString());
			String newValue = String.valueOf(current + 1); 
			et.setText(newValue); 
			break;
		}

		default:
			break;
		}
		
	}
	
	public void test() {
		boolean success = CUPrint.loadTestFile(this);
		// sm("Load File was Successful? " + success);
		//asyncHttpPrintTest();		
		//sm("async done");
		loadPrinters();
	}
	
	
	public void asyncHttpTest() {
		System.out.println("Making ASYNC Request2 ...");		
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://httpbin.org/get?q=search";
		client.get(url, new AsyncHttpResponseHandler() {							
			@Override
		    public void onSuccess(String response) {		        
		    	System.out.println(response);
		    }
		});	
		
		
	}
	public void asyncHttpPrintTest() {
		CUPrint.loadTestFile(this);
		CUPrint.testPrint();
	}
	
	
	public void loadPrinters() {
		Printers.getPrintersList(this);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.print, menu);
		return true;
	}

}
