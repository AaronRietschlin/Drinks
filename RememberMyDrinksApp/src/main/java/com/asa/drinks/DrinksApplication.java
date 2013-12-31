package com.asa.drinks;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;

public class DrinksApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        // Init crashlytics fort he app
        Crashlytics.start(this);

        sContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return sContext;
    }

}
