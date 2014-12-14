package com.triumph.momscookbook;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.triumph.momscookbook.views.MyTextView;
import com.triumph.momscookbook.views.MyTextViewBirdie;
import com.triumph.momscookbook.views.TouchHighlightImageButton;

public class IceCreamRecipeActivity extends Activity {

	private Animator mCurrentAnimator;
	TouchHighlightImageButton imgbtn;
	private int mShortAnimationDuration;

	@SuppressLint("NewApi")
	private void zoomImageFromThumb(final View thumbView, Drawable drawable) {
		if (mCurrentAnimator != null) {
			mCurrentAnimator.cancel();
		}

		final ImageView expandedImageView = (ImageView) findViewById(R.id.expanded_image);
		expandedImageView.setImageDrawable(drawable);
		final Rect startBounds = new Rect();
		final Rect finalBounds = new Rect();
		final Point globalOffset = new Point();

		thumbView.getGlobalVisibleRect(startBounds);
		findViewById(R.id.container).getGlobalVisibleRect(finalBounds,
				globalOffset);
		startBounds.offset(-globalOffset.x, -globalOffset.y);
		finalBounds.offset(-globalOffset.x, -globalOffset.y);

		float startScale;
		if ((float) finalBounds.width() / finalBounds.height() > (float) startBounds
				.width() / startBounds.height()) {
			startScale = (float) startBounds.height() / finalBounds.height();
			float startWidth = startScale * finalBounds.width();
			float deltaWidth = (startWidth - startBounds.width()) / 2;
			startBounds.left -= deltaWidth;
			startBounds.right += deltaWidth;
		} else {
			startScale = (float) startBounds.width() / finalBounds.width();
			float startHeight = startScale * finalBounds.height();
			float deltaHeight = (startHeight - startBounds.height()) / 2;
			startBounds.top -= deltaHeight;
			startBounds.bottom += deltaHeight;
		}
		thumbView.setAlpha(0f);
		expandedImageView.setVisibility(View.VISIBLE);

		expandedImageView.setPivotX(0f);
		expandedImageView.setPivotY(0f);

		AnimatorSet set = new AnimatorSet();
		set.play(
				ObjectAnimator.ofFloat(expandedImageView, View.X,
						startBounds.left, finalBounds.left))
				.with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
						startBounds.top, finalBounds.top))
				.with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
						startScale, 1f))
				.with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y,
						startScale, 1f));
		set.setDuration(mShortAnimationDuration);
		set.setInterpolator(new DecelerateInterpolator());
		set.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				mCurrentAnimator = null;
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				mCurrentAnimator = null;
			}
		});
		set.start();
		mCurrentAnimator = set;

		// Upon clicking the zoomed-in image, it should zoom back down
		// to the original bounds
		// and show the thumbnail instead of the expanded image.
		final float startScaleFinal = startScale;
		expandedImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mCurrentAnimator != null) {
					mCurrentAnimator.cancel();
				}

				// Animate the four positioning/sizing
				// properties in parallel, back to their
				// original values.
				AnimatorSet set = new AnimatorSet();
				set.play(
						ObjectAnimator.ofFloat(expandedImageView, View.X,
								startBounds.left))
						.with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
								startBounds.top))
						.with(ObjectAnimator.ofFloat(expandedImageView,
								View.SCALE_X, startScaleFinal))
						.with(ObjectAnimator.ofFloat(expandedImageView,
								View.SCALE_Y, startScaleFinal));
				set.setDuration(mShortAnimationDuration);
				set.setInterpolator(new DecelerateInterpolator());
				set.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						thumbView.setAlpha(1f);
						expandedImageView.setVisibility(View.GONE);
						mCurrentAnimator = null;
					}

					@Override
					public void onAnimationCancel(Animator animation) {
						thumbView.setAlpha(1f);
						expandedImageView.setVisibility(View.GONE);
						mCurrentAnimator = null;
					}
				});
				set.start();
				mCurrentAnimator = set;
			}
		});
	}

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ice_cream_recipe);
		int[] images = new int[] { R.drawable.blackcurrant,
				R.drawable.butterscotch, R.drawable.chocolate,
				R.drawable.hotbrownie, R.drawable.mango, R.drawable.kesarkulfi,
				R.drawable.strawberryyoghurt, R.drawable.vanilla };

		// re-style Action bar
		getActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.drawable.stripedbackground));
		getActionBar().setDisplayShowHomeEnabled(false);
		Typeface typeFace = Typeface.createFromAsset(getAssets(),
				"fonts/riesling.ttf");
		try {
			final int titleId = Resources.getSystem().getIdentifier(
					"action_bar_title", "id", "android");
			TextView titleView = (TextView) findViewById(titleId);
			titleView.setTypeface(typeFace, Typeface.BOLD);
			titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources()
					.getDimension(R.dimen.action_bar_size));
		} catch (Exception e) {
		}

		MyTextViewBirdie tv = (MyTextViewBirdie) findViewById(R.id.tv);
		MyTextViewBirdie recipecontent = (MyTextViewBirdie) findViewById(R.id.recipecontent);
		MyTextView recipetitle = (MyTextView) findViewById(R.id.recipetitle);
		final View thumb1View = findViewById(R.id.recipeimg);
		imgbtn = (TouchHighlightImageButton) findViewById(R.id.recipeimg);

		String ingredients = getString(R.string.ingredients);
		String title = getString(R.string.ice_cream);
		String content = getString(R.string.recipe);
		Intent intent = getIntent();
		int val = intent.getIntExtra(getString(R.string.itemclicked), 1);
		switch (val) {
		case 1:
			title = getString(R.string.black_current);
			ingredients = getString(R.string._125gm)
					+ getString(R.string._125_gm);
			content = getString(R.string._1_only)
					+ getString(R.string.cook_black_grapes);
			break;
		case 2:
			ingredients = getString(R.string._2_tsp_butter);
			title = getString(R.string.butter_scotch);
			content = getString(R.string.for_praline);
			break;
		case 3:
			title = getString(R.string.chocolate);
			ingredients = getString(R.string._150_gm_whipped);
			content = getString(R.string._1_beat_thick);
			break;
		case 4:
			title = getString(R.string.hot_brownie_fudge);
			ingredients = getString(R.string._2_scoops);
			content = getString(R.string.glass_with_);
			break;
		case 5:
			title = "Mango";
			ingredients = getString(R.string._1_plain_base);
			content = getString(R.string._1_10_o);
			break;
		case 6:
			title = getString(R.string.kesar_kulfi);
			ingredients = getString(R.string._500_ml);
			content = getString(R.string._1_milk);
			break;
		case 7:
			title = getString(R.string.strawberry_yoghurt);
			ingredients = getString(R.string._cup_);
			content = getString(R.string._1_mix);
			break;
		case 8:
			title = getString(R.string.vanilla);
			ingredients = getString(R.string._3_cup);
			content = getString(R.string._1_beat);
		}
		tv.setText(ingredients);
		recipecontent.setText(content);
		recipetitle.setText(title);
		imgbtn.setImageDrawable(getResources().getDrawable(images[val-1]));

		// Animation on Image of Recipe
		mShortAnimationDuration = getResources().getInteger(
				android.R.integer.config_shortAnimTime);
		thumb1View.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				zoomImageFromThumb(thumb1View, imgbtn.getDrawable());
			}
		});
	}
}