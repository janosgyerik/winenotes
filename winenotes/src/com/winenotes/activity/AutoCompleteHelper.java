package com.winenotes.activity;

import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

import java.util.ArrayList;

public abstract class AutoCompleteHelper {

    public static void configureAutoCompleteTextView(Context context,
                                                     AutoCompleteTextView autoCompleteTextView,
                                                     Cursor autoCompleteListCursor, String columnName, String asciiColumnName) {
        // TODO store id too
        ArrayList<String> autoCompleteList = new ArrayList<String>();
        int columnIndex = autoCompleteListCursor.getColumnIndex(columnName);
        if (asciiColumnName != null) {
            int asciiColumnIndex = autoCompleteListCursor.getColumnIndex(asciiColumnName);
            while (autoCompleteListCursor.moveToNext()) {
                String name = autoCompleteListCursor.getString(columnIndex);
                autoCompleteList.add(name);
                String asciiName = autoCompleteListCursor.getString(asciiColumnIndex);
                if (asciiName != null && !name.equals(asciiName)) {
                    autoCompleteList.add(asciiName);
                }
            }
        } else {
            while (autoCompleteListCursor.moveToNext()) {
                String name = autoCompleteListCursor.getString(columnIndex);
                autoCompleteList.add(name);
            }
        }
        autoCompleteListCursor.close();

        ArrayAdapter<String> itemsAutoCompleteAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_dropdown_item_1line, autoCompleteList);

        autoCompleteTextView.setAdapter(itemsAutoCompleteAdapter);
        if (autoCompleteTextView instanceof MultiAutoCompleteTextView) {
            ((MultiAutoCompleteTextView) autoCompleteTextView)
                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        }
    }
}
