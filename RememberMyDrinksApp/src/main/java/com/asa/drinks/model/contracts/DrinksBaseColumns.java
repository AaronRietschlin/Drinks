package com.asa.drinks.model.contracts;

import android.provider.BaseColumns;

/**
 * The base columns that will be in (mostly) every table.
 */
public abstract class DrinksBaseColumns implements BaseColumns {

	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_LATITUDE = "latitude";
	public static final String COLUMN_LONGITUDE = "longitude";

}
