package com.zhonghe.shiangou.data.cache;

import android.content.Context;

import com.zhonghe.shiangou.data.pref.BasePrefrences;
import com.zhonghe.shiangou.system.constant.CstProject;



public class CachePreferences extends BasePrefrences {
    
	public CachePreferences(Context context) {
		super(context, CstProject.PROJECT + "_cache");
	}
	
	public CachePreferences(Context context, String name) {
		super(context, name);
	}

}
