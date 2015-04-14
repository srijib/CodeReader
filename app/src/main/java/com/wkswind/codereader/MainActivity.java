package com.wkswind.codereader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;

import com.wkswind.codereader.fileexplorer.FileExplorerFragment;
import com.wkswind.codereader.fileexplorer.RelatedFragment;
import com.wkswind.codereader.model.RootInfo;
import com.wkswind.minilibrary.utils.PrefsUtils;

import java.io.File;

public class MainActivity extends BaseActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks, FileExplorerFragment.IFileSelected {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

//	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
//		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

//	public void onSectionAttached(RootInfo info) {
//		mTitle = info.getTitle();
//	}

//	public void restoreActionBar() {
//		ActionBar actionBar = getSupportActionBar();
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//		actionBar.setDisplayShowTitleEnabled(true);
//		actionBar.setTitle(mTitle);
//	}

	@Override
	public void onNavigationDrawerItemSelected(RootInfo info) {
		getSupportActionBar().setTitle(info.getIntent());
		if(info.getIntent().equals(getString(R.string.title_activity_settings))){
			startActivity(new Intent(this,SettingsActivity.class));
		}else if(info.getIntent().equals(getString(R.string.action_history))){
			FragmentManager fragmentManager = getSupportFragmentManager();
			Bundle args = new Bundle();
			args.putInt(RelatedFragment.class.getSimpleName(), RelatedFragment.HISTORY_LOADER);
			Fragment history = RelatedFragment.newInstance(args);
			fragmentManager
					.beginTransaction()
					.replace(R.id.container,
							history).commit();
		}else if(info.getIntent().equals(getString(R.string.action_starred))){
			FragmentManager fragmentManager = getSupportFragmentManager();
			Bundle args = new Bundle();
			args.putInt(RelatedFragment.class.getSimpleName(), RelatedFragment.STAR_LOADER);
			Fragment history = RelatedFragment.newInstance(args);
			fragmentManager
					.beginTransaction()
					.replace(R.id.container,
							history).commit();
		} else{
			FragmentManager fragmentManager = getSupportFragmentManager();
			Bundle args = new Bundle();
			args.putString(FileExplorerFragment.CODE_TYPE, info.getIntent());
			args.putSerializable(FileExplorerFragment.FILE_DIRECTORY, new File(PrefsUtils.get(this, "doc_directory", Environment.getExternalStorageDirectory().getAbsolutePath())));
			fragmentManager
					.beginTransaction()
					.replace(R.id.container,
							FileExplorerFragment.newInstance(args)).commit();
		}

//		mTitle = info.getIntent();
//		restoreActionBar();
	}

	@Override
	public void onFileSelected(File file) {
		// TODO Auto-generated method stub
		if(file.isFile()){
			startActivity(new Intent(this, ReaderActivity.class).setAction(Intent.ACTION_VIEW).setData(Uri.fromFile(file)));
		}
	}		

}
