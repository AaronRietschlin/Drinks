package com.asa.drinks.model;

import android.location.Location;

import com.asa.drinks.AppData;
import com.google.android.gms.location.Geofence;

/**
 * An item that is used to store the {@link Geofence} data. The docs recommend
 * creating your own item for this.
 */
public class TabGeofenceItem {
    public static final String TABLE = "tab_geofence_item";

    private double latitude;
    private double longitude;
    private float radius;
    private long expirationTime;
    private String geofenceId;
    private int intentFlag;

    /**
     * Builds this item. Passing in 0 to the experationTime will set it to
     * {@link Geofence#NEVER_EXPIRE}.
     *
     * @param location
     * @param radius
     * @param experiationTime
     */
    public TabGeofenceItem(Location location, float radius, long experiationTime) {
        super();
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        this.radius = radius;
        if (experiationTime > 0) {
            this.expirationTime = experiationTime;
        } else {
            this.expirationTime = Geofence.NEVER_EXPIRE;
        }
    }

    /**
     * Builds a geofence, defaulting the transition type to Exit
     * {@link Geofence#GEOFENCE_TRANSITION_EXIT}).
     *
     * @return
     */
    public Geofence toGeofence() {
        return toGeofence(Geofence.GEOFENCE_TRANSITION_EXIT);
    }

    /**
     * Builds a {@link Geofence} object with this items given values. You can
     * manually set the Transition type here.
     *
     * @param transitionType
     * @return
     */
    public Geofence toGeofence(int transitionType) {
        Geofence.Builder builder = new Geofence.Builder();
        builder.setRequestId(geofenceId);
        if (transitionType > 0) {
            builder.setTransitionTypes(transitionType);
        } else if (transitionType == 0) {
            builder.setTransitionTypes(Geofence.GEOFENCE_TRANSITION_EXIT);
        }
        builder.setExpirationDuration(expirationTime);
        builder.setCircularRegion(latitude, longitude, radius);
        return builder.build();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getGeofenceId() {
        return geofenceId;
    }

    public void setGeofenceId(String geofenceId) {
        this.geofenceId = geofenceId;
    }

    public void setGeofenceId() {
        // TODO - Compute an actual id
        this.geofenceId = TABLE + ":" + 1;
    }

    public int getIntentFlag() {
        return intentFlag;
    }

    public void setIntentFlag(int intentFlag) {
        this.intentFlag = intentFlag;
    }

    public void computeIntentFlag() {
        // TODO - Get an actual id
        Long dbId = 1L;
        if (dbId != null && dbId != 0) {
            intentFlag = dbId.intValue() + AppData.IntentFlagLevels.RM_TAB;
            return;
        }
    }

    public static class TransitionType {
        public static final String EXIT = "exit";
        public static final String ENTER = "enter";
        public static final String BOTH = "both";
    }

    public static class Columns {
        public static final String LATITUDE = "lat";
        public static final String LONGITUDE = "lon";
        public static final String RADIUS = "radius";
        public static final String EXPIRATION_TIME = "expiration_time";
        public static final String GEOFENCE_ID = "geofence_id";
        public static final String INTENT_FLAG = "intent_flag";
    }

}
