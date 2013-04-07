package com.jmuindi.cuprint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class PrintActivity extends Activity {

	
	public static final String TAG = "PrintActivity";
	public HashMap<String, ArrayList<String>> printMap = null;
	
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

	
	private void onClickImageButtonMinus(ImageButton ib) {
		EditText et = (EditText) findViewById(R.id.editTextCopies);
		int current = Integer.parseInt(et.getText().toString());
		if (current > 1) {
			String newValue = String.valueOf(current - 1);
			et.setText(newValue);				
		}
	}
	
	private void onClickImageButtonPlus(ImageButton ib) {
		EditText et = (EditText) findViewById(R.id.editTextCopies);
		int current = Integer.parseInt(et.getText().toString());
		String newValue = String.valueOf(current + 1); 
		et.setText(newValue); 
	}
		
	
	private void initBuildingSpinner() {
		
		// Initialize the Print Map if Needed
		if (printMap == null) {
			printMap = Printers.getPrintersList(this);			
		}
		
		Spinner spinner = (Spinner) findViewById(R.id.spinnerBuilding);
		ArrayList<String> buildings = new ArrayList<String>(20);
		for (String building : printMap.keySet()) {
			buildings.add(building);
		}
		Collections.sort(buildings);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, buildings);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		
		
		// Attach OnItem Selected Listener so that we update the printers Spinner 
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				String building = (String) parent.getItemAtPosition(pos);
				updatePrinterSpinner(building);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {				
				// Do nothing
			}			
		});
		
		
		
	}
	
	private void updatePrinterSpinner(String building) {		
		Spinner spinner = (Spinner) findViewById(R.id.spinnerPrinter);
		ArrayList<String> printers = printMap.get(building);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, printers);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);		
	}
	
	
	public void onClick(View v) {
		
		int id = v.getId(); 
		switch (id) {
		case R.id.ImageButtonMinus: {			
			ImageButton ib = (ImageButton) v;
			onClickImageButtonMinus(ib);
			break;
		}
		case R.id.ImageButtonPlus: {			
			ImageButton ib = (ImageButton) v; 
			onClickImageButtonPlus(ib);
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
		//loadPrinters();
		initBuildingSpinner();
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
