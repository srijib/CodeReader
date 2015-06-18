package com.wkswind.password;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Administrator on 2015/6/18.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);
    }
}
