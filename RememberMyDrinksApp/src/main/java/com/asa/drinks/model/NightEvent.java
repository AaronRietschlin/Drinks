package com.asa.drinks.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NightEvent extends BaseItem {

	private Type type;
	private double date;
	private String descrip;
	private String from;
	private String pic;
	private String icon;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public double getDate() {
		return date;
	}

	public void setDate(double date) {
		this.date = date;
	}

	public String getDescrip() {
		return descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public static enum Type implements Parcelable {
		SMS, CALL, EMAIL, HANGOUT, FALL, OTHER;

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(final Parcel dest, final int flags) {
			dest.writeInt(ordinal());
		}

		public static final Creator<Type> CREATOR = new Creator<Type>() {
			@Override
			public Type createFromParcel(final Parcel source) {
				return Type.values()[source.readInt()];
			}

			@Override
			public Type[] newArray(final int size) {
				return new Type[size];
			}
		};
	}

}
