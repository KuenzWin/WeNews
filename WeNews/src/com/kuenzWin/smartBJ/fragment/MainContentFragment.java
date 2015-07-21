package com.kuenzWin.smartBJ.fragment;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.kuenzWin.smartBJ.MainActivity;
import com.kuenzWin.smartBJ.R;
import com.kuenzWin.smartBJ.base.BaseFragment;
import com.kuenzWin.smartBJ.base.BasePage;
import com.kuenzWin.smartBJ.base.impl.GovAffairsPage;
import com.kuenzWin.smartBJ.base.impl.HomePage;
import com.kuenzWin.smartBJ.base.impl.NewsCenterPage;
import com.kuenzWin.smartBJ.base.impl.SettingPage;
import com.kuenzWin.smartBJ.base.impl.SmartServicePage;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MainContentFragment extends BaseFragment implements
		OnCheckedChangeListener {

	@ViewInject(R.id.pager_main)
	private ViewPager pager_main;
	@ViewInject(R.id.rg_main)
	private RadioGroup rg_main;
	/**
	 * 内容页面集合
	 */
	private List<BasePage> pages;

	@Override
	protected View initView() {
		View view = View.inflate(mContext, R.layout.view_content_layout, null);
		ViewUtils.inject(this, view);
		pager_main.setAdapter(new ContentPagerAdapter());
		rg_main.setOnCheckedChangeListener(this);
		return view;
	}

	@Override
	protected void initData() {
		pages = new ArrayList<BasePage>();
		BasePage page = new HomePage(mContext);
		page.initData();
		pages.add(page);
		pages.add(new NewsCenterPage(mContext));
		pages.add(new SmartServicePage(mContext));
		pages.add(new GovAffairsPage(mContext));
		pages.add(new SettingPage(mContext));
	}

	private class ContentPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return pages.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BasePage page = pages.get(position);
			View view = page.getRootView();
			page.initData();
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int pos = -1;
		switch (checkedId) {
		case R.id.rb_content_fragment_home:
			pos = 0;
			break;
		case R.id.rb_content_fragment_newscenter:
			pos = 1;
			break;
		case R.id.rb_content_fragment_smartservice:
			pos = 2;
			break;
		case R.id.rb_content_fragment_govaffairs:
			pos = 3;
			break;
		case R.id.rb_content_fragment_settings:
			pos = 4;
			break;
		}

		if (pos == 0 || pos == 4) {
			((MainActivity) mContext).getSlidingMenu().setTouchModeAbove(
					SlidingMenu.TOUCHMODE_NONE);
		} else {
			((MainActivity) mContext).getSlidingMenu().setTouchModeAbove(
					SlidingMenu.TOUCHMODE_FULLSCREEN);
		}
		pages.get(pos).initData();
		pager_main.setCurrentItem(pos);
	}

	/**
	 * 获取新闻页面
	 * 
	 * @return 新闻页面
	 */
	public NewsCenterPage getNewsCenterPage() {
		return (NewsCenterPage) pages.get(1);
	}

}
