package com.kuenzWin.smartBJ.base.newscenterimpl;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kuenzWin.smartBJ.R;
import com.kuenzWin.smartBJ.base.BaseMenuDetailPage;
import com.kuenzWin.smartBJ.domin.NewsCenterBean.NewsCenterData;
import com.kuenzWin.smartBJ.domin.PhotosBean;
import com.kuenzWin.smartBJ.domin.PhotosBean.PhotosItem;
import com.kuenzWin.smartBJ.utils.Constant;
import com.kuenzWin.smartBJ.utils.SharePreferencesUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class PhotosMenuDetailPage extends BaseMenuDetailPage {

	private ListView lv_photos;
	private GridView gv_photos;
	private List<PhotosItem> photosList; // 组图数据

	private boolean isList = true;
	private PhotosAdapter mAdapter;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

		}
	};

	public boolean isList() {
		return isList;
	}

	public PhotosMenuDetailPage(Context mContext) {
		super(mContext);
	}

	public PhotosMenuDetailPage(Context mContext, NewsCenterData newsCenterData) {
		super(mContext);
	}

	@Override
	protected View initView() {
		View view = View.inflate(mContext, R.layout.view_photos, null);
		lv_photos = (ListView) view.findViewById(R.id.lv_photos);
		gv_photos = (GridView) view.findViewById(R.id.gv_photos);
		return view;
	}

	@Override
	public void initData() {
		super.initData();

		String cache = SharePreferencesUtils.getString(mContext,
				Constant.PHOTOS_URL, "");
		if (!TextUtils.isEmpty(cache)) {
			this.processData(cache);
		}

		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, Constant.PHOTOS_URL,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						SharePreferencesUtils.putString(mContext,
								Constant.PHOTOS_URL, responseInfo.result);
						processData(responseInfo.result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub

					}
				});

	}

	private void processData(String json) {
		Gson gson = new Gson();
		PhotosBean bean = gson.fromJson(json, PhotosBean.class);

		photosList = bean.data.news;
		mAdapter = new PhotosAdapter();
		lv_photos.setAdapter(mAdapter);
	}

	private class PhotosAdapter extends BaseAdapter {

		class PhotosViewHolder {
			public ImageView ivImage;
			public TextView tvTitle;
		}

		// private BitmapUtils bu;

		@Override
		public int getCount() {
			return photosList.size();
		}

		@Override
		public Object getItem(int position) {
			return photosList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			PhotosViewHolder mHolder = null;

			if (convertView == null) {
				convertView = View.inflate(mContext, R.layout.view_photos_item,
						null);
				mHolder = new PhotosViewHolder();
				mHolder.ivImage = (ImageView) convertView
						.findViewById(R.id.iv_photos_item);
				mHolder.tvTitle = (TextView) convertView
						.findViewById(R.id.tv_photos_item);
				convertView.setTag(mHolder);
			} else {
				mHolder = (PhotosViewHolder) convertView.getTag();
			}

			PhotosItem photosItem = photosList.get(position);
			mHolder.tvTitle.setText(photosItem.title);

			// 为了放置图片错乱, 给ivImage设置一张默认的图片
			mHolder.ivImage.setImageResource(R.drawable.pic_item_list_default);

			// 给imageView打一个tag(标识)
			mHolder.ivImage.setTag(position);

			// 取图片.
			// Bitmap bitmap = mImageUtils.getImageFromUrl(photosItem.listimage,
			// position);
			// if (bitmap != null) {
			// // 当前是从内存或者本地取的图片
			// mHolder.ivImage.setImageBitmap(bitmap);
			// }
			// if (bu == null) {
			// bu = new BitmapUtils(mContext);
			// bu.configDefaultBitmapConfig(Config.ARGB_4444);
			// }
			// bu.display(mHolder.ivImage, photosItem.listimage);
			return convertView;
		}

	}

	public void switchList(ImageButton ib) {
		isList = !isList;
		if (isList) {
			gv_photos.setVisibility(View.GONE);
			lv_photos.setVisibility(View.VISIBLE);
			lv_photos.setAdapter(mAdapter);
			ib.setImageResource(R.drawable.icon_pic_grid_type);

		} else {
			lv_photos.setVisibility(View.GONE);
			gv_photos.setVisibility(View.VISIBLE);
			gv_photos.setAdapter(mAdapter);
			ib.setImageResource(R.drawable.icon_pic_list_type);
		}
	}

}
