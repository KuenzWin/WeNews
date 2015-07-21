package com.kuenzWin.smartBJ.base.impl;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.kuenzWin.smartBJ.base.BasePage;

public class SmartServicePage extends BasePage {

	public SmartServicePage(Context mContext) {
		super(mContext);
	}

	@Override
	public void initData() {
		tv_title.setText("智能服务");

		TextView tv = new TextView(mContext);
		tv.setText("智能服务");
		tv.setTextColor(Color.RED);
		tv.setTextSize(25);
		tv.setGravity(Gravity.CENTER);

		fl_tab_content.addView(tv);
	}

}
