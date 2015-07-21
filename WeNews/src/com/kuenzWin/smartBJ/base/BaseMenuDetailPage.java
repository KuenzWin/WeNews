package com.kuenzWin.smartBJ.base;

import android.content.Context;
import android.view.View;

/**
 * 菜单页的基类
 * 
 * @author Administrator
 * 
 */
public abstract class BaseMenuDetailPage {

	protected Context mContext;
	private View rootView;

	public BaseMenuDetailPage(Context mContext) {
		this.mContext = mContext;
		rootView = initView();
	}

	protected abstract View initView();

	public View getRootView() {
		return rootView;
	}
	
	public void initData(){
		
	}

}
