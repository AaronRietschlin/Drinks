package com.asa.drinks.ui.tab;

import android.os.Bundle;

import com.asa.drinks.ui.BaseItemActivity;

/**
 * Created by Aaron on 1/2/14.
 */
public class ActivityTab extends BaseItemActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            addFragment(new TabSetupGeofenceFragment(), TabSetupGeofenceFragment.TAG, false, false);
        }
    }
}
