package com.triumph.momscookbook.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextViewBirdie extends TextView {

	public MyTextViewBirdie(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public MyTextViewBirdie(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MyTextViewBirdie(Context context) {
		super(context);
		init();
	}

	public void init() {
		Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
				"fonts/black_jack.ttf");
		setTypeface(tf);

	}
}