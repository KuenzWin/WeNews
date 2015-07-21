package com.kuenzWin.smartBJ.base;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kuenzWin.smartBJ.MainActivity;
import com.kuenzWin.smartBJ.R;

/**
 * MainActivity的ViewPager中的页
 * 
 * @author Administrator
 * 
 */
public abstract class BasePage implements OnClickListener {

	protected Context mContext;
	protected ImageButton ib_title_bar_menu;
	protected TextView tv_title;
	protected FrameLayout fl_tab_content;
	protected View rootView;
	protected ImageButton ib_title_bar_list_or_grid;

	public BasePage(Context mContext) {
		this.mContext = mContext;
		rootView = this.initView();
	}

	/**
	 * 初始化View对象
	 * 
	 * @return
	 */
	private View initView() {
		View view = View.inflate(mContext, R.layout.view_base_page, null);
		ib_title_bar_menu = (ImageButton) view
				.findViewById(R.id.ib_title_bar_menu);
		ib_title_bar_menu.setOnClickListener(this);
		tv_title = (TextView) view.findViewById(R.id.tv_title_bar_title);
		fl_tab_content = (FrameLayout) view.findViewById(R.id.fl_tab_content);
		ib_title_bar_list_or_grid = (ImageButton) view
				.findViewById(R.id.ib_title_bar_list_or_grid);
		return view;
	}

	/**
	 * 获取总的View对象
	 * 
	 * @return
	 */
	public View getRootView() {
		return rootView;
	}

	public abstract void initData();

	@Override
	public void onClick(View view) {
		((MainActivity) mContext).getSlidingMenu().toggle();
	}
}
