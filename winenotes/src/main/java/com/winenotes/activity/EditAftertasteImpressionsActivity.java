package com.winenotes.activity;

import android.database.Cursor;

import com.winenotes.R;

public class EditAftertasteImpressionsActivity extends EditTagsActivity {

    @Override
    int getContentViewId() {
        return R.layout.edittags;
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

    @Override
    boolean hasAsciiName() {
        return false;
    }
}
