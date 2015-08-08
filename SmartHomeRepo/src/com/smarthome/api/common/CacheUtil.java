package com.smarthome.api.common;

import java.io.File;
import java.io.IOException;

import com.jakewharton.disklrucache.DiskLruCache;
import com.jakewharton.disklrucache.DiskLruCache.Editor;
import com.smarthome.SmartHomeApplication;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;

public class CacheUtil {
	public static final String ACCOUNT_INFO = "acc_related";
	public static final String MISC = "misc";
	
	public static DiskLruCache openDiskLruCache(String uniqueName) throws IOException{
		File fileToStore = getDiskCacheDir(SmartHomeApplication.getApplication(), uniqueName);
		if(!fileToStore.exists())
			fileToStore.mkdirs();
		int appVer = getAppVersion(SmartHomeApplication.getApplication());
		DiskLruCache diskLruCache = DiskLruCache.open(fileToStore, appVer, 2, 1024*1024*10);
		return diskLruCache;
	}
	
	
	public static File getDiskCacheDir(Context context, String uniqueName) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			cachePath = context.getExternalCacheDir().getPath()	;
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		return new File(cachePath + File.separator + uniqueName);
	}
	
	public static int getAppVersion(Context context) {
		try {
	      PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
	      return info.versionCode;
	    } catch (NameNotFoundException e) {
	      e.printStackTrace();
	    }
	    return 1;
	}
	
	public static void storeAccToDisk(String content, String pwd) {
		try {
			DiskLruCache diskLruCache = CacheUtil.openDiskLruCache(MISC);
			Editor editor = diskLruCache.edit(ACCOUNT_INFO);
			editor.set(ApiConstants.TOKEN_DISK_LRU_INDEX, content);
			editor.set(ApiConstants.PWD_DISK_LRU_INDEX, pwd);
			editor.commit();
			// OutputStream newOutputStream = editor.newOutputStream(0);
			// newOutputStream.write(ConvertTool.charToByteArray(content.toCharArray()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
