package com.smarthomeloginreg.api.common;

import org.json.JSONException;

public interface RequestCallback {

	void onSuccess(String response) throws JSONException;
	void onError(String errorMsg);
}
