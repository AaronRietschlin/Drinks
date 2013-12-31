package com.asa.drinks.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v4.widget.DrawerLayout.SimpleDrawerListener;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.asa.drinks.R;
import com.asa.drinks.ui.AsaBaseActivity;
import com.asa.drinks.ui.MainActivity;
import com.asa.drinks.ui.night.FragmentNightHome;
import com.asa.drinks.ui.tab.TabSetupGeofenceFragment;

public class DrawerLayoutUtils {
	private final static String TAG = LogUtils.makeLogTag(DrawerLayoutUtils.class);

	public final static int POS_NIGHT = 0;
	public final static int POS_TAB = 1;
	public static final int POS_DRINK = 2;

	private ListView mDrawerList;
	private DrawerLayout mDrawer;
	private ActionBarDrawerToggle mDrawerToggle;

	private DrawerListener mDrawerListener;
	private OnItemClickListener mDrawerListClickListener;

	private Context mContext;
	private AsaBaseActivity mActivity;

	private int mCurrentSelectedPos;
	private boolean mWithAbToggle;

	private CharSequence mTitle;
	private String[] mListOptions;

	/**
	 * See {@link MainActivity for how to implement. {@code withActionBarToggle}
	 * will setup the ActionBar toggle in the ActionBar. If you use this, you
	 * MUST forward certain lifecycle events to this class. They are the
	 * following:
	 * <ul>
	 * <li>onConfigurationChanged</li>
	 * <li>onPostCreate</li>
	 * <li>onOptionsItemSelected</li>
	 * </ul>
	 * 
	 * 
	 * 
	 * @param actvity
	 * @param withActionBarToggle
	 */
	public DrawerLayoutUtils(Activity actvity, boolean withActionBarToggle) {
		mContext = actvity.getApplicationContext();
		mActivity = (AsaBaseActivity) actvity;
		mWithAbToggle = withActionBarToggle;

		mListOptions = mContext.getResources().getStringArray(R.array.drawer_list);
		mTitle = mListOptions[0];
	}

	/**
	 * Gets access to the Views.
	 */
	public void setContentView() {
		// Get the drawer
		try {
			mDrawer = (DrawerLayout) mActivity.findViewById(R.id.drawer_layout);
			if (mDrawer == null) {
				throw new IllegalStateException("You MUST pass in an Activity that has a view with the id \"drawer_layout\" in the constructor.");
			}
		} catch (ClassCastException e) {
			// TODO - Maybe allow for other content in teh NavDrawer.
			throw new IllegalStateException("The id \"drawer_layout\" is not a DrawerLayout widget in this view.");
		}

		// Get the list
		try {
			mDrawerList = (ListView) mActivity.findViewById(R.id.drawer_layout_list);
			if (mDrawerList == null) {
				throw new IllegalStateException("You MUST pass in an Activity that has a view with the id \"drawer_layout_list\" in the Constructor");
			}
		} catch (ClassCastException e) {
			// TODO - Maybe allow for other content in teh NavDrawer.
			throw new IllegalStateException("The content view in the DrawerLayout was not a list view. ");
		}
	}

	public void setupViews() {
		// Setup the list with the available options
		// TODO - Use non system list items
		mDrawerList.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, android.R.id.text1, mListOptions));
		// init the first one to selected since it's setup
		mDrawerList.setSelection(0);
		checkDrawerListClickListener();
		mDrawerList.setOnItemClickListener(mDrawerListClickListener);

		// Now setup the drawer toggle, if applicable
		if (mWithAbToggle) {
			mDrawerToggle = new ActionBarDrawerToggle(mActivity, mDrawer, R.drawable.ic_drawer, R.string.drawer_close, R.string.drawer_close) {
				@Override
				public void onDrawerClosed(View view) {
					drawerClosed();
				}

				/** Called when a drawer has settled in a completely open state. */
				@Override
				public void onDrawerOpened(View drawerView) {
					drawerOpened();
				}
			};
			mDrawerToggle.setDrawerIndicatorEnabled(true);
		}

		// Set the listener. if we're using the drawer toggle, we will use that.
		// Otherwise, we can allow the user to set it.
		if (mWithAbToggle) {
			mDrawerListener = (DrawerListener) mDrawerToggle;
		}
		checkDrawerListener();
		mDrawer.setDrawerListener(mDrawerListener);
	}

	private void checkDrawerListener() {
		if (mDrawerListener == null) {
			mDrawerListener = new SimpleDrawerListener() {
				@Override
				public void onDrawerClosed(View view) {
					drawerClosed();
				}

				/** Called when a drawer has settled in a completely open state. */
				@Override
				public void onDrawerOpened(View drawerView) {
					drawerOpened();
				}
			};
		}
	}

	/*
	 * Using two possible ways to set drawer opened. This is here for reduction
	 * purposes.
	 */
	private void drawerOpened() {
		setTitle(R.string.app_name, false);
		mActivity.invalidateOptionsMenu();
	}

	private void drawerClosed() {
		setTitle(mTitle, false);
		mActivity.invalidateOptionsMenu();
	}

	private void checkDrawerListClickListener() {
		if (mDrawerListClickListener == null) {
			mDrawerListClickListener = new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
					selectItem(pos);
				}
			};
		}
	}

	public void selectItem(int position) {

		// Swap the fragment
		switch (position) {
		case POS_NIGHT:
			mActivity.replaceFragment(FragmentNightHome.newInstance(), FragmentNightHome.TAG, false, false);
			break;
		case POS_TAB:
			mActivity.replaceFragment(TabSetupGeofenceFragment.newInstance(), TabSetupGeofenceFragment.TAG, false, false);
			break;
		case POS_DRINK:
			break;
		}

		mDrawerList.setSelection(position);
		String name = mListOptions[position];
		setTitle(name, true);
		mDrawer.closeDrawer(Gravity.START);
	}

	/**
	 * Removes everything to prevent memory leaks.
	 */
	public void onDestroy() {
		mActivity = null;
		mContext = null;
		mDrawerList = null;
	}

	public void onPostCreate(Bundle savedInstanceState) {
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	public void onConfigurationChanged(Configuration newConfig) {
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		return mDrawerToggle.onOptionsItemSelected(item);
	}

	/**
	 * Sets the title of the Activity with the given title. If {@code setVar},
	 * then it will keep a reference to that in a global variable.
	 */
	public void setTitle(CharSequence title, boolean setVar) {
		if (setVar) {
			mTitle = title;
		}
		mActivity.setTitle(title);
	}

	public void setTitle(int titleId, boolean setVar) {
		setTitle(mContext.getString(titleId), setVar);
	}

	public void setCurrentPosition(int position) {
		mCurrentSelectedPos = position;
	}

}
