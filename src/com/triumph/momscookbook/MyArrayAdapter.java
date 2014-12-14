package com.triumph.momscookbook;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<RowItem> {
	Context context;

	public MyArrayAdapter(Context context, int resource) {
		super(context, resource);
		this.context = context;
	}

	public MyArrayAdapter(Context context, int resourceId, List<RowItem> items) {
		super(context, resourceId, items);
		this.context = context;
	}

	private class ViewHolder {
		ImageView imageView;
		TextView txtTitle;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		RowItem rowItem = getItem(position);
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.recipe_list_layout, null);
			holder = new ViewHolder();
			holder.txtTitle = (TextView) convertView
					.findViewById(R.id.recipetitle);
			holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();
		holder.txtTitle.setText(rowItem.getTitle());
		holder.imageView.setImageResource(rowItem.getImageId());

		return convertView;
	}
}

class RowItem {

	private int imageId;
	private String title;

	public RowItem(int imageId, String title) {
		this.imageId = imageId;
		this.title = title;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return title + "\n";
	}
}