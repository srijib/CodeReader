package com.wkswind.codereader.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wkswind.codereader.R;
import com.wkswind.codereader.utils.IconUtils;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DirectoryAdapter extends BaseAdapter {
	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("MM-dd", Locale.getDefault());
	private static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat("hh:mm", Locale.getDefault());
	private LayoutInflater inflater;
	private List<File> files;
	private Context context;
	public DirectoryAdapter(Context context, File initialDirectory){
		this.context = context;
		inflater = LayoutInflater.from(context);
		files = Arrays.asList(initialDirectory.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return !pathname.getName().startsWith(".")&&!pathname.isFile();
			}
		}));
		Collections.sort(files);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return files == null ? 0 : files.size();
	}

	@Override
	public File getItem(int position) {
		// TODO Auto-generated method stub
		return files == null ? null : files.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_doc_list, parent,
					false);
			holder = new ViewHolder();
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		File item = getItem(position);
		holder.iconMime = (ImageView) convertView
				.findViewById(R.id.icon_mime);
		holder.title = (TextView) convertView
				.findViewById(android.R.id.title);
		holder.date = (TextView) convertView.findViewById(R.id.date);
		holder.size = (TextView) convertView.findViewById(R.id.size);
		
		holder.size.setVisibility(View.GONE);
		
		holder.title.setText(item.getName());
		
		long lastModify = item.lastModified();
//		if(DateUtils.isToday(lastModify))
		holder.date.setText(DateUtils.isToday(lastModify) ?  FORMAT_TIME.format(new Date(item.lastModified())):FORMAT_DATE.format(new Date(item.lastModified())));
		if(item.isDirectory()){
			holder.size.setVisibility(View.GONE);
		}else{
			holder.size.setVisibility(View.VISIBLE);;
		}
//		holder.size.setText(readableFileSize(getFileSize(item)));
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
			holder.iconMime.setBackground(IconUtils.loadMimeIcon(context, item));
		}else{
			holder.iconMime.setBackgroundDrawable(IconUtils.loadMimeIcon(context, item));
		}
		return convertView;

	}
	
	static class ViewHolder{
		ImageView iconMime;
		TextView title, date, size;
	}


}
