package com.wkswind.password.base;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Administrator on 2015/6/9.
 */
public class PasswordApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
        LeakCanary.install(this);
    }
}
