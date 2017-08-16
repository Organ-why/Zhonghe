package com.zhonghe.shiangou.system.global;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.lib_httpok.OkHttp3Stack;
import com.zhonghe.lib_base.utils.UtilImage;
import com.zhonghe.shiangou.http.ProReqestQueue;

import okhttp3.OkHttpClient;

/**
 * Date: 2017/8/7.
 * Author: whyang
 */
public class ProjectApplication extends Application {
    public static ProjectApplication mInstance;
    //图片加载
    public static UtilImage mImageLoader;

    public static ProReqestQueue proReqestQueue;

    public static synchronized ProjectApplication getInstance() {
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //图片加载实例
        mImageLoader = UtilImage.getInstance(this);
        proReqestQueue = ProReqestQueue.getInstance(this);

    }






}
