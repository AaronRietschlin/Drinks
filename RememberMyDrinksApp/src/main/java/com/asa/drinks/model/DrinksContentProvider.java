package com.asa.drinks.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.asa.drinks.AppData;
import com.asa.drinks.model.contracts.DrinksBaseColumns;
import com.asa.drinks.model.contracts.DrinksContract.DrinkCountEntry;
import com.asa.drinks.model.contracts.DrinksContract.EventEntry;
import com.asa.drinks.model.contracts.DrinksContract.LocationEntry;
import com.asa.drinks.model.contracts.DrinksContract.NightEntry;
import com.asa.drinks.model.contracts.DrinksContract.TabEntry;
import com.asa.drinks.utils.LogUtils;

public class DrinksContentProvider extends ContentProvider {
	public static final String TAG = LogUtils.makeLogTag(DrinksContentProvider.class);

	public static final String AUTHORITY = AppData.CP_AUHTORITY;

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

	// Match up the URIs with the type. anything with just a path will match to
	// the LIST_*. Anything with a path and an ID (tabs/3) will match to the
	// ITEM_*.
	private static final int LIST_TAB = 0;
	private static final int ITEM_TAB = 1;
	private static final int LIST_DRINK_COUNT = 10;
	private static final int ITEM_DRINK_COUNT = 11;
	private static final int LIST_NIGHT = 20;
	private static final int ITEM_NIGHT = 21;
	private static final int LIST_LOCATION = 30;
	private static final int ITEM_LOCATION = 31;
	private static final int LIST_EVENT = 40;
	private static final int ITEM_EVENT = 41;

