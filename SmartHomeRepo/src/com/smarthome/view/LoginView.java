package com.smarthome.view;

public interface LoginView extends CustomView{
	void showLoading();
	void hideLoading();
	void reqRegSuccess(String response);
	void onError();
}
