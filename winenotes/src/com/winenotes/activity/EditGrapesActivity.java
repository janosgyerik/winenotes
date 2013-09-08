package com.winenotes.activity;

import android.database.Cursor;

import com.winenotes.R;

public class EditGrapesActivity extends EditTagsActivity {

    @Override
    protected String getHint() {
        return this.getString(R.string.hint_grape);
    }

    @Override
    int getContentViewId() {
        return R.layout.edittags;
    }

    @Override
    Cursor getAutoCompleteListCursor() {
        return helper.getGrapeListCursor();
    }

    @Override
    Cursor getItemListCursor() {
        return helper.getWineGrapesCursor(wineId);
    }

    @Override
    String getOrCreateItem(String name) {
        return helper.getOrCreateGrape(name);
    }

    @Override
    boolean addWineItem(String itemId) {
        return helper.addWineGrape(wineId, itemId);
    }

    @Override
    String getItemIdByName(String name) {
        return helper.getGrapeIdByName(name);
    }

    @Override
    boolean removeWineItem(String itemId) {
        return helper.removeWineGrape(wineId, itemId);
    }

    @Override
    boolean hasAsciiName() {
        return true;
    }
}
