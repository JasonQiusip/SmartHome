package com.smarthome.api.common;

public class ConvertTool {

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
			i++;
		}
		return input;
	}
	
}
