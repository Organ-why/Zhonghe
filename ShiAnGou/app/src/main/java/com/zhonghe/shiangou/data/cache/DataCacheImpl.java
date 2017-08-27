package com.zhonghe.shiangou.data.cache;

import android.content.Context;

import com.google.gson.reflect.TypeToken;


import java.util.List;

public class DataCacheImpl {


//	public static class UpgradeCache extends DataCache<UpdateInfo>{
//
//		public UpgradeCache(Context context) throws Exception {
//			super(context, UpdateInfo.class.getName(),
//					new TypeToken<UpdateInfo>(){}.getType());
//		}
//
//	}


//	public static class FeedDetailCache extends DataCache<Feed>{
//
//		public FeedDetailCache(Context context, String uid, String fid) throws Exception {
//			super(context, FeedDetailCache.class.getName()+ "_" + uid  + "_" + fid,
//					FeedDetailCache.class.getName(),
//					new TypeToken<Feed>(){}.getType());
//		}
//
//		@Override
//		public void clearInDisk() {
//			mCsp.clear();
//		}
//
//	}


    public static class SearchKeyCache extends DataCache<List<String>> {

        public SearchKeyCache(Context context) throws Exception {
            super(context, "SearchKeyCache",
                    new TypeToken<List<String>>() {
                    }.getType());
        }

    }
}
