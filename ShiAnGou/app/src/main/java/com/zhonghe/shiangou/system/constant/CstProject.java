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
        public final static String CODE = "_code";
        public final static String INDEX = "_index";
        public final static String TYPE = "_type";
        public final static String COUNT = "_count";
        public final static String ATTRID = "_attrid";
        public final static String VALUES1 = "_values1";
        public final static String VALUES2 = "_values2";
        public final static String VALUES3 = "_values3";
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
        //位置
        public static final String LOCATION_ACTION = PACKAGE_NAME+"android.intent.action.LOCATION_ACTION";
        // 支付返回
        public static final String PAY_RESULT_ACTION = PACKAGE_NAME + ".BROADCAST_ACTION.PAY_RESULT_ACTION";
        //tab 选中
        public static final String MAINTAB_CHECK_ACTION = PACKAGE_NAME + ".BROADCAST_ACTION.MAINTAB_CHECK_ACTION";
        //购物车商品
        public static final String CART_DEL_ACTION = PACKAGE_NAME + ".BROADCAST_ACTION.CART_DEL_ACTION";
        public static final String CART_ADD_ACTION = PACKAGE_NAME + ".BROADCAST_ACTION.CAET_ADD_ACTION";
        public static final String POINT_EXCHANGE_ACTION = PACKAGE_NAME + ".BROADCAST_ACTION.POINT_EXCHANGE_ACTION";

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
