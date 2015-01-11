package com.asa.drinks.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.asa.drinks.AppData;
import com.asa.drinks.R;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.location.Geofence;

public class TabGeofenceExitedService extends Service {
	private final static String TAG = "TabGeofenceExitedService";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		if (intent == null) {
			throwException("Intent Service entered. INtent was null.");
			return START_NOT_STICKY;
		}

		if (LocationClient.hasError(intent)) {
			// TODO - test this. Force an error somehow.
			Bundle extras = intent.getExtras();
			if (extras != null) {
				throwException(extras.toString());
			}
			Crashlytics.setBool(AppData.CrashlyticKeys.LOCATION_CLIENT_INTENT_HAS_ERROR, true);
			Crashlytics.log(Log.ERROR, TAG, "The location Client has an error.");
			// TODO - send user to handle error
			return START_NOT_STICKY;
		}
		int transitionType = LocationClient.getGeofenceTransition(intent);
		Crashlytics.log(Log.DEBUG, TAG, "Transition Type: " + transitionType);
		if (transitionType == Geofence.GEOFENCE_TRANSITION_EXIT) {
			Crashlytics.log(Log.DEBUG, TAG, "Transition type was Exit.");
			Crashlytics.setString(AppData.CrashlyticKeys.TRANSITION_TYPE, "exit");
			showNotification();
		}

		return START_NOT_STICKY;
	}

	private void showNotification() {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
		builder.setSmallIcon(R.drawable.ic_launcher).setContentTitle("Leaving location!").setContentTitle("Did you remember to close your tab!").setAutoCancel(false);
		Notification notif = builder.build();
		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		manager.notify(AppData.NotificationIds.TAB_LEAVING_LOCATION_NOTIFICATION, notif);
	}

	/**
	 * Logs an exception to Crashlytics.
	 */
	private void throwException(String message) {
		Exception e = new Exception(message);
		Crashlytics.logException(e);
	}

}
