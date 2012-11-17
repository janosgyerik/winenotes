package com.winenotes;

import android.database.Cursor;

public class EditTasteImpressionsActivity extends AbstractEditWineItemsActivity {

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
		return helper.getWineTasteImpressionsCursor(wineId);
	}

	@Override
	String getOrCreateItem(String name) {
		return helper.getOrCreateTasteImpression(name);
	}

	@Override
	boolean addWineItem(String itemId) {
		return helper.addWineTasteImpression(wineId, itemId);
	}

	@Override
	String getItemIdByName(String name) {
		return helper.getTasteImpressionIdByName(name);
	}

	@Override
	boolean removeWineItem(String itemId) {
		return helper.removeWineTasteImpression(wineId, itemId);
	}

	@Override
	boolean hasAsciiName() {
		return false;
	}
}
