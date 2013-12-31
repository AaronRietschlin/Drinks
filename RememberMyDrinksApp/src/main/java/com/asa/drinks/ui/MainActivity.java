package com.asa.drinks.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.asa.drinks.R;
import com.asa.drinks.model.DrinksSQLiteHelper;
import com.asa.drinks.ui.tab.TabSetupGeofenceFragment;
import com.asa.drinks.utils.DrawerLayoutUtils;

public class MainActivity extends AsaBaseActivity {

	private DrawerLayoutUtils mDrawerUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_container);

		mDrawerUtils = new DrawerLayoutUtils(this, true);
		mDrawerUtils.setContentView();
		mDrawerUtils.setupViews();

		if (savedInstanceState == null) {
			addFragment(new TabSetupGeofenceFragment(), TabSetupGeofenceFragment.TAG, false, false);
		}

		DrinksSQLiteHelper helper = new DrinksSQLiteHelper(getApplicationContext());
		helper.getReadableDatabase();
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mDrawerUtils.onDestroy();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerUtils.onPostCreate(savedInstanceState);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerUtils.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerUtils.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}

}
