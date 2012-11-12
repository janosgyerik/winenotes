package com.winenotes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

public class EditWineActivity extends AbstractWineActivity {

	private static final String TAG = EditWineActivity.class.getSimpleName();

	private static final int RETURN_FROM_EDIT_AROMA = 1;
	private static final int RETURN_FROM_EDIT_TASTE = 2;
	private static final int RETURN_FROM_EDIT_AFTERTASTE = 3;
	private static final int RETURN_FROM_ADD_PHOTO = 4;

	private static final String PHOTO_INFO_FILE = "photoInfo.bin";

	private static final String OUT_DELETED = "DELETED";

	private EditText nameView;
	private EditText wineryNameView;
	//	private EditText priceView;
	private Spinner wineTypeView;
	private Spinner yearView;
	private Spinner buyFlagView;
	private AutoCompleteTextView regionView;
	private MultiAutoCompleteTextView grapeView;
	private RatingBar aromaRatingView;
	private RatingBar tasteRatingView;
	private RatingBar aftertasteRatingView;
	private RatingBar overallRatingView;
	private EditText memoView;

	private boolean newWine = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editwine);

		// for debugging:
		//		wineId = "42"; // rich
		//		wineId = "999"; // non-existent
		//		 wineId = "44"; // lean

		if (wineId == null) {
			PhotoInfo info = loadPhotoInfo();
			if (info != null) {
				if (helper.isExistingWineId(info.wineId)) {
					wineId = info.wineId;
					photoFile = info.photoFile;
				}
				deletePhotoInfo();
			}
		}

		wineId = "3";
		if (wineId == null) {
			wineId = helper.newWine();
			newWine = true;
		}

		nameView = (EditText) findViewById(R.id.name_edit);

		//    	private EditText wineryNameView;
		//    	private EditText priceView;

		// TODO calculate dynamically from preferences minimum and current year
		final int minYear = 1997;
		List<Integer> yearChoices = new ArrayList<Integer>();
		for (int i = minYear; i <= 2012; ++i) {
			yearChoices.add(i);
		}
		ArrayAdapter<Integer> yearListAdapter = new ArrayAdapter<Integer>(this,
				android.R.layout.simple_spinner_item, yearChoices.toArray(new Integer[0]));
		yearListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		yearView = (Spinner) findViewById(R.id.year);
		yearView.setAdapter(yearListAdapter);
		
		// TODO get list from database
		final String[] wineTypeChoices = new String[] {
				"Red", "White", "Rose", "Orange", "Gray", "Yellow", "Tawny", "Other",
		};
		ArrayAdapter<String> wineTypeListAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, wineTypeChoices);
		wineTypeListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		wineTypeView = (Spinner) findViewById(R.id.wine_type);
		wineTypeView.setAdapter(wineTypeListAdapter);

		// TODO get list from database
		final String[] buyFlagChoices = new String[] {
				"-", "Buy", "Maybe", "Never",
		};
		ArrayAdapter<String> buyFlagListAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, buyFlagChoices);
		buyFlagListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		buyFlagView = (Spinner) findViewById(R.id.buy_flag);
		buyFlagView.setAdapter(buyFlagListAdapter);

		regionView = (AutoCompleteTextView) findViewById(R.id.region);
		AutoCompleteHelper.configureAutoCompleteTextView(getBaseContext(), regionView,
				helper.getAromaImpressionsListCursor());
		
