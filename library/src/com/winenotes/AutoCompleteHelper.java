package com.winenotes;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

public abstract class AutoCompleteHelper {

	public static void configureAutoCompleteTextView(Context context,
			AutoCompleteTextView autoCompleteTextView,
			Cursor autoCompleteListCursor) {
		configureAutoCompleteTextView(context, autoCompleteTextView,
				autoCompleteListCursor, "name");
	}
	
	public static void configureAutoCompleteTextView(Context context,
			AutoCompleteTextView autoCompleteTextView,
			Cursor autoCompleteListCursor, String columnName) {
		// TODO store id too
		ArrayList<String> autoCompleteList = new ArrayList<String>();
		int columnIndex = autoCompleteListCursor.getColumnIndex(columnName);
		while (autoCompleteListCursor.moveToNext()) {
			autoCompleteList.add(autoCompleteListCursor.getString(columnIndex));
		}
		autoCompleteListCursor.close();

		ArrayAdapter<String> itemsAutoCompleteAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_dropdown_item_1line, autoCompleteList);
		
		autoCompleteTextView.setAdapter(itemsAutoCompleteAdapter);
		if (autoCompleteTextView instanceof MultiAutoCompleteTextView) {
			((MultiAutoCompleteTextView)autoCompleteTextView)
			.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
		}
	}
}
