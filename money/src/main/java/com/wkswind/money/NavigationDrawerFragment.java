package com.wkswind.money;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wkswind.minilibrary.utils.UIUtils;
import com.wkswind.money.base.ToolbarActivity;
import com.wkswind.money.drawer.DrawerItem;
import com.wkswind.money.drawer.DrawerItemUtils;

import java.util.ArrayList;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private static final String TAG = NavigationDrawerFragment.class.getSimpleName();

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
//    private ListView mDrawerListView;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    private ArrayList<DrawerItem> roots;
    private View[] mNavDrawerItemViews;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
//        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer,container, false);
        FrameLayout accountContainer = (FrameLayout) view.findViewById(R.id.nav_profile_container);
        if(BuildConfig.DEBUG){
            accountContainer.addView(inflater.inflate(R.layout.navdrawer_item_account, container, false));
        }
        ViewGroup itemContainer = (ViewGroup) view.findViewById(R.id.nav_item);
        makeItemView(itemContainer);
        view.findViewById(R.id.container_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
            }
        });
        view.findViewById(R.id.container_feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        accountContainer.setFitsSystemWindows(true);
//        view.setFitsSystemWindows(true);
//        ViewCompat.setElevation(view.findViewById(R.id.container_others), getResources().getDimensionPixelSize(R.dimen.headerbar_elevation));
        return view;
    }

    private void makeItemView(ViewGroup group) {
        group.removeAllViews();
        roots = DrawerItemUtils.initDrawerItems(getActivity());
        mNavDrawerItemViews = new View[roots.size()];
        for(int i=0,j=roots.size();i<j;i++){
            DrawerItem root = roots.get(i);
//            if(!TextUtils.isEmpty(root.getLabel())){?
                View navItemView = makeNavDrawerItem(root, group, i);
                mNavDrawerItemViews[i] = navItemView;
                group.addView(navItemView);
//            }
        }
        if(roots != null && !roots.isEmpty()){
            selectItem(roots.get(0), 0);
        }
//		setSelectedNavDrawerItem(roots.get(0),0);
    }

    private View makeNavDrawerItem(final DrawerItem rootInfo, ViewGroup container, final int position) {
//		boolean selected = getSelfNavDrawerItem() == rootInfo;
        boolean selected = false;
        int layoutToInflate = 0;
        switch (rootInfo.getType()){
            case DrawerItem.TYPE_ITEM:
                layoutToInflate = R.layout.navdrawer_item;
                break;
            case DrawerItem.TYPE_DIVIDER:
                layoutToInflate = R.layout.navdrawer_separator;
                break;
        }
        View view = LayoutInflater.from(getActivity()).inflate(layoutToInflate, container, false);

        if (rootInfo.isSeparator()) {
            // we are done
            UIUtils.setAccessibilityIgnore(view);
            return view;
        }

        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        TextView titleView = (TextView) view.findViewById(R.id.title);
        int iconId = rootInfo.getIconId();

        // set icon and text
        iconView.setVisibility(iconId > 0 ? View.VISIBLE : View.GONE);
        if (iconId > 0) {
            iconView.setImageResource(iconId);
        }
        titleView.setText(rootInfo.getLabel());

        formatNavDrawerItem(view, rootInfo, selected);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(rootInfo, position);
            }
        });

        return view;
    }

    private void selectItem(DrawerItem info, int position) {
        mCurrentSelectedPosition = position;
        setSelectedNavDrawerItem(position);
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(info);
        }


    }

    private void setSelectedNavDrawerItem(int position) {
        Log.i(NavigationDrawerFragment.class.getSimpleName(), "postion # " + position + ", navItems # " + mNavDrawerItemViews);
        if (mNavDrawerItemViews != null) {
            for (int i = 0; i < mNavDrawerItemViews.length; i++) {
//				if (i < mNavDrawerItems.size()) {
//					int thisItemId = mNavDrawerItems.get(i);
                DrawerItem info = roots.get(i);
                formatNavDrawerItem(mNavDrawerItemViews[i], info, position == i);
//				}
            }
        }
    }

    private void formatNavDrawerItem(View view, DrawerItem info, boolean selected) {
        if (info.isSeparator()) {
            // not applicable
            return;
        }
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        TextView titleView = (TextView) view.findViewById(R.id.title);
        int backgroundResId = R.drawable.selected_navdrawer_item_background;
        if(!selected){
            backgroundResId = UIUtils.getAttributeResourceId(getActivity(),android.R.attr.selectableItemBackground, R.style.AppTheme);
        }
        view.setBackgroundResource(backgroundResId);
        Log.i(TAG, "TITLE NULL # " + (titleView == null));
        Log.i(TAG, "ICON NULL # " + (iconView == null));
        // configure its appearance according to whether or not it's selected
        titleView.setTextColor(selected ?
                getResources().getColor(R.color.navdrawer_text_color_selected) :
                getResources().getColor(R.color.navdrawer_text_color));
//        iconView.setColorFilter(selected ?
//                getResources().getColor(R.color.navdrawer_icon_tint_selected) :
//                getResources().getColor(R.color.navdrawer_icon_tint));
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerVisible(Gravity.START);
//        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        Toolbar toolbar = getActionBar();
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),mDrawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };
//        mDrawerLayout.setStatusBar
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
//        mDrawerToggle = new ActionBarDrawerToggle(
//                getActivity(),                    /* host Activity */
//                mDrawerLayout,                    /* DrawerLayout object */
//                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
//                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
//                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
//        ) {
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//                if (!isAdded()) {
//                    return;
//                }
//
//                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                if (!isAdded()) {
//                    return;
//                }
//
//                if (!mUserLearnedDrawer) {
//                    // The user manually opened the drawer; store this flag to prevent auto-showing
//                    // the navigation drawer automatically in the future.
//                    mUserLearnedDrawer = true;
//                    SharedPreferences sp = PreferenceManager
//                            .getDefaultSharedPreferences(getActivity());
//                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
//                }
//
//                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
//            }
//        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

//    private void selectItem(DrawerItem info, int position) {
//        mCurrentSelectedPosition = position;
////        if (mDrawerListView != null) {
////            mDrawerListView.setItemChecked(position, true);
////        }
//        if (mDrawerLayout != null) {
//            mDrawerLayout.closeDrawer(mFragmentContainerView);
//        }
//        if (mCallbacks != null) {
//            mCallbacks.onNavigationDrawerItemSelected(info);
//        }
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

//        if (item.getItemId() == R.id.action_example) {
//            Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        Toolbar toolbar = getActionBar();
//        toolbar.setDisplayShowTitleEnabled(true);
//        toolbar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        toolbar.setTitle(R.string.app_name);
    }

    private Toolbar getActionBar() {
        return ((ToolbarActivity) getActivity()).getToolbar();
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(DrawerItem item);
    }
}
