package com.jmuindi.cuprint;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Responsible for loading the printers from file. 
 * @author Jervis
 *
 */
public class Printers {

	private static String printers_file = "printers.json";
	
	private static String readEntireStream(InputStream in) {		
		InputStreamReader isr = new InputStreamReader(in); 
		char[] buf = new char[1024];
		int length; 
		StringBuffer sb = new StringBuffer(); 
		try {
			while ( (length = isr.read(buf)) > 0) {
				for (int i = 0; i < length; ++i) {
					sb.append(buf[i]);
				}				
			}
			return sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}							
	}
	
	private static String loadPrinterFile(Context ctx) throws IOException {		
		InputStream in = ctx.getAssets().open(printers_file);		
		return readEntireStream(in);
	}
			
	public static HashMap<String, ArrayList<String>> getPrintersList(Context ctx) {
		String printersJson;
		try {
			printersJson = loadPrinterFile(ctx);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		Gson gson = new Gson();
		Type collectionType = new TypeToken<HashMap<String,ArrayList<String>>>(){}.getType();
		HashMap<String, ArrayList<String>> hm = gson.fromJson(printersJson, collectionType);		
		return hm;
	}
}
