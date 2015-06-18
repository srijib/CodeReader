package com.wkswind.password;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.wkswind.password.R;
import com.wkswind.password.base.ToolbarActivity;

/**
 * Created by Administrator on 2015/6/18.
 */
public class ViewPasswordActivity extends ToolbarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_password);
        setupToolbar();
        enableHomeUpIndicator();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

        }
        return super.onOptionsItemSelected(item);
    }
}
