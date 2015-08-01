package com.smarthome.presenter.view;

public interface DevManageView extends CustomView{

	void showLoading();
	void hideLoading();
	void onCheckDevArgs();
	void onCheckError();
	void onChangeDevArgs();
	void onChangError();
	void onDispatchConf();
	void onDispatchConfError();
	void onDevControl();
	void onDevControlError();

}
