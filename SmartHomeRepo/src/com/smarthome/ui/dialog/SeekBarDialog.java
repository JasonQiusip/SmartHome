package com.smarthome.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.rey.material.widget.Slider;
import com.rey.material.widget.Slider.OnPositionChangeListener;
import com.smarthome.R;

public class SeekBarDialog extends Dialog implements OnPositionChangeListener{

	private Slider slidr;

	public SeekBarDialog(Context context) {
		super(context);
		setContentView(R.layout.dialog_seekbar);
		slidr = (Slider)findViewById(R.id.slider_sl_discrete);
		slidr.setOnPositionChangeListener(this);
	}

	@Override
	public void onPositionChanged(Slider view, boolean fromUser, float oldPos,
			float newPos, int oldValue, int newValue) {
		Log.e("onPositionChanged", ""+oldPos+" " + newPos + " " + oldValue +" " + newValue);
	}
	
	


}
