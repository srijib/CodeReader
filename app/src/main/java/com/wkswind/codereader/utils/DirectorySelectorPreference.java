package com.wkswind.codereader.utils;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Environment;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.wkswind.codereader.R;
import com.wkswind.codereader.adapter.DirectoryAdapter;
import com.wkswind.codereader.fileexplorer.FileExplorerFragment;
import com.wkswind.minilibrary.utils.PrefsUtils;

import java.io.File;


public class DirectorySelectorPreference extends DialogPreference {
	
	private static final File INITIAL_DIR = Environment.getExternalStorageDirectory();
	private File currentDir = INITIAL_DIR;
	private ListView lst;
//    private Toolbar toolbar;

    public DirectorySelectorPreference(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public DirectorySelectorPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected View onCreateDialogView() {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_main, null);
//        toolbar = (Toolbar)view.findViewById(R.id.toolbar_actionbar);
//        toolbar.inflateMenu(R.menu.directory_selector_preference);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                switch (menuItem.getItemId()){
//                    case R.id.action_back:
//                        File file = currentDir.getParentFile();
//                        toolbar.setTitle(file.getAbsolutePath());
//                        DirectoryAdapter curAdapter = new DirectoryAdapter(getContext(),file);
//                        lst.setAdapter(curAdapter);
//                        break;
//                }
//                return true;
//            }
//        });
//        toolbar.setLogo(R.drawable.ic_action_back);
		lst = (ListView) view.findViewById(R.id.history);
		final DirectoryAdapter adapter = new DirectoryAdapter(getContext(), currentDir);
		lst.setAdapter(adapter);
		lst.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				currentDir = ((DirectoryAdapter)parent.getAdapter()).getItem(position);
				getDialog().setTitle(currentDir.getAbsolutePath());
//                toolbar.setTitle(currentDir.getAbsolutePath());
				DirectoryAdapter curAdapter = new DirectoryAdapter(getContext(), ((DirectoryAdapter)parent.getAdapter()).getItem(position)); 
				lst.setAdapter(curAdapter);
				
			}
		});
		FileExplorerFragment.setEmptyView(lst);
//		getDialog().setTitle(currentDir.getAbsolutePath());
//		lst.addHeaderView(v);
		return view;
	}

    @Override
	protected void onPrepareDialogBuilder(Builder builder) {
		// TODO Auto-generated method stub
		super.onPrepareDialogBuilder(builder);
//		builder.setTitle(currentDir.getAbsolutePath());
//        builder.setNeutralButton("hello", this);
        builder.setNeutralButton(R.string.action_back,null);
	}
	
	@Override
	protected void onBindDialogView(View view) {
		// TODO Auto-generated method stub
		super.onBindDialogView(view);
		lst.setAdapter(new DirectoryAdapter(getContext(), currentDir));
	}
	
	@Override
	protected void onSetInitialValue(boolean restorePersistedValue,
			Object defaultValue) {
		// TODO Auto-generated method stub
		super.onSetInitialValue(restorePersistedValue, defaultValue);
		if(restorePersistedValue){
			currentDir = new File(PrefsUtils.get(getContext(), getKey(), Environment.getExternalStorageDirectory().getAbsolutePath()));
		}else{
			if (defaultValue == null){
				currentDir = INITIAL_DIR;				
			}else{
				currentDir = new File((String)defaultValue);
			}
		}
//		lst.setAdapter(new DirectoryAdapter(getContext(), currentDir));
	}
	
	@Override
	protected void onDialogClosed(boolean positiveResult) {
		// TODO Auto-generated method stub
		super.onDialogClosed(positiveResult);
		if(positiveResult){
			PrefsUtils.put(getContext(), getKey(), currentDir.getAbsolutePath());
			callChangeListener(currentDir.getAbsolutePath());
//			persistString(currentDir.getAbsolutePath());
//			shouldPersist();
		}
	}

}
