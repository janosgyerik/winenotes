package com.winenotes;

import android.app.Application;

public class WineNotesApplication extends Application {
	public boolean isLiteVersion() {
		return getPackageName().toLowerCase().endsWith(".lite"); 
	}
}
