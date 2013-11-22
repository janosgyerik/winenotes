release
-------
- indicate in list if has aroma/taste/... rating or tag
- indicate in list the flag


next
----
- improve build script
    - backup
    - uninstall
    - start


release
-------
- fix the photo rotation bug
- add pulldown for tag and grape and region selectors
- redo menu icons for dark bg
- make all dialogs bright theme
- option to email notes in multiple formats
    - human-friendly
    - csv
    - json
- UI config
    region
    grape
    aroma rating
    aroma impressions
    taste rating
    taste impressions
    aftertaste rating
    aftertaste impressions
    memo
    custom tags


cleaning
--------
- clean up db related deprecated calls in WineListActivity and others
    - cursor.requery
    - stopManagingCursor
    - startManagingCursor
    http://developer.android.com/reference/android/app/Activity.html
    This method was deprecated in API level 11. Use the new CursorLoader class with LoaderManager instead; this is also available on older platforms through the Android compatibility package.
- error: 9-patch image malformed
    "No marked region found along edge."
    "Found along top edge."
    btn_default_normal.9.png
    btn_default_pressed.9.png
    btn_default_selected.9.png


next next
---------
- duplicate wine
- next-previous wine with swipe right-left
- wine editing: cancel wine if empty
    - see recipe notes 178 179
- install "Android Compatibility package" and rewrite queries


next next next
--------------
- clean up file paths in WineFileManager
    - a.getDatabasePath("sqlite3.db") -> /data/data/com.winenotes/databases/sqlite3.db
    - a.getPackageCodePath -> /data/app/com.winenotes.apk
    - a.getPackageName -> com.winenotes
    - Environment.getDataDirectory -> /data
    - Environment.getExternalStorageDirectory -> /sdcard
    - eliminate code duplication
- delete misspelled impressions
- do not crash if no camera or not accessible
- make it possible to reorder lists
- more efficient handling of impressions editing
    - make use of the _id of impressions in the autocomplete list
    - when saving, use the _id of impressions from the autocomplete list
    - impressions that were entered manually, try to find in autocomplete list
    - only lookup/insert impressions when necessary