//		grapeView = (MultiAutoCompleteTextView) findViewById(R.id.grape);

		aromaRatingView = (RatingBar) findViewById(R.id.rating_aroma);
		tasteRatingView = (RatingBar) findViewById(R.id.rating_taste);
		aftertasteRatingView = (RatingBar) findViewById(R.id.rating_aftertaste);
		overallRatingView = (RatingBar) findViewById(R.id.rating_overall);

		memoView = (EditText) findViewById(R.id.memo);

		final Activity this_ = this;

		View editAromaImpressionsButton = findViewById(R.id.btn_edit_aroma);
		editAromaImpressionsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(this_, EditAromaImpressionsActivity.class);
				intent.putExtra(BaseColumns._ID, wineId);
				startActivityForResult(intent, RETURN_FROM_EDIT_AROMA);
			}
		});

		View editTasteImpressionsButton = findViewById(R.id.btn_edit_taste);
		editTasteImpressionsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(this_, EditTasteImpressionsActivity.class);
				intent.putExtra(BaseColumns._ID, wineId);
				startActivityForResult(intent, RETURN_FROM_EDIT_TASTE);
			}
		});

		View editAftertasteImpressionsButton = findViewById(R.id.btn_edit_aftertaste);
		editAftertasteImpressionsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(this_, EditAftertasteImpressionsActivity.class);
				intent.putExtra(BaseColumns._ID, wineId);
				startActivityForResult(intent, RETURN_FROM_EDIT_AFTERTASTE);
			}
		});

		View addPhotoButton = findViewById(R.id.btn_add_photo);
		addPhotoButton.setOnClickListener(new AddPhotoOnClickListener());

		Button save = (Button) findViewById(R.id.btn_save);
		save.setOnClickListener(new SaveWineOnClickListener());

		reloadAndRefreshWineDetails(true);
	}

	class SaveWineOnClickListener implements OnClickListener {
		@Override
		public void onClick(View view) {
			String name = capitalize(nameView.getText().toString());
			String wineryName = "";//TODO capitalize(wineryNameView.getText().toString());
			float price = 0;//TODO Float.parseFloat(priceView.getText().toString());
			int wineTypeId = 0;// TODO
			int year = 0;//TODO
			int regionId = 0;//TODO
			float aromaRating = aromaRatingView.getRating();
			float tasteRating = tasteRatingView.getRating();
			float aftertasteRating = aftertasteRatingView.getRating();
			float overallRating = overallRatingView.getRating();
			int buyFlagId = 0;//TODO
			String memo = capitalize(memoView.getText().toString());

			// TODO save the grapes: normalize, capitalize

			if (helper.saveWine(wineId, name, wineryName, price,
					wineTypeId, year, regionId,
					aromaRating, tasteRating, aftertasteRating, overallRating,
					buyFlagId, memo)) {
				int msgId = newWine ? R.string.msg_created_wine : R.string.msg_updated_wine;
				Toast.makeText(getApplicationContext(), msgId, Toast.LENGTH_SHORT).show();
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

	private void handleReturnFromEditAroma(Intent data) {
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

	private void handleReturnFromEditTaste(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			boolean isChanged = extras.getBoolean(AbstractEditWineItemsActivity.OUT_CHANGED);
			if (isChanged) {
				Log.i(TAG, "taste impressions have changed -> reloading details");
				reloadAndRefreshWineDetails(true);
				return;
			}
		}
		Log.i(TAG, "taste impressions have NOT changed -> NOT reloading details");
	}

	private void handleReturnFromEditAftertaste(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			boolean isChanged = extras.getBoolean(AbstractEditWineItemsActivity.OUT_CHANGED);
			if (isChanged) {
				Log.i(TAG, "aftertaste impressions have changed -> reloading details");
				reloadAndRefreshWineDetails(true);
				return;
			}
		}
		Log.i(TAG, "aftertaste impressions have NOT changed -> NOT reloading details");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(TAG, "onActivityResult");
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case RETURN_FROM_EDIT_AROMA:
				Log.i(TAG, "OK edit aroma impressions");
				handleReturnFromEditAroma(data);
				break;
			case RETURN_FROM_EDIT_TASTE:
				Log.i(TAG, "OK edit taste impressions");
				handleReturnFromEditTaste(data);
				break;
			case RETURN_FROM_EDIT_AFTERTASTE:
				Log.i(TAG, "OK edit aftertaste impressions");
				handleReturnFromEditAftertaste(data);
				break;
			case RETURN_FROM_ADD_PHOTO:
				Log.i(TAG, "OK take photo");
				deletePhotoInfo();
				handleSmallCameraPhoto(data);
				break;
			default:
				Log.i(TAG, "OK ???");
			}
		}
		else {
			switch (requestCode) {
			case RETURN_FROM_EDIT_AROMA:
				Log.i(TAG, "CANCEL edit aroma impressions");
				break;
			case RETURN_FROM_EDIT_TASTE:
				Log.i(TAG, "CANCEL edit taste impressions");
				break;
			case RETURN_FROM_ADD_PHOTO:
				Log.i(TAG, "CANCEL add photo");
				deletePhotoInfo();
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
		if (photoFile != null) {
			savePhotoInfo();
			Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
			startActivityForResult(takePictureIntent, RETURN_FROM_ADD_PHOTO);
		}
		else {
			new AlertDialog.Builder(this)
			.setTitle(R.string.title_unexpected_error)
			.setMessage(R.string.error_allocating_photo_file)
			.setCancelable(true)
			.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			})
			.show();
		}
	}

	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list =
				packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	private void addPhotoToWine(String photoFilename) {
		helper.addWinePhoto(wineId, photoFilename);
	}

	private void handleSmallCameraPhoto(Intent intent) {
		if (photoFile != null && photoFile.isFile()) {
			deletePhotoInfo();
			Log.d(TAG, "adding photo: " + photoFile);
			String filename = photoFile.getName();
			addPhotoToWine(filename);
			addPhotoToLayout(filename, true);
		}
		else {
			Log.e(TAG, "Something's wrong with the photo file: " + photoFile);
		}
	}

	private void savePhotoInfo() {
		try {
			FileOutputStream fos = openFileOutput(PHOTO_INFO_FILE, Context.MODE_PRIVATE);
			PhotoInfo info = new PhotoInfo();
			info.wineId = wineId;
			info.photoFile = photoFile;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(info);
			fos.write(baos.toByteArray());
			fos.close();
		}
		catch (Exception e) {
			Log.e(TAG, "Could not create photo info file!");
			e.printStackTrace();
		}
	}

	private void deletePhotoInfo() {
		deleteFile(PHOTO_INFO_FILE);
	}

	private PhotoInfo loadPhotoInfo() {
		try {
			FileInputStream fis = openFileInput(PHOTO_INFO_FILE);
			Log.w(TAG, "Loading photo info file, the app must have crashed earlier...");
			ObjectInputStream ois = new ObjectInputStream(fis);
			PhotoInfo info = (PhotoInfo)ois.readObject();
			Log.i(TAG, "read wineId = " + info.wineId);
			Log.i(TAG, "read photoFile = " + info.photoFile);
			return info;
		}
		catch (FileNotFoundException e) {
			// this is normal, normally there should be no photo info file
		}
		catch (Exception e) {
			Log.e(TAG, "Could not read photo file!");
			e.printStackTrace();
		}
		return null;
	}
}