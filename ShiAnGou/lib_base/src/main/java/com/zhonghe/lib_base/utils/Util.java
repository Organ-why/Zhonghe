package com.zhonghe.lib_base.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.StringRes;
import android.util.Patterns;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;


import com.zhonghe.lib_base.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date: 2017/8/7.
 * Author: whyang
 */
public class Util {
    /**
     * list<String>
     * , 隔开
     *
     * @param strArray
     * @return
     */
    public static String strArrayToStr(List<String> strArray) {
        String strs = "";
        if (strArray.size() > 1) {
            for (int i = 0; i < strArray.size(); i++) {
                String str = strArray.get(i);
                if (i == 0) {
                    strs += str;
                } else {
                    strs += "," + str;
                }
            }

        } else {
            strs = strArray.get(0);
        }
        return strs;
    }

    /**
     * 注册检查
     *
     * @param context
     * @param phoneview
     * @param codeview
     * @param pwdview
     * @param pwdagview
     * @return
     */
    public static boolean isRegiste(Context context, EditText phoneview, EditText codeview, EditText pwdview, EditText pwdagview) {

        String strphone = phoneview.getText().toString();
        String strcode = codeview.getText().toString();
        String strpwd = pwdview.getText().toString();
        String strpwdag = pwdagview.getText().toString();
        if (!isPhone(strphone)) {
            toast(context, R.string.title_register_phone_tip);
            return false;
        }
        if (UtilString.isBlank(strcode)) {
            toast(context, R.string.title_register_code_tip);
            return false;
        }
        if (!isPwd(strpwd)) {
            toast(context, R.string.title_register_pwd_tip);
            return false;
        }
        if (UtilString.isBlank(strpwdag)) {
            toast(context, R.string.title_register_pwdag_tip);
            return false;
        }
        if (!strpwd.equals(strpwdag)) {
            toast(context, R.string.title_register_pwddfrt_tip);
            return false;
        }

        return true;

    }


    /**
     * 是否是手机号码
     *
     * @param phone
     * @return 是true
     */
    public static boolean isPhone(String phone) {
        if (UtilString.isBlank(phone) || phone.length() != 11) {
            return false;
        }
        return isMobile(phone);
//        String reg = "^[a-zA-Z0-9\u4e00-\u9fa5]+$";
//        Pattern pattern = Pattern.compile(reg);
//        Matcher matcher = pattern.matcher(phone);
//        boolean b = matcher.matches();
////        reg代表就是你要写的正则的规则 !让后就会返回...
////        Pattern pattern = Patterns.PHONE;
////        Matcher matcher = pattern.matcher(phone);
//        return b;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (UtilString.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    /**
     * 密码格式
     *
     * @param
     * @return 是true
     */
    public static boolean isPwd(String pwd) {
        if (UtilString.isBlank(pwd) || pwd.length() < 6 || pwd.length() > 18) {
            return false;
        }
        return true;
    }


    /**
     * 格式化价格
     *
     * @param price
     */
    public static String formatPrice(double price) {
        int p = (int) price;
        if (p != 0) {
            return new DecimalFormat("###.00").format(price);
//            return new DecimalFormat("###.00").format(price);
        } else {
            return "0" + new DecimalFormat("###.00").format(price);
//            return "0" + new DecimalFormat("###.00").format(price);

        }
    }

    /**
     * 格式积分
     *
     * @param price
     * @return
     */
    public static String formatPoint(double price) {
        int p = (int) price;
        if (p != 0) {
            return new DecimalFormat("###.00").format(price) + "积分";
//            return new DecimalFormat("###.00").format(price);
        } else {
            return "0" + new DecimalFormat("###.00").format(price) + "积分";
//            return "0" + new DecimalFormat("###.00").format(price);

        }
    }

    /**
     * 格式化距离  千米->米
     *
     * @param metre
     * @return
     */
    public static String formatMetre(double metre) {
        int p = (int) metre;
        if (p != 0) {
            return new DecimalFormat("###.000").format(metre) + "千米";
//            return new DecimalFormat("###.00").format(price);
        } else {
            return new DecimalFormat("###").format(metre * 1000) + "米";
//            return "0" + new DecimalFormat("###.00").format(price);

        }
    }

    public static String ALLDATE = "yyyy-MM-dd";
    public static String ALLDATETIME = "yyyy-MM-dd";

    /**
     * 格式化时间
     *
     * @param
     * @param format
     * @return
     */
    public static String formatDate(long mills, String format) {
        mills = mills * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(mills));
    }

    private static WindowManager wm;

    /**
     * 屏幕宽
     *
     * @param context
     * @return
     */
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


}
