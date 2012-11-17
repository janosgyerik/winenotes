package com.winenotes;

import android.database.Cursor;

public class EditAromaImpressionsActivity extends AbstractEditWineItemsActivity {

	@Override
	int getContentViewId() {
		return R.layout.editimpressions;
	}

	@Override
	Cursor getAutoCompleteListCursor() {
		return helper.getAromaImpressionListCursor();
	}

	@Override
	Cursor getItemListCursor() {
		return helper.getWineAromaImpressionsCursor(wineId);
	}

	@Override
	String getOrCreateItem(String name) {
		return helper.getOrCreateAromaImpression(name);
	}

	@Override
	boolean addWineItem(String itemId) {
		return helper.addWineAromaImpression(wineId, itemId);
	}

	@Override
	String getItemIdByName(String name) {
		return helper.getAromaImpressionIdByName(name);
	}

	@Override
	boolean removeWineItem(String itemId) {
		return helper.removeWineAromaImpression(wineId, itemId);
	}

	@Override
	boolean hasAsciiName() {
		return false;
	}
}
