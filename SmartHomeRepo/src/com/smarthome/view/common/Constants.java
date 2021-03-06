package com.smarthome.view.common;

public class Constants {

	
	public interface ReqValCodeState{
		public static final String SUCCESS = "0";
		public static final String ACCOUNT_FORMAT_ERROR = "1";
		public static final String ACCOUNT_NOT_MOBILE = "2";
	}
	
	public interface RegisterState{
		public static final String SUCCESS = "0";
		public static final String ACCOUNT_FORMAT_ERROR = "1";
		public static final String ACCOUNT_NOT_MOBILE = "2";
		public static final String VAL_CODE_FORMAT_ERROR = "3";
		public static final String VAL_CODE_INVALID_OR_NOT_EXIST = "4";
		public static final String ACCOUNT_EXIST = "5";
	}
	
	public interface LoginState{
		
	}
	
	public interface PwdNewState{
		public static final String SUCCESS = "0";
		public static final String ACCOUNT_MISS = "1";
		public static final String PWD_MISS = "2";
		public static final String NO_VAL = "3";
		public static final String VAL_CODE_ERROR = "4";
	}
}
