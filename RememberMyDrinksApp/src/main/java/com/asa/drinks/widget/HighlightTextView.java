package com.asa.drinks.widget;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.widget.TextView;

// Highly based off of http://blog.stylingandroid.com/archives/218
public class HighlightTextView extends TextView {
	private Pattern pattern;
	private int highlightStyle = Typeface.BOLD;

	public HighlightTextView(Context context) {
		this(context, null);
	}

	public HighlightTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HighlightTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public String getPatternString() {
		return pattern.pattern();
	}

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = TextUtils.isEmpty(pattern) ? null : Pattern.compile(pattern);
		updateText(getText());
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
		updateText(getText());
	}

	public int getHighlightStyle() {
		return highlightStyle;
	}

	public void setHighlightStyle(int highlightStyle) {
		this.highlightStyle = highlightStyle;
		updateText(getText());
	}

	public void updateText(CharSequence text) {
		Spannable spannable = new SpannableString(text);
		if (pattern != null) {
			Matcher matcher = pattern.matcher(text);
			while (matcher.find()) {
				int start = matcher.start();
				int end = matcher.end();
				StyleSpan span = new StyleSpan(highlightStyle);
				spannable.setSpan(span, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			}
		}
		super.setText(spannable, BufferType.SPANNABLE);
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		updateText(text);
	}

}
