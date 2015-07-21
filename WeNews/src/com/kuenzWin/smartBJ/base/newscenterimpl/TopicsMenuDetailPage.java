package com.kuenzWin.smartBJ.base.newscenterimpl;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.kuenzWin.smartBJ.base.BaseMenuDetailPage;

public class TopicsMenuDetailPage extends BaseMenuDetailPage {

	public TopicsMenuDetailPage(Context mContext) {
		super(mContext);
	}

	@Override
	protected View initView() {
		TextView tv = new TextView(mContext);
		tv.setText("×¨Ìâ");
		return tv;
	}

}
