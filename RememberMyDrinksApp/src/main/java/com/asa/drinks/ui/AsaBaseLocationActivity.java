package com.asa.drinks.ui;

import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;

public abstract class AsaBaseLocationActivity extends AsaBaseActivity {

    private GoogleApiClient mApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupApiClient();
    }

    protected abstract void setupApiClient();
}
