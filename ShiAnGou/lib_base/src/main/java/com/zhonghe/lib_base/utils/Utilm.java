package com.zhonghe.lib_base.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.util.Patterns;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;


import com.zhonghe.lib_base.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date: 2017/8/7.
 * Author: whyang
 */
public class Utilm {

    public static boolean isRegiste(Context context,EditText phoneview, EditText codeview, EditText pwdview, EditText pwdagview) {

        String strphone = phoneview.getText().toString();
        String strcode = codeview.getText().toString();
        String strpwd = pwdview.getText().toString();
        String strpwdag = pwdagview.getText().toString();
        if (!isPhone(strphone)){
            toast(context, R.string.title_register_phone_tip);
            return  false;
        }
        if (UtilString.isBlank(strcode)){
            toast(context, R.string.title_register_code_tip);
            return  false;
        }
        if (UtilString.isBlank(strpwd)){
            toast(context, R.string.title_register_pwd_tip);
            return  false;
        }
        if (UtilString.isBlank(strpwdag)){
            toast(context, R.string.title_register_pwdag_tip);
            return  false;
        }
        if (!strpwd.equals(strpwdag)){
            toast(context, R.string.title_register_pwddfrt_tip);
            return  false;
        }

        return true;

    }


    /**
     * 是否是手机号码
     *
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        if (UtilString.isBlank(phone)) {
            return false;
        }

        Pattern pattern = Patterns.PHONE;
        Matcher matcher = pattern.matcher(phone);
        return matcher.find();
    }


    private static WindowManager wm;

    public static int getScreenWidth(Context context) {
        if (wm == null) {
            wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }


    /**
     * 显示Toast
     *
     * @param context
     * @param string
     * @param resId
     */
    public static void toast(Context context, String string, int resId) {
        if (resId > 0) {
            Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示Toast
     *
     * @param context
     * @param text
     */
    public static void toast(Context context, String text) {
        toast(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast
     *
     * @param context
     * @param
     */
    public static void toast(Context context, int resId) {
        toast(context, context.getString(resId), Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast
     *
     * @param context
     * @param resId
     * @param duration
     */
    public static void toast(Context context, @StringRes int resId, int duration) {
        toast(context, context.getString(resId), duration);
    }

    /**
     * 获取屏幕宽
     *
     * @param context
     * @return
     */
    public static int GetWindowWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        return width;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
