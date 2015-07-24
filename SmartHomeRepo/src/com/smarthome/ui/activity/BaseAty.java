package com.smarthome.ui.activity;

import butterknife.ButterKnife;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class BaseAty extends ActionBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ButterKnife.inject(this);
	}
	
}
