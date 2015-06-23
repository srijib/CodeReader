package com.wkswind.password;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.wkswind.password.R;
import com.wkswind.password.base.ToolbarActivity;

/**
 * Created by Administrator on 2015/6/18.
 */
public class ViewPasswordActivity extends ToolbarActivity {
    private View collapsingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_password);
        collapsingView = findViewById(R.id.header_collapsing);

        setupToolbar();
        enableHomeUpIndicator();
    }

    public View getEditButton(){
        FloatingActionButton fab = new FloatingActionButton(this);
        fab.setImageResource(R.drawable.ic_action_edit);
        fab.setId(R.id.fab_edit);
        fab.setScaleY(0);
        fab.setScaleX(0);
        fab.setVisibility(View.GONE);
        return fab;
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
