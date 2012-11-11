package com.winenotes;

import android.content.Intent;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.view.View.OnClickListener;

public class ViewWineActivity extends AbstractWineActivity {

//	private static final String TAG = "ViewWineActivity";

	private static final int RETURN_FROM_EDIT = 1;

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

		reloadAndRefreshWineDetails(false);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case RETURN_FROM_EDIT:
			reloadAndRefreshWineDetails(false);
			break;
		}
	}

}