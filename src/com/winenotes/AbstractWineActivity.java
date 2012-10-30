package com.winenotes;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class AbstractWineActivity extends Activity {

	private static final String TAG = "AbstractWineActivity";

	protected WineNotesSQLiteOpenHelper helper;
	protected String wineId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		helper = new WineNotesSQLiteOpenHelper(this);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			wineId = extras.getString(BaseColumns._ID);
		}
	}

	private void clearPhotosFromLayout() {
		LinearLayout layout = (LinearLayout) findViewById(R.id.photos);
		layout.removeAllViews();
	}

	protected void addPhotoToLayout(File photoFile, boolean editable) {
		if (photoFile.isFile()) {
			int appWidth = getWindowManager().getDefaultDisplay().getWidth();
			Bitmap bitmap = BitmapTools.createScaledBitmap(photoFile, appWidth);
			ImageView photoView = new ImageView(this);
			photoView.setImageBitmap(bitmap);
			photoView.setPadding(10, 10, 10, 10);
			photoView.setTag(photoFile.getName());
			if (editable) {
				photoView.setOnLongClickListener(new PhotoOnLongClickListener(photoFile));
			}

			// dirty hack for motorola
			int targetHeight = appWidth * bitmap.getHeight() / bitmap.getWidth();
			LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			photoView.setLayoutParams(params);
			photoView.getLayoutParams().height = targetHeight;

			LinearLayout layout = (LinearLayout) findViewById(R.id.photos);
			layout.addView(photoView);
		}
	}

	protected void reloadAndRefreshWineDetails(boolean editable) {
		if (wineId != null) {
			Cursor wineCursor = helper.getWineDetailsCursor(wineId);
			if (wineCursor.moveToNext()) {
				TextView nameView = (TextView) findViewById(R.id.name);
				EditText nameEditView = (EditText) findViewById(R.id.name_edit);
				String name = wineCursor.getString(0);
				if (name != null && name.length() > 0) {
					nameView.setText(name);
					if (nameEditView != null) {
						nameEditView.setText(name);
					}
				}
				else {
					nameView.setText(R.string.title_noname_wine);
				}

				Cursor aromaImpressionsCursor = helper.getWineAromaImpressionsCursor(wineId);
				StringBuffer aromaImpressionsBuffer = new StringBuffer();
				while (aromaImpressionsCursor.moveToNext()) {
					String aromaImpression = aromaImpressionsCursor.getString(0);
					aromaImpressionsBuffer.append(aromaImpression);
					aromaImpressionsBuffer.append(", ");
				}
				aromaImpressionsCursor.close();
				
				TextView aromaImpressionsView = (TextView) findViewById(R.id.impressions);
				if (aromaImpressionsBuffer.length() > 2) {
					aromaImpressionsView.setText(aromaImpressionsBuffer.substring(0, aromaImpressionsBuffer.length() - 2));
				}
				else {
					aromaImpressionsView.setText(R.string.label_none);
				}

				Cursor tagsCursor = helper.getWineTagsCursor(wineId);
				StringBuffer tagsBuffer = new StringBuffer();
				while (tagsCursor.moveToNext()) {
					String tag = tagsCursor.getString(0);
					tagsBuffer.append(tag);
					tagsBuffer.append(", ");
				}
				tagsCursor.close();
				
				TextView tagsView = (TextView) findViewById(R.id.tags);
				if (tagsBuffer.length() > 2) {
					tagsView.setText(tagsBuffer.substring(0, tagsBuffer.length() - 2));
				}
				else {
					tagsView.setText(R.string.label_none);
				}

				Cursor photosCursor = helper.getWinePhotosCursor(wineId);
				clearPhotosFromLayout();
				while (photosCursor.moveToNext()) {
					String filename = photosCursor.getString(0);
					addPhotoToLayout(WineFileManager.getPhotoFile(filename), editable);
				}
				photosCursor.close();
			}
			else {
				// TODO
			}
			wineCursor.close();
		}
	}

	private void removePhoto(File photoFile) {
		if (photoFile.delete()) {
			if (removePhotoFromWine(photoFile)) {
				removePhotoFromLayout(photoFile);
			}
		}
	}

	private void removePhotoFromLayout(File photoFile) {
		LinearLayout layout = (LinearLayout) findViewById(R.id.photos);
		layout.removeView(layout.findViewWithTag(photoFile.getName()));
	}

	private boolean removePhotoFromWine(File photoFile) {
		return helper.removeWinePhoto(wineId, photoFile.getName());
	}

	class PhotoOnLongClickListener implements OnLongClickListener {
		private final File photoFile;

		public PhotoOnLongClickListener(File photoFile) {
			this.photoFile = photoFile;
		}

		@Override
		public boolean onLongClick(View arg0) {
			new AlertDialog.Builder(AbstractWineActivity.this)
			.setMessage(R.string.confirm_are_you_sure)
			.setCancelable(true)
			.setTitle(R.string.title_delete_photo)
			.setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					removePhoto(photoFile);
				}
			})
			.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			})
			.show();
			return true;
		}

	}

	@Override  
	protected void onDestroy() {
		Log.d(TAG, "++onDestroy");
		super.onDestroy();
		helper.close();
	}
}