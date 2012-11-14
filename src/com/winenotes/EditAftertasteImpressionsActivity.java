package com.winenotes;

import android.database.Cursor;

public class EditAftertasteImpressionsActivity extends AbstractEditWineItemsActivity {

	@Override
	int getContentViewId() {
		return R.layout.editimpressions;
	}

	@Override
	Cursor getAutoCompleteListCursor() {
		return helper.getTasteImpressionListCursor();
	}

	@Override
	Cursor getItemListCursor() {
		return helper.getWineAftertasteImpressionsCursor(wineId);
	}

	@Override
	String getOrCreateItem(String name) {
		return helper.getOrCreateTasteImpression(name);
	}

	@Override
	boolean addWineItem(String itemId) {
		return helper.addWineAftertasteImpression(wineId, itemId);
	}

	@Override
	String getItemIdByName(String name) {
		return helper.getTasteImpressionIdByName(name);
	}

	@Override
	boolean removeWineItem(String itemId) {
		return helper.removeWineAftertasteImpression(wineId, itemId);
	}
}
