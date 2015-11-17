package com.wkswind.codereader;

import android.os.Bundle;

import com.mikepenz.materialdrawer.DrawerBuilder;

/**
 * Created by Administrator on 2015-11-16.
 */
public class HomeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        new DrawerBuilder().withActivity(this).build();
    }
}
