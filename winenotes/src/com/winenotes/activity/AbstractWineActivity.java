package com.winenotes.activity;

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

import com.winenotes.R;
import com.winenotes.db.WineNotesSQLiteOpenHelper;
import com.winenotes.tools.WineFileManager;

public abstract class AbstractWineActivity extends Activity {

    private static final String TAG = AbstractWineActivity.class.getSimpleName();

    protected WineNotesSQLiteOpenHelper helper;
    protected String wineId;

    RatingBar aromaRatingBar;
    RatingBar tasteRatingBar;
    RatingBar aftertasteRatingBar;
    RatingBar overallRatingBar;

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
        return cursor.getString(columnIndex);
    }

    private float getFloatColumn(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getFloat(columnIndex);
    }

    private int getIntColumn(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getInt(columnIndex);
    }

    private Currency getCurrencyColumn(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return Currency.fromKey(cursor.getString(columnIndex));
    }

    private WineInfo wineInfo = new WineInfo();

    protected void loadWineInfo(boolean editable) {
        if (wineId != null) {
            Cursor wineCursor = helper.getWineDetailsCursor(wineId);

            if (wineCursor.moveToNext()) {

                wineInfo = new WineInfo();
                wineInfo.name = getStringColumn(wineCursor, "name");
                wineInfo.listingText = getStringColumn(wineCursor, "listing_text");
                wineInfo.wineryId = getIntColumn(wineCursor, "winery_id");
                wineInfo.wineryName = getStringColumn(wineCursor, "winery_name");
                wineInfo.price = getFloatColumn(wineCursor, "price");
                wineInfo.currency = getCurrencyColumn(wineCursor, "currency");
                wineInfo.wineTypeId = getIntColumn(wineCursor, "winetype_id");
                wineInfo.wineType = getStringColumn(wineCursor, "winetype");
                wineInfo.year = getIntColumn(wineCursor, "year");
                wineInfo.regionId = getIntColumn(wineCursor, "region_id");
                wineInfo.region = getStringColumn(wineCursor, "region");
                wineInfo.aromaRating = getFloatColumn(wineCursor, "aroma_rating");
                wineInfo.tasteRating = getFloatColumn(wineCursor, "taste_rating");
                wineInfo.aftertasteRating = getFloatColumn(wineCursor, "aftertaste_rating");
                wineInfo.overallRating = getFloatColumn(wineCursor, "overall_rating");
                wineInfo.flagId = getIntColumn(wineCursor, "flag_id");
                wineInfo.flag = getStringColumn(wineCursor, "flag");
                wineInfo.memo = getStringColumn(wineCursor, "memo");

                wineInfoLoaded(wineInfo);

                Cursor photosCursor = helper.getWinePhotosCursor(wineId);
                clearPhotosFromLayout();
                while (photosCursor.moveToNext()) {
                    String filename = photosCursor.getString(0);
                    addPhotoToLayout(filename, editable);
                }
                photosCursor.close();

                TextView nameView = (TextView) findViewById(R.id.name);
                if (wineInfo.name != null && wineInfo.name.length() > 0) {
                    nameView.setText(wineInfo.name);
                } else {
                    nameView.setText(R.string.title_noname_wine);
                }

                updateGrapes(editable);

                aromaRatingBar = (RatingBar) findViewById(R.id.rating_aroma);
                aromaRatingBar.setRating(wineInfo.aromaRating);
                updateAroma(editable);

                tasteRatingBar = (RatingBar) findViewById(R.id.rating_taste);
                tasteRatingBar.setRating(wineInfo.tasteRating);
                updateTaste(editable);

                aftertasteRatingBar = (RatingBar) findViewById(R.id.rating_aftertaste);
                aftertasteRatingBar.setRating(wineInfo.aftertasteRating);
                updateAftertaste(editable);

                overallRatingBar = (RatingBar) findViewById(R.id.rating_overall);
                overallRatingBar.setRating(wineInfo.overallRating);
                boolean haveOverall = wineInfo.overallRating > 0;

                TextView memoView = (TextView) findViewById(R.id.memo);
                memoView.setText(wineInfo.memo);
                boolean haveMemo = false;
                if (wineInfo.memo != null && wineInfo.memo.length() > 0) {
                    haveMemo = true;
                }

                if (editable) {
                    EditText nameEditView = (EditText) findViewById(R.id.name_edit);
                    nameEditView.setText(wineInfo.name);
                } else {
                    aromaRatingBar.setEnabled(false);
                    tasteRatingBar.setEnabled(false);
                    aftertasteRatingBar.setEnabled(false);
                    overallRatingBar.setEnabled(false);

                    View overallLabel = findViewById(R.id.label_overall);
                    if (haveOverall) {
                        overallLabel.setVisibility(View.VISIBLE);
                        overallRatingBar.setVisibility(View.VISIBLE);
                    } else {
                        overallLabel.setVisibility(View.GONE);
                        overallRatingBar.setVisibility(View.GONE);
                    }

                    View memoLabel = findViewById(R.id.memo_label);
                    if (haveMemo) {
                        memoLabel.setVisibility(View.VISIBLE);
                        memoView.setVisibility(View.VISIBLE);
                    } else {
                        memoLabel.setVisibility(View.GONE);
                        memoView.setVisibility(View.GONE);
                    }
                }
            } else {
                // TODO
            }
            wineCursor.close();
        }
    }


    protected void updateGrapes(boolean editable) {
        if (editable) {
            Cursor grapesCursor = helper.getWineGrapesCursor(wineId);
            StringBuilder builder = new StringBuilder();
            while (grapesCursor.moveToNext()) {
                String grape = grapesCursor.getString(0);
                builder.append(grape);
                builder.append(", ");
            }
            grapesCursor.close();

            TextView grapesView = (TextView) findViewById(R.id.grapes);
            if (builder.length() > 2) {
                grapesView.setText(builder.substring(0, builder.length() - 2));
            } else {
                grapesView.setText(R.string.label_none);
            }
        }
    }

    protected void updateAroma(boolean editable) {
        Cursor aromaImpressionsCursor = helper.getWineAromaImpressionsCursor(wineId);
        StringBuilder builder = new StringBuilder();
        while (aromaImpressionsCursor.moveToNext()) {
            String aromaImpression = aromaImpressionsCursor.getString(0);
            builder.append(aromaImpression);
            builder.append(", ");
        }
        aromaImpressionsCursor.close();
        boolean haveAromaImpressions = builder.length() > 0;
        boolean haveAroma = haveAromaImpressions || wineInfo.aromaRating > 0;

        TextView aromaImpressionsView = (TextView) findViewById(R.id.aroma);
        if (builder.length() > 2) {
            aromaImpressionsView.setText(builder.substring(0, builder.length() - 2));
        } else {
            aromaImpressionsView.setText(R.string.label_none);
        }

        if (!editable) {
            View aromaLabel = findViewById(R.id.label_aroma);
            if (haveAroma) {
                if (haveAromaImpressions) {
                    aromaImpressionsView.setVisibility(View.VISIBLE);
                } else {
                    aromaImpressionsView.setVisibility(View.GONE);
                }
                aromaLabel.setVisibility(View.VISIBLE);
                aromaRatingBar.setVisibility(View.VISIBLE);
            } else {
                aromaLabel.setVisibility(View.GONE);
                aromaRatingBar.setVisibility(View.GONE);
                aromaImpressionsView.setVisibility(View.GONE);
            }
        }
    }

    protected void updateTaste(boolean editable) {
        Cursor tasteImpressionsCursor = helper.getWineTasteImpressionsCursor(wineId);
        StringBuilder builder = new StringBuilder();
        while (tasteImpressionsCursor.moveToNext()) {
            String tasteImpression = tasteImpressionsCursor.getString(0);
            builder.append(tasteImpression);
            builder.append(", ");
        }
        tasteImpressionsCursor.close();
        boolean haveTasteImpressions = builder.length() > 0;
        boolean haveTaste = haveTasteImpressions || wineInfo.tasteRating > 0;

        TextView tasteImpressionsView = (TextView) findViewById(R.id.taste);
        if (builder.length() > 2) {
            tasteImpressionsView.setText(builder.substring(0, builder.length() - 2));
        } else {
            tasteImpressionsView.setText(R.string.label_none);
        }

        if (!editable) {
            View tasteLabel = findViewById(R.id.label_taste);
            if (haveTaste) {
                if (haveTasteImpressions) {
                    tasteImpressionsView.setVisibility(View.VISIBLE);
                } else {
                    tasteImpressionsView.setVisibility(View.GONE);
                }
                tasteLabel.setVisibility(View.VISIBLE);
                tasteRatingBar.setVisibility(View.VISIBLE);
            } else {
                tasteLabel.setVisibility(View.GONE);
                tasteRatingBar.setVisibility(View.GONE);
                tasteImpressionsView.setVisibility(View.GONE);
            }
        }
    }

    protected void updateAftertaste(boolean editable) {
        Cursor aftertasteImpressionsCursor = helper.getWineAftertasteImpressionsCursor(wineId);
        StringBuilder builder = new StringBuilder();
        while (aftertasteImpressionsCursor.moveToNext()) {
            String aftertasteImpression = aftertasteImpressionsCursor.getString(0);
            builder.append(aftertasteImpression);
            builder.append(", ");
        }
        aftertasteImpressionsCursor.close();
        boolean haveAftertasteImpressions = builder.length() > 0;
        boolean haveAftertaste = haveAftertasteImpressions || wineInfo.aftertasteRating > 0;

        TextView aftertasteImpressionsView = (TextView) findViewById(R.id.aftertaste);
        if (builder.length() > 2) {
            aftertasteImpressionsView.setText(builder.substring(0, builder.length() - 2));
        } else {
            aftertasteImpressionsView.setText(R.string.label_none);
        }

        if (!editable) {
            View aftertasteLabel = findViewById(R.id.label_aftertaste);
            if (haveAftertaste) {
                if (haveAftertasteImpressions) {
                    aftertasteImpressionsView.setVisibility(View.VISIBLE);
                } else {
                    aftertasteImpressionsView.setVisibility(View.GONE);
                }
                aftertasteLabel.setVisibility(View.VISIBLE);
                aftertasteRatingBar.setVisibility(View.VISIBLE);
            } else {
                aftertasteLabel.setVisibility(View.GONE);
                aftertasteRatingBar.setVisibility(View.GONE);
                aftertasteImpressionsView.setVisibility(View.GONE);
            }
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
        if (layout != null) {
            View photo = layout.findViewWithTag(photoFilename);
            if (photo != null) {
                layout.removeView(photo);
            }
        }
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