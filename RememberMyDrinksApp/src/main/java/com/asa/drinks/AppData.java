package com.asa.drinks;

public class AppData {

	public static final String INTENT_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
	
	public static final String CP_AUHTORITY = "com.asa.drinks.provider";
	
	/**
	 * These are the ids of the notifications used in the app. Use the following
	 * standards:
	 * <ul>
	 * <li>Remember My Tab related notifications will be in the 100 level.</li>
	 * <li>Remember My Drink related notifications will be in the 200 level</li>
	 * <li>Remember My Night related notifications will be in the 300 level.</li>
	 * <li>Drink db related will be in the 400 level</li>
	 * </ul>
	 */
	public static class NotificationIds {
		/** An ongoing notification inidcating that a tab is opened */
		public static int TAB_OPENED_NOTIFICATION = 100;
		public static int TAB_LEAVING_LOCATION_NOTIFICATION = 101;
	}

	public static class CrashlyticKeys {
		public static final String LOCATION_CLIENT_INTENT_HAS_ERROR = "location_client_intent_has_error";
		public static final String TRANSITION_TYPE = "geofence_tranisition_type";
	}

	/**
	 * Intent flag levels so that we can have unique flags for each type of
	 * intent we want to send out.
	 */
	public static class IntentFlagLevels {
		public static final int RM_TAB = 1000;
		public static final int RM_DRINK = 2000;
		public static final int RM_NIGHT = 3000;
	}
}
