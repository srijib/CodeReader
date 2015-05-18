package com.wkswind.money;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getSupportFragmentManager();
        setContentView(R.layout.activity_main);
        initToolbar();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        fm.addOnBackStackChangedListener(this);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.scrimInsetsFrameLayout,
                mDrawerLayout);
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getWindow().getDecorView().setBackgroundColor(Color.BLACK);
//            }
//        }, 5 * 1000);
    }

    @Override
    public void onNavigationDrawerItemSelected(final DrawerItem item) {
        // update the main content by replacing fragments
//        if(getString(R.string.item_settings).equals(item.getLabel())){
//            startActivity(new Intent(this, SettingsActivity.class));
//        }else{
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.container, PlaceholderFragment.newInstance(item.getLabel()))
//                    .commit();
//        }
//        Toast.makeText(this, "MAX # " + testMethod(), Toast.LENGTH_SHORT) ;
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), item.getIconId());
//        Palette.Builder builder = new Palette.Builder(bitmap);
//        builder.resizeBitmapSize(R.dimen.navdrawer_item_size);
//        builder.generate(new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//                int colorPrimary = palette.getLightVibrantColor(R.color.colorPrimary);
//                int colorPrimaryDark = palette.getLightMutedColor(R.color.colorPrimaryDark);
////                int colorPrimaryDark = palette.getDarkVibrantColor(R.color.colorPrimary);
//                Fragment target = TransactionFragment.newInstance(item.getLabel(),colorPrimary, colorPrimaryDark);
//                fm.beginTransaction()
////                        .addToBackStack(item.getLabel())
//                        .add(R.id.container, target, item.toString()).commit();
//            }
//        });

        Fragment target = TransactionFragment.newInstance(item.getLabel(),item.getColorPrimaryId(), item.getColorPrimaryDarkId());
                fm.beginTransaction()
//                        .addToBackStack(item.getLabel())
                        .add(R.id.container, target, item.toString()).commit();
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
//        if(fm.getBackStackEntryCount() <2 ){
//            finish();
//        }else{
            super.onBackPressed();
//        }
    }

    @Override
    public void onBackStackChanged() {
        Log.i("FRAGMENT ENTRY", " COUNT # " + fm.getBackStackEntryCount());
        FragmentManager.BackStackEntry entry = fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1);
        mTitle = entry.getName();
        setTitle(mTitle);
//        fm.get
    }

    public void changeStatusBarColor(int colorResId){
        if(mDrawerLayout != null){
            mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(colorResId));
        }
    }
}
