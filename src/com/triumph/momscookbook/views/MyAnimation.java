package com.triumph.momscookbook.views;

import android.view.View;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.LinearLayout.LayoutParams;

public class MyAnimation extends ScaleAnimation {
	private View mView;

	private LayoutParams mLayoutParams;

	private boolean mclick;

	private int mMarginBottomFromY, mMarginBottomToY;

	private boolean mVanishAfter = false;

	public MyAnimation(float fromX, float toX, float fromY, float toY,
			int duration, View view, boolean vanishAfter, boolean click) {
		super(fromX, toX, fromY, toY);
		setDuration(duration);
		mView = view;
		mclick = click;
		mVanishAfter = vanishAfter;
		mLayoutParams = (LayoutParams) view.getLayoutParams();
		int height = mView.getHeight();
		mMarginBottomFromY = (int) (height * fromY)
				+ mLayoutParams.bottomMargin - height;
		mMarginBottomToY = (int) (0 - ((height * toY) + mLayoutParams.bottomMargin))
				- height;
		if (!mclick) {
			mView.setVisibility(View.GONE);
		}

	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		if (interpolatedTime < 1.0f) {
			int newMarginBottom = mMarginBottomFromY
					+ (int) ((mMarginBottomToY - mMarginBottomFromY) * interpolatedTime);
			mLayoutParams.setMargins(mLayoutParams.leftMargin,
					mLayoutParams.topMargin, mLayoutParams.rightMargin,
					newMarginBottom);
			mView.getParent().requestLayout();
		} else if (mVanishAfter) {
			mView.setVisibility(View.VISIBLE);
		}

	}

}