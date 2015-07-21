package com.kuenzWin.smartBJ.utils;

import android.graphics.Bitmap;
import android.os.Handler;

public class ImageCacheUtils {

	private ImageNetCacheUtils utils;

	public ImageCacheUtils(Handler handler) {
		utils = new ImageNetCacheUtils(handler);
	}

	/**
	 * 1. 根据url, 去内存中取一张图片.
	 * 
	 * 2. 根据url, 构建出文件名, 去本地取.
	 * 
	 * 3. 根据url, 去网络中取.
	 * 
	 * 得到图片后, 使用url作为key, 向内存中存储一份.
	 * 
	 * 得到图片后, 使用url构建出文件名, 向本地存储一份.
	 * 
	 * 把图片还给调用者, 让他自己去操作显示图片.
	 * 
	 * @param imgUrl
	 * @return
	 */
	public Bitmap getBitmap(String imgUrl) {
		// 根据url, 去内存中取一张图片.

		// 根据url, 构建出文件名, 去本地取.

		// 根据url, 去网络中取.
		utils.getBitmapFromNet(imgUrl);
		return null;
	}

}
