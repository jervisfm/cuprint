package com.jmuindi.cuprint;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.ipaulpro.afilechooser.utils.FileUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Base64OutputStream;

/**
 * Contains helpful utility methods
 * @author Jervis
 *
 */
public class Util {
	public static boolean isNetworkAvailable(Context ctx) {
	    ConnectivityManager cm = (ConnectivityManager) 
	    						ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	public static String getCurrentTime() {
		Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        return format.format(calendar.getTime());
	}
	
	public static String saveObjectToBase64(Serializable b) {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
	    ObjectOutputStream objectOutput;
	    try {
	        objectOutput = new ObjectOutputStream(arrayOutputStream);
	        objectOutput.writeObject(b);
	        byte[] data = arrayOutputStream.toByteArray();
	        objectOutput.close();
	        arrayOutputStream.close();

	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        Base64OutputStream b64 = new Base64OutputStream(out, Base64.DEFAULT);
	        b64.write(data);
	        b64.close();
	        out.close();

	        return new String(out.toByteArray());
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null; 
	    }
	}
	
	public static Object getObjectFromBase64(String base64) {
		
		byte[] data = base64.getBytes(); 
		ByteArrayInputStream byteArray = new ByteArrayInputStream(data);
	    Base64InputStream base64InputStream = new Base64InputStream(byteArray, Base64.DEFAULT);
	    ObjectInputStream in;	   
	    try {
	    	in = new ObjectInputStream(base64InputStream);
			return in.readObject();
		} catch (OptionalDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return null;
	}
	
	public static boolean canPrintExtension(String ext) {
		if (ext != null) {
			String[] formats = CUPrint.SUPPORTED_FILE_FORMATS;
			for(String f : formats) {
				if(ext.trim().equalsIgnoreCase(f))
					return true;
			}
			
		} 
		return false;
		
	}
}
