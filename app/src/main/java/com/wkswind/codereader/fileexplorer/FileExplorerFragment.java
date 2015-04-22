package com.wkswind.codereader.fileexplorer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ListView;

import com.wkswind.codereader.BaseListFragment;
import com.wkswind.codereader.R;
import com.wkswind.codereader.ReaderActivity;
import com.wkswind.codereader.SettingsActivity;
import com.wkswind.codereader.database.CodeProvider;
import com.wkswind.codereader.database.HistorysColumn;
import com.wkswind.codereader.database.StarsColumn;
import com.wkswind.codereader.fileexplorer.sort.SortFolder;
import com.wkswind.codereader.fileexplorer.sort.SortType;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class FileExplorerFragment extends BaseListFragment implements
		LoaderManager.LoaderCallbacks<List<File>> {
	public static final String FILE_DIRECTORY = "file_directory";
	public static final String CODE_TYPE = "code_type";//只查询指定后缀名的文件
	private int LOADER_ID = 1;

	public static FileExplorerFragment newInstance(Bundle args){
		FileExplorerFragment fragment = new FileExplorerFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	public static interface IFileSelected{
		public void onFileSelected(File file);
	}

	private FileAdapter adapter;
	private IFileSelected iFileSelected;
	private boolean isSearching = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		if(activity instanceof IFileSelected){
			iFileSelected = (IFileSelected) activity;
		}else{
			throw new IllegalArgumentException("activity not instanceof IFileSelected ");
		}
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		iFileSelected = null;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		registerForContextMenu(getListView());
		setHasOptionsMenu(true);
		adapter = new FileAdapter(getActivity(),null);
		getListView().setFastScrollEnabled(true);
		setListAdapter(adapter);
//		setListShown(false);
//		getLoaderManager().initLoader(LOADER_ID, getArguments(), this);
		query();
	}
	

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
//		inflater.inflate(R.menu.drawer, menu);
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onPrepareOptionsMenu(menu);
		menu.setGroupEnabled(R.id.group, !isSearching);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		if(iFileSelected != null){
			iFileSelected.onFileSelected(adapter.getItem(position));
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.sort_by_name:
			adapter.sort(SortType.byDefault);
			break;
		case R.id.sort_by_date:
			adapter.sort(SortType.byDate);
			break;
		case R.id.sort_by_size:
			adapter.sort(SortType.bySize);
			break;
		case R.id.action_refresh:
			query();
			break;
		case android.R.id.home:
			getActivity().finish();
			break;
		case R.id.action_open:
			startActivityForResult(
					new Intent(getActivity(), FileExplorerActivity.class),
					ReaderActivity.REQUEST_FILE_CHOOSE);
			break;
		case R.id.action_setting:
			startActivity(new Intent(getActivity(),SettingsActivity.class));
			break;
		default:
			break;
		}
		return true;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	void query(){
		showProgress(true);
		if(getLoaderManager().getLoader(LOADER_ID) == null){
			getLoaderManager().initLoader(LOADER_ID, getArguments(), this);
		}else{
			getLoaderManager().restartLoader(LOADER_ID, getArguments(), this);
		}
	}
	
	void showProgress(boolean show){
		if(show){
			setListShown(false);
		}else{
			if (isResumed()) {
				setListShown(true);
			} else {
				setListShownNoAnimation(true);
			}
		}
		setListShown(!show);
		isSearching = show;
		getActivity().supportInvalidateOptionsMenu();
	}
	
	static class FileLoader extends AsyncTaskLoaderEx<List<File>>{

		public FileLoader(Context context, Bundle extras) {
			super(context, extras);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected List<File> loadDataInBackground(Bundle extras) {
			// TODO Auto-generated method stub

			File file = null;
			String codeType = extras == null ? null : extras.getString(CODE_TYPE);
			if(getContext().getString(R.string.action_starred).equals(codeType)){
				ArrayList<File> stars = new ArrayList<>();
				Cursor cursor = getContext().getContentResolver().query(CodeProvider.Stars.CONTENT_URI,new String[]{"*"},StarsColumn.star+"=1",null,null);
				if(cursor.moveToFirst()){
					while(cursor.moveToNext()){
						stars.add(new File(cursor.getString(cursor.getColumnIndex(StarsColumn.fileName))));
					}
				}
				return stars;
			}else if((getContext().getString(R.string.action_history)).equals(codeType)){
				ArrayList<File> historys = new ArrayList<>();
				Cursor cursor = getContext().getContentResolver().query(CodeProvider.Historys.CONTENT_URI,new String[]{"*"},null,null,null);
				if(cursor.moveToFirst()){
					while(cursor.moveToNext()){
						historys.add(new File(cursor.getString(cursor.getColumnIndex(HistorysColumn.fileName))));
					}
				}
				return historys;
			}
			if(extras==null || extras.getSerializable(FILE_DIRECTORY) == null){
				file = Environment.getExternalStorageDirectory();
			}else{
				file = (File) extras.getSerializable(FILE_DIRECTORY);
			}
			
			FilenameFilter filter = new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String filename) {
					// TODO Auto-generated method stub
					return !filename.startsWith(".");
				}
			};
			
			List<File> rst = new ArrayList<File>();
			if(file.exists() && file != null){
				if(TextUtils.isEmpty(codeType)){
					rst = Arrays.asList(file.listFiles(filter));
				}else{
					rst = (List<File>) FileUtils.listFiles(file, new String[]{codeType}, true);
				}
				Collections.sort(rst, new SortFolder());
				return rst;
			}
			return null;
		}
		
	}

	
	@Override
	public Loader<List<File>> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		return new FileLoader(getActivity(), args);
	}

	@Override
	public void onLoadFinished(Loader<List<File>> arg0,
			List<File> arg1) {
		// TODO Auto-generated method stub
		adapter.setFiles(arg1);
		adapter.notifyDataSetChanged();
		showProgress(false);
	}

	@Override
	public void onLoaderReset(Loader<List<File>> arg0) {
		// TODO Auto-generated method stub
		adapter.reset();
	}
}
