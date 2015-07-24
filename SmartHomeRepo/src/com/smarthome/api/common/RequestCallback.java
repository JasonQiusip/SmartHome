package com.smarthome.api.common;

import org.json.JSONException;

import com.smarthome.api.model.RegResult;

public interface RequestCallback {

	void onSuccess(String result) throws JSONException;
	void onError(String errorMsg);
}
