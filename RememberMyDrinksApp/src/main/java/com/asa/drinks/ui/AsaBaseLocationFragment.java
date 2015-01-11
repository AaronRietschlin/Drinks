package com.asa.drinks.ui;

import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;


public abstract class AsaBaseLocationFragment extends AsaBaseFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mApiClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleApiClient.Builder apiClientBuilder = new GoogleApiClient.Builder(mActivity, this, this);
        setupGoogleApiClient(apiClientBuilder);
        mApiClient = apiClientBuilder.build();
    }

    protected abstract void setupGoogleApiClient(GoogleApiClient.Builder apiClientBuilder);
}
