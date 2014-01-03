package com.asa.drinks.model;

import android.content.ContentValues;

public class DrinkTab extends BaseItem {

	private long date;
	private double cost;
	private boolean favorite;
	private boolean open;
	private double radius;
	private String name;
	private int drinkNightId;

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDrinkNightId() {
		return drinkNightId;
	}

	public void setDrinkNightId(int drinkNightId) {
		this.drinkNightId = drinkNightId;
	}

    @Override
    public ContentValues toContentValues() {
        // TODO - implement
        return null;
    }
}
