package com.smarthomeloginreg.ui.activity;

import com.smarthomeloginreg.R;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class LoginAty extends BaseAty {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}
