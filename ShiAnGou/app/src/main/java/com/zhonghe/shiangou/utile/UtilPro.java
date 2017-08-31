package com.zhonghe.shiangou.utile;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.system.constant.CstProject;

/**
 * auther: whyang
 * date: 2017/8/31
 * desc:
 */

public class UtilPro {

    /**
     * 获取清单指定meta key的值
     *
     * @param key
     * @return
     */
    public static String getMetaValue(Context context, String key) {
        String metaValue = "";
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            metaValue = appInfo.metaData.getString(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return metaValue;
    }

    /**
     * 获取当前的应用渠道
     *
     * @param context
     * @return
     */
    public static String getAppChannel(Context context) {
        String channel = getMetaValue(context, CstProject.CHANNEL_META);

        if (UtilString.isBlank(channel)) {
            channel = CstProject.PROJECT;
        }

        return channel;
    }

    /**
     * 获取版本名
     *
     * @return
     */
    public static String getVersionName(Context context) {
        String name = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            name = pi.versionName;
        } catch (Exception ex) {
            name = "unknown";
        }

        return name;
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public static int getVersionCode(Context context) {
        int code = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            code = pi.versionCode;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return code;
    }
}
