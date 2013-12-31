package com.asa.drinks.model;

import com.asa.drinks.model.contracts.DrinksBaseColumns;
import com.asa.drinks.model.contracts.DrinksContract;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DrinksSQLiteHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "drinks.db";
	public static final int DATABASE_VERSION = 1;

	private final static String SQL_TYPE_DATE = " INTEGER, ";
	private final static String DROP_TABLE_STRING = "DROP TABLE IF EXISTS ";

	// Create statement for the TAB table
	public static final String CREATE_TABLE_TABS = "CREATE TABLE " + DrinksContract.TabEntry.TABLE_NAME + " (" + DrinksBaseColumns._ID + " INTEGER PRIMARY KEY, "
			+ DrinksBaseColumns.COLUMN_DATE + SQL_TYPE_DATE + DrinksBaseColumns.COLUMN_LATITUDE + " REAL, " + DrinksBaseColumns.COLUMN_LONGITUDE + " REAL, "
			+ DrinksContract.TabEntry.COLUMN_COST + " REAL, " + DrinksContract.TabEntry.COLUMN_FAVORITE + " INTEGER, " + DrinksContract.TabEntry.COLUMN_OPEN + " INTEGER, "
			+ DrinksContract.TabEntry.COLUMN_RADIUS + " REAL, " + DrinksContract.TabEntry.COLUMN_NAME + " TEXT, " + DrinksContract.TabEntry.COLULMN_DRINK_NIGHT_ID + " INTEGER)";
	// Create statement for the Drinks table
	public static final String CREATE_TABLE_DRINK_COUNT = "CREATE TABLE " + DrinksContract.DrinkCountEntry.TABLE_NAME + " (" + DrinksBaseColumns._ID + " INTEGER PRIMARY KEY, "
			+ DrinksBaseColumns.COLUMN_DATE + SQL_TYPE_DATE + DrinksBaseColumns.COLUMN_LATITUDE + " REAL, " + DrinksBaseColumns.COLUMN_LONGITUDE + " REAL, "
			+ DrinksContract.DrinkCountEntry.COLUMN_COUNT + " INTEGER, " + DrinksContract.DrinkCountEntry.COLUMN_DRINK_NIGHT_ID + " INTEGER, "
			+ DrinksContract.DrinkCountEntry.COLUMN_NAME + " TEXT)";
	// Create statement for the Night table
	public static final String CREATE_TABLE_NIGHT = "CREATE TABLE " + DrinksContract.NightEntry.TABLE_NAME + " (" + DrinksBaseColumns._ID + " INTEGER PRIMARY KEY, "
			+ DrinksBaseColumns.COLUMN_DATE + SQL_TYPE_DATE + DrinksContract.NightEntry.COLUMN_OPEN + " INTEGER, " + DrinksContract.NightEntry.COLUMN_NAME + " TEXT)";
	// Create statement for the locations table
	public static final String CREATE_TABLE_LOCATIONS = "CREATE TABLE " + DrinksContract.LocationEntry.TABLE_NAME + " (" + DrinksBaseColumns._ID + " INTEGER PRIMARY KEY, "
			+ DrinksBaseColumns.COLUMN_DATE + SQL_TYPE_DATE + DrinksBaseColumns.COLUMN_LATITUDE + " REAL, " + DrinksBaseColumns.COLUMN_LONGITUDE + " REAL, "
			+ DrinksContract.DrinkCountEntry.COLUMN_DRINK_NIGHT_ID + " INTEGER)";
	// Create statement for the event table
	public static final String CREATE_TABLE_EVENTS = "CREATE TABLE " + DrinksContract.EventEntry.TABLE_NAME + " (" + DrinksBaseColumns._ID + " INTEGER PRIMARY KEY, "
			+ DrinksBaseColumns.COLUMN_DATE + SQL_TYPE_DATE + DrinksBaseColumns.COLUMN_LATITUDE + " REAL, " + DrinksBaseColumns.COLUMN_LONGITUDE + " REAL, "
			+ DrinksContract.EventEntry.COLUMN_TYPE + " INTEGER, " + DrinksContract.EventEntry.COLUMN_DESCRIP + " TEXT, " + DrinksContract.EventEntry.COLUMN_ADDRESS_FROM
			+ " TEXT, " + DrinksContract.EventEntry.COLUMN_PIC + " TEXT, " + DrinksContract.EventEntry.COLUMN_ICON + " TEXT)";

	public DrinksSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_TABS);
		db.execSQL(CREATE_TABLE_DRINK_COUNT);
		db.execSQL(CREATE_TABLE_NIGHT);
		db.execSQL(CREATE_TABLE_LOCATIONS);
		db.execSQL(CREATE_TABLE_EVENTS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(dropTable(DrinksContract.TabEntry.TABLE_NAME));
		db.execSQL(dropTable(DrinksContract.DrinkCountEntry.TABLE_NAME));
		db.execSQL(dropTable(DrinksContract.NightEntry.TABLE_NAME));
		db.execSQL(dropTable(DrinksContract.LocationEntry.TABLE_NAME));
		db.execSQL(dropTable(DrinksContract.EventEntry.TABLE_NAME));
		onCreate(db);
	}

	// Because I'm lazy, I created this method.
	private String dropTable(String table) {
		return DROP_TABLE_STRING + table;
	}

}
