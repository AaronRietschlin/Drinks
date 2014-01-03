package com.asa.drinks.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.asa.drinks.R;
import com.asa.drinks.utils.DrawerLayoutUtils;

/**
 * A Base Activity for those Activities that will be on screen. This is here to help with the DrawerLayout
 * and such.
 */
public class BaseItemActivity extends AsaBaseActivity {

    protected DrawerLayoutUtils mDrawerUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        mDrawerUtils = new DrawerLayoutUtils(this, true);
        mDrawerUtils.setContentView();
        mDrawerUtils.setupViews();

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

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mDrawerUtils.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        mDrawerUtils.setTitle(titleId);
    }
}
