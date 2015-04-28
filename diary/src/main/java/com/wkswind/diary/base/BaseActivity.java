package com.wkswind.diary.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.wkswind.diary.R;

/**
 * Created by Administrator on 2015/4/28.
 */
public class BaseActivity extends AppCompatActivity {
    private boolean mHasToolbar = false;
    protected Toolbar getToolbar(){
        return (Toolbar) findViewById(R.id.toolbar_actionbar);
    }

    protected void setHasToolbar(boolean hasToolbar){
        mHasToolbar = hasToolbar;
    }

    protected boolean hasToolbar(){
        return mHasToolbar;
    }

    protected void initToolbar(){
        if(hasToolbar()){
            Toolbar toolbar = getToolbar();
            if(toolbar != null){
//                setActionBar(toolbar);
                setSupportActionBar(toolbar);
            }
        }
    }

}
