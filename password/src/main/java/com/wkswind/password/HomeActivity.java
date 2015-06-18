package com.wkswind.password;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.wkswind.password.base.ToolbarActivity;

/**
 * Created by Administrator on 2015/6/5.
 */
public class HomeActivity extends ToolbarActivity implements View.OnClickListener {
    private View fabAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fabAdd = findViewById(R.id.action_add);
        fabAdd.setOnClickListener(this);
        setupToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView sv = (SearchView) MenuItemCompat.getActionView(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.action_view:
                startActivity(new Intent(this, ViewPasswordActivity.class));
                break;
            case R.id.action_edit:
                startActivity(new Intent(this, EditPasswordActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_add:
                startActivity(new Intent(this, EditPasswordActivity.class));
                break;
        }
    }
}
