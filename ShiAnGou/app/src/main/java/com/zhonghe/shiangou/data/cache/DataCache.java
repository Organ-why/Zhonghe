package com.zhonghe.shiangou.data.cache;

import android.content.Context;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import com.google.gson.Gson;
import com.zhonghe.lib_base.utils.UtilLog;
import com.zhonghe.lib_base.utils.UtilString;

import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;


public abstract class DataCache<T> implements Cache<T>{
	private static ConcurrentHashMap<String, Object> mCacheMap;
	private static ConcurrentHashMap<String, Long> mExpiredMap;
	private static ConcurrentHashMap<String, String> mEtagMap;
	private static Gson mGson;
	protected CachePreferences mCsp;
	private String mKey;
	private Class<T> mClassofT;
	private Type mTypeofT;
	
	
	static {
		mCacheMap = new ConcurrentHashMap<String, Object>();
		mExpiredMap = new ConcurrentHashMap<String, Long>();
		mEtagMap = new ConcurrentHashMap<String, String>();
		mGson = new Gson();
		
	}
	
	public DataCache(Context context, String key, Class<T> classofT) {
		init(context, key, key, classofT);
	}
	
	public DataCache(Context context, String key, Type typeofT) {
		init(context, key, key, typeofT);
	}
	
	public DataCache(Context context, String key, String sp, Class<T> classofT)  {
		init(context, key, sp, classofT);
	}
	
	public DataCache(Context context, String key, String sp, Type typeofT) {
		init(context, key, sp, typeofT);
	}
	
	/**
	 * 初始化
	 * @param context 		上下文
	 * @param key			缓存key
	 * @param sp			sp文件名
	 * @param classofT		缓存类型
	 * @throws Exception
	 */
	private void init(Context context, String key, String sp, Class<T> classofT)  {
		if(context == null) {
			UtilLog.e("Context is null");
		} 
		
		if(UtilString.isBlank(key)) {
			UtilLog.e("Key is null");
		}

		if(UtilString.isBlank(sp)) {
			UtilLog.e("SharePreference is null");
		}

		if(classofT == null) {
			UtilLog.e("Class is null");
		}
		
		this.mCsp = new CachePreferences(context, sp);
		this.mKey = key;
		this.mClassofT = classofT;
	}
	
	/**
	 * 初始化
	 * @param context
	 * @param key
	 * @param sp
	 * @param typeofT
	 * @throws Exception
	 */
	private void init(Context context, String key, String sp, Type typeofT)  {
		if(context == null) {
			UtilLog.e("Context is null");
		}
		
		if(UtilString.isBlank(key)) {
			UtilLog.e("Key is null");
		}
		
		if(UtilString.isBlank(sp)) {
			UtilLog.e("SharePreference is null");
		}
		
		if(typeofT == null) {
			UtilLog.e("Type is null");
		}
		
		this.mCsp = new CachePreferences(context, sp);
		this.mKey = key;
		this.mTypeofT = typeofT;
	}
	
	@Override
	public void save(T t) {
		save(t, Long.MAX_VALUE, null);
	}

	@Override
	public void save(T t, String etag) {
		save(t, Long.MAX_VALUE, etag);
	}

	@Override
	public void save(T t, long expired) {
		save(t, expired, null);
	}

	@Override
	public void save(T t, long expired, String etag) {
		if(t == null || expired <= System.currentTimeMillis()) {
			return;
		}

		mCacheMap.put(mKey, t);
		mExpiredMap.put(mKey, expired);
		mEtagMap.put(mKey, UtilString.nullToEmpty(etag));
		
		String jsonStr = mGson.toJson(t, t.getClass());
		mCsp.putString(mKey, jsonStr);
		mCsp.putLong(mKey+"_expired", expired);
		mCsp.putString(mKey+"_etag", UtilString.nullToEmpty(etag));
		
		UtilLog.d(DataCache.class.getSimpleName()+":save in Memory & Disk cache");
	}

	@Override
	public T getData() {
		T t = (T)mCacheMap.get(mKey);
		
		if(t != null) {
			long expired = mExpiredMap.get(mKey);
			if(System.currentTimeMillis() < expired) {
				UtilLog.d(DataCache.class.getSimpleName()+":get from MemoryCache");
				return t;
			}
		} else {
			long expired = mCsp.getLong(mKey+"_expired", 0L);		
			if(System.currentTimeMillis() < expired) {
				String jsonStr = mCsp.getString(mKey, "");
				if(UtilString.isNotBlank(jsonStr)){
					UtilLog.d(DataCache.class.getSimpleName()+":get from DiskCache");
					if(mTypeofT != null) {
						t = mGson.fromJson(jsonStr, mTypeofT);
					} else if(mClassofT != null) {
						t = mGson.fromJson(jsonStr, mClassofT);
					}
				}
			}
		}
		
		return t;
	}

	@Override
	public String getEtag() {
		Long expired = mExpiredMap.get(mKey);
		
		if(expired != null) {
			if(System.currentTimeMillis() < expired) {
				return mEtagMap.get(mKey);
			}
		} else {
			expired = mCsp.getLong(mKey+"_expired", 0L);
			
			if(System.currentTimeMillis() < expired) {
				return mCsp.getString(mKey+"_etag", "");
			}
		}
		
		return "";
	}

	@Override
	public long getExpired() {
		Long expired = mExpiredMap.get(mKey);
		
		if(expired != null) {
			return expired;
		}
		
		expired = mCsp.getLong(mKey+"_expired", 0L);
		return expired;
	}

	@Override
	public boolean hasExpired() {
		return System.currentTimeMillis() > getExpired();
	}

	@Override
	public void clear() {
		mCacheMap.remove(mKey);
		mExpiredMap.remove(mKey);
		mEtagMap.remove(mKey);
		mCsp.remove(mKey);
	}
	
	@Override
	public void clearInDisk() {
		mCsp.remove(mKey);
	}
	
	@Override
	public void clearInMemory() {
		mCacheMap.remove(mKey);
		mExpiredMap.remove(mKey);
		mEtagMap.remove(mKey);
	}

	@Override
	public void registerCacheChangedListener(OnSharedPreferenceChangeListener listener) {
		mCsp.registerOnSharedPreferenceChangeListener(listener);
	}

	@Override
	public void unregisterCacheChangedListener(OnSharedPreferenceChangeListener listener) {
		mCsp.unregisterOnSharedPreferenceChangeListener(listener);
	}
	
	/**
	 * 清除内存中所有缓存
	 */
	public static void clearAllInMemory() {
		mCacheMap.clear();
		mExpiredMap.clear();
		mEtagMap.clear();
	}
}
