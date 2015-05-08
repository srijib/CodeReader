package com.wkswind.money.base;

import android.support.v7.widget.Toolbar;

import com.wkswind.money.R;

/**
 * Created by Administrator on 2015/5/5.
 */
public class ToolbarActivity extends BaseActivity {
    public Toolbar getToolbar(){
        return (Toolbar) findViewById(R.id.toolbar_actionbar);
    }

    protected void initToolbar(){
        Toolbar toolbar = getToolbar();
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
    }

}
