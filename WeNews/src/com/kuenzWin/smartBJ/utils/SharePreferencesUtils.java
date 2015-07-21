package com.kuenzWin.smartBJ.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPreferences������
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
	 * �洢boolean�͵�SharedPreferencesֵ
	 * 
	 * @param context
	 *            �����Ķ���
	 * @param key
	 *            ��
	 * @param value
	 *            ֵ
	 */
	public static void putBoolean(Context context, String key, boolean value) {
		getSharedPreferences(context);
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * ��ȡboolean�͵�SharedPreferencesֵ
	 * 
	 * @param context
	 *            �����Ķ���
	 * @param key
	 *            ��
	 * @param defValue
	 *            Ĭ��ֵ
	 * @return
	 */
	public static boolean getBoolean(Context context, String key,
			boolean defValue) {
		getSharedPreferences(context);
		return sp.getBoolean(key, defValue);
	}

	/**
	 * �洢һ��String���͵�����
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
	 * ����keyȡ��һ��String���͵�ֵ
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
