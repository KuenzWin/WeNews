package com.kuenzWin.smartBJ.base.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.gson.Gson;
import com.kuenzWin.smartBJ.MainActivity;
import com.kuenzWin.smartBJ.base.BaseMenuDetailPage;
import com.kuenzWin.smartBJ.base.BasePage;
import com.kuenzWin.smartBJ.base.newscenterimpl.InteractionMenuDetailPage;
import com.kuenzWin.smartBJ.base.newscenterimpl.NewsMenuDetailPage;
import com.kuenzWin.smartBJ.base.newscenterimpl.PhotosMenuDetailPage;
import com.kuenzWin.smartBJ.base.newscenterimpl.TopicsMenuDetailPage;
import com.kuenzWin.smartBJ.domin.NewsCenterBean;
import com.kuenzWin.smartBJ.domin.NewsCenterBean.NewsCenterData;
import com.kuenzWin.smartBJ.fragment.LeftMenuFragment;
import com.kuenzWin.smartBJ.utils.Constant;
import com.kuenzWin.smartBJ.utils.SharePreferencesUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class NewsCenterPage extends BasePage {

	/**
	 * 菜单页面的集合
	 */
	private List<BaseMenuDetailPage> pages;
	/**
	 * 菜单页面title的集合
	 */
	private List<NewsCenterData> datas;

	public NewsCenterPage(Context mContext) {
		super(mContext);
	}

	@Override
	public void initData() {
		// 从缓存中取数据,并对其进行解析
		String cache = SharePreferencesUtils.getString(mContext,
				Constant.NEWSCENTER_URL, null);
		if (!TextUtils.isEmpty(cache)) {
			processData(cache);
		}
		getDataFromNetwork();
	}

	/**
	 * 从网络上获取数据
	 */
	private void getDataFromNetwork() {
		// 进行数据请求
		HttpUtils utils = new HttpUtils();
		// RequestCallBack的泛型是请求数据的结果是什么类型
		utils.send(HttpMethod.GET, Constant.NEWSCENTER_URL,
				new RequestCallBack<String>() {

					/**
					 * 请求失败回调这个方法
					 */
					@Override
					public void onFailure(HttpException error, String msg) {

					}

					/**
					 * 请求成功回调这个方法
					 */
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// 缓存数据
						SharePreferencesUtils.putString(mContext,
								Constant.NEWSCENTER_URL, responseInfo.result);
						processData(responseInfo.result);
					}
				});
	}

	/**
	 * 解析和处理从互联网上下载的json数据
	 * 
	 * @param result
	 *            将要进行解析和处理的数据
	 */
	private void processData(String result) {
		Gson gson = new Gson();
		NewsCenterBean bean = gson.fromJson(result, NewsCenterBean.class);
		datas = bean.data;

		pages = new ArrayList<BaseMenuDetailPage>();
		pages.add(new NewsMenuDetailPage(mContext, bean.data.get(0)));
		pages.add(new TopicsMenuDetailPage(mContext));
		pages.add(new PhotosMenuDetailPage(mContext, bean.data.get(2)));
		pages.add(new InteractionMenuDetailPage(mContext));

		LeftMenuFragment lm = ((MainActivity) mContext).getLeftFragmentData();
		lm.setListData(datas);
	}

	/**
	 * 切换页面
	 * 
	 * @param pos
	 *            页面位置
	 */
	public void switchPage(int pos) {
		fl_tab_content.removeAllViews();
		// 把页面添加到FrameLayout中去
		final BaseMenuDetailPage page = pages.get(pos);
		fl_tab_content.addView(page.getRootView());
		// 改变文字
		tv_title.setText(datas.get(pos).title);

		if (pos == 2) {
			ib_title_bar_list_or_grid.setVisibility(View.VISIBLE);
			ib_title_bar_list_or_grid.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					PhotosMenuDetailPage pp = (PhotosMenuDetailPage) page;
					pp.switchList(ib_title_bar_list_or_grid);
				}
			});
		} else {
			ib_title_bar_list_or_grid.setVisibility(View.GONE);
		}
		page.initData();

	}
}
