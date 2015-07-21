package com.kuenzWin.smartBJ.base.impl;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.kuenzWin.smartBJ.base.BasePage;

public class SettingPage extends BasePage {

	public SettingPage(Context mContext) {
		super(mContext);
	}

	@Override
	public void initData() {
		tv_title.setText("设置中心");
		ib_title_bar_menu.setVisibility(View.GONE);

		TextView tv = new TextView(mContext);
		tv.setText("设置中心");
		tv.setTextColor(Color.RED);
		tv.setTextSize(25);
		tv.setGravity(Gravity.CENTER);

		fl_tab_content.addView(tv);
	}

}
