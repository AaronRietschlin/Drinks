package com.asa.drinks.ui.night;

import android.os.Bundle;

import com.asa.drinks.R;
import com.asa.drinks.ui.BaseItemActivity;

/**
 * Created by Aaron on 1/2/14.
 */
public class ActivityNight extends BaseItemActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            addFragment(FragmentNightHome.newInstance(), FragmentNightHome.TAG, false, false);
        }

        setActionBarTitle(R.string.drawer_list_night);
    }
}
