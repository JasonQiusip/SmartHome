package com.smarthome.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtils {
	
	public static class LoginSp{
		private static final String API_SECRET = "apiSecret";
		private static final String ACCOUNT = "account";
		private static final String LOGIN_PREF = "LoginPref";
	
		public static SharedPreferences getAccountPref(Context context){
			return context.getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);
		}
		
		public static void putAccount(SharedPreferences sp, String account){
			sp.edit().putString(ACCOUNT, account);
		}
		public static void putSecret(SharedPreferences sp, String secret){
			sp.edit().putString(API_SECRET, secret);
		}
		
		public static String getAccount(SharedPreferences sp){
			return sp.getString(ACCOUNT, null);
		}
		public static String getSecret(SharedPreferences sp){
			return sp.getString(API_SECRET, null);
		}
		
	}
}
