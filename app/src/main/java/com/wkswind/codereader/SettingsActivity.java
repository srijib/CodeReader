package com.wkswind.codereader;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.wkswind.codereader.fileexplorer.DirectoryExplorerDialog;
import com.wkswind.codereader.utils.DirectorySelectorPreference;
import com.wkswind.codereader.utils.PrefsUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


/**
 * A {@link android.preference.PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends BaseActivity {
	/**
	 * Determines whether to always show the simplified settings UI, where
	 * settings are presented in a single list. When false, settings are shown
	 * as a master/detail two-pane view on tablets. When true, a single pane is
	 * shown on tablets.
	 */
	private static final boolean ALWAYS_SIMPLE_PREFS = false;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = getActionBarToolbar();
        toolbar.setTitle(R.string.title_activity_settings);
        toolbar.setNavigationIcon(R.drawable.ic_up);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * Since Settings can be reached from multiple entry points, the up button should
                 * choose to the referring screen, behaving identically to Back.
                 * So we just call {@link Activity.onBackPressed()} or simply call {@link Activity.finish()}
                 */
                onBackPressed();
            }
        });
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new GeneralPreferenceFragment())
                    .commit();
        }
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class GeneralPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{
        private static final String TAG = GeneralPreferenceFragment.class.getSimpleName();

        @Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.pref_general);
			// findPreference("code")
			// Bind the summaries of EditText/List/Dialog/Ringtone preferences
			// to their values. When their values change, their summaries are
			// updated to reflect the new value, per the Android Design
			// guidelines.
			bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_doc_types)),
					getActivity());
			bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_directory)),
					getActivity());
		}

        @Override
        public void onResume() {
            super.onResume();
            try {
                PackageManager packageManager = getActivity().getPackageManager();
                PackageInfo  packInfo = packageManager.getPackageInfo(getActivity().getPackageName(),0);
                String version = packInfo.versionName;
                findPreference(getString(R.string.pref_key_version_code)).setSummary(version);
                Log.i(TAG, PrefsUtils.get(getActivity(), getString(R.string.pref_key_directory), Environment.getExternalStorageDirectory().getAbsolutePath()));
                findPreference(getString(R.string.pref_key_directory)).setSummary(PrefsUtils.get(getActivity(),getString(R.string.pref_key_directory),Environment.getExternalStorageDirectory().getAbsolutePath()));
                getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
            } catch (NameNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void onStop() {
            super.onStop();
//            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
            getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals(getString(R.string.pref_key_directory))){
                findPreference(key).setSummary(PrefsUtils.get(getActivity(),key,Environment.getExternalStorageDirectory().getAbsolutePath()));
            }
        }
    }

	public static void bindPreferenceSummaryToValue(Preference preference,
			final Context context) {
		BindPreference sBindPreference = new BindPreference(context);
		preference
				.setOnPreferenceChangeListener(sBindPreference);
		if(preference.getKey().equals(context.getString(R.string.pref_key_doc_types))){
			String[] defaults = context.getResources().getStringArray(R.array.default_doc_type);
			HashSet<String> sets = new HashSet<String>();
			for(String d : defaults){
				sets.add(d);
			}
			sBindPreference.onPreferenceChange(preference, PrefsUtils.get(context, preference.getKey(), sets));
		}else if(preference.getKey().equals(context.getString(R.string.pref_key_directory))){
            preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    if(context instanceof BaseActivity){
                        DirectoryExplorerDialog dialog = DirectoryExplorerDialog.newInstance(null);
                        dialog.show(((BaseActivity) context).getSupportFragmentManager(),dialog.getClass().getSimpleName());
                    }
                    return true;
                }
            });
//            sBindPreference.onPreferenceChange(preference, PrefsUtils.get(context, preference.getKey(), Environment.getExternalStorageDirectory().getAbsolutePath()));
        }
		
		
	}
	
	static class BindPreference implements Preference.OnPreferenceChangeListener{
		private Context context;
		public BindPreference(Context context){
			this.context = context;
		}
		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			// TODO Auto-generated method stub
			StringBuffer summary = new StringBuffer();
			if (preference instanceof MultiSelectListPreference) {
				TreeSet<String> orderSet = new TreeSet<String>();
				for (String s : (Set<String>)newValue) {
					orderSet.add(s);
				}
				for(String s : orderSet){
					summary.append(s).append(" ,");
				}
                summary.deleteCharAt(summary.lastIndexOf(","));
			} else if (preference instanceof DirectorySelectorPreference) {
				preference.setDefaultValue(Environment.getExternalStorageDirectory().getAbsolutePath());
				summary.append(PrefsUtils.get(context, preference.getKey(),
                        Environment.getExternalStorageDirectory().getAbsolutePath()));
			} else if(preference.getKey().equals(context.getString(R.string.pref_key_directory))){
                summary.append(PrefsUtils.get(context,context.getString(R.string.pref_key_directory), Environment.getExternalStorageDirectory().getAbsolutePath()));
            }

			preference.setSummary(summary);
			return true;
		}
		
	}
}
