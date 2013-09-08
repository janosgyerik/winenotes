package com.winenotes.tools;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class WineFileManager {

	private static final String TAG = WineFileManager.class.getSimpleName();

	public static final String BACKUPS_DIRPARAM = "WineNotes/backups";
	public static final File BACKUPS_DIR =
			new File(Environment.getExternalStorageDirectory(), BACKUPS_DIRPARAM);

	public static final String PHOTOS_DIRPARAM = "WineNotes/photos";
	public static final File PHOTOS_DIR =
			new File(Environment.getExternalStorageDirectory(), PHOTOS_DIRPARAM);

	private static final String MEDIUM_PHOTOS_DIRPARAM = "WineNotes/medium";
	private static final File MEDIUM_PHOTOS_DIR =
			new File(Environment.getExternalStorageDirectory(), MEDIUM_PHOTOS_DIRPARAM);
	private static final int MEDIUM_PHOTO_FACTOR = 1;

	private static final String SMALL_PHOTOS_DIRPARAM = "WineNotes/small";
	private static final File SMALL_PHOTOS_DIR =
			new File(Environment.getExternalStorageDirectory(), SMALL_PHOTOS_DIRPARAM);
	private static final int SMALL_PHOTO_FACTOR = 2;

	public static final String BACKUPFILE_FORMAT = "";
	public static final String BACKUPFILES_PATTERN = "^sqlite3-.*\\.db$";
	public static final String DAILY_BACKUPFILE = "sqlite3-autobackup.db";

	public static final String WINE_PHOTOFILE_FORMAT = "wine_%s_%d.jpg";
	public static final String WINE_PHOTOFILES_PATTERN = "^wine_%s_.*";

	private static boolean deleteWinePhotosFromDir(String wineId, File storageDir) {
		if (storageDir.isDirectory()) {
			String pattern = String.format(WINE_PHOTOFILES_PATTERN, wineId);
			FileFilter fileFilter = new PatternFileFilter(pattern);
			for (File file : storageDir.listFiles(fileFilter)) {
				Log.d(TAG, "deleting photo: " + file);
				file.delete();
			}
		}
		return true;
	}

	public static boolean deleteWinePhotos(String wineId) {
		boolean allOK = true;
		allOK &= deleteWinePhotosFromDir(wineId, PHOTOS_DIR);
		allOK &= deleteWinePhotosFromDir(wineId, MEDIUM_PHOTOS_DIR);
		allOK &= deleteWinePhotosFromDir(wineId, SMALL_PHOTOS_DIR);
		Log.d(TAG, "delete photos all ok? -> " + allOK);
		return allOK;
	}

	public static void deletePhotos(String photoFilename) {
		getPhotoFile(photoFilename).delete();
		getMediumPhotoFile(photoFilename).delete();
		getSmallPhotoFile(photoFilename).delete();
	}

	private static String getDatabasePath(String packageName) {
		String dbname = "sqlite3.db";
		return String.format("/data/%s/databases/%s", packageName, dbname);
	}

	public static File getBackupFile(String filename) {
		return new File(BACKUPS_DIR, filename);
	}

	public static boolean backupDatabaseFile(String packageName) throws IOException {
		String filename = String.format("sqlite3-%s.db", new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date()));
		return backupDatabaseFile(filename, packageName);
	}

	private static boolean backupDatabaseFile(String filename, String packageName) throws IOException {
		File data = Environment.getDataDirectory();

		if (BACKUPS_DIR.canWrite()) {
			File backupDir = BACKUPS_DIR;
			if (! backupDir.isDirectory()) {
				backupDir.mkdirs();
			}
			File currentDB = new File(data, getDatabasePath(packageName));
			File backupFile = new File(backupDir, filename);

			FileChannel src = new FileInputStream(currentDB).getChannel();
			FileChannel dst = new FileOutputStream(backupFile).getChannel();
			dst.transferFrom(src, 0, src.size());
			src.close();
			dst.close();
			return true;
		}
		return false;
	}

	public static boolean restoreDatabaseFile(String filename, String packageName) throws IOException {
		File dataDir = Environment.getDataDirectory();

		File currentFile = new File(dataDir, getDatabasePath(packageName));
		File backupFile = new File(BACKUPS_DIR, filename);

		FileChannel src = new FileInputStream(backupFile).getChannel();
		FileChannel dst = new FileOutputStream(currentFile).getChannel();
		dst.transferFrom(src, 0, src.size());
		src.close();
		dst.close();
		return true;
	}

	public static File getPhotoFile(String filename) {
		return new File(PHOTOS_DIR, filename);
	}

	public static File getMediumPhotoFile(String filename) {
		if (! MEDIUM_PHOTOS_DIR.isDirectory()) {
			MEDIUM_PHOTOS_DIR.mkdirs();
		}
		return new File(MEDIUM_PHOTOS_DIR, filename);
	}

	public static File getSmallPhotoFile(String filename) {
		if (! SMALL_PHOTOS_DIR.isDirectory()) {
			SMALL_PHOTOS_DIR.mkdirs();
		}
		return new File(SMALL_PHOTOS_DIR, filename);
	}

	public static File newPhotoFile(String wineId) throws IOException {
		if (! PHOTOS_DIR.isDirectory()) {
			PHOTOS_DIR.mkdirs();
		}
		return File.createTempFile(String.format("wine_%s_", wineId),
				".jpg", PHOTOS_DIR);
	}

	/**
	 * Update daily database backup, only once a day.
	 */
	public static void updateDailyBackup(String packageName) {
		Date now = new Date();
		Date lastModified = null;
		File backupFile = getBackupFile(DAILY_BACKUPFILE);
		if (backupFile.isFile()) {
			lastModified = new Date(backupFile.lastModified());
		}
		if (lastModified == null || lastModified.getDay() < now.getDay()) {
			try {
				backupDatabaseFile(DAILY_BACKUPFILE, packageName);
				Log.d(TAG, "Updated daily backup");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isExternalStorageWritable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	private static Bitmap createScaledPhotoBitmap(File srcFile, File dstFile, int dstWidth, int inSampleSize) {
		if (srcFile.isFile()) {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = inSampleSize;
			Bitmap bitmap = BitmapFactory.decodeFile(srcFile.getAbsolutePath(), options);
			int dstHeight = dstWidth * bitmap.getHeight() / bitmap.getWidth();
			Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, true);

			if (!scaledBitmap.equals(bitmap)) {
				bitmap.recycle();
				bitmap = null;
			}

			FileOutputStream outStream;
			try {
				outStream = new FileOutputStream(dstFile);
				scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
				Log.i(TAG, String.format("resized photo: %dx%d %s", dstWidth, dstHeight, dstFile));
				return scaledBitmap;
			} catch (FileNotFoundException e) {
				Log.e(TAG, "could not save resized photo: " + dstFile);
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Bitmap getMediumPhotoBitmap(String photoFilename, int appWidth) {
		File dstFile = getMediumPhotoFile(photoFilename);
		if (dstFile.isFile()) {
			return BitmapFactory.decodeFile(dstFile.getAbsolutePath());
		}
		File srcFile = getPhotoFile(photoFilename);
		int dstWidth = appWidth / MEDIUM_PHOTO_FACTOR;
		int inSampleSize = MEDIUM_PHOTO_FACTOR;
		return createScaledPhotoBitmap(srcFile, dstFile, dstWidth, inSampleSize);
	}

	public static Bitmap getSmallPhotoBitmap(String photoFilename, int appWidth) {
		File dstFile = getSmallPhotoFile(photoFilename);
		if (dstFile.isFile()) {
			return BitmapFactory.decodeFile(dstFile.getAbsolutePath());
		}
		File srcFile = getPhotoFile(photoFilename);
		int dstWidth = appWidth / SMALL_PHOTO_FACTOR;
		int inSampleSize = SMALL_PHOTO_FACTOR;
		return createScaledPhotoBitmap(srcFile, dstFile, dstWidth, inSampleSize);
	}
}
