package com.asa.drinks.model.contracts;

import com.asa.drinks.model.DrinksContentProvider;

import android.content.ContentResolver;
import android.net.Uri;

public class DrinksContract {

	public DrinksContract() {
	}

	public static abstract class TabEntry extends DrinksBaseColumns {
		public static final String TABLE_NAME = "tabs";
		public static final Uri CONTENT_URI = getUri(TABLE_NAME);
		public static final String CONTENT_TYPE = getContentListType(TABLE_NAME);
		public static final String CONTENT_TYPE_ITEM = getContentItemType(TABLE_NAME);

		public static final String COLUMN_COST = "cost";
		// TODO - Keep this?
		public static final String COLUMN_FAVORITE = "favorite";
		public static final String COLUMN_OPEN = "open";
		public static final String COLUMN_RADIUS = "radius";
		public static final String COLUMN_NAME = "name";
		public static final String COLULMN_DRINK_NIGHT_ID = "night_id";
	}

	public static abstract class DrinkCountEntry extends DrinksBaseColumns {
		public static final String TABLE_NAME = "drink_counts";
		public static final Uri CONTENT_URI = getUri(TABLE_NAME);
		public static final String CONTENT_TYPE = getContentListType(TABLE_NAME);
		public static final String CONTENT_TYPE_ITEM = getContentItemType(TABLE_NAME);

		public static final String COLUMN_COUNT = "count";
		public static final String COLUMN_DRINK_NIGHT_ID = "night_id";
		public static final String COLUMN_NAME = "name";
	}

	public static abstract class NightEntry extends DrinksBaseColumns {
		public static final String TABLE_NAME = "nights";
		public static final Uri CONTENT_URI = getUri(TABLE_NAME);
		public static final String CONTENT_TYPE = getContentListType(TABLE_NAME);
		public static final String CONTENT_TYPE_ITEM = getContentItemType(TABLE_NAME);

		public static final String COLUMN_OPEN = "open";
		public static final String COLUMN_NAME = "name";
	}

	public static abstract class LocationEntry extends DrinksBaseColumns {
		public static final String TABLE_NAME = "locations";
		public static final Uri CONTENT_URI = getUri(TABLE_NAME);
		public static final String CONTENT_TYPE = getContentListType(TABLE_NAME);
		public static final String CONTENT_TYPE_ITEM = getContentItemType(TABLE_NAME);

		public static final String COLUMN_DRINK_NIGHT_ID = "night_id";
	}

	public static abstract class EventEntry extends DrinksBaseColumns {
		public static final String TABLE_NAME = "events";
		public static final Uri CONTENT_URI = getUri(TABLE_NAME);
		public static final String CONTENT_TYPE = getContentListType(TABLE_NAME);
		public static final String CONTENT_TYPE_ITEM = getContentItemType(TABLE_NAME);

		public static final String COLUMN_TYPE = "type";
		public static final String COLUMN_DESCRIP = "descrip";
		public static final String COLUMN_ADDRESS_FROM = "address_from";
		public static final String COLUMN_PIC = "pic";
		public static final String COLUMN_ICON = "icon";
	}

	private static Uri getUri(String tableName) {
		return Uri.parse("content://" + DrinksContentProvider.AUTHORITY + "/" + tableName);
	}

	private static String getContentListType(String name) {
		return ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + "vnd." + DrinksContentProvider.AUTHORITY + "." + name;
	}

	private static String getContentItemType(String name) {
		return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + "vnd." + DrinksContentProvider.AUTHORITY + "." + name;
	}
	
}
