package com.asa.drinks.model;

import android.content.ContentValues;

public class DrinkCount extends BaseItem {

	private long date;
	private int count;
	private int drinkNightId;
	private String name;

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getDrinkNightId() {
		return drinkNightId;
	}

	public void setDrinkNightId(int drinkNightId) {
		this.drinkNightId = drinkNightId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    @Override
    public ContentValues toContentValues() {
        // TODO - implement
        return null;
    }
}
