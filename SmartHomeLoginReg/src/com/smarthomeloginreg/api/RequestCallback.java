package com.smarthomeloginreg.api;

import org.json.JSONException;

public interface RequestCallback {

	void onSuccess(String response) throws JSONException;
	void onError(String errorMsg);
}
