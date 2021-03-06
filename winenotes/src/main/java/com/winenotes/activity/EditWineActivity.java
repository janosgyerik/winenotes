package com.winenotes.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.winenotes.R;
import com.winenotes.tools.StringTools;
import com.winenotes.tools.WineFileManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditWineActivity extends AbstractWineActivity {

    private static final String TAG = EditWineActivity.class.getSimpleName();

    private static final int RETURN_FROM_EDIT_GRAPES = 1;
    private static final int RETURN_FROM_EDIT_AROMA = 2;
    private static final int RETURN_FROM_EDIT_TASTE = 3;
    private static final int RETURN_FROM_EDIT_AFTERTASTE = 4;
    private static final int RETURN_FROM_ADD_PHOTO = 5;

    private static final String PHOTO_INFO_FILE = "photoInfo.bin";

    //private static final String OUT_DELETED = "DELETED";

    private EditText nameView;
    private EditText priceView;
    private Spinner currencyView;
    private Spinner wineTypeView;
    private Spinner yearView;
    private Spinner flagView;
    private AutoCompleteTextView regionView;
    private RatingBar aromaRatingView;
    private RatingBar tasteRatingView;
    private RatingBar aftertasteRatingView;
    private RatingBar overallRatingView;
    private EditText memoView;

    private boolean newWine = false;

    private final ForeignKey[] YEAR_CHOICES = new ForeignKey[30];

    private ForeignKey[] WINETYPE_CHOICES;

    private ForeignKey[] FLAG_CHOICES;


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

        if (wineId == null) {
            wineId = helper.newWine();
            newWine = true;
        }

        nameView = (EditText) findViewById(R.id.name_edit);

        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < YEAR_CHOICES.length; ++i) {
            int year = thisYear - i;
            YEAR_CHOICES[i] = new ForeignKey(year, String.valueOf(year));
        }

        ArrayAdapter<ForeignKey> yearListAdapter = new ArrayAdapter<ForeignKey>(this,
                android.R.layout.simple_spinner_item, YEAR_CHOICES);
        yearListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearView = (Spinner) findViewById(R.id.year);
        yearView.setAdapter(yearListAdapter);

        Cursor wineTypeListCursor = helper.getWineTypeListCursor();
        List<ForeignKey> wineTypeChoices = new ArrayList<ForeignKey>();
        while (wineTypeListCursor.moveToNext()) {
            int refId = wineTypeListCursor.getInt(0);
            String name = wineTypeListCursor.getString(1);
            wineTypeChoices.add(new ForeignKey(refId, name));
        }
        wineTypeListCursor.close();
        WINETYPE_CHOICES = wineTypeChoices.toArray(new ForeignKey[wineTypeChoices.size()]);

        ArrayAdapter<ForeignKey> wineTypeListAdapter = new ArrayAdapter<ForeignKey>(this,
                android.R.layout.simple_spinner_item, WINETYPE_CHOICES);
        wineTypeListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wineTypeView = (Spinner) findViewById(R.id.wine_type);
        wineTypeView.setAdapter(wineTypeListAdapter);

        Cursor flagListCursor = helper.getFlagListCursor();
        List<ForeignKey> flagChoices = new ArrayList<ForeignKey>();
        while (flagListCursor.moveToNext()) {
            int refId = flagListCursor.getInt(0);
            String name = flagListCursor.getString(1);
            flagChoices.add(new ForeignKey(refId, name));
        }
        flagListCursor.close();
        FLAG_CHOICES = flagChoices.toArray(new ForeignKey[flagChoices.size()]);

        ArrayAdapter<ForeignKey> flagListAdapter = new ArrayAdapter<ForeignKey>(this,
                android.R.layout.simple_spinner_item, FLAG_CHOICES);
        flagListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flagView = (Spinner) findViewById(R.id.flag);
        flagView.setAdapter(flagListAdapter);

        regionView = (AutoCompleteTextView) findViewById(R.id.region);
        AutoCompleteHelper.configureAutoCompleteTextView(getBaseContext(), regionView,
                helper.getRegionListCursor(), "name", "ascii_name");

        priceView = (EditText) findViewById(R.id.price);

        ArrayAdapter<Currency> currencyListAdapter = new CurrencyAdapter(this);
        currencyView = (Spinner) findViewById(R.id.currency);
        currencyView.setAdapter(currencyListAdapter);

        aromaRatingView = (RatingBar) findViewById(R.id.rating_aroma);
        tasteRatingView = (RatingBar) findViewById(R.id.rating_taste);
        aftertasteRatingView = (RatingBar) findViewById(R.id.rating_aftertaste);
        overallRatingView = (RatingBar) findViewById(R.id.rating_overall);

        memoView = (EditText) findViewById(R.id.memo);

        final Activity this_ = this;

        View editGrapesButton = findViewById(R.id.btn_edit_grapes);
        editGrapesButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(this_, EditGrapesActivity.class);
                intent.putExtra(BaseColumns._ID, wineId);
                startActivityForResult(intent, RETURN_FROM_EDIT_GRAPES);
            }
        });

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

        OnClickListener saveWineOnClickListener =
                new SaveWineOnClickListener();
        ImageButton save = (ImageButton) findViewById(R.id.btn_save);
        save.setOnClickListener(saveWineOnClickListener);
        Button save2 = (Button) findViewById(R.id.btn_save2);
        save2.setOnClickListener(saveWineOnClickListener);

        loadWineInfo(true);
    }

    class SaveWineOnClickListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            String name = StringTools.toTitleCaseFrench(nameView.getText().toString());
            int wineryId = 0;  //TODO capitalize(wineryNameView.getText().toString());
            float price = inputToFloat(priceView.getText().toString());
            Currency currency = (Currency) currencyView.getSelectedItem();
            int wineTypeId = ((ForeignKey) wineTypeView.getSelectedItem()).refId;
            int year = ((ForeignKey) yearView.getSelectedItem()).refId;
            String region = StringTools.toTitleCaseFull(regionView.getText().toString());
            String regionId = region == null ? null : helper.getOrCreateRegion(region);
            float aromaRating = aromaRatingView.getRating();
            float tasteRating = tasteRatingView.getRating();
            float aftertasteRating = aftertasteRatingView.getRating();
            float overallRating = overallRatingView.getRating();
            int flagId = ((ForeignKey) flagView.getSelectedItem()).refId;
            String memo = StringTools.capitalize(memoView.getText().toString());

            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(EditWineActivity.this);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(getString(R.string.key_default_currency), currency.key);
            editor.commit();
            Log.d(TAG, "save key = " + getString(R.string.key_default_currency));
            Log.d(TAG, "save value = " + currency.key);

            StringBuilder builder = new StringBuilder();
            if (wineTypeId > 0) {
                builder.append(((ForeignKey) wineTypeView.getSelectedItem()).value);
                builder.append(", ");
            }
            if (year > 0) {
                builder.append(year).append(", ");
            }
            if (price > 0) {
                String priceStr = currency.format(price);
                builder.append(priceStr).append(", ");
            }
            if (regionId != null) {
                builder.append(region);
                builder.append(", ");
            }
            Cursor grapesCursor = helper.getWineGrapesCursor(wineId);
            while (grapesCursor.moveToNext()) {
                builder.append(grapesCursor.getString(0));
                builder.append(", ");
            }
            String listingText = builder.length() > 2 ? builder.substring(0, builder.length() - 2) : "";

            if (helper.saveWine(wineId, name, listingText,
                    wineryId, price, currency.key,
                    wineTypeId, year, regionId,
                    aromaRating, tasteRating, aftertasteRating, overallRating,
                    flagId, memo)) {
                if (newWine) {
                    Toast.makeText(getApplicationContext(), R.string.msg_created_wine, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditWineActivity.this, ViewWineActivity.class);
                    intent.putExtra(BaseColumns._ID, wineId);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.msg_updated_wine, Toast.LENGTH_SHORT).show();
                }
                finish();
            } else {
                Toast.makeText(getApplicationContext(), R.string.error_update_wine, Toast.LENGTH_SHORT).show();
            }
        }
    }

    static float inputToFloat(String input) {
        try {
            return Float.parseFloat(input.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private void handleReturnFromEditGrapes(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            boolean isChanged = extras.getBoolean(EditTagsActivity.OUT_CHANGED);
            if (isChanged) {
                Log.i(TAG, "grapes have changed -> reloading");
                updateGrapes(true);
                return;
            }
        }
        Log.i(TAG, "grapes have NOT changed -> NOT reloading");
    }

    private void handleReturnFromEditAroma(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            boolean isChanged = extras.getBoolean(EditTagsActivity.OUT_CHANGED);
            if (isChanged) {
                Log.i(TAG, "aroma impressions have changed -> reloading");
                updateAroma(true);
                return;
            }
        }
        Log.i(TAG, "aroma impressions have NOT changed -> NOT reloading");
    }

    private void handleReturnFromEditTaste(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            boolean isChanged = extras.getBoolean(EditTagsActivity.OUT_CHANGED);
            if (isChanged) {
                Log.i(TAG, "taste impressions have changed -> reloading");
                updateTaste(true);
                return;
            }
        }
        Log.i(TAG, "taste impressions have NOT changed -> NOT reloading");
    }

    private void handleReturnFromEditAftertaste(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            boolean isChanged = extras.getBoolean(EditTagsActivity.OUT_CHANGED);
            if (isChanged) {
                Log.i(TAG, "aftertaste impressions have changed -> reloading");
                updateAftertaste(true);
                return;
            }
        }
        Log.i(TAG, "aftertaste impressions have NOT changed -> NOT reloading");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult");
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RETURN_FROM_EDIT_GRAPES:
                    Log.i(TAG, "OK edit grapes");
                    handleReturnFromEditGrapes(data);
                    break;
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
                    handleSmallCameraPhoto();
                    break;
                default:
                    Log.i(TAG, "OK ???");
            }
        } else {
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
        } else {
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

    private void addPhotoToWine(String photoFilename) {
        helper.addWinePhoto(wineId, photoFilename);
    }

    private void handleSmallCameraPhoto() {
        if (photoFile != null && photoFile.isFile()) {
            deletePhotoInfo();
            Log.d(TAG, "adding photo: " + photoFile);
            String filename = photoFile.getName();
            addPhotoToWine(filename);
            addPhotoToLayout(filename, true);
        } else {
            Log.e(TAG, "Something's wrong with the photo file: " + photoFile);
        }
    }

    private void savePhotoInfo() {
        try {
            FileOutputStream fos = openFileOutput(PHOTO_INFO_FILE, MODE_PRIVATE);
            PhotoInfo info = new PhotoInfo();
            info.wineId = wineId;
            info.photoFile = photoFile;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(info);
            fos.write(baos.toByteArray());
            fos.close();
        } catch (Exception e) {
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
            PhotoInfo info = (PhotoInfo) ois.readObject();
            Log.i(TAG, "read wineId = " + info.wineId);
            Log.i(TAG, "read photoFile = " + info.photoFile);
            return info;
        } catch (FileNotFoundException e) {
            // this is normal, normally there should be no photo info file
        } catch (Exception e) {
            Log.e(TAG, "Could not read photo file!");
            e.printStackTrace();
        }
        return null;
    }

    private void setSpinnerValue(Spinner spinner, Object value, Object[] choices) {
        int position = 0;
        for (Object choice : choices) {
            if (choice.equals(value)) {
                spinner.setSelection(position);
                return;
            }
            ++position;
        }
    }

    @Override
    void wineInfoLoaded(WineInfo wineInfo) {
        setSpinnerValue(yearView, wineInfo.year, YEAR_CHOICES);
        setSpinnerValue(wineTypeView, wineInfo.wineTypeId, WINETYPE_CHOICES);
        setSpinnerValue(flagView, wineInfo.flagId, FLAG_CHOICES);
        regionView.setText(wineInfo.region);
        priceView.setText(wineInfo.currency.stringValue(wineInfo.price));
        if (newWine) {
            String defaultCurrencyKey = PreferenceManager.getDefaultSharedPreferences(this).getString(
                    getString(R.string.key_default_currency), Currency.DEFAULT_CURRENCY_KEY);
            Currency defaultCurrency = Currency.fromKey(defaultCurrencyKey);
            setSpinnerValue(currencyView, defaultCurrency, CurrencyAdapter.CURRENCY_CHOICES);
        } else {
            setSpinnerValue(currencyView, wineInfo.currency, CurrencyAdapter.CURRENCY_CHOICES);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown");
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if (newWine) {
                helper.deleteWine(wineId);
                finish();
            }
        }

        return super.onKeyDown(keyCode, event);
    }
}