package com.smarthome.presenter;

import org.json.JSONException;

import com.smarthome.api.GetConfigFileApi;
import com.smarthome.api.common.RequestCallback;
import com.smarthome.api.model.DevCtrlAction;
import com.smarthome.api.model.DeviceStatus;
import com.smarthome.presenter.view.CustomView;
import com.smarthome.presenter.view.DevManageView;

public class DevConfigPresenter {

	private DevManageView view;
	private String pid;
	
	public DevConfigPresenter(CustomView view){
		this.view = (DevManageView)view;
	}
	
	public void setPid(String pid){
		this.pid = pid;
	}
	
	public void devControl(DevCtrlAction controlAction){
		GetConfigFileApi.devCtrl(pid, controlAction, new RequestCallback<String>() {

			@Override
			public void onSuccess(String result) throws JSONException {
				view.onDevControl();
			}

			@Override
			public void onError(String errorMsg) {
				view.onDevControlError();
			}
		});
	}
	
	public void dispatchConfig(String savedTime){
		GetConfigFileApi.dispatchConf(pid, savedTime, new RequestCallback<String>() {
			
			@Override
			public void onSuccess(String result) throws JSONException {
				view.onDispatchConf();
			}
			
			@Override
			public void onError(String errorMsg) {
				view.onDispatchConfError();
			}
		});
	}
	
	public void checkDevArgs(){
		GetConfigFileApi.checkDevArgs(pid, new RequestCallback<String>() {
			
			@Override
			public void onSuccess(String result) throws JSONException {
				view.onCheckDevArgs();
			}
			
			@Override
			public void onError(String errorMsg) {
				view.onCheckError();
			}
		});
	}
	
	public void changeDevArgs(DeviceStatus devStatus){
		GetConfigFileApi.changeDevArgs(devStatus, new RequestCallback<String>() {
			
			@Override
			public void onSuccess(String result) throws JSONException {
				view.onChangeDevArgs();
			}
			
			@Override
			public void onError(String errorMsg) {
				view.onChangError();
			}
		});
	}
	
}
