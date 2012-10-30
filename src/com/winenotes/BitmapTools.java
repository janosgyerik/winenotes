package com.winenotes;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class BitmapTools {
	
	private static final String TAG = "BitmapTools";
	
	public static Bitmap createScaledBitmap(File file, int appWidth) {
		String path = file.getAbsolutePath();
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		Log.d(TAG, String.format("dstWidth=%d srcWidth=%d", appWidth, bitmap.getWidth()));
		int dstHeight = appWidth * bitmap.getHeight() / bitmap.getWidth();
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, appWidth, dstHeight, false);
		if (!scaledBitmap.equals(bitmap)) {
			bitmap.recycle();
			bitmap = null;
		}
		return scaledBitmap;
	}
	
}
