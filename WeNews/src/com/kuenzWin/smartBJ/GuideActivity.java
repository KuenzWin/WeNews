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

	// ����������֮��Ŀ��
	private int basicWidth;

	/**
	 * ѡ�е�ҳ���λ��
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

		// view��������: measure -> layout -> draw
		// ����mSelectPointView�ؼ�layout

		// �����ͼ���Ĺ۲���, ����ȫ�����ֵĻص�
		ll_guide_point_groups.getViewTreeObserver().addOnGlobalLayoutListener(
				this);
	}

	@Override
	public void onGlobalLayout() {
		// ִֻ��һ��, �ѵ�ǰ���¼�����ͼ���Ĺ۲������Ƴ���
		ll_guide_point_groups.getViewTreeObserver()
				.removeGlobalOnLayoutListener(this);
		// ȡ��������֮��Ŀ��
		basicWidth = ll_guide_point_groups.getChildAt(1).getLeft()
				- ll_guide_point_groups.getChildAt(0).getLeft();
	}

	/**
	 * ���õ�����
	 * 
	 * @param position
	 *            ��ǰҳ��λ��
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
	 *            ������״̬ :0��1��2��arg0
	 *            ==1��ʱ��Ĭʾ���ڻ�����arg0==2��ʱ��Ĭʾ��������ˣ�arg0==0��ʱ��Ĭʾʲô��û����
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
	 *            :��ǰҳ�棬������������ҳ��
	 * 
	 * @param positionOffset
	 *            :��ǰҳ��ƫ�Ƶİٷֱ�
	 * 
	 * @param positionOffsetPixels
	 *            :��ǰҳ��ƫ�Ƶ�����λ��
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
	 * ҳ�汻ѡ��
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
