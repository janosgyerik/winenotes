package com.winenotes;

import android.content.Intent;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RatingBar;
import android.widget.TextView;

public class ViewWineActivity extends AbstractWineActivity {

	private static final int RETURN_FROM_EDIT = 1;

	private TextView basicInfoView;

	private RatingBar aromaRatingView;
	private RatingBar tasteRatingView;
	private RatingBar aftertasteRatingView;
	private RatingBar overallRatingView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewwine);

		View editButton = findViewById(R.id.btn_edit);
		editButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ViewWineActivity.this, EditWineActivity.class);
				intent.putExtra(BaseColumns._ID, wineId);
				startActivityForResult(intent, RETURN_FROM_EDIT);
			}
		});

		basicInfoView = (TextView) findViewById(R.id.basic_info);

		aromaRatingView = (RatingBar) findViewById(R.id.rating_aroma);
		tasteRatingView = (RatingBar) findViewById(R.id.rating_taste);
		aftertasteRatingView = (RatingBar) findViewById(R.id.rating_aftertaste);
		overallRatingView = (RatingBar) findViewById(R.id.rating_overall);

		loadWineInfo(false);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case RETURN_FROM_EDIT:
			loadWineInfo(false);
			break;
		}
	}

	@Override
	void wineInfoLoaded(WineInfo wineInfo) {
		if (wineInfo.listingText == null) {
			basicInfoView.setVisibility(View.GONE);
		}
		else {
			basicInfoView.setText(wineInfo.listingText);
			basicInfoView.setVisibility(View.VISIBLE);
		}
		
		updateRatingView(aromaRatingView, wineInfo.aromaRating);
		updateRatingView(tasteRatingView, wineInfo.tasteRating);
		updateRatingView(aftertasteRatingView, wineInfo.aftertasteRating);
		updateRatingView(overallRatingView, wineInfo.overallRating);
	}
	
	private void updateRatingView(RatingBar view, float rating) {
		if (rating > 0) {
			int value = (int)rating;
			view.setNumStars(value);
			view.setVisibility(View.VISIBLE);
		}
		else {
			view.setVisibility(View.GONE);
		}
	}
}