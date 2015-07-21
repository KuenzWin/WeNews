package com.kuenzWin.smartBJ.utils;

import android.content.Context;
import android.os.Environment;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.kuenzWin.smartBJ.R;

public class ShareUtils {

	public static void showShare(Context context, String text, String imagePath) {
		ShareSDK.initSDK(context);

		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字
		oks.setNotification(R.drawable.ic_launcher,
				context.getString(R.string.app_name));

		// text是分享文本，所有平台都需要这个字段
		oks.setText(text);
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImagePath(imagePath);

		// 启动分享GUI
		oks.show(context);
	}

	public static void showShare(Context context, String text) {
		ShareSDK.initSDK(context);

		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字
		oks.setNotification(R.drawable.ic_launcher,
				context.getString(R.string.app_name));

		// text是分享文本，所有平台都需要这个字段
		oks.setText(text);
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImagePath(Environment.getExternalStorageDirectory() + "/"
				+ "1.jpg");

		// 启动分享GUI
		oks.show(context);
	}
}
