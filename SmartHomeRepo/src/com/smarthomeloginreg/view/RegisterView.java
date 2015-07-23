package com.smarthomeloginreg.view;

public interface RegisterView extends CustomView{

	void showLoading();
	void hideLoading();
	void reqRegSuccess(String response);
	void onGetValCode(String val);
}
