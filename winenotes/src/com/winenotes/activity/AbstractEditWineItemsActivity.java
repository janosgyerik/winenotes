package com.winenotes.activity;

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
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.winenotes.R;
import com.winenotes.db.WineNotesSQLiteOpenHelper;

import java.util.ArrayList;

public abstract class AbstractEditWineItemsActivity extends ListActivity {

    private static final String TAG = AbstractEditWineItemsActivity.class.getSimpleName();

    public static final String OUT_CHANGED = "CHANGED";

    protected WineNotesSQLiteOpenHelper helper;
    protected String wineId;

    private MultiAutoCompleteTextView inputView;
    private ArrayAdapter<String> itemListAdapter;

    private boolean isChanged;

    abstract int getContentViewId();

    abstract boolean hasAsciiName();

    abstract Cursor getAutoCompleteListCursor();

    abstract Cursor getItemListCursor();

    abstract String getOrCreateItem(String name);

    abstract boolean addWineItem(String itemId);

    abstract String getItemIdByName(String name);

    abstract boolean removeWineItem(String itemId);

    protected String getHint() {
        return this.getString(R.string.hint_impressions);
    }

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

        // TODO store id too
        ArrayList<String> itemsAutoCompleteList = new ArrayList<String>();
        if (hasAsciiName()) {
            Cursor itemsCursor = getAutoCompleteListCursor();
            int columnIndex = itemsCursor.getColumnIndex("name");
            int asciiColumnIndex = itemsCursor.getColumnIndex("ascii_name");
            while (itemsCursor.moveToNext()) {
                String name = itemsCursor.getString(columnIndex);
                if (name != null) {
                    itemsAutoCompleteList.add(name);
                    String asciiName = itemsCursor.getString(asciiColumnIndex);
                    if (asciiName != null && !name.equals(asciiName)) {
                        itemsAutoCompleteList.add(asciiName);
                    }
                }
            }
            itemsCursor.close();
        } else {
            Cursor itemsCursor = getAutoCompleteListCursor();
            int columnIndex = itemsCursor.getColumnIndex("name");
            while (itemsCursor.moveToNext()) {
                itemsAutoCompleteList.add(itemsCursor.getString(columnIndex));
            }
            itemsCursor.close();
        }

        ArrayAdapter<String> itemsAutoCompleteAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, itemsAutoCompleteList);
        inputView = (MultiAutoCompleteTextView) findViewById(R.id.input);
        inputView.setAdapter(itemsAutoCompleteAdapter);
        inputView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        inputView.setHint(getHint());

        itemListAdapter =
                new ArrayAdapter<String>(this, R.layout.impressionslist_item);
        ListView itemListView = (ListView) findViewById(android.R.id.list);
        itemListView.setAdapter(itemListAdapter);
        itemListView.setOnItemLongClickListener(new ItemListOnItemLongClickListener());

        View addItemButton = findViewById(R.id.btn_add);
        addItemButton.setOnClickListener(new AddItemOnClickListener());

        View doneButton = findViewById(R.id.btn_done);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
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
            StringBuilder builder = new StringBuilder();
            for (String item : items.split(",")) {
                item = EditWineActivity.capitalize(item);
                builder.append(item);
                builder.append(", ");
                String itemId = getOrCreateItem(item);
                if (itemId != null
                        && addWineItem(itemId)) {
                    itemListAdapter.insert(item, 0);
                    isChanged = true;
                }
            }
            inputView.setText("");
            String itemsListMsg = builder.substring(0, builder.lastIndexOf(","));
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
