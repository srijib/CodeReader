package com.wkswind.codereader.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.wkswind.codereader.R;
import com.wkswind.minilibrary.utils.PrefsUtils;

public class RootInfo {
	private String title;
	private int icon;
	private String intent;
	public RootInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String titile) {
		this.title = titile;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public String getIntent() {
		return intent;
	}
	public void setIntent(String intent) {
		this.intent = intent;
	}
	
	public static ArrayList<RootInfo> init(Resources res, Context context){
		ArrayList<RootInfo> infos = new ArrayList<RootInfo>();
		String[] titles = res.getStringArray(R.array.doc_type);
		String[] intent = res.getStringArray(R.array.doc_intent);
		Set<String> defaultValues = new HashSet<String>();
		String[] types = res.getStringArray(R.array.default_doc_type);
		for(String t : types){
			defaultValues.add(t);
		}
		
		Set<String> selectedTypes = PrefsUtils.get(context, "doc_types", defaultValues);
		TypedArray icons = res.obtainTypedArray(R.array.doc_icon);
		for(int i=0,j=titles.length;i<j;i++){
			RootInfo info = new RootInfo();
			info.setIcon(icons.getResourceId(i, -1));
			info.setIntent(intent[i]);
			info.setTitle(titles[i]);
			if(selectedTypes.contains(titles[i])){
				infos.add(info);
			}
		}
		icons.recycle();
		return infos;
	}
}	
