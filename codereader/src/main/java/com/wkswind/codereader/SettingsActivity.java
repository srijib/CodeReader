package com.wkswind.codereader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.view.MenuItem;

import com.wkswind.codereader.base.BasePreferenceActivity;

/**
 * Created by Administrator on 2015-11-20.
 */
public class SettingsActivity extends BasePreferenceActivity implements Preference.OnPreferenceClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addPreferencesFromResource(R.xml.pref_general);
        findPreference(getString(R.string.pref_key_comment)).setOnPreferenceClickListener(this);
        findPreference(getString(R.string.pref_key_email)).setOnPreferenceClickListener(this);
        findPreference(getString(R.string.pref_key_comment)).setOnPreferenceClickListener(this);
        findPreference(getString(R.string.pref_key_version_code)).setSummary(BuildConfig.VERSION_NAME);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if (key.equals(getString(R.string.pref_key_email))) {
            Intent emailIntent = new Intent(Intent.ACTION_VIEW);
            emailIntent.setData(Uri.parse("mailto:wkswind@gmail.com"));
            if (emailIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(emailIntent);
            }
            return true;
        } else if (key.equals(getString(R.string.pref_key_comment))) {
            Intent commentIntent = new Intent(Intent.ACTION_VIEW);
            commentIntent.setData(Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID));
            if(commentIntent.resolveActivity(getPackageManager()) != null){
                startActivity(commentIntent);
            }
            return true;
        } else if(key.equals(getString(R.string.pref_key_libraries))){
            return true;
        }
        return false;
    }

}
