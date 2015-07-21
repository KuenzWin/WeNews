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
	 * ���˵�fragment��tag
	 */
	private static final String LEFT_MENU_FRAGMENT_TAG = "left_menu";
	/**
	 * ������fragment��tag
	 */
	private static final String MAIN_CONTENT_FRAGMENT_TAG = "main_content";
	private FragmentManager fm;
	private SlidingMenu sm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// �����沼��
		setContentView(R.layout.activity_main);
		// ���˵�����
		setBehindContentView(R.layout.view_left_menu);

		sm = getSlidingMenu();
		// �������˵�����.
		sm.setMode(SlidingMenu.LEFT);
		// ������Ļ��������ק���˵�.
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		// ����������������Ļ�ϵĿ��
		sm.setBehindOffset(200);
		// ���û����˵�����Ӱ
		sm.setShadowDrawable(R.drawable.bg_shadow);
		// ������Ӱ���
		sm.setShadowWidthRes(R.dimen.shadow_width);

		fm = this.getSupportFragmentManager();

		this.initFragment();
	}

	private void initFragment() {
		FragmentTransaction ft = fm.beginTransaction();
		// �滻���˵�����
		ft.replace(R.id.fl_leftFrame, new LeftMenuFragment(),
				LEFT_MENU_FRAGMENT_TAG);
		// �滻�����沼��
		ft.replace(R.id.fl_main, new MainContentFragment(),
				MAIN_CONTENT_FRAGMENT_TAG);
		ft.commit();
	}

	/**
	 * ��ȡ���˵�
	 * 
	 * @return ���˵�
	 */
	public LeftMenuFragment getLeftFragmentData() {
		LeftMenuFragment lm = (LeftMenuFragment) fm
				.findFragmentByTag(LEFT_MENU_FRAGMENT_TAG);
		return lm;
	}

	/**
	 * ��ȡ���ݲ˵�
	 * 
	 * @return ���ݲ˵�
	 */
	public MainContentFragment getMainContentFragment() {
		MainContentFragment mf = (MainContentFragment) fm
				.findFragmentByTag(MAIN_CONTENT_FRAGMENT_TAG);
		return mf;
	}

	/**
	 * ���û����˵�������񣬼��ɱ��������
	 */
	public void setSlidingMenuEnableOrNot(boolean isEnable) {
		if (isEnable)
			sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		else
			sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
	}
}
