import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;

import com.wkswind.codereader.R;
import com.wkswind.codereader.base.BaseActivity;
import com.wkswind.codereader.base.BasePreferenceActivity;
import com.wkswind.codereader.utils.DirectoryExplorerDialog;
import com.wkswind.codereader.utils.DirectorySelectorPreference;
import com.wkswind.codereader.utils.PrefsUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Administrator on 2015-11-20.
 */
public class SettingsActivity extends BasePreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_doc_types)), this);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_directory)), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
            String version = packInfo.versionName;
            findPreference(getString(R.string.pref_key_version_code)).setSummary(version);
//            Log.i(TAG, PrefsUtils.get(getActivity(), getString(R.string.pref_key_directory), Environment.getExternalStorageDirectory().getAbsolutePath()));
            findPreference(getString(R.string.pref_key_directory)).setSummary(PrefsUtils.get(this, getString(R.string.pref_key_directory), Environment.getExternalStorageDirectory().getAbsolutePath()));
            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        } catch (PackageManager.NameNotFoundException e) {
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
            findPreference(key).setSummary(PrefsUtils.get(this,key,Environment.getExternalStorageDirectory().getAbsolutePath()));
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
