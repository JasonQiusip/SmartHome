package com.smarthome.presenter.view;

public interface NewPasswordView extends CustomView{
	void showLoading();
	void hideLoading();
	void onReqPwdLostSuccess(String response);
	void onReqPwdNewSuccess(String response);
	void onError();
}
