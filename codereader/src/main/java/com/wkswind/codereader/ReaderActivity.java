package com.wkswind.codereader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.Toast;

import com.wkswind.codereader.base.BaseActivity;
import com.wkswind.codereader.synatax.DocumentHandler;
import com.wkswind.codereader.synatax.DocumentHandlerImpl;
import com.wkswind.codereader.utils.PrefsUtils;
import com.wkswind.codereader.utils.SourceEditor;
import com.wkswind.codereader.utils.io.CharsetDetector;
import com.wkswind.codereader.utils.uihelper.SystemUiHelper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class ReaderActivity extends BaseActivity implements SystemUiHelper.OnVisibilityChangeListener {
	private Uri selectedFile;
	public static final int REQUEST_FILE_CHOOSE = 0;
	private WebView codeReader;

	private SourceEditor sourceEditor;

	private int maxFileSizeBytes ;
	private static final String LAST_READ = "last_read";
	private boolean isStarred = false;
    private SystemUiHelper uiHelper;
	private GestureDetectorCompat gestureDetectorCompat;

    @SuppressLint("SetJavaScriptEnabled")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reader);
		maxFileSizeBytes = getResources().getInteger(R.integer.max_file_size_bytes);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        uiHelper = new SystemUiHelper(this, SystemUiHelper.LEVEL_IMMERSIVE, SystemUiHelper.FLAG_IMMERSIVE_STICKY, this);
		codeReader = (WebView) findViewById(R.id.code_reader);
		sourceEditor = new SourceEditor(codeReader);
        gestureDetectorCompat = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDown(MotionEvent e) {
                return super.onDown(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                uiHelper.show();
                return true;
            }
        });
        codeReader.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetectorCompat.onTouchEvent(event);
            }
        });
		String action = getIntent().getAction();
		if (Intent.ACTION_VIEW.equals(action) || Intent.ACTION_OPEN_DOCUMENT.equals(action)) {
			selectedFile = getIntent().getData();
			if (selectedFile != null
					&& selectedFile.toString().startsWith("file")) {
                try {
                    loadFile(selectedFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		} else {
			Uri lastUri = null;
			String lastRead = PrefsUtils.get(this, LAST_READ, "");
			if (!TextUtils.isEmpty(lastRead)) {
				lastUri = Uri.parse(lastRead);
			}

			if (savedInstanceState != null) {
				codeReader.restoreState(savedInstanceState);
				lastRead = savedInstanceState.getString(LAST_READ);
				if (!TextUtils.isEmpty(lastRead)) {
					lastUri = Uri.parse(lastRead);
				}
			}

			if (lastUri != null) {
                try {
                    loadFile(lastUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (selectedFile != null) {
			outState.putString(LAST_READ, String.valueOf(selectedFile));
		}
	}

    @Override
	protected void onDestroy() {
		if (selectedFile != null) {
			PrefsUtils.put(this, LAST_READ, selectedFile.toString());
		}
//		codeReader.getSettings().setBuiltInZoomControls(false);
		ViewParent parent = codeReader.getParent();
		if(parent != null){
			if(parent instanceof ViewGroup){
				((ViewGroup) parent).removeView(codeReader);
			}
		}
		codeReader.destroy();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reader, menu);
		MenuItem menuItem = menu.findItem(R.id.action_share);
		Log.d("TAG", "Share Action Provider : " + (menuItem == null));
		// Get the provider and hold onto it to set/change the share intent.
		ShareActionProvider actionProvider = (ShareActionProvider) MenuItemCompat
				.getActionProvider(menuItem);
		actionProvider
				.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
		// Note that you can set/change the intent any time,
		// say when the user has selected an image.
		actionProvider.setShareIntent(createShareIntent());

		final MenuItem starredItem = menu.findItem(R.id.action_starred);
		final CheckBox chkStarred = (CheckBox) MenuItemCompat.getActionView(starredItem);
		chkStarred.setClickable(true);
//		Cursor cursor = getApplication().getContentResolver().query(CodeProvider.Stars.CONTENT_URI,new String[]{"*"}, StarsColumn.fileName+"=? and "+StarsColumn.star+"=?", new String[]{selectedFile.getPath(),"1"},null);
//		chkStarred.setChecked(cursor != null && cursor.moveToLast() &&cursor.getCount()>0);
//		chkStarred.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				getApplication().getContentResolver().delete(CodeProvider.Stars.CONTENT_URI, StarsColumn.fileName + "= ? ", new String[]{selectedFile.getPath()});
//				ContentValues cv = new ContentValues();
//				cv.put(StarsColumn.lastReadTime, Calendar.getInstance().getTimeInMillis());
//				cv.put(StarsColumn.fileName, selectedFile.getPath());
//				cv.put(StarsColumn.star, isChecked);
//				getApplication().getContentResolver().insert(CodeProvider.Stars.CONTENT_URI,cv);
//			}
//		});
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.action_share).setEnabled(selectedFile != null);
		return super.onPrepareOptionsMenu(menu);
	}

	private Intent createShareIntent() {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, selectedFile);
		return shareIntent;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
            /**
             * handle like {@link SettingsActivity}'s up button click
             */
            onBackPressed();
            break;
        case R.id.action_fullscreen:
            uiHelper.delayHide(100);
			Toast.makeText(getApplication(), R.string.double_tap_exit_fullscreen, Toast.LENGTH_SHORT).show();
            break;
		default:
			break;
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == REQUEST_FILE_CHOOSE) {
				selectedFile = data.getData();
                try {
                    loadFile(selectedFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
	}

	private void loadFile(Uri uri) throws IOException {
		File file = new File(uri.getPath());
		if (!file.exists()) {
			Toast.makeText(this, R.string.file_not_exists, Toast.LENGTH_SHORT)
					.show();
			return;
		}
		if (file.length() > maxFileSizeBytes) {
			Toast.makeText(this, getResources().getString(R.string.file_too_large, Formatter.formatFileSize(this,maxFileSizeBytes)), Toast.LENGTH_SHORT)
					.show();
			return;
		}
        Charset charset = CharsetDetector.decodeCharset(file);
//		LLog.i(charset.displayName());
		DocumentHandler handler = getHandlerByFileExtension(uri);
		final long length = file.length();

		if (handler == null) {
			return;
		}
		sourceEditor.setSource(uri);
		getSupportActionBar().setTitle(file.getName());
		getSupportActionBar().setSubtitle(file.getAbsolutePath());

		selectedFile = uri;
		supportInvalidateOptionsMenu();
		updateRecentReading(uri.getPath());
	}
	
	private void updateRecentReading(String url){
//		ContentValues cv = new ContentValues();
//		cv.put(HistorysColumn.fileName, url);
//		cv.put(HistorysColumn.lastReadTime, Calendar.getInstance().getTimeInMillis());
//		getApplication().getContentResolver().delete(CodeProvider.Historys.CONTENT_URI, HistorysColumn.fileName + "=?", new String[]{url});
//		getApplication().getContentResolver().insert(CodeProvider.Historys.CONTENT_URI, cv);
	}

	private DocumentHandler getHandlerByFileExtension(Uri uri) {
		DocumentHandlerImpl impl = new DocumentHandlerImpl();
		File file = new File(uri.getPath());
		if (file.exists() && file.isFile()) {
			String extension = MimeTypeMap.getFileExtensionFromUrl(file
					.getAbsolutePath());
			impl.setFileExtension(extension);
			if(!TextUtils.isEmpty(extension)){
				impl.setFileScriptClass(extension.substring(1));
			}
			return impl;
		}
		return null;

	}

    @Override
    public void onVisibilityChange(boolean visible) {
    }

}
