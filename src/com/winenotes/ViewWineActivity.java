package com.winenotes;

import android.content.Intent;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ViewWineActivity extends AbstractWineActivity {

	private static final int RETURN_FROM_EDIT = 1;

	private TextView regionView;

	private View basicInfoView;
	private TextView wineTypeView;
	private TextView yearView;
	private TextView flagView;

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

		regionView = (TextView) findViewById(R.id.region);
		basicInfoView = findViewById(R.id.basic_info);
		wineTypeView = (TextView) findViewById(R.id.wine_type);
		yearView = (TextView) findViewById(R.id.year);
		flagView = (TextView) findViewById(R.id.flag);

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
		if (wineInfo.wineType != null && wineInfo.year > 0 && wineInfo.flag != null) {
			if (wineInfo.wineType != null) {
				wineTypeView.setText(wineInfo.wineType);
			}
			if (wineInfo.year > 0) {
				yearView.setText("" + wineInfo.year);
			}
			if (wineInfo.flag != null) {
				flagView.setText(wineInfo.flag);
			}
		}
		else {
			basicInfoView.setVisibility(View.GONE);
		}
		basicInfoView.setVisibility(View.GONE);  // TODO

		if (wineInfo.region != null && wineInfo.region.length() > 0) {
			regionView.setText(wineInfo.region);
		}
		else {
			findViewById(R.id.label_region).setVisibility(View.GONE);
		}
	}

}