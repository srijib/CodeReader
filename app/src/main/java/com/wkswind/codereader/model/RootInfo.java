package com.wkswind.codereader.model;

import android.content.Context;
import android.content.res.Resources;

import com.wkswind.codereader.R;
import com.wkswind.codereader.utils.PrefsUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RootInfo {
	public static final int NAVDRAWER_ITEM = -1;
	public static final int NAVDRAWER_ITEM_SEPARATOR = -2;

	private int type = NAVDRAWER_ITEM;
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
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isSeparator(){
		return type == NAVDRAWER_ITEM_SEPARATOR;
	}
	
	public static ArrayList<RootInfo> init(Resources res, Context context){
		ArrayList<RootInfo> infos = new ArrayList<RootInfo>();
		String[] titles = res.getStringArray(R.array.doc_types);
		String[] intent = res.getStringArray(R.array.doc_intent);
		Set<String> defaultValues = new HashSet<String>();
		String[] types = res.getStringArray(R.array.default_doc_type);
		for(String t : types){
			defaultValues.add(t);
		}
		
		Set<String> selectedTypes = PrefsUtils.get(context, "doc_types", defaultValues);
		RootInfo star = new RootInfo();
		star.setTitle(res.getString(R.string.action_starred));
		star.setIcon(R.drawable.ic_action_starred);
		star.setIntent(star.getTitle());

		RootInfo history = new RootInfo();
		history.setTitle(res.getString(R.string.action_history));
		history.setIcon(R.drawable.ic_action_history);
		history.setIntent(history.getTitle());

		RootInfo divider = new RootInfo();
		divider.setType(NAVDRAWER_ITEM_SEPARATOR);

		infos.add(star);
		infos.add(history);
		infos.add(divider);
		for(int i=0,j=titles.length;i<j;i++){
			RootInfo info = new RootInfo();
			info.setIcon(R.drawable.ic_action_source);
			info.setIntent(intent[i]);
			info.setTitle(titles[i]);
			if(selectedTypes.contains(titles[i])){
				infos.add(info);
			}
		}
		infos.add(divider);
		RootInfo setting = new RootInfo();
		setting.setTitle(res.getString(R.string.title_activity_settings));
		setting.setIcon(R.drawable.ic_action_settings);
		setting.setIntent(setting.getTitle());
		infos.add(setting);
		return infos;
	}


}
