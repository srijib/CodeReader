package com.wkswind.password.base;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wkswind.password.R;

/**
 * Created by Administrator on 2015/6/18.
 */
public class ToolbarActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected Toolbar getToolbar(){
        return (Toolbar) findViewById(R.id.toolbar_actionbar);
    }

    protected void setupToolbar(){
        Toolbar toolbar = getToolbar();
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
    }

    protected void enableHomeUpIndicator(){
        Toolbar toolbar = getToolbar();
        if(toolbar != null){
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onHomeUpIndicatorClick(v);
                }
            });
        }
    }

    protected void enableClearIndicator(){
        Toolbar toolbar = getToolbar();
        if(toolbar != null){
            toolbar.setNavigationIcon(R.drawable.abc_ic_clear_mtrl_alpha);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onHomeClearIndicatorClick(v);
                }
            });
        }
    }


    protected void onHomeUpIndicatorClick(View v){
        NavUtils.navigateUpFromSameTask(this);
    }

    protected void onHomeClearIndicatorClick(View v){
        finish();
    }
}
