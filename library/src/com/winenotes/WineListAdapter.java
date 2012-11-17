package com.winenotes;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class WineListAdapter extends CursorAdapter {

	private LayoutInflater mLayoutInflater;
	private int mIdIndex;
	private int mNameIndex;
	private int mSummaryIndex;
	private int mRatingIndex;

	public WineListAdapter(Context context, Cursor cursor) {
		super(context, cursor);

		mIdIndex = cursor.getColumnIndex("_id");
		mNameIndex = cursor.getColumnIndex("name");
		mSummaryIndex = cursor.getColumnIndex("summary");
		mRatingIndex = cursor.getColumnIndex("rating");

		mLayoutInflater = LayoutInflater.from(context);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView _ID = (TextView)view.findViewById(R.id._ID);
		_ID.setText(cursor.getString(mIdIndex));

		TextView name = (TextView)view.findViewById(R.id.name);
		name.setText(cursor.getString(mNameIndex));

		TextView summary = (TextView) view.findViewById(R.id.summary);
		summary.setText(cursor.getString(mSummaryIndex));

		float rating = cursor.getFloat(mRatingIndex);
		RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rating);
		ratingBar.setEnabled(false);
		if (rating > 0) {
			ratingBar.setNumStars((int)rating);
			ratingBar.setRating((int)rating);
			ratingBar.setVisibility(View.VISIBLE);
		}
		else {
			ratingBar.setVisibility(View.GONE);
		}
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return mLayoutInflater.inflate(R.layout.winelist_item, null);
	}

}
