package com.wkswind.password;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ViewAnimator;

import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.wkswind.password.adapters.PasswordTypeAdapter;
import com.wkswind.password.base.ToolbarActivity;
import com.wkswind.password.databases.AppDatabase;
import com.wkswind.password.databases.PasswordType;
import com.wkswind.password.databases.PasswordType$Table;
import com.wkswind.password.utils.ItemClickSupport;
import com.wkswind.password.utils.ItemSelectionSupport;

import java.util.List;

/**
 * Created by Administrator on 2015/6/23.
 */
public class NavigationHomeActivity extends ToolbarActivity implements SearchView.OnQueryTextListener, View.OnClickListener, ItemClickSupport.OnItemClickListener {
    private FloatingActionButton fab;
    private static final long DELAY_IN_MS = 200;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private RecyclerView content;
    private android.os.Handler mHandler;

    private PasswordTypeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_navigation_view);
        mHandler = new android.os.Handler();
        fab = (FloatingActionButton) findViewById(R.id.action_add);
        animateFab();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.home_drawer);
        mNavigationView = (NavigationView) findViewById(R.id.home_drawer_menu);
        content = (RecyclerView) findViewById(R.id.password_content);
        content.setItemAnimator(new DefaultItemAnimator());
        int columns = getResources().getInteger(R.integer.main_grid_columns);
        RecyclerView.LayoutManager manager = columns == 1 ? new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) : new GridLayoutManager(this,columns);
        content.setLayoutManager(manager);
        ItemClickSupport.addTo(content).setOnItemClickListener(this);

        setupToolbar();
        setupNavigationDrawer();
        fab.setOnClickListener(this);
    }

    private void animateFab() {
        fab.animate().setStartDelay(100).scaleX(1).scaleY(1).setDuration(400).setInterpolator(new FastOutSlowInInterpolator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(item);
        mSearchView.setOnQueryTextListener(this);
        return true;
    }

    private void setupNavigationDrawer() {
        final Toolbar toolbar = getToolbar();
        if(toolbar != null){
            toolbar.setNavigationIcon(R.drawable.ic_drawer);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            });

            Menu mMenuBuilder = mNavigationView.getMenu();
            List<PasswordType> types = new Select().from(PasswordType.class).where(Condition.column(PasswordType$Table.STATUS).is(AppDatabase.STATUS_NORMAL)).orderBy(true, PasswordType$Table.ID).queryList();
            if(types != null && !types.isEmpty()){
                for(int i=0,j=types.size();i<j;i++){
                    PasswordType type = types.get(i);
                    mMenuBuilder.add(0, (int) type.getId(), Menu.NONE, type.getName());
                }
            }
            mMenuBuilder.setGroupCheckable(0,true,true);
            getMenuInflater().inflate(R.menu.home_navigation, mMenuBuilder);
            mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
//                    menuItem.setChecked(true);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    switch (menuItem.getItemId()){
                        case R.id.action_settings:
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(NavigationHomeActivity.this, SettingsActivity.class));
                                }
                            }, DELAY_IN_MS);
                            break;
                        case R.id.action_feedback:
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                }
                            }, DELAY_IN_MS);
                            break;
                        default:
                            toolbar.setTitle(menuItem.getTitle());
                            menuItem.setChecked(true);
                            break;
                    }
                    return true;
                }
            });
//            mMenuBuilder.setGroupCheckable(0, true, true);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_add:
                startActivity(new Intent(this, EditPasswordActivity.class));
                break;
        }
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position, long id) {

    }
}
