package com.jmuindi.cuprint;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
}
