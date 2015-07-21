package com.kuenzWin.smartBJ.base.newscenterimpl;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.kuenzWin.smartBJ.base.BaseMenuDetailPage;

public class InteractionMenuDetailPage extends BaseMenuDetailPage {

	public InteractionMenuDetailPage(Context mContext) {
		super(mContext);
	}

	@Override
	protected View initView() {
		TextView tv = new TextView(mContext);
		tv.setText("½»»¥");
		return tv;
	}

	
}
