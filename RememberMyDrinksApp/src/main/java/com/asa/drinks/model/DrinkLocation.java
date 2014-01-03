package com.asa.drinks.model;

import android.content.ContentValues;

public class DrinkLocation extends BaseItem {

	private int drinkNightId;
	private long date;

	public int getDrinkNightId() {
		return drinkNightId;
	}

	public void setDrinkNightId(int drinkNightId) {
		this.drinkNightId = drinkNightId;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

    @Override
    public ContentValues toContentValues() {
        // TODO - implement
        return null;
    }
}
