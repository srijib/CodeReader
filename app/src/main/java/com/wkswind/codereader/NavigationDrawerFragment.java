package com.wkswind.codereader;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wkswind.codereader.model.RootInfo;
import com.wkswind.codereader.utils.UIUtils;

import java.util.ArrayList;

/**
 * Fragment used for managing interactions for and presentation of a navigation
 * drawer. See the <a href=
 * "https://developer.android.com/design/patterns/navigation-drawer.html#Interaction"
 * > design guidelines</a> for a complete explanation of the behaviors
 * implemented here.
 */
public class NavigationDrawerFragment extends BaseFragment {
	/**
	 * Remember the position of the selected item.
	 */
	private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

	/**
	 * Per the design guidelines, you should show the drawer on launch until the
	 * user manually expands it. This shared preference tracks this.
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
    private View mFragmentContainerView;

	private int mCurrentSelectedPosition = 0;
	private boolean mFromSavedInstanceState;
	private boolean mUserLearnedDrawer;

	private ArrayList<CharSequence> items;
	private View[] mNavDrawerItemViews;
	private ArrayList<RootInfo> roots;


	public NavigationDrawerFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Read in the flag indicating whether or not the user has demonstrated
		// awareness of the
		// drawer. See PREF_USER_LEARNED_DRAWER for details.
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

		if (savedInstanceState != null) {
			mCurrentSelectedPosition = savedInstanceState
					.getInt(STATE_SELECTED_POSITION);
			mFromSavedInstanceState = true;
		}

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Indicate that this fragment would like to influence the set of
		// actions in the action bar.
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView Called");
		View rootView = inflater.inflate(R.layout.navdrawer, container, false);
		ViewGroup group = (ViewGroup) rootView.findViewById(R.id.navdrawer_items_list);
		makeItemView(group);

		return rootView;
	}

	private void makeItemView(ViewGroup group) {
		group.removeAllViews();
		roots = RootInfo.init(getResources(), getActivity());
		mNavDrawerItemViews = new View[roots.size()];
		for(int i=0,j=roots.size();i<j;i++){
			RootInfo root = roots.get(i);
			View navItemView = makeNavDrawerItem(root, group, i);
			mNavDrawerItemViews[i] = navItemView;
			group.addView(navItemView);
		}
		if(roots != null && !roots.isEmpty()){
			selectItem(roots.get(0), 0);
		}
//		setSelectedNavDrawerItem(roots.get(0),0);
	}

	public boolean isDrawerOpen() {
		return mDrawerLayout != null
				&& mDrawerLayout.isDrawerOpen(mFragmentContainerView);
	}

	/**
	 * Users of this fragment must call this method to set up the navigation
	 * drawer interactions.
	 * 
	 * @param fragmentId
	 *            The android:id of this fragment in its activity's layout.
	 * @param drawerLayout
	 *            The DrawerLayout containing this fragment's UI.
	 */
	public void setUp(int fragmentId, DrawerLayout drawerLayout) {
		mFragmentContainerView = getActivity().findViewById(fragmentId);
		mDrawerLayout = drawerLayout;

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),mDrawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if(!isAdded()){
                    return;
                }
                getActivity().supportInvalidateOptionsMenu();
				if(getActivity() instanceof MainActivity){
					((MainActivity) getActivity()).restoreActionBar();
				}
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }
                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to
                    // prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true)
                            .commit();
                }

                getActivity().supportInvalidateOptionsMenu(); // calls
                // onPrepareOptionsMenu()
            }
        };

		// If the user hasn't 'learned' about the drawer, open it to introduce
		// them to the drawer,
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

	private void selectItem(RootInfo info, int position) {
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
		Log.i(NavigationDrawerFragment.class.getSimpleName(), "postion # " + position +", navItems # " + mNavDrawerItemViews);
		if (mNavDrawerItemViews != null) {
			for (int i = 0; i < mNavDrawerItemViews.length; i++) {
//				if (i < mNavDrawerItems.size()) {
//					int thisItemId = mNavDrawerItems.get(i);
					RootInfo info = roots.get(i);
					formatNavDrawerItem(mNavDrawerItemViews[i], info, position == i);
//				}
			}
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallbacks = (NavigationDrawerCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(
					"Activity must implement NavigationDrawerCallbacks.");
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
		// If the drawer is open, show the global app actions in the action bar.
		// See also
		// showGlobalContextActionBar, which controls the top-left area of the
		// action bar.
		if (mDrawerLayout != null && isDrawerOpen()) {
			showGlobalContextActionBar();
		}else{
			inflater.inflate(R.menu.explorer, menu);
		}
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Per the navigation drawer design guidelines, updates the action bar to
	 * show the global app 'context', rather than just what's in the current
	 * screen.
	 */
	private void showGlobalContextActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setTitle(R.string.app_name);
	}

	private ActionBar getActionBar() {
		return ((AppCompatActivity) getActivity()).getSupportActionBar();
	}
	/**
	 * Callbacks interface that all activities using this fragment must
	 * implement.
	 */
	public static interface NavigationDrawerCallbacks {
		/**
		 * Called when an item in the navigation drawer is selected.
		 */
		void onNavigationDrawerItemSelected(RootInfo info);
	}



	private View makeNavDrawerItem(final RootInfo rootInfo, ViewGroup container, final int position) {
//		boolean selected = getSelfNavDrawerItem() == rootInfo;
		boolean selected = false;
		int layoutToInflate = 0;
		switch (rootInfo.getType()){
			case RootInfo.NAVDRAWER_ITEM:
				layoutToInflate = R.layout.navdrawer_item;
				break;
			case RootInfo.NAVDRAWER_ITEM_SEPARATOR:
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
		int iconId = rootInfo.getIcon();

		// set icon and text
		iconView.setVisibility(iconId > 0 ? View.VISIBLE : View.GONE);
		if (iconId > 0) {
			iconView.setImageResource(iconId);
		}
		titleView.setText(rootInfo.getTitle());

		formatNavDrawerItem(view, rootInfo, selected);
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectItem(rootInfo, position);
			}
		});

		return view;
	}

	private void formatNavDrawerItem(View view, RootInfo info, boolean selected) {
		if (info.isSeparator()) {
			// not applicable
			return;
		}
		Log.i(TAG,view.toString());
		ImageView iconView = (ImageView) view.findViewById(R.id.icon);
		TextView titleView = (TextView) view.findViewById(R.id.title);
		Log.i(TAG, "TITLE NULL # " + (titleView == null));
		Log.i(TAG, "ICON NULL # " + (iconView == null));
		// configure its appearance according to whether or not it's selected
		titleView.setTextColor(selected ?
				getResources().getColor(R.color.navdrawer_text_color_selected) :
				getResources().getColor(R.color.navdrawer_text_color));
		iconView.setColorFilter(selected ?
				getResources().getColor(R.color.navdrawer_icon_tint_selected) :
				getResources().getColor(R.color.navdrawer_icon_tint));
	}



//	protected int getSelfNavDrawerItem() {
//		return NAVDRAWER_ITEM_INVALID;
//	}



}
