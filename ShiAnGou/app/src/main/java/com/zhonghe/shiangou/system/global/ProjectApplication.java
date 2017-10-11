package com.zhonghe.shiangou.system.global;

import android.app.Application;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhonghe.lib_base.utils.UtilImage;
import com.zhonghe.shiangou.data.bean.UserInfo;
import com.zhonghe.shiangou.data.db.DaoFactory;
import com.zhonghe.shiangou.data.pref.ProPrefrences;
import com.zhonghe.shiangou.http.ProReqestQueue;
import com.zhonghe.shiangou.service.LocationService;
import com.zhonghe.shiangou.system.constant.CstProject;

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
    //位置信息
    public static BDLocation mLocation;
    public static LocationService mLocationService;

    public static synchronized ProjectApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        /**
         * 分享
         */
        Config.DEBUG = true;
        UMShareAPI.get(this);//初始化sdk
        PlatformConfig.setWeixin("wx91470ddde903ce11","ade964807819601cbdd95508c83fae8b");
        PlatformConfig.setQQZone("1106387261","O4qzG9jyl6r8AXeF");
        PlatformConfig.setSinaWeibo("1539407096","c82a8cb6b9768cdc01b45864fabdb43a","http://www.shiangou.com.cn");

        /***
         * 初始化定位sdk，建议在Application中创建
         */
        SDKInitializer.initialize(getApplicationContext());
        mLocationService = new LocationService(getApplicationContext());


        WXapi = WXAPIFactory.createWXAPI(getApplicationContext(), CstProject.WEIXIN.WEIXIN_APP_ID);
//        WXapi = WXAPIFactory.createWXAPI(getApplicationContext(), null);

// 将该app注册到微信

        WXapi.registerApp(CstProject.WEIXIN.WEIXIN_APP_ID);

//        api.handleIntent(getInstance(), this);
        //图片加载实例
        mImageLoader = UtilImage.getInstance(this);
        proReqestQueue = ProReqestQueue.getInstance(this);

        mDaoFactory = DaoFactory.getInstance(this);
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
