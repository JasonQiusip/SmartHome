package com.smarthome.view;

public interface MobileCheckView extends CustomView{
	void showLoading();
	void hideLoading();
	void onReqSuccess(String response);
	void onReqError();
}
