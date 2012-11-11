package com.winenotes;

import java.io.IOException;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class WineNotesActivity extends ListActivity {

	private static final String TAG = "WineListActivity";

	private WineNotesSQLiteOpenHelper helper;
	private Cursor cursor;

	private static final int FILE_SELECTED = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "++onCreate");

		if (!WineFileManager.isExternalStorageWritable()) {
			new AlertDialog.Builder(this)
			.setTitle(R.string.app_name)
			.setMessage(R.string.fatal_sdcard_required)
			.setCancelable(false)
			.setIcon(R.drawable.launcher_main)
			.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					finish();
				}
			})
			.show();
		}

		setContentView(R.layout.winelist);

		initCursor();

		getListView().setOnItemClickListener(new WineListOnItemClickListener());
		getListView().setOnItemLongClickListener(new WineListOnItemLongClickListener());

		((View)findViewById(R.id.btn_add_wine)).setOnClickListener(new AddWineOnClickListener());

		WineFileManager.updateDailyBackup();
	}
	
	private void initCursor() {
		helper = new WineNotesSQLiteOpenHelper(this);
		cursor = helper.getWineListCursor();
		startManagingCursor(cursor);
		ListAdapter adapter = new SimpleCursorAdapter(
				this, // Context.
				R.layout.winelist_item,
				cursor,
				new String[] { 
						BaseColumns._ID, "name", "display_name",
				}, // column names in the query ...
				new int[] { 
						R.id._ID, R.id.name, R.id.impressions,
				}
				); // ... view ids to bind to
		setListAdapter(adapter);
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "++onResume");
	}

	class AddWineOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(WineNotesActivity.this, EditWineActivity.class);
			startActivity(intent);
		}
	}

	class WineListOnItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(WineNotesActivity.this, ViewWineActivity.class);
			intent.putExtra(BaseColumns._ID, ((TextView)view.findViewById(R.id._ID)).getText());
			startActivity(intent);
		}
	}

	class WineListOnItemLongClickListener implements OnItemLongClickListener {
		private void deleteWine(String wineId) {
			if (helper.deleteWine(wineId)) {
				WineFileManager.deleteWine(wineId);
				cursor.requery();
				Toast.makeText(getBaseContext(), R.string.msg_wine_deleted, Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			final String wineId = String.valueOf(arg3);
			new AlertDialog.Builder(WineNotesActivity.this)
			.setTitle(R.string.title_delete_wine)
			.setMessage(R.string.confirm_are_you_sure)
			.setCancelable(true)
			.setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					deleteWine(wineId);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_backup:
			try {
				boolean success = WineFileManager.backupDatabaseFile();
				if (success) {
					Toast.makeText(getBaseContext(), R.string.msg_backup_created, Toast.LENGTH_LONG).show();
				}
				else {
					Toast.makeText(getBaseContext(), R.string.error_backup_failed, Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				Toast.makeText(getBaseContext(), R.string.error_backup_exception, Toast.LENGTH_LONG).show();
				Log.e(TAG, "Exception in backupDatabaseFile", e);
			}
			return true;

		case R.id.menu_restore:
			Intent intent = new Intent(this, FileSelectorActivity.class);
			intent.putExtra(FileSelectorActivity.IN_TITLE, getString(R.string.title_select_backupfile));
			intent.putExtra(FileSelectorActivity.IN_DIRPARAM, WineFileManager.BACKUPS_DIRPARAM);
			intent.putExtra(FileSelectorActivity.IN_PATTERN, WineFileManager.BACKUPFILES_PATTERN);
			intent.putExtra(FileSelectorActivity.IN_ORDER, FileSelectorActivity.ORDER_ZYX);
			intent.putExtra(FileSelectorActivity.IN_CONFIRMATION_TITLE, getString(R.string.title_confirm_restore));
			intent.putExtra(FileSelectorActivity.IN_CONFIRMATION_MESSAGE, getString(R.string.confirm_restore));
			startActivityForResult(intent, FILE_SELECTED);
			return true;

		case R.id.menu_quit:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.msg_quit)
			.setCancelable(true)
			.setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					finish();
				}
			})
			.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			}).show();
			return true;
		}
		return false;
	}

	private void handleRestoreDatabaseResult(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			String filename = extras.getString(FileSelectorActivity.OUT_FILENAME);
			Log.d(TAG, "selected filename = " + filename);
			if (filename != null) {
				helper.close();
				try {
					if (WineFileManager.restoreDatabaseFile(filename)) {
						Toast.makeText(getBaseContext(), R.string.msg_restore_success, Toast.LENGTH_LONG).show();
					}
					else {
						Toast.makeText(getBaseContext(), R.string.error_restore_failed, Toast.LENGTH_LONG).show();
					}
				} catch (IOException e) {
					e.printStackTrace();
					Toast.makeText(getBaseContext(), R.string.error_restore_exception, Toast.LENGTH_LONG).show();
				}
				initCursor();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case FILE_SELECTED:
				handleRestoreDatabaseResult(data);
				break;
			}
		}
	}

	@Override  
	protected void onDestroy() {
		Log.d(TAG, "++onDestroy");
		super.onDestroy();
		helper.close();
	}
}
