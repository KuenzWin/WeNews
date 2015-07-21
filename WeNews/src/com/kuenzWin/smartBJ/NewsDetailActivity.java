package com.kuenzWin.smartBJ;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.kuenzWin.smartBJ.view.CustomRadioDialog;
import com.kuenzWin.smartBJ.view.CustomRadioDialog.OnListItemClickListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_news_detail)
public class NewsDetailActivity extends Activity implements OnClickListener,
		OnListItemClickListener {

	private String url;

	@ViewInject(R.id.wv_news_detail)
	private WebView wv_news_detail;

	@ViewInject(R.id.pb_news_detail)
	private ProgressBar pb_news_detail;

	private CustomRadioDialog dlg;

	private String[] items;

	private WebSettings settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);

		items = new String[] { "超大号字体", "大号字体", "正常字体", "小号字体", "超小号字体" };
		dlg = new CustomRadioDialog(this, 2).setData(items);
		dlg.setOnListItemClickListener(this);

		findViewById(R.id.ib_title_bar_menu).setVisibility(View.GONE);
		findViewById(R.id.tv_title_bar_title).setVisibility(View.GONE);

		findViewById(R.id.ib_title_bar_back).setVisibility(View.VISIBLE);
		findViewById(R.id.ib_title_bar_textsize).setVisibility(View.VISIBLE);
		findViewById(R.id.ib_title_bar_share).setVisibility(View.VISIBLE);

		findViewById(R.id.ib_title_bar_back).setOnClickListener(this);
		findViewById(R.id.ib_title_bar_textsize).setOnClickListener(this);
		findViewById(R.id.ib_title_bar_share).setOnClickListener(this);

		url = this.getIntent().getStringExtra("url");

		settings = wv_news_detail.getSettings();
		settings.setJavaScriptEnabled(true);
		// 启动界面放大或缩小按钮
		settings.setBuiltInZoomControls(true);
		// 启用界面双击放大或缩小效果
		settings.setUseWideViewPort(true);

		wv_news_detail.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				pb_news_detail.setVisibility(View.GONE);
				super.onPageFinished(view, url);
			}
		});
		wv_news_detail.loadUrl(url);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_title_bar_back:
			this.finish();
			break;
		case R.id.ib_title_bar_textsize:
			dlg.show();
			break;
		case R.id.ib_title_bar_share:
			// ShareUtils.showShare(this, "share");
			break;
		}
	}

	@Override
	public void onItemClick(int position) {
		switch (position) {
		case 0:
			settings.setTextSize(TextSize.LARGEST);
			break;
		case 1:
			settings.setTextSize(TextSize.LARGER);
			break;
		case 2:
			settings.setTextSize(TextSize.NORMAL);
			break;
		case 3:
			settings.setTextSize(TextSize.SMALLER);
			break;
		case 4:
			settings.setTextSize(TextSize.SMALLEST);
			break;
		default:
			break;
		}
	}
}
