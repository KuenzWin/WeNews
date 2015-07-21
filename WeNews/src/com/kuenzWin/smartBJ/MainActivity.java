package com.kuenzWin.smartBJ;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.kuenzWin.smartBJ.fragment.LeftMenuFragment;
import com.kuenzWin.smartBJ.fragment.MainContentFragment;

public class MainActivity extends SlidingFragmentActivity {

	/**
	 * 左侧菜单fragment的tag
	 */
	private static final String LEFT_MENU_FRAGMENT_TAG = "left_menu";
	/**
	 * 主界面fragment的tag
	 */
	private static final String MAIN_CONTENT_FRAGMENT_TAG = "main_content";
	private FragmentManager fm;
	private SlidingMenu sm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 主界面布局
		setContentView(R.layout.activity_main);
		// 左侧菜单布局
		setBehindContentView(R.layout.view_left_menu);

		sm = getSlidingMenu();
		// 设置左侧菜单可用.
		sm.setMode(SlidingMenu.LEFT);
		// 整个屏幕都可以拖拽出菜单.
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		// 设置主界面留在屏幕上的宽度
		sm.setBehindOffset(200);
		// 设置滑动菜单的阴影
		sm.setShadowDrawable(R.drawable.bg_shadow);
		// 设置阴影宽度
		sm.setShadowWidthRes(R.dimen.shadow_width);

		fm = this.getSupportFragmentManager();

		this.initFragment();
	}

	private void initFragment() {
		FragmentTransaction ft = fm.beginTransaction();
		// 替换左侧菜单布局
		ft.replace(R.id.fl_leftFrame, new LeftMenuFragment(),
				LEFT_MENU_FRAGMENT_TAG);
		// 替换主界面布局
		ft.replace(R.id.fl_main, new MainContentFragment(),
				MAIN_CONTENT_FRAGMENT_TAG);
		ft.commit();
	}

	/**
	 * 获取左侧菜单
	 * 
	 * @return 左侧菜单
	 */
	public LeftMenuFragment getLeftFragmentData() {
		LeftMenuFragment lm = (LeftMenuFragment) fm
				.findFragmentByTag(LEFT_MENU_FRAGMENT_TAG);
		return lm;
	}

	/**
	 * 获取内容菜单
	 * 
	 * @return 内容菜单
	 */
	public MainContentFragment getMainContentFragment() {
		MainContentFragment mf = (MainContentFragment) fm
				.findFragmentByTag(MAIN_CONTENT_FRAGMENT_TAG);
		return mf;
	}

	/**
	 * 设置滑动菜单可用与否，即可被滑动与否
	 */
	public void setSlidingMenuEnableOrNot(boolean isEnable) {
		if (isEnable)
			sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		else
			sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
	}
}
