
package com.wkswind.codereader.utils;

import android.util.Log;

import com.wkswind.codereader.BuildConfig;


public final class LLog {
	public static final String TAG = LLog.class.getSimpleName();
	public static void d(String msg){
		if(BuildConfig.DEBUG){
			Log.d(TAG, msg);
		}
	}
	
	public static void i(String msg){
		if(BuildConfig.DEBUG){
			Log.i(TAG, msg);
		}
	}
	
	public static void e(String msg){
		if(BuildConfig.DEBUG){
			Log.e(TAG, msg);
		}
	}
}
