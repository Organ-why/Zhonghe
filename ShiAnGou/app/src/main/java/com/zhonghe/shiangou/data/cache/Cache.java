package com.zhonghe.shiangou.data.cache;

import android.content.SharedPreferences.OnSharedPreferenceChangeListener;



public interface Cache<T> {
	/**
	 * 保存缓存
	 * @param t
	 */
	public void save(T t);
	
	/**
	 * 保存缓存
	 * @param t
	 * @param etag
	 */
	public void save(T t, String etag);
	
	/**
	 * 保存缓存
	 * @param t
	 * @param expired
	 */
	public void save(T t, long expired);
	
	/**
	 * 保存缓存
	 * @param t
	 * @param expired
	 * @param etag
	 */
	public void save(T t, long expired, String etag);
	
	/**
	 * 获取缓存
	 * @return
	 */
	public T getData();
	
	/**
	 * 获取ETAG
	 * @return
	 */
	public String getEtag();
	
	/**
	 * 获取获取时间
	 * @return
	 */
	public long getExpired();
	
	/**
	 * 是否过期
	 * @return
	 */
	public boolean hasExpired();
	
	/**
	 * 清除缓存
	 */
	public void clear();
	
	/**
	 * 清除磁盘文件中的缓存
	 */
	public void clearInDisk();
	
	/**
	 * 清除内存中的缓存
	 */
	public void clearInMemory();
	
	
	/**
	 * 注册缓存内容改变监听器
	 */
	public void registerCacheChangedListener(OnSharedPreferenceChangeListener listener);
	
	/**
	 * 取消缓存内容改变监听器
	 */
	public void unregisterCacheChangedListener(OnSharedPreferenceChangeListener listener);
	
}
