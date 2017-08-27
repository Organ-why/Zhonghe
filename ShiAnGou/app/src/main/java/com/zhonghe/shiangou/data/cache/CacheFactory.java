package com.zhonghe.shiangou.data.cache;

import android.content.Context;




public class CacheFactory {
	protected static CacheFactory mInstance;
	private Context mContext;
	
	private CacheFactory(Context ctx) {
		this.mContext = ctx;
	}


	/**
	 * 单例模式
	 * 
	 * @param ctx 上下文
	 * @return
	 */
	public synchronized static CacheFactory getInstance(Context ctx) {
		if (null == mInstance) {
			mInstance = new CacheFactory(ctx);
		}

		return mInstance;
	}
	
	
	/**
	 * 获取缓存
	 * @param cls
	 * @return
	 */
	public <T> DataCache<T> getCache(Class<? extends DataCache<T>> cls) {
		return getCache(cls, new Object[]{});
	}
	
	/**
	 * 获取缓存
	 * @param cls
	 * @param params
	 * @return
	 */
	public <T> DataCache<T> getCache(Class<? extends DataCache<T>> cls, Object... params) {
		Object[] args = null;
		
		if(params == null || params.length == 0) {
			args = new Object[]{mContext};
		} else {
			int len = params.length + 1;
			args = new Object[len];
			args[0] = mContext;
			
			for(int i=1; i<len; i++) {
				args[i]= params[i-1];
			}
		}
		
		DataCache<T> cache = null;
		
		try {
			cache = cls.getConstructor(cls.getConstructors()[0].getParameterTypes()).newInstance(args);
		} catch(Exception ex) {
//			UtilLog.d("Instance " + cls.getCanonicalName() + " failed: " + ex.getMessage());
		}
		
		return cache;
	}
	

	/**
	 * 清除所有数据
	 */
	public void clear() {
		clearInMemory();
		clearInDisk();
	}
	
	/**
	 * 清除缓存在sp中的数据
	 */
	public void clearInDisk() {  
		Class[] clss = DataCacheImpl.class.getDeclaredClasses();
		for(Class cls : clss) {
			try {
				Object[] args = new Object[cls.getConstructors()[0].getParameterTypes().length -1];
				getCache(cls, args).clearInDisk();
			}catch(Exception ex) {
//				UtilLog.d("Can't clear cache: " + cls.getCanonicalName());
			}
		}
		
	}
	
	/**
	 * 清除内存中数据
	 */
	public void clearInMemory() {
		DataCache.clearAllInMemory();
	}


}
