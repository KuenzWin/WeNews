package com.kuenzWin.smartBJ.base.impl;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.kuenzWin.smartBJ.base.BasePage;

public class GovAffairsPage extends BasePage {

	public GovAffairsPage(Context mContext) {
		super(mContext);
	}

	@Override
	public void initData() {
		tv_title.setText("政府政务");

		TextView tv = new TextView(mContext);
		tv.setText("政府政务");
		tv.setTextColor(Color.RED);
		tv.setTextSize(25);
		tv.setGravity(Gravity.CENTER);

		fl_tab_content.addView(tv);
	}

}
