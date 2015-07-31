package com.smarthome.api.common;

import android.util.Log;


public class ConvertTool {

	public static byte[] hexStrToByteArray(String hexString)
    {
		if (hexString == null || hexString.equals("")) {  
	        return null;  
	    }  
	    int length = hexString.length() / 2;  
	    char[] hexChars = hexString.toCharArray();  
	    byte[] d = new byte[length];  
	    for (int i = 0; i < length; i++) {  
	        int pos = i * 2;  
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
	    }  
	    return d;  
    }
	
	private static byte charToByte(char c) {  
	    return (byte) "0123456789abcedf".indexOf(c);  
	}  
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	public static StringBuilder byteArrayToStr(byte[] byteArray) {
		StringBuilder sb = new StringBuilder();
		for(byte b : byteArray)
		{
			char ch = (char)(b&0xff);
			sb.append(ch);
		}
		return sb;
	}
	
	public  static byte[] charToByteArray(char[] charArray) {
		
		byte[] input = new byte[charArray.length];
		int i = 0;
		for(char ch : charArray)
		{
			input[i] = (byte) ch;
			Log.e("charToByteArray  ", ch+"   "+input[i]);
			i++;
		}
		return input;
	}
	
}
