package com.asa.drinks.model;

public class DrinkNight extends BaseItem {

	private double date;
	private boolean open;
	private String name;

	public double getDate() {
		return date;
	}

	public void setDate(double date) {
		this.date = date;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
