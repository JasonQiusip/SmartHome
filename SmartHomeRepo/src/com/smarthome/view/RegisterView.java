package com.smarthome.view;

public interface RegisterView extends CustomView{

	void showLoading();
	void hideLoading();
	void onReqRegSuccess(String response);
	void onFinishRegSuccess(String response);
	void onGetValCode(String val);
	void onError(String errorMsg);
}
