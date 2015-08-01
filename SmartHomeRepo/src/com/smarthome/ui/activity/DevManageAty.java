package com.smarthome.ui.activity;

import com.nineoldandroids.view.ViewHelper;
import com.smarthome.R;
import com.smarthome.api.model.DevCtrlAction;
import com.smarthome.presenter.DevConfigPresenter;
import com.smarthome.presenter.view.DevManageView;
import com.smarthome.ui.dialog.SeekBarDialog;
import com.smarthome.ui.fragment.JiashuFragment;
import com.smarthome.ui.view.RevealBackgroundView;
import com.smarthome.ui.view.RevealBackgroundView.OnStateChangeListener;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class DevManageAty extends BaseAty implements OnClickListener, OnStateChangeListener,DevManageView{

    private static final int USER_OPTIONS_ANIMATION_DELAY = 300;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();
    
	private RelativeLayout rootView;
	private RevealBackgroundView realView;
	private int[] revealStratPosition;
	private TextView bluetoothTv;
	private TextView wifiTv;
	private TextView volumeTv;
	private TextView brightTv;
	private RelativeLayout devProfileLayout;
	private LinearLayout onoffLayout;
	private LinearLayout otherSettingLayout;
	private Button checkDevBtn;
	private DevConfigPresenter devConfigPresenter;
	private Button devControlBtn;
	private Button dispatchConfigBtn;
	private Button changeDevArgsBtn;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dev_manage);
		Intent intent = getIntent();
		revealStratPosition = intent.getIntArrayExtra(JiashuFragment.REVEAL_BG_START_POSITION);
		
		devConfigPresenter = new DevConfigPresenter(this);
		devConfigPresenter.setPid("12345678");
		initView();
		setOnClickListenerToView();
		setRootViewPreObserver(rootView);
		
	}

	private void initView() {
		realView = (RevealBackgroundView) findViewById(R.id.devManageRevealView);
		
		rootView = (RelativeLayout)findViewById(R.id.devManageRootView);
		devProfileLayout = (RelativeLayout)findViewById(R.id.devManageProfileLayout);
		
		onoffLayout = (LinearLayout)findViewById(R.id.devManageOnoffSettingLayout);
		otherSettingLayout = (LinearLayout)findViewById(R.id.devManageOtherSettingLayout);
		
		bluetoothTv = (TextView)findViewById(R.id.devManageBluetoothTv);
		wifiTv = (TextView)findViewById(R.id.devManageWifiTv);
		volumeTv = (TextView)findViewById(R.id.devManageVolumeTv);
		brightTv = (TextView)findViewById(R.id.devManageBrightTv);
		
		checkDevBtn = (Button)findViewById(R.id.checkDevBtn);
		devControlBtn = (Button)findViewById(R.id.devControlBtn);
		dispatchConfigBtn = (Button)findViewById(R.id.dispatchConfigBtn);
		changeDevArgsBtn = (Button)findViewById(R.id.changeDevArgsBtn);
	}

	private void setOnClickListenerToView() {
		bluetoothTv.setOnClickListener(this);
		wifiTv.setOnClickListener(this);
		volumeTv.setOnClickListener(this);
		brightTv.setOnClickListener(this);
		
		checkDevBtn.setOnClickListener(this);
		devControlBtn.setOnClickListener(this);
		dispatchConfigBtn.setOnClickListener(this);
		changeDevArgsBtn.setOnClickListener(this);
	}
	
	private void setRootViewPreObserver(RelativeLayout view) {
		view.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
			
			@Override
			public boolean onPreDraw() {
				realView.setOnStateChangeListener(DevManageAty.this);
				realView.getViewTreeObserver().removeOnPreDrawListener(this);
				realView.setFillPaintColor(Color.parseColor("#009688"));
				realView.startFromLocation(revealStratPosition);
				ViewHelper.setTranslationY(onoffLayout, -onoffLayout.getHeight()-40);
				ViewHelper.setTranslationY(otherSettingLayout, -otherSettingLayout.getHeight() - onoffLayout.getHeight()-120);
				animateDevProfile();
				return true;
			}
		});
	}
	
	private void animateDevProfile() {
		
		animate(onoffLayout).translationY(0).setDuration(500).setStartDelay(USER_OPTIONS_ANIMATION_DELAY).setInterpolator(INTERPOLATOR);
		animate(otherSettingLayout).translationY(0).setDuration(400).setStartDelay(USER_OPTIONS_ANIMATION_DELAY).setInterpolator(INTERPOLATOR);
	}
	
	protected void startIntroAnimation() {
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.devManageBluetoothTv:
			bluetoothOnOff();
			break;
		case R.id.devManageWifiTv:
			wifiOnOff();
			break;
		case R.id.devManageVolumeTv:
			showVolumeSettingDialog();
			break;
		case R.id.devManageBrightTv:
			showBrightnessSettingDialog();
			break;
		case R.id.checkDevBtn:
			devConfigPresenter.checkDevArgs();
			break;
		case R.id.devControlBtn:
			devConfigPresenter.devControl(DevCtrlAction.reset);
			break;
		case R.id.dispatchConfigBtn:
			devConfigPresenter.dispatchConfig("2015-08-02 11:16:02");
			break;
		case R.id.changeDevArgsBtn:
			devConfigPresenter.changeDevArgs(null);
			break;
		}
	}

	private void bluetoothOnOff() {
		
	}

	private void wifiOnOff() {
		
	}
	
	private void showVolumeSettingDialog() {
		SeekBarDialog dialog = new SeekBarDialog(this);
		dialog.show();
	}
	
	private void showBrightnessSettingDialog() {
		SeekBarDialog dialog = new SeekBarDialog(this);
		dialog.show();
	}

	@Override
	public void showLoading() {
		
	}

	@Override
	public void hideLoading() {
		
	}

	@Override
	public void onCheckDevArgs() {
		
	}

	@Override
	public void onCheckError() {
		
	}

	@Override
	public void onChangeDevArgs() {
		
	}

	@Override
	public void onChangError() {
		
	}

	@Override
	public void onDispatchConf() {
		
	}

	@Override
	public void onDispatchConfError() {
		
	}

	@Override
	public void onDevControl() {
		
	}

	@Override
	public void onDevControlError() {
		
	}

	@Override
	public void onStateChange(int state) {

		animateDevProfile();
	}

}
