package com.winenotes.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;

import com.winenotes.R;

import java.io.File;
import java.io.FileFilter;
import java.util.Comparator;

public class FileSelectorActivity extends ListActivity {

    private static final String TAG = FileSelectorActivity.class.getSimpleName();

    public static final String IN_TITLE = "TITLE";
    public static final String IN_DIRPARAM = "DIRPARAM";
    public static final String IN_PATTERN = "PATTERN";
    public static final String IN_ORDER = "ORDER";
    public static final String IN_CONFIRMATION_TITLE = "CONFIRMATION_TITLE";
    public static final String IN_CONFIRMATION_MESSAGE = "CONFIRMATION_MESSAGE";

    public static final String ORDER_ABC = "ABC";
    public static final String ORDER_ZYX = "ZYX";

    public static final String OUT_FILENAME = "filename";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "+++ ON CREATE +++");

        setContentView(R.layout.fileselector);

        Bundle extras = getIntent().getExtras();

        final String title = extras.getString(IN_TITLE);
        final String dirparam = extras.getString(IN_DIRPARAM);
        final String pattern = extras.getString(IN_PATTERN);
        final String order = extras.getString(IN_ORDER);
        final String confirmationTitle = extras.getString(IN_CONFIRMATION_TITLE);
        final String confirmationMessage = extras.getString(IN_CONFIRMATION_MESSAGE);

        Comparator<String> comparator;
        if (order != null && order.equals(ORDER_ZYX)) {
            comparator = new ReverseAlphabeticComparator();
        } else {
            comparator = new AlphabeticComparator();
        }

        setTitle(title);

        final ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.filename);

        final File externalDir =
                new File(Environment.getExternalStorageDirectory(), dirparam);

        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().matches(pattern);
            }
        };

        if (externalDir.isDirectory()) {
            for (File file : externalDir.listFiles(fileFilter)) {
                adapter.add(file.getName());
            }
            adapter.sort(comparator);
        }

        setListAdapter(adapter);
        getListView().setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                final String filename = adapter.getItem(arg2);

                if (confirmationTitle != null || confirmationMessage != null) {
                    new AlertDialog.Builder(FileSelectorActivity.this)
                            .setTitle(confirmationTitle)
                            .setMessage(confirmationMessage)
                            .setCancelable(true)
                            .setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    returnResult(filename);
                                }
                            })
                            .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                } else {
                    returnResult(filename);
                }
            }
        });

        getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
            private void deleteSelectedFilename(String filename) {
                if (externalDir.isDirectory()) {
                    if (new File(externalDir, filename).delete()) {
                        adapter.remove(filename);
                    }
                }
            }

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                final String filename = adapter.getItem(arg2);

                new AlertDialog.Builder(FileSelectorActivity.this)
                        .setTitle(R.string.title_delete_file)
                        .setMessage(R.string.confirm_are_you_sure)
                        .setCancelable(true)
                        .setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteSelectedFilename(filename);
                            }
                        })
                        .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    private void returnResult(String filename) {
        Intent intent = new Intent();
        intent.putExtra(OUT_FILENAME, filename);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public static boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    class AlphabeticComparator implements Comparator<String> {
        @Override
        public int compare(String lhs, String rhs) {
            return lhs.compareTo(rhs);
        }
    }

    class ReverseAlphabeticComparator implements Comparator<String> {
        @Override
        public int compare(String lhs, String rhs) {
            return rhs.compareTo(lhs);
        }
    }
}
