package com.zhonghe.lib_base.utils;

import android.util.Log;

import com.zhonghe.lib_base.constant.CstBase;

/**
 * auther: whyang
 * date: 2017/8/27
 * desc:
 */

public class UtilLog {
    /**
     * Verbose Log, 输出大于或等于Verbose日志级别的信息
     * @param msg
     */
    public static void v(String msg) {
        if(CstBase.DEBUG) {
            Log.v(CstBase.PROJECT, UtilString.nullToString(msg));
            //saveLog(msg);
        }
    }

    /**
     * Debug Log, 输出大于或等于Debug日志级别的信息
     * @param msg
     */
    public static void d(String msg) {
        if(CstBase.DEBUG) {
            Log.d(CstBase.PROJECT, UtilString.nullToString(msg));
            //saveLog(msg);
        }
    }

    /**
     * Info Log,输出大于或等于Info日志级别的信息
     * @param msg
     */
    public static void i(String msg) {
        if(CstBase.DEBUG) {
            Log.i(CstBase.PROJECT, UtilString.nullToString(msg));
            //saveLog(msg);
        }
    }

    /**
     * Warn Log,输出大于或等于Warn日志级别的信息
     * @param msg
     */
    public static void w(String msg) {
        if(CstBase.DEBUG) {
            Log.w(CstBase.PROJECT, UtilString.nullToString(msg));
            //saveLog(msg);
        }
    }

    /**
     * Error Log, 仅输出Error日志级别的信息.
     * @param msg
     */
    public static void e(String msg) {
        if(CstBase.DEBUG) {
            Log.e(CstBase.PROJECT, UtilString.nullToString(msg));
            //saveLog(msg);
        }
    }
}
