package com.zhonghe.shiangou.system.constant;

/**
 * Date: 2017/8/7.
 * Author: whyang
 */
public class CstProject {
    //项目名
    public static String PROJECT = "shiangou";
    //项目是否处于调试模式
    public static boolean DEBUG = true;
    //当前项目包名
    public static final String PACKAGE_NAME = "com.zhonghe.shiangou";
    // 数据库名
    public static final String DB_NAME = PROJECT + ".db";
    // 数据库版本
    public static final int DB_VERSION = 1;
    // CHANNEL META
    public static final String CHANNEL_META = "InstallChannel";

    public static String URL_PRO = "http://test.shiangou.com.cn/";


    /**
     * 数据传输KEY
     */
    public static class KEY {
        public final static String ID = "_id";
        public final static String KEY = "_key";
        public final static String ORDERBY = "_orderby";
        public final static String DATA = "_data";
    }

    /**
     * 广播
     */
    public static class BROADCAST_ACTION {
        // 网络广播
        public static final String NETWORK_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
        // 来电广播
        public static final String PHONESTATE_ACTION = "android.intent.action.PHONE_STATE";
        // 登录广播
        public static final String LOGIN_ACTION = PACKAGE_NAME + ".BROADCAST_ACTION.LOGIN_ACTION";
        // 登出广播
        public static final String LOGOUT_ACTION = PACKAGE_NAME + ".BROADCAST_ACTION.LOGOUT_ACTION";

    }

    /**
     * 微信相关
     */
    public static class WEIXIN {
        // 微信app id
        public static final String WEIXIN_APP_ID = "wx91470ddde903ce11";
        // 微信secret
        public static final String WEIXIN_APP_SECRET = "ade964807819601cbdd95508c83fae8b";
    }
}
