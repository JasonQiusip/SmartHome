package com.smarthomeloginreg.ui.activity;

import butterknife.ButterKnife;
import android.app.Activity;
import android.os.Bundle;

public class BaseAty extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ButterKnife.inject(this);
	}
	
}
