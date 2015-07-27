package com.smarthome.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import butterknife.ButterKnife;

public class BaseAty extends ActionBarActivity{

	ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ButterKnife.inject(this);
		progressDialog = new ProgressDialog(this);
	}
	
	void showSnackBar(String msg){
		Snackbar.make(this.getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT).show();
	}
	
}
