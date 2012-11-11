package com.winenotes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import android.util.SparseArray;

public class WineNotesSQLiteOpenHelper extends SQLiteOpenHelper {

	// Debugging
	private static final String TAG = "WineNotesSQLiteOpenHelper";

	private static final String DATABASE_NAME = "sqlite3.db";
	private static final int DATABASE_VERSION = 1;

	// lists
	private static final String WINES_TABLE_NAME = "main_wine";
	private static final String AROMA_IMPRESSIONS_TABLE_NAME = "main_aromaimpression";
	private static final String TASTE_IMPRESSIONS_TABLE_NAME = "main_tasteimpression";
	private static final String AFTERTASTE_IMPRESSIONS_TABLE_NAME = "main_aftertasteimpression";
	private static final String COLORS_TABLE_NAME = "main_color";
	private static final String REGIONS_TABLE_NAME = "main_region";
	private static final String BUYFLAGS_TABLE_NAME = "main_buyflag";
	private static final String GRAPES_TABLE_NAME = "main_grape";
	private static final String TAGS_TABLE_NAME = "main_tag";

	// relationships
	private static final String WINE_GRAPES_TABLE_NAME = "main_winegrape";
	private static final String WINE_AROMA_IMPRESSIONS_TABLE_NAME = "main_winearomaimpression";
	private static final String WINE_TASTE_IMPRESSIONS_TABLE_NAME = "main_winetasteimpression";
	private static final String WINE_AFTERTASTE_IMPRESSIONS_TABLE_NAME = "main_wineaftertasteimpression";
	private static final String WINE_TAGS_TABLE_NAME = "main_winetag";
	private static final String WINE_PHOTOS_TABLE_NAME = "main_winephoto";

	private List<String> sqlCreateStatements;
	private SparseArray<List<String>> sqlUpgradeStatements;

	WineNotesSQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		//context.deleteDatabase(DATABASE_NAME);

