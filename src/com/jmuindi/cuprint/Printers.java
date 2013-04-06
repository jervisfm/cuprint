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

	
	public static String readEntireStream(InputStream in) {
		
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
	
	public static void loadFile(Context ctx) throws IOException {
		
		InputStream in = ctx.getAssets().open("printers.json");
		Gson gson = new Gson();
		Type collectionType = new TypeToken<HashMap<String,ArrayList<String>>>(){}.getType();
		String printers = readEntireStream(in);
		HashMap<String, ArrayList<String>> hm = gson.fromJson(printers, collectionType);		
		System.out.println("Hash Map Size == " +hm.size());
		
		for(String k : hm.keySet()) {
			System.out.println(k);
		}
	
		
		
		
		
	}
}
