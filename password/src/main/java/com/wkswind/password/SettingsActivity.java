package com.wkswind.password;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;

import com.wkswind.password.base.ToolbarActivity;

/**
 * Created by Administrator on 2015/6/18.
 */
public class SettingsActivity extends ToolbarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupToolbar();
        enableHomeUpIndicator();
    }
}
