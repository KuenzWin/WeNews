package com.kuenzWin.smartBJ.utils;

import android.graphics.Bitmap;
import android.os.Handler;

public class ImageCacheUtils {

	private ImageNetCacheUtils utils;

	public ImageCacheUtils(Handler handler) {
		utils = new ImageNetCacheUtils(handler);
	}

	/**
	 * 1. ����url, ȥ�ڴ���ȡһ��ͼƬ.
	 * 
	 * 2. ����url, �������ļ���, ȥ����ȡ.
	 * 
	 * 3. ����url, ȥ������ȡ.
	 * 
	 * �õ�ͼƬ��, ʹ��url��Ϊkey, ���ڴ��д洢һ��.
	 * 
	 * �õ�ͼƬ��, ʹ��url�������ļ���, �򱾵ش洢һ��.
	 * 
	 * ��ͼƬ����������, �����Լ�ȥ������ʾͼƬ.
	 * 
	 * @param imgUrl
	 * @return
	 */
	public Bitmap getBitmap(String imgUrl) {
		// ����url, ȥ�ڴ���ȡһ��ͼƬ.

		// ����url, �������ļ���, ȥ����ȡ.

		// ����url, ȥ������ȡ.
		utils.getBitmapFromNet(imgUrl);
		return null;
	}

}
