package com.winenotes.activity;

import android.database.Cursor;

import com.winenotes.R;

public class EditTasteImpressionsActivity extends EditWineItemsBaseActivity {

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
