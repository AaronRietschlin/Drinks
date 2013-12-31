package com.asa.drinks.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asa.drinks.R;

public class DrinkItem extends LinearLayout {

	private HighlightTextView mTextMain;
	private TextView mTextSub;
	private ImageView mImage;
	private LinearLayout mLayoutText;

	private Context mContext;

	public DrinkItem(Context context) {
		this(context, null);
	}

	public DrinkItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		build(context);
		if (attrs != null) {
			init(attrs);
		}
	}

	public DrinkItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		build(context);
		init(attrs);
	}

	private void build(Context context) {
		mContext = context;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		inflater.inflate(R.layout.widget_drink_item, this, true);

		mTextMain = (HighlightTextView) findViewById(R.id.drink_item_text_main);
		mTextSub = (TextView) findViewById(R.id.drink_item_text_sub);
		mImage = (ImageView) findViewById(R.id.drink_item_image);
		mLayoutText = (LinearLayout) findViewById(R.id.drink_item_layout_text);
	}

	private void init(AttributeSet attrs) {
		// TypedArray a = mContext.obtainStyledAttributes(attrs,
		// R.styleable.MapAttrs);
		// TODO
	}

}
