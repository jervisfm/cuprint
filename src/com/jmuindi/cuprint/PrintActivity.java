package com.jmuindi.cuprint;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class PrintActivity extends Activity {

	
	public static final String TAG = "PrintActivity";

	// Request Code for On Activity Result 
	private static final int ACTIVITY_REQUEST_CODE = 6387;
	public HashMap<String, ArrayList<String>> printMap = null;
	public File file = null; 
	
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
		
	
	private boolean havePrintableFile() {
		if (this.file == null) {
			return false;
		} else if (!this.file.exists()) {
			return false;
		} else {
			return true;
		}
	}
	
	private void setFileToPrint(File f, String uriName) {
		if (f == null) {
			Log.w(TAG, "Setting null file to Print");
		} else if (!f.exists()) {
			Log.w(TAG, "Setting non-existent file to Print");
		} else {
			Log.v(TAG, "Setting this file for print: " + f.getAbsolutePath());
			this.file = f; 
			
			// Update selected file in UI 
			String fname = "";
			if (uriName.trim().length() > 0) {
				fname = uriName;
				Log.d(TAG, "Using URI Name: " + uriName);
			} else if (f.getName().trim().length() > 0) {
				Log.d(TAG, "Using FName: " + f.getName());
				fname = f.getName(); 
			} else {
				Log.e(TAG, "File name not available");
			}
			
			TextView tv = (TextView) findViewById(R.id.textViewFilename);
			tv.setText(fname); 
			
			// Enable Print Button
			Button btn = (Button)  findViewById(R.id.btnPrint); 
			if (!btn.isEnabled()) {
				btn.setEnabled(true);
			}
		}
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
	
	private void showFileBrowser() {
		// Use the GET_CONTENT intent from the utility class
		Intent target = FileUtils.createGetContentIntent();
		// Create the chooser Intent
		Intent intent = Intent.createChooser(
				target, "Select File to Print");
		try {
			startActivityForResult(intent, ACTIVITY_REQUEST_CODE);
		} catch (ActivityNotFoundException e) {
			// The reason for the existence of aFileChooser
		}				
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

		case R.id.btnBrowse: {
			showFileBrowser(); 
			break;
		}
		default:
			break;
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ACTIVITY_REQUEST_CODE:	
			// If the file selection was successful
			if (resultCode == RESULT_OK) {		
				if (data != null) {
					// Get the URI of the selected file
					final Uri uri = data.getData();
					try {
						// Create a file instance from the URI
						final File file = FileUtils.getFile(uri);						
						setFileToPrint(file, uri.getLastPathSegment());						
					} catch (Exception e) {
						Log.e("FileSelectorTestActivity", "File select error", e);
					}
				}
			} 
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
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
