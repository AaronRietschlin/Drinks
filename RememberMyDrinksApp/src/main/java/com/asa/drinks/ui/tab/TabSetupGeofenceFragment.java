package com.asa.drinks.ui.tab;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.drinks.R;
import com.asa.drinks.model.TabGeofenceItem;
import com.asa.drinks.services.TabGeofenceExitedService;
import com.asa.drinks.ui.AsaBaseFragment;
import com.asa.drinks.utils.LocationUtils;
import com.asa.drinks.utils.LocationUtils.RequestType;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class TabSetupGeofenceFragment extends AsaBaseFragment implements OnClickListener, OnItemSelectedListener, GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener, OnAddGeofencesResultListener {
    public static final String TAG = "TabSetupGeofenceFragment";

    public static final int DEFAULT_RADIUS = 5;
    public static final float DEFAULT_ZOOM = 16;

    private TextView mTvLocation;
    private Spinner mSpinnerRadius;
    private Button mBtnSave;

    private LocationClient mLocationClient;
    // Stores the PendingIntent used to request geofence monitoring
    private PendingIntent mGeofenceRequestIntent;
    private RequestType mRequestType;
    // Flag that indicates if a request is underway.
    private boolean mInProgress;
    private List<Geofence> mGeofenceList;

    private int[] mSpinnerValues;

    private GoogleMap mMap;
    private MapView mMapView;
    private Circle mCurrentCircle;
    private MarkerOptions mCurrentMarkerOptions;
    private Marker mCurrentMarker;
    private LatLng mCurrentLatLng;
    private float mCurrentZoom = DEFAULT_ZOOM;
    private int mCurrentRadius = DEFAULT_RADIUS;

    private Handler mHandler = new Handler();

    public static final TabSetupGeofenceFragment newInstance() {
        TabSetupGeofenceFragment frag = new TabSetupGeofenceFragment();
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGeofenceList = new ArrayList<Geofence>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab_setup_geofence, container, false);

        mTvLocation = (TextView) v.findViewById(R.id.tab_setup_tv_location);
        mSpinnerRadius = (Spinner) v.findViewById(R.id.tab_setup_spinner_radius);
        mBtnSave = (Button) v.findViewById(R.id.tab_setup_button_save);
        mMapView = (MapView) v.findViewById(R.id.map_view);

        mBtnSave.setOnClickListener(this);
        mSpinnerRadius.setOnItemSelectedListener(this);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        setupMap();

        mInProgress = false;
        mSpinnerValues = getResources().getIntArray(R.array.radius_values);
        buildLocationClient();
    }

    private void setupMap() {
        mMap = mMapView.getMap();
        mMap.setMyLocationEnabled(true);
        buildMapIfNeeded();

        mMap.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mCurrentLatLng = latLng;
                mCurrentMarkerOptions = new MarkerOptions();
                mMap.clear();
                mCurrentMarkerOptions.position(latLng).title("Lat: " + latLng.latitude + "; Lng: " + latLng.longitude);
                mCurrentMarkerOptions.snippet("Clicking save will create the Geofence around this spot!");
                mCurrentMarker = mMap.addMarker(mCurrentMarkerOptions);
                mCurrentMarker.showInfoWindow();
                drawCircle(latLng, mCurrentRadius, false);
                zoom(latLng);
            }
        });

        // Listen for camera changes so we can get the current zoom level
        mMap.setOnCameraChangeListener(new OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPos) {
                if (cameraPos != null) {
                    mCurrentZoom = cameraPos.zoom;
                }
            }
        });
    }

    private void zoom(LatLng latLng) {
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, mCurrentZoom);
        mMap.animateCamera(update);
    }

    @Override
    public void onStart() {
        super.onStart();
        mLocationClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mLocationClient.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void buildLocationClient() {
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(mActivity, this, this);
        }
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        switch (vId) {
            case R.id.tab_setup_button_save:
                saveClicked();
                break;
        }
    }

    private void saveClicked() {
        Location location = mLocationClient.getLastLocation();
        if (location != null) {
            new SaveGeofenceItemTask().execute(location);
        }
    }

    private class SaveGeofenceItemTask extends AsyncTask<Location, Void, TabGeofenceItem> {

        @Override
        protected TabGeofenceItem doInBackground(Location... params) {
            Location location = params[0];
            int selectedRadiusPos = mSpinnerRadius.getSelectedItemPosition();
            int radius = mSpinnerValues[selectedRadiusPos];
            TabGeofenceItem item = new TabGeofenceItem(location, (float) radius, 0);
//            item.save();
            // TODO - save this
            item.setGeofenceId();
            item.computeIntentFlag();
//            item.save();
            // TODO - save this
            mGeofenceList.add(item.toGeofence());
            return item;
        }

        @Override
        protected void onPostExecute(TabGeofenceItem item) {
            addGeofence(item);
        }

    }

    private void buildMapIfNeeded() {
        try {
            MapsInitializer.initialize(mActivity);
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws a circle around the given LatLng point with the given radius.
     * Passing in {@code true} for {@code clearMap} will clear the map first.
     *
     * @param latlng
     * @param radius
     * @param clearMap
     */
    private void drawCircle(LatLng latlng, double radius, boolean clearMap) {
        if (latlng == null) {
            return;
        }
        if (clearMap) {
            mMap.clear();
        }
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latlng).radius(radius).fillColor(Color.RED).strokeWidth(3);
        mCurrentCircle = mMap.addCircle(circleOptions);

        if (mCurrentMarkerOptions != null && clearMap) {
            mMap.addMarker(mCurrentMarkerOptions);
        }
    }

    private void setLocation() {
        Location location = mLocationClient.getLastLocation();
        if (location == null) {
            Toast.makeText(mActivity, "error getting location.", Toast.LENGTH_SHORT).show();
            return;
        }
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        mTvLocation.setText(getString(R.string.tab_setup_location, lat, lon));

        mCurrentLatLng = LocationUtils.locationToLatLng(location);

        // Zoom to the users location
        buildMapIfNeeded();
        zoom(mCurrentLatLng);

        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                drawCircle(mCurrentLatLng, DEFAULT_RADIUS, false);
            }
        }, 1000);
    }

    private void addGeofence(TabGeofenceItem item) {
        mRequestType = RequestType.ADD;
        if (!LocationUtils.servicesConnected(mActivity, mActivity.getSupportFragmentManager())) {
            return;
        }
        mInProgress = true;
        mGeofenceRequestIntent = getGeofencePendingIntent(item);
        if (mGeofenceRequestIntent == null) {
            // TODO - Move to string
            Toast.makeText(mActivity, "There has already been a Tab opened for this location.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isLocationClientConnected()) {
            mLocationClient.addGeofences(mGeofenceList, mGeofenceRequestIntent, this);
        }
    }

    private boolean isLocationClientConnected() {
        return mLocationClient != null && mLocationClient.isConnected();
    }

    private PendingIntent getGeofencePendingIntent(TabGeofenceItem item) {
        Intent intent = new Intent(mActivity, TabGeofenceExitedService.class);
        PendingIntent pIntent = PendingIntent.getService(mActivity, item.getIntentFlag(), intent, PendingIntent.FLAG_NO_CREATE);
        // We return null because they've already created a Geofennce for this
        // location.
        if (pIntent != null) {
            return null;
        } else {
            return PendingIntent.getService(mActivity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int radius = mSpinnerValues[position];
        if (radius < 0) {
            // Custom clicked
            Toast.makeText(mActivity, "Custom Clicked", Toast.LENGTH_SHORT).show();
            return;
        }
        mCurrentRadius = radius;
        drawCircle(mCurrentLatLng, mCurrentRadius, true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        mInProgress = false;
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(mActivity, LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);
                // Thrown if Google Play services canceled the original
                // PendingIntent
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            // If no resolution is available, display a dialog to the user with
            // the error.
            // TODO - test this!
            LocationUtils.showErrorDialog(connectionResult.getErrorCode(), mActivity, mActivity.getSupportFragmentManager());
        }
    }

    @Override
    public void onConnected(Bundle arg0) {
        setLocation();
    }

    @Override
    public void onDisconnected() {
        mInProgress = false;
        mLocationClient = null;
    }

    @Override
    public void onAddGeofencesResult(int statusCode, String[] arg1) {
        boolean added = LocationStatusCodes.SUCCESS == statusCode;
        Toast.makeText(mActivity, "Geofence Added: " + added, Toast.LENGTH_SHORT).show();
        mInProgress = false;
    }

}