		sqlCreateStatements = getSqlStatements(context, "sql_create.sql");
		sqlUpgradeStatements = new SparseArray<List<String>>();
//		sqlUpgradeStatements.put(2, getSqlStatements(context, "sql_upgrade2.sql"));
	}

	private List<String> getSqlStatements(Context context, String assetName) {
		List<String> statements;
		try {
			statements = readSqlStatements(context, assetName);
		} catch (IOException e) {
			statements = Collections.emptyList();
			e.printStackTrace();
		}
		return statements;
	}

	static List<String> readSqlStatements(Context context, String assetName) throws IOException {
		List<String> statements = new ArrayList<String>();
		InputStream stream = context.getAssets().open(assetName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String line;
		StringBuilder builder = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			builder.append(line);
			if (line.trim().equals(";")) {
				statements.add(builder.toString());
				builder = new StringBuilder();
			}
		}
		return statements;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for (String sql : sqlCreateStatements) {
			db.execSQL(sql);
			// TODO check success
		}
		// TODO replace with import from an export file
		// dummy wines
//		db.execSQL("insert into main_wine (name, display_name) values ('Steak', 'Steak');");

		// initial ingredients collection
//		db.execSQL("insert into main_ingredient (name) values ('Avocado');");

		// initial tags collection
//		db.execSQL("insert into main_tag (name) values ('Main');");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (int i = oldVersion; i < newVersion; ++i) {
			upgradeToVersion(db, i + 1);
		}
	}

	private void upgradeToVersion(SQLiteDatabase db, int version) {
		Log.d(TAG, "upgrade to version " + version);
		for (String sql : sqlUpgradeStatements.get(version)) {
			db.execSQL(sql);
		}
	}

	/**
	 * Returns new wineId on success or else null
	 * @return
	 */
	public String newWine() {
		ContentValues values = new ContentValues();
		long createdDt = new Date().getTime();
		values.put("created_dt", createdDt);
		values.put("updated_dt", createdDt);
		long ret = getWritableDatabase().insert(WINES_TABLE_NAME, null, values);
		Log.d(TAG, "insert wine ret = " + ret);
		if (ret >= 0) {
			String wineId = String.valueOf(ret);
			return wineId;
		}
		else {
			return null;
		}
	}

	public boolean saveWine(String wineId,
			String name,
			String displayName) {
		ContentValues values = new ContentValues();
		values.put("name", name);
		long updatedDt = new Date().getTime();
		values.put("updated_dt", updatedDt);

		values.put("display_name", displayName);

		// display_image
		//TODO

		int ret = getWritableDatabase().update(WINES_TABLE_NAME, values, 
				BaseColumns._ID + " = ?", new String[]{ wineId });
		Log.d(TAG, String.format("update wine %s -> %s <- %s", wineId, name, ret));
		return ret == 1;
	}

	public boolean deleteWine(String wineId) {
		getWritableDatabase().delete(WINE_GRAPES_TABLE_NAME, "wine_id = ?", new String[]{ wineId });
		getWritableDatabase().delete(WINE_AROMA_IMPRESSIONS_TABLE_NAME, "wine_id = ?", new String[]{ wineId });
		getWritableDatabase().delete(WINE_TASTE_IMPRESSIONS_TABLE_NAME, "wine_id = ?", new String[]{ wineId });
		getWritableDatabase().delete(WINE_AFTERTASTE_IMPRESSIONS_TABLE_NAME, "wine_id = ?", new String[]{ wineId });
		getWritableDatabase().delete(WINE_TAGS_TABLE_NAME, "wine_id = ?", new String[]{ wineId });
		getWritableDatabase().delete(WINE_PHOTOS_TABLE_NAME, "wine_id = ?", new String[]{ wineId });
		getWritableDatabase().delete(WINES_TABLE_NAME, "_id = ?", new String[]{ wineId });
		Log.d(TAG, "deleted wine " + wineId);
		// TODO error handling
		return true;
	}

	public String getOrCreateTag(String name) {
		String tagId = getTagIdByName(name);
		if (tagId == null) {
			tagId = newTag(name);
		}
		return tagId;
	}

	/**
	 * Returns tagId or null if tag does not exist.
	 * @param name
	 * @return
	 */
	public String getTagIdByName(String name) {
		String tagId = null;
		Cursor cursor = getReadableDatabase().query(
				TAGS_TABLE_NAME, 
				new String[] { BaseColumns._ID }, 
				"name = ?", 
				new String[] { name }, 
				null, null, null, "1");
		if (cursor.moveToNext()) {
			tagId = cursor.getString(0);
			Log.d(TAG, String.format("got tag: %s -> %s", tagId, name));
		}
		cursor.close();
		return tagId;
	}

	/**
	 * Returns new tagId on success or else null
	 * @return
	 */
	private String newTag(String name) {
		ContentValues values = new ContentValues();
		values.put("name", name);
		long createdDt = new Date().getTime();
		values.put("created_dt", createdDt);
		values.put("updated_dt", createdDt);
		long ret = getWritableDatabase().insert(TAGS_TABLE_NAME, null, values);
		Log.d(TAG, String.format("insert tag: %s <- %s", name, ret));
		if (ret >= 0) {
			String tagId = String.valueOf(ret);
			return tagId;
		}
		else {
			return null;
		}
	}

	public boolean addWineTag(String wineId, String tagId) {
		ContentValues values = new ContentValues();
		values.put("wine_id", wineId);
		values.put("tag_id", tagId);
		long createdDt = new Date().getTime();
		values.put("created_dt", createdDt);
		values.put("updated_dt", createdDt);
		long ret = getWritableDatabase().insert(WINE_TAGS_TABLE_NAME, null, values);
		Log.d(TAG, String.format("insert wine tag: %s, %s <- %s",
				wineId, tagId, ret));
		return ret >= 0;
	}

	public boolean removeWineTag(String wineId, String tagId) {
		int ret = getWritableDatabase().delete(WINE_TAGS_TABLE_NAME,
				"wine_id = ? AND tag_id = ?",
				new String[]{ wineId, tagId });
		Log.d(TAG, String.format("delete wine tag: %s, %s <- %s",
				wineId, tagId, ret));
		return ret > 0;
	}

	public String getOrCreateAromaImpression(String name) {
		String aromaImpressionId = getAromaImpressionIdByName(name);
		if (aromaImpressionId == null) {
			aromaImpressionId = newAromaImpression(name);
		}
		return aromaImpressionId;
	}

	/**
	 * Returns aromaImpressionId or null if aroma impression does not exist.
	 * @param name
	 * @return
	 */
	public String getAromaImpressionIdByName(String name) {
		String aromaImpressionId = null;
		Cursor cursor = getReadableDatabase().query(
				AROMA_IMPRESSIONS_TABLE_NAME, 
				new String[] { BaseColumns._ID }, 
				"name = ?", 
				new String[] { name }, 
				null, null, null, "1");
		if (cursor.moveToNext()) {
			aromaImpressionId = cursor.getString(0);
			Log.d(TAG, String.format("got aroma impression: %s -> %s", aromaImpressionId, name));
		}
		cursor.close();
		return aromaImpressionId;
	}

	/**
	 * Returns new aromaImpressionId on success or else null
	 * @return
	 */
	private String newAromaImpression(String name) {
		ContentValues values = new ContentValues();
		values.put("name", name);
		long createdDt = new Date().getTime();
		values.put("created_dt", createdDt);
		values.put("updated_dt", createdDt);
		long ret = getWritableDatabase().insert(AROMA_IMPRESSIONS_TABLE_NAME, null, values);
		Log.d(TAG, String.format("insert aroma impression: %s <- %s", name, ret));
		if (ret >= 0) {
			String aromaImpressionId = String.valueOf(ret);
			return aromaImpressionId;
		}
		else {
			return null;
		}
	}

	public boolean addWineAromaImpression(String wineId, String aromaImpressionId) {
		ContentValues values = new ContentValues();
		values.put("wine_id", wineId);
		values.put("aroma_impression_id", aromaImpressionId);
		long createdDt = new Date().getTime();
		values.put("created_dt", createdDt);
		values.put("updated_dt", createdDt);
		long ret = getWritableDatabase().insert(WINE_AROMA_IMPRESSIONS_TABLE_NAME, null, values);
		Log.d(TAG, String.format("insert wine aroma impression: %s, %s <- %s",
				wineId, aromaImpressionId, ret));
		return ret >= 0;
	}

	public boolean removeWineAromaImpression(String wineId, String aromaImpressionId) {
		int ret = getWritableDatabase().delete(WINE_AROMA_IMPRESSIONS_TABLE_NAME,
				"wine_id = ? AND aroma_impression_id = ?",
				new String[]{ wineId, aromaImpressionId });
		Log.d(TAG, String.format("delete wine aroma impression: %s, %s <- %s",
				wineId, aromaImpressionId, ret));
		return ret > 0;
	}

	public boolean addWinePhoto(String wineId, String filename) {
		ContentValues values = new ContentValues();
		values.put("wine_id", wineId);
		values.put("filename", filename);
		long createdDt = new Date().getTime();
		values.put("created_dt", createdDt);
		values.put("updated_dt", createdDt);
		long ret = getWritableDatabase().insert(WINE_PHOTOS_TABLE_NAME, null, values);
		Log.d(TAG, String.format("insert wine photo: %s, %s <- %s",
				wineId, filename, ret));
		return ret >= 0;
	}

	public boolean removeWinePhoto(String wineId, String filename) {
		int ret = getWritableDatabase().delete(WINE_PHOTOS_TABLE_NAME,
				"wine_id = ? AND filename = ?",
				new String[]{ wineId, filename });
		Log.d(TAG, String.format("delete wine photo: %s, %s <- %s",
				wineId, filename, ret));
		return ret > 0;
	}

	public Cursor getWineListCursor() {
		Log.d(TAG, "get all wines");
		Cursor cursor = getReadableDatabase().rawQuery(
				"select _id, ifnull(nullif(name, ''), '(wine)') name, summary, listing_text"
						+ " from main_wine"
						+ " order by updated_dt desc, name",
						null);
		return cursor;
	}

	public Cursor getAromaImpressionsListCursor() {
		Log.d(TAG, "get all aroma impressions");
		Cursor cursor = getReadableDatabase().query(
				AROMA_IMPRESSIONS_TABLE_NAME, 
				new String[]{ BaseColumns._ID, "name", }, 
				null, null, null, null, "name");
		return cursor;
	}

	public Cursor getTagsListCursor() {
		Log.d(TAG, "get all tags");
		Cursor cursor = getReadableDatabase().query(
				TAGS_TABLE_NAME, 
				new String[]{ BaseColumns._ID, "name", }, 
				null, null, null, null, "name");
		return cursor;
	}

	public Cursor getWineDetailsCursor(String wineId) {
		Log.d(TAG, "get wine " + wineId);
		Cursor cursor = getReadableDatabase().query(
				WINES_TABLE_NAME, new String[]{ "name", }, 
				BaseColumns._ID + " = ?", new String[]{ wineId },
				null, null, null);
		return cursor;
	}

	public Cursor getWineAromaImpressionsCursor(String wineId) {
		Log.d(TAG, "get wine aroma impressions " + wineId);
		Cursor cursor = getReadableDatabase().rawQuery(
				String.format(
						"SELECT i.name FROM %s ri JOIN %s i ON ri.aroma_impression_id = i.%s WHERE ri.wine_id = ? ORDER BY i.name",
						WINE_AROMA_IMPRESSIONS_TABLE_NAME, AROMA_IMPRESSIONS_TABLE_NAME, BaseColumns._ID
						),
						new String[]{ wineId }
				);
		return cursor;
	}

	public Cursor getWineTagsCursor(String wineId) {
		Log.d(TAG, "get wine tags " + wineId);
		Cursor cursor = getReadableDatabase().rawQuery(
				String.format(
						"SELECT t.name FROM %s rt JOIN %s t ON rt.tag_id = t.%s WHERE rt.wine_id = ? ORDER BY t.name",
						WINE_TAGS_TABLE_NAME, TAGS_TABLE_NAME, BaseColumns._ID
						),
						new String[]{ wineId }
				);
		return cursor;
	}

	public Cursor getWinePhotosCursor(String wineId) {
		Log.d(TAG, "get wine photos " + wineId);
		Cursor cursor = getReadableDatabase().rawQuery(
				String.format(
						"SELECT filename FROM %s WHERE wine_id = ?",
						WINE_PHOTOS_TABLE_NAME
						),
						new String[]{ wineId }
				);
		return cursor;
	}

}
