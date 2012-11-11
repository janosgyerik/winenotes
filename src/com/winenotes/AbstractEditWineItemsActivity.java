package com.winenotes;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

public abstract class AbstractEditWineItemsActivity extends ListActivity {

	private static final String TAG = AbstractEditWineItemsActivity.class.getSimpleName();

	public static final String OUT_CHANGED = "CHANGED";

	protected WineNotesSQLiteOpenHelper helper;
	protected String wineId;

	private MultiAutoCompleteTextView inputView;
	private ArrayAdapter<String> itemListAdapter;
	private ListView itemListView;

	private boolean isChanged;

	abstract int getContentViewId();

	abstract Cursor getAutoCompleteListCursor();
	abstract Cursor getItemListCursor();

	abstract String getOrCreateItem(String name);
	abstract boolean addWineItem(String itemId);
	abstract String getItemIdByName(String name);
	abstract boolean removeWineItem(String itemId);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getContentViewId());
		Log.d(TAG, "++onCreate");

		helper = new WineNotesSQLiteOpenHelper(this);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			wineId = extras.getString(BaseColumns._ID);
		}
		else {
			//			wineId = "44";
		}

		// TODO store id too
		ArrayList<String> itemsAutoCompleteList = new ArrayList<String>();
		{
			Cursor itemsCursor = getAutoCompleteListCursor();
			int i = itemsCursor.getColumnIndex("name");
			while (itemsCursor.moveToNext()) {
				itemsAutoCompleteList.add(itemsCursor.getString(i));
			}
			itemsCursor.close();
		}

		ArrayAdapter<String> itemsAutoCompleteAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, itemsAutoCompleteList);
		inputView = (MultiAutoCompleteTextView) findViewById(R.id.input);
		inputView.setAdapter(itemsAutoCompleteAdapter);
		inputView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

		itemListAdapter =
				new ArrayAdapter<String>(this, R.layout.impressionslist_item);
		itemListView = (ListView) findViewById(android.R.id.list);
		itemListView.setAdapter(itemListAdapter);
		itemListView.setOnItemLongClickListener(new ItemListOnItemLongClickListener());

		Button addItemButton = (Button) findViewById(R.id.btn_add);
		addItemButton.setOnClickListener(new AddItemOnClickListener());

		Button doneButton = (Button) findViewById(R.id.btn_done);
		doneButton.setOnClickListener(new DoneOnClickListener());

		Cursor itemsCursor = getItemListCursor();
		while (itemsCursor.moveToNext()) {
			String item = itemsCursor.getString(0);
			itemListAdapter.add(item);
		}
		itemsCursor.close();

		setListAdapter(itemListAdapter);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		Log.d(TAG, "onKeyDown");
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {
			returnResult();
		}

		return super.onKeyDown(keyCode, event);
	}

	private void returnResult() {
		Intent intent = new Intent();
		intent.putExtra(OUT_CHANGED, isChanged);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}

	private void addItems() {
		String items = inputView.getText().toString().trim();
		if (items.length() > 0) {
			StringBuffer itemsListMsgBuffer = new StringBuffer();
			for (String item : items.split(",")) {
				item = EditWineActivity.capitalize(item);
				itemsListMsgBuffer.append(item);
				itemsListMsgBuffer.append(", ");
				String itemId = getOrCreateItem(item);
				if (itemId != null
						&& addWineItem(itemId)) {
					itemListAdapter.insert(item, 0);
					isChanged = true;
				}
			}
			inputView.setText("");
			String itemsListMsg = itemsListMsgBuffer.substring(0, itemsListMsgBuffer.lastIndexOf(","));
			Toast.makeText(getApplicationContext(), "Added " + itemsListMsg, Toast.LENGTH_SHORT).show();
		}
	}

	private void removeItem(String itemName) {
		String itemId = getItemIdByName(itemName);
		if (itemId != null
				&& removeWineItem(itemId)) {
			itemListAdapter.remove(itemName);
			isChanged = true;
		}
	}

	class AddItemOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			addItems();
		}
	}

	class DoneOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			addItems();
			returnResult();
		}
	}

	class ItemListOnItemLongClickListener implements OnItemLongClickListener {
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			String itemName = itemListAdapter.getItem(arg2);
			removeItem(itemName);
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
