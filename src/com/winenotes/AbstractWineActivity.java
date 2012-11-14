package com.winenotes;

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
import android.widget.RatingBar;
import android.widget.TextView;

public abstract class AbstractWineActivity extends Activity {

	private static final String TAG = AbstractWineActivity.class.getSimpleName();

	protected WineNotesSQLiteOpenHelper helper;
	protected String wineId;

	private boolean emptyWine = true;

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

	protected void addPhotoToLayout(String photoFilename, boolean editable) {
		int appWidth = getWindowManager().getDefaultDisplay().getWidth();
		Bitmap bitmap = WineFileManager.getMediumPhotoBitmap(photoFilename, appWidth);
		if (bitmap != null) {
			ImageView photoView = new ImageView(this);
			photoView.setImageBitmap(bitmap);
			photoView.setPadding(2, 2, 2, 2);
			photoView.setTag(photoFilename);
			if (editable) {
				photoView.setOnLongClickListener(new PhotoOnLongClickListener(photoFilename));
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

	private String getStringColumn(Cursor cursor, String columnName) {
		int columnIndex = cursor.getColumnIndex(columnName);
		String value = cursor.getString(columnIndex);
		return value;
	}

	private float getFloatColumn(Cursor cursor, String columnName) {
		int columnIndex = cursor.getColumnIndex(columnName);
		float value = cursor.getFloat(columnIndex);
		return value;
	}

	private int getIntColumn(Cursor cursor, String columnName) {
		int columnIndex = cursor.getColumnIndex(columnName);
		int value = cursor.getInt(columnIndex);
		return value;
	}

	protected void loadWineInfo(boolean editable) {
		if (wineId != null) {
			Cursor wineCursor = helper.getWineDetailsCursor(wineId);

			if (wineCursor.moveToNext()) {

				String name = getStringColumn(wineCursor, "name");
				int wineryId = getIntColumn(wineCursor, "winery_id");
				String wineryName = getStringColumn(wineCursor, "winery_name");
				float price = getFloatColumn(wineCursor, "price");
				int wineTypeId = getIntColumn(wineCursor, "winetype_id");
				String wineType = getStringColumn(wineCursor, "winetype");
				int year = getIntColumn(wineCursor, "year");
				int regionId = getIntColumn(wineCursor, "region_id");
				String region = getStringColumn(wineCursor, "region");
				float aromaRating = getFloatColumn(wineCursor, "aroma_rating");
				float tasteRating = getFloatColumn(wineCursor, "taste_rating");
				float aftertasteRating = getFloatColumn(wineCursor, "aftertaste_rating");
				float overallRating = getFloatColumn(wineCursor, "overall_rating");
				int flagId = getIntColumn(wineCursor, "flag_id");
				String flag = getStringColumn(wineCursor, "flag");
				String memo = getStringColumn(wineCursor, "memo");

				WineInfo wineInfo = new WineInfo();
				wineInfo.wineryName = wineryName;
				wineInfo.wineTypeId = wineTypeId;
				wineInfo.wineType = wineType;
				wineInfo.year = year;
				wineInfo.regionId = regionId;
				wineInfo.region = region;
				wineInfo.flagId = flagId;
				wineInfo.flag = flag;

				wineInfoLoaded(wineInfo);

				Cursor photosCursor = helper.getWinePhotosCursor(wineId);
				clearPhotosFromLayout();
				while (photosCursor.moveToNext()) {
					String filename = photosCursor.getString(0);
					addPhotoToLayout(filename, editable);
				}
				photosCursor.close();

				TextView nameView = (TextView) findViewById(R.id.name);
				if (name != null && name.length() > 0) {
					nameView.setText(name);
				}
				else {
					nameView.setText(R.string.title_noname_wine);
				}



				Cursor grapesCursor = helper.getWineGrapesCursor(wineId);
				StringBuffer grapesBuffer = new StringBuffer();
				while (grapesCursor.moveToNext()) {
					emptyWine = false;
					String grape = grapesCursor.getString(0);
					grapesBuffer.append(grape);
					grapesBuffer.append(", ");
				}
				grapesCursor.close();
				boolean haveGrapes = grapesBuffer.length() > 0;

				TextView grapesView = (TextView) findViewById(R.id.grapes);
				if (grapesBuffer.length() > 2) {
					grapesView.setText(grapesBuffer.substring(0, grapesBuffer.length() - 2));
				}
				else {
					grapesView.setText(R.string.label_none);
				}



				RatingBar aromaRatingBar = (RatingBar) findViewById(R.id.rating_aroma);
				aromaRatingBar.setRating(aromaRating);

				Cursor aromaImpressionsCursor = helper.getWineAromaImpressionsCursor(wineId);
				StringBuffer aromaImpressionsBuffer = new StringBuffer();
				while (aromaImpressionsCursor.moveToNext()) {
					emptyWine = false;
					String aromaImpression = aromaImpressionsCursor.getString(0);
					aromaImpressionsBuffer.append(aromaImpression);
					aromaImpressionsBuffer.append(", ");
				}
				aromaImpressionsCursor.close();
				boolean haveAromaImpressions = aromaImpressionsBuffer.length() > 0;
				boolean haveAroma = haveAromaImpressions || aromaRating > 0;

				TextView aromaImpressionsView = (TextView) findViewById(R.id.aroma);
				if (aromaImpressionsBuffer.length() > 2) {
					aromaImpressionsView.setText(aromaImpressionsBuffer.substring(0, aromaImpressionsBuffer.length() - 2));
				}
				else {
					aromaImpressionsView.setText(R.string.label_none);
				}



				RatingBar tasteRatingBar = (RatingBar) findViewById(R.id.rating_taste);
				tasteRatingBar.setRating(tasteRating);

				Cursor tasteImpressionsCursor = helper.getWineTasteImpressionsCursor(wineId);
				StringBuffer tasteImpressionsBuffer = new StringBuffer();
				while (tasteImpressionsCursor.moveToNext()) {
					emptyWine = false;
					String tasteImpression = tasteImpressionsCursor.getString(0);
					tasteImpressionsBuffer.append(tasteImpression);
					tasteImpressionsBuffer.append(", ");
				}
				tasteImpressionsCursor.close();
				boolean haveTasteImpressions = tasteImpressionsBuffer.length() > 0;
				boolean haveTaste = haveTasteImpressions || tasteRating > 0;

				TextView tasteImpressionsView = (TextView) findViewById(R.id.taste);
				if (tasteImpressionsBuffer.length() > 2) {
					tasteImpressionsView.setText(tasteImpressionsBuffer.substring(0, tasteImpressionsBuffer.length() - 2));
				}
				else {
					tasteImpressionsView.setText(R.string.label_none);
				}



				RatingBar aftertasteRatingBar = (RatingBar) findViewById(R.id.rating_aftertaste);
				aftertasteRatingBar.setRating(aftertasteRating);

				Cursor aftertasteImpressionsCursor = helper.getWineAftertasteImpressionsCursor(wineId);
				StringBuffer aftertasteImpressionsBuffer = new StringBuffer();
				while (aftertasteImpressionsCursor.moveToNext()) {
					emptyWine = false;
					String aftertasteImpression = aftertasteImpressionsCursor.getString(0);
					aftertasteImpressionsBuffer.append(aftertasteImpression);
					aftertasteImpressionsBuffer.append(", ");
				}
				aftertasteImpressionsCursor.close();
				boolean haveAftertasteImpressions = aftertasteImpressionsBuffer.length() > 0;
				boolean haveAftertaste = haveAftertasteImpressions || aftertasteRating > 0;

				TextView aftertasteImpressionsView = (TextView) findViewById(R.id.aftertaste);
				if (aftertasteImpressionsBuffer.length() > 2) {
					aftertasteImpressionsView.setText(aftertasteImpressionsBuffer.substring(0, aftertasteImpressionsBuffer.length() - 2));
				}
				else {
					aftertasteImpressionsView.setText(R.string.label_none);
				}



				RatingBar overallRatingBar = (RatingBar) findViewById(R.id.rating_overall);
				overallRatingBar.setRating(overallRating);
				boolean haveOverall = overallRating > 0;


				TextView memoView = (TextView) findViewById(R.id.memo);
				memoView.setText(memo);
				boolean haveMemo = false;
				if (memo != null && memo.length() > 0) {
					emptyWine  = false;
					haveMemo = true;
				}

				if (editable) {
					EditText nameEditView = (EditText) findViewById(R.id.name_edit);
					nameEditView.setText(name);
				}
				else {
					View grapesLabel = findViewById(R.id.label_grapes);
					if (haveGrapes) {
						if (haveGrapes) {
							grapesView.setVisibility(View.VISIBLE);
						}
						else {
							grapesView.setVisibility(View.GONE);
						}
						grapesLabel.setVisibility(View.VISIBLE);
					}
					else {
						grapesLabel.setVisibility(View.GONE);
					}

					View aromaLabel = findViewById(R.id.label_aroma);
					if (haveAroma) {
						if (haveAromaImpressions) {
							aromaImpressionsView.setVisibility(View.VISIBLE);
						}
						else {
							aromaImpressionsView.setVisibility(View.GONE);
						}
						aromaLabel.setVisibility(View.VISIBLE);
						aromaRatingBar.setVisibility(View.VISIBLE);
					}
					else {
						aromaLabel.setVisibility(View.GONE);
						aromaRatingBar.setVisibility(View.GONE);
					}

					View tasteLabel = findViewById(R.id.label_taste);
					if (haveTaste) {
						if (haveTasteImpressions) {
							tasteImpressionsView.setVisibility(View.VISIBLE);
						}
						else {
							tasteImpressionsView.setVisibility(View.GONE);
						}
						tasteLabel.setVisibility(View.VISIBLE);
						tasteRatingBar.setVisibility(View.VISIBLE);
					}
					else {
						tasteLabel.setVisibility(View.GONE);
						tasteRatingBar.setVisibility(View.GONE);
					}

					View aftertasteLabel = findViewById(R.id.label_aftertaste);
					if (haveAftertaste) {
						if (haveAftertasteImpressions) {
							aftertasteImpressionsView.setVisibility(View.VISIBLE);
						}
						else {
							aftertasteImpressionsView.setVisibility(View.GONE);
						}
						aftertasteLabel.setVisibility(View.VISIBLE);
						aftertasteRatingBar.setVisibility(View.VISIBLE);
					}
					else {
						aftertasteLabel.setVisibility(View.GONE);
						aftertasteRatingBar.setVisibility(View.GONE);
					}

					View overallLabel = findViewById(R.id.label_overall);
					if (haveOverall) {
						overallLabel.setVisibility(View.VISIBLE);
						overallRatingBar.setVisibility(View.VISIBLE);
					}
					else {
						overallLabel.setVisibility(View.GONE);
						overallRatingBar.setVisibility(View.GONE);
					}

					View memoLabel = findViewById(R.id.memo_label);
					if (haveMemo) {
						memoLabel.setVisibility(View.VISIBLE);
						memoView.setVisibility(View.VISIBLE);
					}
					else {
						memoLabel.setVisibility(View.GONE);
						memoView.setVisibility(View.GONE);
					}
				}
			}
			else {
				// TODO
			}
			wineCursor.close();
		}
	}

	private void removePhoto(String photoFilename) {
		if (removePhotoFromWine(photoFilename)) {
			WineFileManager.deletePhotos(photoFilename);
			removePhotoFromLayout(photoFilename);
		}
	}

	private void removePhotoFromLayout(String photoFilename) {
		LinearLayout layout = (LinearLayout) findViewById(R.id.photos);
		layout.removeView(layout.findViewWithTag(photoFilename));
	}

	private boolean removePhotoFromWine(String photoFilename) {
		return helper.removeWinePhoto(wineId, photoFilename);
	}

	class PhotoOnLongClickListener implements OnLongClickListener {
		private final String photoFilename;

		public PhotoOnLongClickListener(String photoFilename) {
			this.photoFilename = photoFilename;
		}

		@Override
		public boolean onLongClick(View arg0) {
			new AlertDialog.Builder(AbstractWineActivity.this)
			.setMessage(R.string.confirm_are_you_sure)
			.setCancelable(true)
			.setTitle(R.string.title_delete_photo)
			.setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					removePhoto(photoFilename);
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

	abstract void wineInfoLoaded(WineInfo wineInfo);
}