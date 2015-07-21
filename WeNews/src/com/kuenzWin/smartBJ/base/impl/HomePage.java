package com.kuenzWin.smartBJ.base.impl;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.kuenzWin.smartBJ.R;
import com.kuenzWin.smartBJ.base.BasePage;

public class HomePage extends BasePage {

	public HomePage(Context mContext) {
		super(mContext);
	}

	@Override
	public void initData() {
		String appName = mContext.getResources().getString(R.string.app_name);
		tv_title.setText(appName);
		ib_title_bar_menu.setVisibility(View.GONE);

		TextView tv = new TextView(mContext);
		tv.setText(appName);
		tv.setTextColor(Color.RED);
		tv.setTextSize(25);
		tv.setGravity(Gravity.CENTER);

		fl_tab_content.addView(tv);
	}

}
