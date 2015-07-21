package com.kuenzWin.smartBJ.utils;

/**
 * 用于存储程序常量的接口
 * 
 * @author 温坤哲
 * 
 */
public interface Constant {
	/**
	 * SharePreferences中的started的key值
	 */
	String sp_started = "isStarted";
	/**
	 * 服务器前缀地址
	 */
	String SERVICE_URL = "http://192.168.1.103/zhbj";
	/**
	 * 新闻中心地址
	 */
	String NEWSCENTER_URL = SERVICE_URL + "/categories.json";

	/**
	 * 组图地址
	 */
	String PHOTOS_URL = SERVICE_URL + "/photos/photos_1.json";
}
