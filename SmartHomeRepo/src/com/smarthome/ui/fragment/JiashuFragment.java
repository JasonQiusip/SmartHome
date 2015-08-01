package com.smarthome.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.smarthome.R;
import com.smarthome.ui.activity.DevManageAty;
import com.smarthome.ui.activity.MainContentAty;

public class JiashuFragment extends BaseFragment implements View.OnClickListener {
	
	
	public static final String REVEAL_BG_START_POSITION = "revealBgStartPosition";
	private static final String value = null;

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		View rootView = layoutInflater.inflate(R.layout.fragment_jiashu, container, false);
		Button devManageBtn = (Button) rootView.findViewById(R.id.jiashuDevManageBtn);
		devManageBtn.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.jiashuDevManageBtn:
			int[] btnPosition = getCurrentButtonPosition(v);
			startDevMangeAty(btnPosition);
			break;
		}
	}
	
	private int[] getCurrentButtonPosition(View v) {
		int[] devManageBtnPosition = new int[2];
		v.getLocationOnScreen(devManageBtnPosition);
		devManageBtnPosition[0] += v.getWidth() / 2;
		return devManageBtnPosition;
	}

	private void startDevMangeAty(int[] devManageBtnPosition){
		Intent intent = new Intent();
		intent.setClass(getActivity(), DevManageAty.class);
		intent.putExtra(REVEAL_BG_START_POSITION, devManageBtnPosition);
		startActivity(intent);
		getActivity().overridePendingTransition(0, 0);
	}
}
