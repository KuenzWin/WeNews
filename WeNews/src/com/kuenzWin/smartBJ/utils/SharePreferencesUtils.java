package com.kuenzWin.smartBJ.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPreferences工具类
 * 
 * @author Administrator
 * 
 */
public class SharePreferencesUtils {

	private static SharedPreferences sp;
	
	private static void getSharedPreferences(Context context) {
		if (sp == null)
			sp = context.getSharedPreferences("sp_smartBJ",
					Context.MODE_PRIVATE);
	}

	/**
	 * 存储boolean型的SharedPreferences值
	 * 
	 * @param context
	 *            上下文对象
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static void putBoolean(Context context, String key, boolean value) {
		getSharedPreferences(context);
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 获取boolean型的SharedPreferences值
	 * 
	 * @param context
	 *            上下文对象
	 * @param key
	 *            键
	 * @param defValue
	 *            默认值
	 * @return
	 */
	public static boolean getBoolean(Context context, String key,
			boolean defValue) {
		getSharedPreferences(context);
		return sp.getBoolean(key, defValue);
	}

	/**
	 * 存储一个String类型的数据
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putString(Context context, String key, String value) {
		getSharedPreferences(context);
		sp.edit().putString(key, value).commit();
	}

	/**
	 * 根据key取出一个String类型的值
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getString(Context context, String key, String defValue) {
		getSharedPreferences(context);
		return sp.getString(key, defValue);
	}

}
