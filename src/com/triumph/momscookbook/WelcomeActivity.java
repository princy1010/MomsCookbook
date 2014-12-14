package com.triumph.momscookbook;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class WelcomeActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
			e.printStackTrace();
		}
		getActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.drawable.stripedbackground));
		getActionBar().setDisplayShowHomeEnabled(false);
		String[] values = new String[] { "Black Current", "Butter Scotch",
				"Chocolate", "Hot Brownie Fudge", "Mango", "Kesar Kulfi",
				"Strawberry Yoghurt", "Vanilla" };
		int[] images = new int[] { R.drawable.blackcurrant,
				R.drawable.butterscotch, R.drawable.chocolate,
				R.drawable.hotbrownie, R.drawable.mango, R.drawable.kesarkulfi,
				R.drawable.strawberryyoghurt, R.drawable.vanilla };

		final ArrayList<RowItem> list = new ArrayList<RowItem>();
		for (int i = 0; i < values.length; ++i) {
			RowItem item = new RowItem(images[i], values[i]);
			list.add(item);
		}
		MyArrayAdapter adapter = new MyArrayAdapter(this,
				R.layout.recipe_list_layout, list);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Intent startDetailsIntent = new Intent(getApplicationContext(),
				IceCreamRecipeActivity.class);
		startDetailsIntent.putExtra("itemclicked", position + 1);
		startActivity(startDetailsIntent);
	}
}
