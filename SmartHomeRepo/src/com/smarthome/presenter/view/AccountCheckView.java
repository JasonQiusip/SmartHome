package com.smarthome.presenter.view;

public interface AccountCheckView extends CustomView{
	void showLoading();
	void hideLoading();
	void onReqSuccess(String response);
	void onReqError();
}
