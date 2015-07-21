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
		// �ر�sso��Ȩ
		oks.disableSSOWhenAuthorize();

		// ����ʱNotification��ͼ�������
		oks.setNotification(R.drawable.ic_launcher,
				context.getString(R.string.app_name));

		// text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		oks.setText(text);
		// imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
		oks.setImagePath(imagePath);

		// ��������GUI
		oks.show(context);
	}

	public static void showShare(Context context, String text) {
		ShareSDK.initSDK(context);

		OnekeyShare oks = new OnekeyShare();
		// �ر�sso��Ȩ
		oks.disableSSOWhenAuthorize();

		// ����ʱNotification��ͼ�������
		oks.setNotification(R.drawable.ic_launcher,
				context.getString(R.string.app_name));

		// text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		oks.setText(text);
		// imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
		oks.setImagePath(Environment.getExternalStorageDirectory() + "/"
				+ "1.jpg");

		// ��������GUI
		oks.show(context);
	}
}
