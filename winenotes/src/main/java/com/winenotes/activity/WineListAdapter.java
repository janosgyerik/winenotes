package com.winenotes.activity;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.winenotes.R;

public class WineListAdapter extends CursorAdapter {

    private static final int RED_WINE = 1;
    private static final int WHITE_WINE = 2;

    private final LayoutInflater mLayoutInflater;
    private final int mIdIndex;
    private final int mWineTypeIdIndex;
    private final int mNameIndex;
    private final int mSummaryIndex;
    private final int mRatingIndex;

    public WineListAdapter(Context context, Cursor cursor) {
        super(context, cursor, true);

        mIdIndex = cursor.getColumnIndex("_id");
        mWineTypeIdIndex = cursor.getColumnIndex("winetype_id");
        mNameIndex = cursor.getColumnIndex("name");
        mSummaryIndex = cursor.getColumnIndex("summary");
        mRatingIndex = cursor.getColumnIndex("rating");

        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView _ID = (TextView) view.findViewById(R.id._ID);
        _ID.setText(cursor.getString(mIdIndex));

        int imageResourceId;
        switch (cursor.getInt(mWineTypeIdIndex)) {
            case RED_WINE:
                imageResourceId = R.drawable.bullet_redwine;
                break;
            case WHITE_WINE:
                imageResourceId = R.drawable.bullet_whitewine;
                break;
            default:
                imageResourceId = R.drawable.bullet_other;
        }
        ImageView bullet = (ImageView) view.findViewById(R.id.bullet);
        bullet.setImageResource(imageResourceId);

        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(cursor.getString(mNameIndex));

        TextView summary = (TextView) view.findViewById(R.id.summary);
        summary.setText(cursor.getString(mSummaryIndex));

        float rating = cursor.getFloat(mRatingIndex);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rating);
        ratingBar.setEnabled(false);
        if (rating > 0) {
            ratingBar.setNumStars((int) rating);
            ratingBar.setRating((int) rating);
            ratingBar.setVisibility(View.VISIBLE);
        } else {
            ratingBar.setVisibility(View.GONE);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mLayoutInflater.inflate(R.layout.winelist_item, null);
    }

}
