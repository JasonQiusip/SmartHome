package com.smarthomeloginreg.tools;

import android.util.Log;

public class LogUtil {

	private static final boolean DEBUG = true;

	public static void showError(String tag, String txt){
		if(DEBUG)
			Log.e(tag, txt);
	}
}
