package com.kuenzWin.smartBJ.base.newscenterimpl;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.kuenzWin.smartBJ.MainActivity;
import com.kuenzWin.smartBJ.R;
import com.kuenzWin.smartBJ.base.BaseMenuDetailPage;
import com.kuenzWin.smartBJ.domin.NewsCenterBean.ChildRen;
import com.kuenzWin.smartBJ.domin.NewsCenterBean.NewsCenterData;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;

public class NewsMenuDetailPage extends BaseMenuDetailPage implements
		OnPageChangeListener {

	@ViewInject(R.id.tpi_news_menu)
	private TabPageIndicator tpi_news_menu;
	@ViewInject(R.id.ib_news_menu_next_tab)
	private ImageButton ib_news_menu_next_tab;
	@ViewInject(R.id.vp_news_menu_content)
	private ViewPager vp_news_menu_content;

	/**
	 * 当前页面页签数据
	 */
	private List<ChildRen> children;
	/**
	 * 页签详情页面
	 */
	private List<NewMenuTabDetailPage> pages;

	public NewsMenuDetailPage(Context mContext) {
		super(mContext);
	}

	public NewsMenuDetailPage(Context mContext, NewsCenterData newsCenterData) {
		super(mContext);
		this.children = newsCenterData.children;
		initData();
	}

	@Override
	protected View initView() {
		View view = View.inflate(mContext, R.layout.view_news_menu, null);
		// 将view对象注入到XUtils框架中
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void initData() {
		pages = new ArrayList<NewMenuTabDetailPage>();
		for (int i = 0; i < children.size(); i++) {
			pages.add(new NewMenuTabDetailPage(mContext, children.get(i)));
		}

		NewsDatailPagerAdapter adapter = new NewsDatailPagerAdapter();
		vp_news_menu_content.setAdapter(adapter);
		// 把ViewPager对象设置给TabPageIndicator
		// 关联完之后页签的数据都来自于mViewPager的数据适配NewsMenuAdapter
		tpi_news_menu.setViewPager(vp_news_menu_content);
		vp_news_menu_content.setOnPageChangeListener(this);
	}

	@OnClick(R.id.vp_news_menu_content)
	public void nextTab(View v) {
		// 下一个页签按钮的点击事件
		vp_news_menu_content.setCurrentItem(vp_news_menu_content
				.getCurrentItem() + 1);
	}

	private class NewsDatailPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return children.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			NewMenuTabDetailPage page = pages.get(position);
			View view = page.getRootView();
			container.addView(view);
			//初始化数据
			page.initData();
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return children.get(position).title;
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int pos) {
		((MainActivity) mContext).setSlidingMenuEnableOrNot(pos == 0);
		// 不加上这一句，指针不发生移动
		tpi_news_menu.setCurrentItem(pos);
	}

}
