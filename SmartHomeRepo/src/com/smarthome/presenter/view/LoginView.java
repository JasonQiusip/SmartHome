package com.smarthome.presenter.view;

import com.smarthome.api.model.LoginResult;

public interface LoginView extends CustomView{
	void showLoading();
	void hideLoading();
	void onLoginSuccess(LoginResult response);
	void onError();
}
