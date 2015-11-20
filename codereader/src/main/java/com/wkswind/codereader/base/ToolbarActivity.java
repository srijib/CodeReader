package com.wkswind.codereader.base;

import android.support.v7.widget.Toolbar;

import com.wkswind.codereader.R;

/**
 * Created by Administrator on 2015-11-17.
 */
public class ToolbarActivity extends BaseActivity {
    protected Toolbar getToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
        return toolbar;
    }
}
