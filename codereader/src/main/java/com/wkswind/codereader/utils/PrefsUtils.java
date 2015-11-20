package com.wkswind.codereader.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Set;

public final class PrefsUtils{
	private PrefsUtils(){};
	
	public static boolean put(Context ctx, String key, String value){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		Editor editor = prefs.edit();
		editor.putString(key, value);
		return editor.commit();
	}
	
	public static boolean put(Context ctx, String key, int value){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		Editor editor = prefs.edit();
		editor.putInt(key, value);
		return editor.commit();
	}
	
	public static boolean put(Context ctx, String key, boolean value){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		Editor editor = prefs.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}
	
	public static boolean put(Context ctx, String key, float value){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		Editor editor = prefs.edit();
		editor.putFloat(key, value);
		return editor.commit();
	}
	
	public static boolean put(Context ctx, String key, long value){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		Editor editor = prefs.edit();
		editor.putLong(key, value);
		return editor.commit();
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static boolean put(Context ctx, String key, Set<String> value){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		Editor editor = prefs.edit();
		if(Build.VERSION.SDK_INT>Build.VERSION_CODES.HONEYCOMB){
			editor.putStringSet(key, value);
		}
		return editor.commit();
	}
	
	public static String get(Context ctx, String key, String defValue){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		return prefs.getString(key, defValue);
	}
	
	public static boolean get(Context ctx, String key, boolean defValue){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		return prefs.getBoolean(key, defValue);
	}
	
	public static int get(Context ctx, String key, int defValue){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		return prefs.getInt(key, defValue);
	}
	
	public static long get(Context ctx, String key, long defValue){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		return prefs.getLong(key, defValue);
	}
	
	public static float get(Context ctx, String key, float defValue){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		return prefs.getFloat(key, defValue);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static Set<String> get(Context ctx, String key, Set<String> defValue){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		if(Build.VERSION.SDK_INT>Build.VERSION_CODES.HONEYCOMB){
			return prefs.getStringSet(key, defValue);
		}
		return defValue;
		
	}
}
