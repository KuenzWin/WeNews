package com.kuenzWin.smartBJ.base.newscenterimpl;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kuenzWin.smartBJ.NewsDetailActivity;
import com.kuenzWin.smartBJ.R;
import com.kuenzWin.smartBJ.base.BaseMenuDetailPage;
import com.kuenzWin.smartBJ.domin.NewsCenterBean.ChildRen;
import com.kuenzWin.smartBJ.domin.TabDetailBean;
import com.kuenzWin.smartBJ.domin.TabDetailBean.News;
import com.kuenzWin.smartBJ.domin.TabDetailBean.TopNew;
import com.kuenzWin.smartBJ.utils.Constant;
import com.kuenzWin.smartBJ.utils.SharePreferencesUtils;
import com.kuenzWin.smartBJ.view.HorizontalScrollViewPager;
import com.kuenzWin.smartBJ.view.RefreshListView;
import com.kuenzWin.smartBJ.view.RefreshListView.OnRefreshListener;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class NewMenuTabDetailPage extends BaseMenuDetailPage implements
		OnPageChangeListener, OnRefreshListener, OnItemClickListener {

	@ViewInject(R.id.hsvp_news_menu_tab_detail_top_news)
	private HorizontalScrollViewPager mPager;

	@ViewInject(R.id.tv_news_menu_tab_detail_description)
	private TextView tv_news_menu_tab_detail_description;

	@ViewInject(R.id.ll_news_menu_tab_detail_point_group)
	private LinearLayout ll_news_menu_tab_detail_point_group;

	@ViewInject(R.id.lv)
	private RefreshListView lv;

	private ChildRen childRen;

	/**
	 * 新闻轮播图片的url
	 */
	private String url;

	/**
	 * 新闻的集合
	 */
	private List<TopNew> TopNewsList;

	private BitmapUtils bu;
	/**
	 * ViewPager的适配器
	 */
	private TopNewsAdapter topAdapter;

	/**
	 * 正在被选择的图片ViewPager的页面下标，默认选中第一个
	 */
	private int selectedPos = 0;

	/**
	 * 切换图片的事件间隔
	 */
	private static final Long SWITCH_DELAY = 3 * 1000L;

	/**
	 * 切换的Handler
	 */
	private Handler mHandler;

	/**
	 * ListView的列表新闻集合
	 */
	private List<News> newsList;

	/**
	 * ListView的适配器
	 */
	private NewsAdapter newsApter;

	private HttpUtils utils;

	/**
	 * 加载更多的url
	 */
	private String moreUrl;

	/**
	 * 读过的新闻id数组
	 */
	private final String READ_NEWS_ID_ARRAY_KEY = "read_news_id_array";

	public NewMenuTabDetailPage(Context mContext) {
		super(mContext);
	}

	public NewMenuTabDetailPage(Context mContext, ChildRen childRen) {
		super(mContext);

		this.childRen = childRen;

		bu = new BitmapUtils(mContext);
		bu.configDefaultBitmapConfig(Config.ARGB_8888);

		url = Constant.SERVICE_URL + childRen.url;

		String result = SharePreferencesUtils.getString(mContext, url, null);
		if (!TextUtils.isEmpty(result)) {
			processData(result);
		}
		initData();
	}

	@Override
	protected View initView() {
		View view = View.inflate(mContext, R.layout.view_news_menu_tab_detail,
				null);
		ViewUtils.inject(this, view);
		View topView = View.inflate(mContext,
				R.layout.view_news_menu_tab_detail_topnews, null);
		ViewUtils.inject(this, topView);
		lv.addCustomHeaderView(topView);
		lv.isEnabledPullDownRefresh(true);
		return view;
	}

	@Override
	public void initData() {
		super.initData();

		utils = new HttpUtils();

		utils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				processDataFromNetwork(responseInfo);
			}

		});
	}

	/**
	 * 处理从网上获取的数据
	 * 
	 * @param responseInfo
	 *            网上获取的数据
	 */
	private void processDataFromNetwork(ResponseInfo<String> responseInfo) {
		String result = responseInfo.result;
		SharePreferencesUtils.putString(mContext, url, result);
		processData(result);
	}

	/**
	 * 处理json数据,使之转化为bean对象
	 * 
	 * @param result
	 *            json数据
	 */
	private void processData(String result) {
		TabDetailBean tb = parseJson(result);

		ll_news_menu_tab_detail_point_group.removeAllViews();
		for (int i = 0; i < TopNewsList.size(); i++) {
			ImageView iv = new ImageView(mContext);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(5, 5);
			iv.setEnabled(false);
			lp.leftMargin = 10;
			iv.setImageResource(R.drawable.tab_detail_top_news_point_bg);
			iv.setLayoutParams(lp);
			ll_news_menu_tab_detail_point_group.addView(iv);
		}
		ll_news_menu_tab_detail_point_group.getChildAt(0).setEnabled(true);
		tv_news_menu_tab_detail_description.setText(TopNewsList.get(0).title);

		if (topAdapter == null) {
			topAdapter = new TopNewsAdapter();
			mPager.setAdapter(topAdapter);
			mPager.setOnPageChangeListener(this);
		} else {
			topAdapter.notifyDataSetChanged();
		}

		if (mHandler == null)
			mHandler = new InternalHandler();
		mHandler.removeCallbacksAndMessages(null);
		mHandler.postDelayed(new AutoSwitchRunnbale(), SWITCH_DELAY);

		newsList = tb.data.news;
		newsApter = new NewsAdapter();
		lv.setAdapter(newsApter);
		lv.setOnRefreshListener(this);
		lv.setOnItemClickListener(this);
	}

	private TabDetailBean parseJson(String result) {
		Gson gson = new Gson();
		TabDetailBean tb = gson.fromJson(result, TabDetailBean.class);
		if (tb == null)
			LogUtils.d("tb is  null");
		TopNewsList = tb.data.topnews;
		moreUrl = tb.data.more;
		if (!TextUtils.isEmpty(moreUrl)) {
			moreUrl = Constant.SERVICE_URL + moreUrl;
		}
		return tb;
	}

	/**
	 * 新闻轮播的Handler
	 * 
	 * @author 温坤哲
	 * 
	 */
	private class InternalHandler extends Handler {
		public void handleMessage(android.os.Message msg) {
			int pos = (mPager.getCurrentItem() + 1) % topAdapter.getCount();
			mPager.setCurrentItem(pos);
			mHandler.postDelayed(new AutoSwitchRunnbale(), SWITCH_DELAY);
		}
	};

	/**
	 * 切换图片的Runnable
	 * 
	 * @author 温坤哲
	 * 
	 */
	private class AutoSwitchRunnbale implements Runnable {
		@Override
		public void run() {
			// 先得到自身的消息，再发给自己
			mHandler.obtainMessage().sendToTarget();
		}
	}

	/**
	 * 新闻图片ViewPager的适配器类
	 * 
	 * @author 温坤哲
	 * 
	 */
	private class TopNewsAdapter extends PagerAdapter implements
			OnTouchListener {

		@Override
		public int getCount() {
			return TopNewsList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView iv = new ImageView(mContext);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setImageResource(R.drawable.home_scroll_default);

			String imgUrl = TopNewsList.get(position).topimage;
			/**
			 * 第一个参数是 把图片将要显示在哪一个控件上: iv.setImageBitmap 第二个参数是 图片的url地址
			 */
			bu.display(iv, imgUrl);

			container.addView(iv);

			iv.setOnTouchListener(this);

			return iv;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			/*
			 * 按下图片就取消轮播，抬起就重新启动轮播
			 */
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mHandler.removeCallbacksAndMessages(null);
				break;
			case MotionEvent.ACTION_UP:
				mHandler.postDelayed(new AutoSwitchRunnbale(), SWITCH_DELAY);
				break;
			default:
				break;
			}
			return false;
		}

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onPageScrolled(int pos, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int pos) {
		// 改变ViewPager的导航点的被选中与未选中状态
		ll_news_menu_tab_detail_point_group.getChildAt(selectedPos).setEnabled(
				false);
		ll_news_menu_tab_detail_point_group.getChildAt(pos).setEnabled(true);
		selectedPos = pos;
		tv_news_menu_tab_detail_description.setText(TopNewsList.get(pos).title);
		mHandler.removeCallbacksAndMessages(null);
		mHandler.postDelayed(new AutoSwitchRunnbale(), SWITCH_DELAY);
	}

	/**
	 * 新闻ListView的适配器
	 * 
	 * @author 温坤哲
	 * 
	 */
	private class NewsAdapter extends BaseAdapter {

		class ViewHolder {
			ImageView iv_image;
			TextView tv_title;
			TextView tv_date;
		}

		private ViewHolder holder;

		@Override
		public int getCount() {
			return newsList.size();
		}

		@Override
		public Object getItem(int position) {
			return newsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = View.inflate(mContext,
						R.layout.view_news_menu_tab_detail_news_item, null);
				holder = new ViewHolder();
				holder.iv_image = (ImageView) convertView
						.findViewById(R.id.iv_news_menu_tab_detail_news_item_image);
				holder.tv_title = (TextView) convertView
						.findViewById(R.id.tv_news_menu_tab_detail_news_item_title);
				holder.tv_date = (TextView) convertView
						.findViewById(R.id.tv_news_menu_tab_detail_news_item_date);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// 给控件赋值.
			News news = newsList.get(position);
			holder.tv_title.setText(news.title);
			holder.tv_date.setText(news.pubdate);
			bu.display(holder.iv_image, news.listimage);
			String readed_str = SharePreferencesUtils.getString(mContext,
					READ_NEWS_ID_ARRAY_KEY, "");
			if (!TextUtils.isEmpty(readed_str) && readed_str.contains(news.id)) {
				holder.tv_title.setTextColor(Color.GRAY);
			} else {
				holder.tv_title.setTextColor(Color.BLACK);
			}
			return convertView;
		}
	}

	@Override
	public void onPullDownRefresh() {
		utils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				lv.onRefreshFinish();
				processDataFromNetwork(responseInfo);
				Toast.makeText(mContext, "refreshed", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				lv.onRefreshFinish();
			}
		});
	}

	@Override
	public void onLoadingMore() {
		// 查看是否还有更多数据
		if (TextUtils.isEmpty(moreUrl)) {
			lv.onRefreshFinish(); // 把脚布局隐藏
			Toast.makeText(mContext, "没有更多数据了", 0).show();
		} else {
			// 加载更多数据
			utils.send(HttpMethod.GET, moreUrl, new RequestCallBack<String>() {

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					lv.onRefreshFinish(); // 把脚布局隐藏
					Toast.makeText(mContext, "加载更多成功", 0).show();

					// 把最新加载出来的数据, 添加到newsList集合中. 刷新ListView
					TabDetailBean bean = parseJson(responseInfo.result);
					newsList.addAll(bean.data.news);
					topAdapter.notifyDataSetChanged();
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					lv.onRefreshFinish(); // 把脚布局隐藏
					Toast.makeText(mContext, "加载更多失败", 0).show();
				}
			});
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		News news = newsList.get(position - 1);
		// 把当前被点击的这条新闻的id存起来 1#2#3#4

		String readNewsIDArray = SharePreferencesUtils.getString(mContext,
				READ_NEWS_ID_ARRAY_KEY, null);

		String idArray = null;
		if (TextUtils.isEmpty(readNewsIDArray)) {
			idArray = news.id;
		} else {
			idArray = readNewsIDArray + "#" + news.id;
		}
		SharePreferencesUtils.putString(mContext, READ_NEWS_ID_ARRAY_KEY,
				idArray);
		newsApter.notifyDataSetChanged();

		Intent intent =  new Intent(mContext, NewsDetailActivity.class);
		intent.putExtra("url", news.url);
		mContext.startActivity(intent);
	}
}
