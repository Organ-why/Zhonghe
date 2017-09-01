package com.zhonghe.shiangou.system.global;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.lib_httpok.OkHttp3Stack;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhonghe.lib_base.utils.UtilImage;
import com.zhonghe.shiangou.data.bean.UserInfo;
import com.zhonghe.shiangou.data.db.DaoFactory;
import com.zhonghe.shiangou.data.pref.ProPrefrences;
import com.zhonghe.shiangou.http.ProReqestQueue;
import com.zhonghe.shiangou.system.constant.CstProject;

import okhttp3.OkHttpClient;

/**
 * Date: 2017/8/7.
 * Author: whyang
 */
public class ProjectApplication extends Application {
    public static ProjectApplication mInstance;
    //数据库
    public static DaoFactory mDaoFactory;
    //图片加载
    public static UtilImage mImageLoader;
    //任务管理
    public static ProReqestQueue proReqestQueue;
    //sp实例
    public static ProPrefrences mPrefrence;
    //当前用户信息
    public static UserInfo mUser;

    public static IWXAPI WXapi;

    public static synchronized ProjectApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        WXapi = WXAPIFactory.createWXAPI(getApplicationContext(), CstProject.WEIXIN.WEIXIN_APP_ID);
//        WXapi = WXAPIFactory.createWXAPI(getApplicationContext(), null);

// 将该app注册到微信

        WXapi.registerApp(CstProject.WEIXIN.WEIXIN_APP_ID);

//        api.handleIntent(getInstance(), this);
        //图片加载实例
        mImageLoader = UtilImage.getInstance(this);
        proReqestQueue = ProReqestQueue.getInstance(this);

        mDaoFactory = DaoFactory.getInstance(this);
//        mDaoFactory.clear();
        mPrefrence = new ProPrefrences(this);

        //获取当前用户信息
        String userId = mPrefrence.getUserId();
        mUser = mDaoFactory.getUserDao().getUser(userId);
//        if (memberInfo != null) {
//            mUser = new User();
//            mUser.setMember(memberInfo);
//        }

    }


}