	private static final UriMatcher sUriMatcher;
	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(AUTHORITY, TabEntry.TABLE_NAME, LIST_TAB);
		sUriMatcher.addURI(AUTHORITY, TabEntry.TABLE_NAME + "/#", ITEM_TAB);
		sUriMatcher.addURI(AUTHORITY, DrinkCountEntry.TABLE_NAME, LIST_DRINK_COUNT);
		sUriMatcher.addURI(AUTHORITY, DrinkCountEntry.TABLE_NAME + "/#", ITEM_DRINK_COUNT);
		sUriMatcher.addURI(AUTHORITY, NightEntry.TABLE_NAME, LIST_NIGHT);
		sUriMatcher.addURI(AUTHORITY, NightEntry.TABLE_NAME + "/#", ITEM_NIGHT);
		sUriMatcher.addURI(AUTHORITY, LocationEntry.TABLE_NAME, LIST_LOCATION);
		sUriMatcher.addURI(AUTHORITY, LocationEntry.TABLE_NAME + "/#", ITEM_LOCATION);
		sUriMatcher.addURI(AUTHORITY, EventEntry.TABLE_NAME, LIST_EVENT);
		sUriMatcher.addURI(AUTHORITY, EventEntry.TABLE_NAME + "/#", ITEM_EVENT);
	}

	private SQLiteDatabase mDb;
	private DrinksSQLiteHelper mDbHelper;

	@Override
	public boolean onCreate() {
		mDbHelper = new DrinksSQLiteHelper(getContext());
		// mDb = mDbHelper.getWritableDatabase();
		// if (mDb == null) {
		// LogUtils.LOGD(TAG, "Creating the DB failed in the ContentProvider.");
		// return false;
		// }
		// if (mDb.isReadOnly()) {
		// mDb.close();
		// mDb = null;
		// return false;
		// }
		return true;
	}

	@Override
	public String getType(Uri uri) {
		checkSupportedUri(uri);

		notifyChange(uri);
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		checkSupportedUri(uri);

		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		long insertedId = -1;

		switch (sUriMatcher.match(uri)) {
		case LIST_TAB:
		case ITEM_TAB:
			insertedId = db.insert(TabEntry.TABLE_NAME, null, values);
			break;
		case LIST_NIGHT:
		case ITEM_NIGHT:
			insertedId = db.insert(NightEntry.TABLE_NAME, null, values);
			break;
		case LIST_DRINK_COUNT:
		case ITEM_DRINK_COUNT:
			insertedId = db.insert(DrinkCountEntry.TABLE_NAME, null, values);
			break;
		case LIST_LOCATION:
		case ITEM_LOCATION:
			insertedId = db.insert(LocationEntry.TABLE_NAME, null, values);
			break;
		case LIST_EVENT:
		case ITEM_EVENT:
			insertedId = db.insert(EventEntry.TABLE_NAME, null, values);
			break;
		}

		if (insertedId > 0) {
			Uri itemUri = ContentUris.withAppendedId(uri, insertedId);
			notifyChange(itemUri);
			return itemUri;
		}

		db.close();
		return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		// NOTE: From the docs. If the query does not match any rows, you should
		// return a
		// Cursor instance whose getCount() method returns 0. You should return
		// null only if an internal error occurred during the query process.
		checkSupportedUri(uri);

		// TODO Default the Sort order

		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		switch (sUriMatcher.match(uri)) {
		case LIST_TAB:
			// For the lists, we only need to set the tables. It will return the
			// full list.
			builder.setTables(TabEntry.TABLE_NAME);
			break;
		case ITEM_TAB:
			builder.setTables(TabEntry.TABLE_NAME);
			builder.appendWhere(DrinksBaseColumns._ID + " = " + uri.getLastPathSegment());
			break;
		case LIST_NIGHT:
			builder.setTables(NightEntry.TABLE_NAME);
			break;
		case ITEM_NIGHT:
			builder.setTables(NightEntry.TABLE_NAME);
			builder.appendWhere(DrinksBaseColumns._ID + " = " + uri.getLastPathSegment());
			break;
		case LIST_DRINK_COUNT:
			builder.setTables(DrinkCountEntry.TABLE_NAME);
			break;
		case ITEM_DRINK_COUNT:
			builder.setTables(DrinkCountEntry.TABLE_NAME);
			builder.appendWhere(DrinksBaseColumns._ID + " = " + uri.getLastPathSegment());
			break;
		case LIST_LOCATION:
			builder.setTables(LocationEntry.TABLE_NAME);
			break;
		case ITEM_LOCATION:
			builder.setTables(LocationEntry.TABLE_NAME);
			builder.appendWhere(DrinksBaseColumns._ID + " = " + uri.getLastPathSegment());
			break;
		case LIST_EVENT:
			builder.setTables(EventEntry.TABLE_NAME);
			break;
		case ITEM_EVENT:
			builder.setTables(EventEntry.TABLE_NAME);
			builder.appendWhere(DrinksBaseColumns._ID + " = " + uri.getLastPathSegment());
			break;
		}

		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		if (cursor != null) {
			cursor.moveToFirst();
			cursor.setNotificationUri(getContext().getContentResolver(), uri);
		}
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		checkSupportedUri(uri);

		notifyChange(uri);
		return 0;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		checkSupportedUri(uri);

		notifyChange(uri);
		return 0;
	}

	private void checkSupportedUri(Uri uri) {
		if (uri == null) {
			throw new IllegalArgumentException("You must pass a valid Uri. The passed in Uri was null!");
		}
		int match = sUriMatcher.match(uri);
		// If it doesn't match one of the defined uris above, then it is not
		// valid!
		if (match != LIST_DRINK_COUNT && match != LIST_EVENT && match != LIST_LOCATION && match != LIST_NIGHT && match != LIST_TAB && match != ITEM_DRINK_COUNT
				&& match != ITEM_EVENT && match != ITEM_LOCATION && match != ITEM_NIGHT && match != ITEM_TAB) {
			throw new IllegalArgumentException("You must pass in a defined Uri. Your uri: " + uri.toString());
		}
	}

	private void notifyChange(Uri uri) {
		getContext().getContentResolver().notifyChange(uri, null);
	}

	// TODO _ base switch
	// switch (sUriMatcher.match(uri)) {
	// case LIST_TAB:
	// case ITEM_TAB:
	// break;
	// case LIST_NIGHT:
	// case ITEM_NIGHT:
	// break;
	// case LIST_DRINK_COUNT:
	// case ITEM_DRINK_COUNT:
	// break;
	// case LIST_LOCATION:
	// case ITEM_LOCATION:
	// break;
	// case LIST_EVENT:
	// case ITEM_EVENT:
	// break;
	// }
}
