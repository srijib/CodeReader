package com.wkswind.password;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.wkswind.password.adapters.PasswordTypeAdapter;
import com.wkswind.password.base.ToolbarActivity;
import com.wkswind.password.databases.AppDatabase;
import com.wkswind.password.databases.PasswordType;
import com.wkswind.password.databases.PasswordType$Table;

import java.util.List;


/**
 * Created by Administrator on 2015/6/5.
 */
public class HomeActivity extends ToolbarActivity implements View.OnClickListener {
    private View fabAdd;
    private RecyclerView passwordContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fabAdd = findViewById(R.id.action_add);
        passwordContent = (RecyclerView) findViewById(R.id.password_content);
        passwordContent.setItemAnimator(new DefaultItemAnimator());
        passwordContent.setLayoutManager(new GridLayoutManager(this,getResources().getInteger(R.integer.main_grid_columns), LinearLayoutManager.VERTICAL, false));

        fabAdd.setOnClickListener(this);
        setupToolbar();
        makePasswordTypeSpinner();
    }

    private void makePasswordTypeSpinner() {
        Toolbar toolbar = getToolbar();
        if(toolbar != null){
            View spinnerContainer = LayoutInflater.from(this).inflate(R.layout.actionbar_spinner,
                    toolbar, false);
            ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            toolbar.addView(spinnerContainer, lp);
            Spinner spinner = (Spinner) spinnerContainer.findViewById(R.id.actionbar_spinner);
            List<PasswordType> types = new Select().from(PasswordType.class).where(Condition.column(PasswordType$Table.STATUS).is(AppDatabase.STATUS_NORMAL)).orderBy(true, PasswordType$Table.NAME).queryList();
            if(types != null && !types.isEmpty()){
                PasswordTypeAdapter adapter = new PasswordTypeAdapter(this,types, false);
                spinner.setAdapter(adapter);
            }

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplication(), ((PasswordType)parent.getAdapter().getItem(position)).getName(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
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
