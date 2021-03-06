package com.winenotes.activity;

import android.database.Cursor;

import com.winenotes.R;

public class EditAromaImpressionsActivity extends EditTagsActivity {

    @Override
    int getContentViewId() {
        return R.layout.edittags;
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
