package com.smarthome.ui.activity;

import com.smarthome.R;
import com.smarthome.presenter.AccountCheckPresenter;
import com.smarthome.presenter.view.AccountCheckView;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AccountCheckAty extends BaseAty implements OnClickListener,
		AccountCheckView {

	static final String MOBILE_EXTRA_KEY = "mobile";
	static final String ACCOUNT_SUFFIX = "@jiashu.com";
	private static final String ACCOUNTNOTEXIST = "0";
	private static final String ACCOUNTNOTEMAIL = "1";
	private static final String ACCOUNTEXIST = "2";
	private EditText edtMobile;
	private Button btnCheckAccount;
	private AccountCheckPresenter mobileCheckPresenter;
	private Snackbar snackBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_check);
		edtMobile = (EditText) findViewById(R.id.edtMobile);
		btnCheckAccount = (Button) findViewById(R.id.btnCheckAccount);
		btnCheckAccount.setOnClickListener(this);
		mobileCheckPresenter = new AccountCheckPresenter(this);
	}

	private void checkAccount(){
		mobileCheckPresenter.startMobileCheck(edtMobile.getText().toString()+ACCOUNT_SUFFIX);
	}
	
	private void startLoginAty() {
		Intent intent = new Intent();
		intent.setClass(this, LoginAty.class);
		intent.putExtra(MOBILE_EXTRA_KEY, edtMobile.getText().toString());
		startActivity(intent);
	}
	
	private void startRegisterAty() {
		Intent intent = new Intent();
		intent.setClass(this, RegisterAty.class);
		intent.putExtra(MOBILE_EXTRA_KEY, edtMobile.getText().toString());
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnCheckAccount:
			checkAccount();
			break;
		}
	}

	@Override
	public void showLoading() {
		progressDialog.setMessage("ÕýÔÚ¼ì²éÕËºÅ...");
		progressDialog.show();
	}

	@Override
	public void hideLoading() {
		progressDialog.dismiss();
	}

	@Override
	public void onReqSuccess(String response) {
		if(ACCOUNTNOTEXIST.equals(response)){
			startRegisterAty();
			finish();
		}else if (ACCOUNTNOTEMAIL.equals(response)){
			snackBar = Snackbar.make(this.getWindow().getDecorView(), R.string.account_format_error, Snackbar.LENGTH_SHORT);
			snackBar.show();
		}else if(ACCOUNTEXIST.equals(response)){
			startLoginAty();
			finish();
		}
	}

	@Override
	public void onReqError() {

	}

}
