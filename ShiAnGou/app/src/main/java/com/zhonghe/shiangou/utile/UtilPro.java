package com.zhonghe.shiangou.utile;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.ui.activity.MainActivity;

/**
 * auther: whyang
 * date: 2017/8/31
 * desc:
 */

public class UtilPro {


    /**
     * 位置权限
     *
     * @param context
     * @return
     */
    public static boolean getLocationPermission(Context context) {
        PackageManager pm = context.getPackageManager();
        boolean flag = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.ACCESS_COARSE_LOCATION", CstProject.PACKAGE_NAME));
        return flag;
    }

    public static int REQUEST_CODE_ASK_PERMISSIONS = 123;

    public static boolean getPermission(Activity context) {
        PackageManager pm = context.getPackageManager();

//        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.CAMERA,
//                Manifest.permission.READ_EXTERNAL_STORAGE,}, REQUEST_CODE_ASK_PERMISSIONS);

        boolean flag = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", CstProject.PACKAGE_NAME))
                || (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.READ_EXTERNAL_STORAGE", CstProject.PACKAGE_NAME)) ||
                (PackageManager.PERMISSION_GRANTED ==
                        pm.checkPermission("android.permission.CAMERA", CstProject.PACKAGE_NAME));
        return flag;
    }


    public static String getImgHttpUrl(String url) {
        if (url.indexOf("http") == 0) {
            return url;
        }
        return CstProject.URL_PRO + url;
    }

    /**
     * 打电话
     * @param mContext
     */
    public static void CallPhone(Context mContext,String phoneNum) {
        //用intent启动拨打电话
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNum));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 隐藏输入面板
     *
     * @param activity
     */
    public static void hideInputManager(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            View view = null;
            if (null != (view = activity.getCurrentFocus())) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }


    /**
     * 显示输入面板
     *
     * @param activity
     */
    public static void showInputManager(final Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = null;

        if (null != (view = activity.getCurrentFocus())) {
            imm.showSoftInput(view, 0);
        }

    }

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
