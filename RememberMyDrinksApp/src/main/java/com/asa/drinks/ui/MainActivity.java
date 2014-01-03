package com.asa.drinks.ui;

import android.os.Bundle;
import android.view.Menu;

import com.asa.drinks.R;
import com.asa.drinks.ui.tab.TabSetupGeofenceFragment;
import com.asa.drinks.utils.DrawerLayoutUtils;

public class MainActivity extends BaseItemActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            addFragment(new TabSetupGeofenceFragment(), TabSetupGeofenceFragment.TAG, false, false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
