package com.wkswind.money;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.wkswind.money.base.ToolbarActivity;
import com.wkswind.money.drawer.DrawerItem;
import com.wkswind.money.ui.TransactionFragment;


public class MainActivity extends ToolbarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, FragmentManager.OnBackStackChangedListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private FragmentManager fm;
//    private Handler mHandler = new Handler() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getSupportFragmentManager();
        setContentView(R.layout.activity_main);
        initToolbar();
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        fm.addOnBackStackChangedListener(this);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getWindow().getDecorView().setBackgroundColor(Color.BLACK);
//            }
//        }, 5 * 1000);
    }

    @Override
    public void onNavigationDrawerItemSelected(DrawerItem item) {
        // update the main content by replacing fragments
//        if(getString(R.string.item_settings).equals(item.getLabel())){
//            startActivity(new Intent(this, SettingsActivity.class));
//        }else{
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.container, PlaceholderFragment.newInstance(item.getLabel()))
//                    .commit();
//        }

        Fragment target = TransactionFragment.newInstance(item.getLabel());
        fm.beginTransaction().addToBackStack(item.getLabel()).add(R.id.container, target, item.toString()).commit();

    }

    public void onSectionAttached(String label) {
        mTitle = label;
    }

    public void restoreActionBar() {
        Toolbar toolbar = getToolbar();
        toolbar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(fm.getBackStackEntryCount() <2 ){
//            super.onBackPressed();
            finish();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void onBackStackChanged() {
        Log.i("FRAGMENT ENTRY", " COUNT # " + fm.getBackStackEntryCount());
        FragmentManager.BackStackEntry entry = fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1);
        mTitle = entry.getName();
        setTitle(mTitle);
//        fm.get
    }

}
