package com.kuenzWin.smartBJ;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

@ContentView(R.layout.activity_guide)
public class GuideActivity extends Activity implements OnPageChangeListener,
		OnGlobalLayoutListener {

	@ViewInject(R.id.pager_guide)
	private ViewPager pager_guide;
	@ViewInject(R.id.btn_guide_start)
	private Button btn_guide_start;
	@ViewInject(R.id.ll_guide_point_groups)
	private LinearLayout ll_guide_point_groups;
	@ViewInject(R.id.view_point_selected)
	private View view_point_selected;

	private ArrayList<ImageView> ivs;
	private int[] imgIds;

	// 两个导航点之间的宽度
	private int basicWidth;

	/**
	 * 选中的页面的位置
	 */
	private int selectedPos = 0;

	private PagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);

		initData();
		adapter = new GuideAdapter();
		pager_guide.setAdapter(adapter);
		pager_guide.setOnPageChangeListener(this);
	}

	private void initData() {
		imgIds = new int[] { R.drawable.guide_1, R.drawable.guide_2,
				R.drawable.guide_3 };
		ivs = new ArrayList<ImageView>();
		ImageView img = null;

		for (int i = 0; i < imgIds.length; i++) {
			img = new ImageView(this);
			img.setBackgroundResource(imgIds[i]);
			ivs.add(img);
		}

		this.setGuidePoint(0);

		// view绘制流程: measure -> layout -> draw
		// 监听mSelectPointView控件layout

		// 获得视图树的观察者, 监听全部布局的回调
		ll_guide_point_groups.getViewTreeObserver().addOnGlobalLayoutListener(
				this);
	}

	@Override
	public void onGlobalLayout() {
		// 只执行一次, 把当前的事件从视图树的观察者中移除掉
		ll_guide_point_groups.getViewTreeObserver()
				.removeGlobalOnLayoutListener(this);
		// 取出两个点之间的宽度
		basicWidth = ll_guide_point_groups.getChildAt(1).getLeft()
				- ll_guide_point_groups.getChildAt(0).getLeft();
	}

	/**
	 * 设置导航点
	 * 
	 * @param position
	 *            当前页面位置
	 */
	private void setGuidePoint(int position) {
		ll_guide_point_groups.removeAllViews();
		LayoutParams lp = null;
		for (int i = 0; i < imgIds.length; i++) {
			View view = new View(this);
			lp = new LayoutParams(10, 10);
			if (i == position)
				view.setBackgroundResource(R.drawable.bg_point_selected);
			else
				view.setBackgroundResource(R.drawable.bg_point_normal);
			if (i != 0) {
				lp.leftMargin = 10;
			}
			view.setLayoutParams(lp);
			ll_guide_point_groups.addView(view);
		}
	}

	private class GuideAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return ivs.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView iv = ivs.get(position);
			container.addView(iv);
			return iv;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	};

	/**
	 * @param position
	 *            有三种状态 :0，1，2。arg0
	 *            ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。
	 */
	@Override
	public void onPageScrollStateChanged(int state) {
		if (state == 1) {
			ll_guide_point_groups.getChildAt(selectedPos)
					.setBackgroundResource(R.drawable.bg_point_normal);
		}
	}

	/**
	 * @param position
	 *            :当前页面，及你点击滑动的页面
	 * 
	 * @param positionOffset
	 *            :当前页面偏移的百分比
	 * 
	 * @param positionOffsetPixels
	 *            :当前页面偏移的像素位置
	 */
	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		int leftMargin = (int) (basicWidth * (position + positionOffset));

		RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) view_point_selected
				.getLayoutParams();

		rlp.leftMargin = leftMargin;
		view_point_selected.setLayoutParams(rlp);

		if (position == imgIds.length - 1 && positionOffset > 0.3) {
			ll_guide_point_groups.getChildAt(position).setBackgroundResource(
					R.drawable.bg_point_normal);
		}
	}

	/**
	 * 页面被选中
	 */
	@Override
	public void onPageSelected(int position) {
		if (position == imgIds.length - 1) {
			btn_guide_start.setVisibility(View.VISIBLE);
		} else {
			btn_guide_start.setVisibility(View.GONE);
		}
		ll_guide_point_groups.getChildAt(selectedPos).setBackgroundResource(
				R.drawable.bg_point_normal);
		ll_guide_point_groups.getChildAt(position).setBackgroundResource(
				R.drawable.bg_point_normal);
		selectedPos = position;
	}

	@OnClick(R.id.btn_guide_start)
	public void onClick(View view) {
		this.startActivity(new Intent(this, MainActivity.class));
		this.finish();
	}

}
