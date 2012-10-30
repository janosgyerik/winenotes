package com.winenotes;

import java.io.File;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditWineActivity extends AbstractWineActivity {

	private static final String TAG = "EditWineActivity";

	private static final int RETURN_FROM_EDIT_AROMA_IMPRESSIONS = 1;
	private static final int RETURN_FROM_EDIT_TAGS = 2;
	private static final int RETURN_FROM_ADD_PHOTO = 3;

	private EditText nameView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editwine);

		// for debugging:
//		wineId = "42"; // rich
//		wineId = "999"; // non-existent
//		 wineId = "44"; // lean
		
		if (wineId == null) {
			wineId = helper.newWine();
		}

		nameView = (EditText) findViewById(R.id.name_edit);

		final Activity this_ = this;

		View editAromaImpressionsButton = (View) findViewById(R.id.btn_edit_impressions);
		editAromaImpressionsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(this_, EditAromaImpressionsActivity.class);
				intent.putExtra(BaseColumns._ID, wineId);
				startActivityForResult(intent, RETURN_FROM_EDIT_AROMA_IMPRESSIONS);
			}
		});

		View addPhotoButton = (View) findViewById(R.id.btn_add_photo);
		addPhotoButton.setOnClickListener(new AddPhotoOnClickListener());

		Button save = (Button) findViewById(R.id.btn_save);
		save.setOnClickListener(new SaveWineOnClickListener());

		reloadAndRefreshWineDetails(true);
	}

	class SaveWineOnClickListener implements OnClickListener {
		@Override
		public void onClick(View view) {
			String name = capitalize(nameView.getText().toString());

			// display_name
			String displayName = "";
			Cursor aromaImpressionsCursor = helper.getWineAromaImpressionsCursor(wineId);
			if (aromaImpressionsCursor.moveToNext()) {
				String aromaImpression = aromaImpressionsCursor.getString(0);
				displayName = aromaImpression;
				while (aromaImpressionsCursor.moveToNext()) {
					displayName += ", ";
					aromaImpression = aromaImpressionsCursor.getString(0);
					displayName += aromaImpression;
				}
			}
			aromaImpressionsCursor.close();

			if (helper.saveWine(wineId, name, displayName)) {
				Toast.makeText(getApplicationContext(), R.string.msg_updated_wine, Toast.LENGTH_SHORT).show();
				finish();
			}
			else {
				Toast.makeText(getApplicationContext(), R.string.error_update_wine, Toast.LENGTH_SHORT).show();
			}
		}
	}

	static String capitalize(String name) {
		if (name == null || name.trim().length() < 1) return name;
		name = name.trim();
		return Character.toUpperCase(name.charAt(0)) + name.substring(1);
	}

	private void handleReturnFromEditAromaImpressions(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			boolean isChanged = extras.getBoolean(AbstractEditWineItemsActivity.OUT_CHANGED);
			if (isChanged) {
				Log.i(TAG, "aroma impressions have changed -> reloading details");
				reloadAndRefreshWineDetails(true);
				return;
			}
		}
		Log.i(TAG, "aroma impressions have NOT changed -> NOT reloading details");
	}

	private void handleReturnFromEditTags(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			boolean isChanged = extras.getBoolean(AbstractEditWineItemsActivity.OUT_CHANGED);
			if (isChanged) {
				Log.i(TAG, "tags have changed -> reloading details");
				reloadAndRefreshWineDetails(true);
				return;
			}
		}
		Log.i(TAG, "tags have NOT changed -> NOT reloading details");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(TAG, "onActivityResult");
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case RETURN_FROM_EDIT_AROMA_IMPRESSIONS:
				Log.i(TAG, "OK edit aroma impressions");
				handleReturnFromEditAromaImpressions(data);
				break;
			case RETURN_FROM_EDIT_TAGS:
				Log.i(TAG, "OK edit tags");
				handleReturnFromEditTags(data);
				break;
			case RETURN_FROM_ADD_PHOTO:
				handleSmallCameraPhoto(data);
				break;
			default:
				Log.i(TAG, "OK ???");
			}
		}
		else {
			switch (requestCode) {
			case RETURN_FROM_EDIT_AROMA_IMPRESSIONS:
				Log.i(TAG, "CANCEL edit aroma impressions");
				break;
			case RETURN_FROM_EDIT_TAGS:
				Log.i(TAG, "CANCEL edit tags");
				break;
			case RETURN_FROM_ADD_PHOTO:
				Log.i(TAG, "CANCEL add photo");
				if (photoFile != null && photoFile.isFile()) {
					photoFile.delete();
				}
				break;
			default:
				Log.i(TAG, "CANCEL ???");
			}
		}
	}

	private File photoFile;

	class AddPhotoOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			dispatchTakePictureIntent();
		}
	}

	private void dispatchTakePictureIntent() {
		try {
			photoFile = WineFileManager.newPhotoFile(wineId);
		} catch (IOException e) {
			e.printStackTrace();
			photoFile = null;
		}
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
		startActivityForResult(takePictureIntent, RETURN_FROM_ADD_PHOTO);
	}

	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list =
				packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	private void addPhotoToWine(File photoFile) {
		helper.addWinePhoto(wineId, photoFile.getName());
	}

	private void handleSmallCameraPhoto(Intent intent) {
		if (photoFile != null && photoFile.isFile()) {
			addPhotoToWine(photoFile);
			addPhotoToLayout(photoFile, true);
		}
	}
}