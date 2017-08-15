package com.zhonghe.shiangou.utile;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.system.global.ProjectApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 各种phone信息接口 例如 imei 手机型号，系统 ，分辨率，应用包及包名
 *
 * @author xugang
 */
public class Device {

    private static Device result = getPhoneInfo(ProjectApplication.getInstance());

    private static final String FILE_MEMORY = "/proc/meminfo";
    private static final String FILE_CPU = "/proc/cpuinfo";
    public String mIMEI;
    public int mPhoneType;
    public int mSysVersion;
    public String mNetWorkCountryIso;
    public String mNetWorkOperator;
    public String mNetWorkOperatorName;
    public int mNetWorkType;
    public boolean mIsOnLine;
    public String mConnectTypeName;
    public long mFreeMem;
    public long mTotalMem;
    public String mCupInfo;
    public String mProductName;
    public String mModelName;
    public String mManufacturerName;

    private Device() {
    }

    public static Device getInstance() {
        return result;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm;
    }

    /**
     * 获取屏幕相关信息 int width = metric.widthPixels; // 屏幕宽度（像素） int height =
     * metric.heightPixels; // 屏幕高度（像素） float density = metric.density; //
     * 屏幕密度（0.75 / 1.0 / 1.5） int densityDpi = metric.densityDpi; // 屏幕密度DPI（120
     * / 160 / 240）
     *
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Activity ac) {
        DisplayMetrics metric = new DisplayMetrics();
        ac.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric;
    }

    /**
     * 获取手机网络制式 获取通讯类型，GSM,CDMA...
     *
     * @param context
     * @return
     */
    public static int getPhoneType(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        return manager.getPhoneType();
    }

    /**
     * 获取手机系统版本
     *
     * @return
     */
    public static int getSysVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取移动通讯的国家注册码（移动区域码）
     *
     * @param context
     * @return
     */
    public static String getNetWorkCountryIso(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        return manager.getNetworkCountryIso();
    }

    public static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();

    }

    /**
     * 用IMSI号码获取手机运营商
     *
     * @return
     */
    public static String getProvidersName(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String ProvidersName = null;
        String IMSI = null;
        // 返回唯一的用户ID;就是这张卡的编号神马的
        IMSI = telephonyManager.getSubscriberId();
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        if (TextUtils.isEmpty(IMSI) || !IMSI.startsWith("460") || IMSI.length() < 15) {
            return "国外运营商或IMSI号无效";
        }
        IMSI = IMSI.substring(3);
        if (IMSI.startsWith("00") || IMSI.startsWith("02") || IMSI.startsWith("07")) {
            ProvidersName = "中国移动";
        } else if (IMSI.startsWith("01")) {
            ProvidersName = "中国联通";
        } else if (IMSI.startsWith("03")) {
            ProvidersName = "中国电信";
        }
        return ProvidersName;
    }

    public static String getIMSI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSubscriberId();
    }

    /**
     * 获取网络运营商名称
     *
     * @param context
     * @return
     */
    public static String getNetWorkOperatorName(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        return manager.getNetworkOperatorName();
    }

    /**
     * 获得当前网络的类型
     *
     * @param context
     * @return
     */
    public static int getNetworkType(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        return manager.getNetworkType();
    }

    /**
     * 是否有可用数据链接
     *
     * @param context
     * @return
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    /**
     * 获得当前数据连接类型的名称.比如：GPRS,WIFI
     *
     * @param context
     * @return
     */
    public static String getConnectTypeName(Context context) {
        if (!isOnline(context)) {
            return "OFFLINE";
        }
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null) {
            return info.getTypeName().toUpperCase();
        } else {
            return "OFFLINE";
        }
    }

    /**
     * 手机剩余内存
     *
     * @param context
     * @return
     */
    public static long getFreeMem(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        MemoryInfo info = new MemoryInfo();
        manager.getMemoryInfo(info);
        long free = info.availMem / 1024 / 1024;
        return free;
    }

    /**
     * 获取sd卡剩余内存
     *
     * @return
     */
    public static long getAvailaleSize() {
        File path = Environment.getExternalStorageDirectory(); // 取得sdcard文件路径
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 获取手机总内存
     *
     * @param context
     * @return
     */
    public static long getTotalMem(Context context) {
        try {
            FileReader fr = new FileReader(FILE_MEMORY);
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split("\\s+");

            return Long.valueOf(array[1]) / 1024;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取手机状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 获取手机CPU型号
     *
     * @return
     */
    public static String getCpuInfo() {
        try {
            FileReader fr = new FileReader(FILE_CPU);
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {

            }

            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取手机名称
     *
     * @return
     */
    public static String getProductName() {
        return Build.PRODUCT;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getModelName() {
        return Build.MODEL;
    }

    /**
     * 手机设备制造商名称
     *
     * @return
     */
    public static String getManufacturerName() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取全部信息方法 调用此方法随后调用toString获取信息
     *
     * @param context
     * @return
     */
    private static Device getPhoneInfo(Context context) {
        Device result = new Device();
        result.mIMEI = getIMEI(context);
        result.mPhoneType = getPhoneType(context);
        result.mSysVersion = getSysVersion();
        result.mNetWorkCountryIso = getNetWorkCountryIso(context);
        // result.mNetWorkOperator = getNetWorkOperator(context);
        result.mNetWorkOperatorName = getNetWorkOperatorName(context);
        result.mNetWorkType = getNetworkType(context);
        result.mIsOnLine = isOnline(context);
        result.mConnectTypeName = getConnectTypeName(context);
        result.mFreeMem = getFreeMem(context);
        result.mTotalMem = getTotalMem(context);
        result.mCupInfo = getCpuInfo();
        result.mProductName = getProductName();
        result.mModelName = getModelName();
        result.mManufacturerName = getManufacturerName();
        return result;
    }

    /**
     * 判断是否为飞行模式
     *
     * @param context
     * @return true为飞行模式
     */
    public static boolean IsAirModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) == 1 ? true
                : false;
    }

    /**
     * 判断SIM卡是否可用
     *
     * @param context
     * @return true为SIM卡可用
     */
    public static boolean isCanUseSim(Context context) {
        try {
            TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            return TelephonyManager.SIM_STATE_READY == mgr.getSimState();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将所有信息转为string类型
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("IMEI : " + mIMEI + "\n");
        builder.append("mPhoneType : " + mPhoneType + "\n");
        builder.append("mSysVersion : " + mSysVersion + "\n");
        builder.append("mNetWorkCountryIso : " + mNetWorkCountryIso + "\n");
        // builder.append("mNetWorkOperator : "+mNetWorkOperator+"\n");
        builder.append("mNetWorkOperatorName : " + mNetWorkOperatorName + "\n");
        builder.append("mNetWorkType : " + mNetWorkType + "\n");
        builder.append("mIsOnLine : " + mIsOnLine + "\n");
        builder.append("mConnectTypeName : " + mConnectTypeName + "\n");
        builder.append("mFreeMem : " + mFreeMem + "M\n");
        builder.append("mTotalMem : " + mTotalMem + "M\n");
        builder.append("mCupInfo : " + mCupInfo + "\n");
        builder.append("mProductName : " + mProductName + "\n");
        builder.append("mModelName : " + mModelName + "\n");
        builder.append("mManufacturerName : " + mManufacturerName + "\n");
        return builder.toString();
    }

    public static String getUUID(Context context) {
        if (!TextUtils.isEmpty(result.mIMEI)) {
            return result.mIMEI;
        }
        return getIMEI(context);
    }

    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceIds = telephonyManager.getDeviceId();
        if (!TextUtils.isEmpty(deviceIds)) {
            String deviceId = deviceIds.replace(deviceIds.charAt(0) + "", "a");
            return deviceId;
        } else {
            return "android";
        }

    }

    // ////////////////

    /**
     * 获取手机IMEI
     */
    public static String getIMEI(Context context) {
        context = ProjectApplication.getInstance();
        String imeiStr = "";
        String IMEI = "IMEI_VAL";
        if (PreferenceManager.getDefaultSharedPreferences(context).contains(IMEI)) {
            imeiStr = PreferenceManager.getDefaultSharedPreferences(context).getString(IMEI, "");
        }

        if (!TextUtils.isEmpty(imeiStr)) {
            return imeiStr;
        }

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        imeiStr = tm.getDeviceId();

        if (TextUtils.isEmpty(imeiStr)) {
            Random ran = new Random(System.currentTimeMillis());
            for (int i = 0; i < 18; i++) {
                imeiStr = imeiStr + ran.nextInt(100);
            }
        }

        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(IMEI, imeiStr).commit();

        return imeiStr;
    }

    /**
     * 读取Asset 里面的Html文件内容
     */
    public static String getFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inStreamReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inStreamReader);
            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line = bufReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 获取手机 UID 首先是imei+phone_id 其次是是随机生成字符串
     */

    // public static String getPhoneUID(Context context) {
    // String strUID = MobclickAgent.getDeviceUUID(context);
    // if (TextUtils.isEmpty(strUID)) {
    // Random ran = new Random(System.currentTimeMillis());
    // for (int i = 0; i < 18; i++) {
    // strUID = strUID + ran.nextInt(100);
    // }
    // }
    //
    // return strUID;
    //
    // }

    /**
     * 获取当前程序版本数值
     *
     * @param context 上下文
     * @return 当前程序版本数值
     */
    public static int getCurrentAppVersionCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
        } finally {
            return verCode;
        }
    }

    /**
     * 获取当前程序版本名
     *
     * @param context 上下文
     * @return 当前程序版本名
     */
    public static String getCurrentAppVersionName(Context context) {
        String versionName = null;
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
        } finally {
            return versionName;
        }
    }

    /**
     * 获取当前程序渠道
     *
     * @param context 上下文
     * @return 当前程序渠道
     */

    public static String get_Channel_ID(Context context) {

        // String code = getMetaData(context, "UMENG_CHANNEL");
        //
        // if (!TextUtils.isEmpty(code)) {
        // return code.replaceAll(" ", "").replaceAll("\t", "");
        // }

        // return "jxdyf";

        return getChannel(context);

    }

    static String jxdyfchannel = null;

    private static String getChannel(Context context) {
        if (TextUtils.isEmpty(jxdyfchannel)) {
            if (PreferenceManager.getDefaultSharedPreferences(context).contains("jxdyfchannel")) {
                jxdyfchannel = PreferenceManager.getDefaultSharedPreferences(context)
                        .getString("jxdyfchannel", "jxdyf");
            } else {
                ApplicationInfo appinfo = context.getApplicationInfo();
                String sourceDir = appinfo.sourceDir;
                String ret = "";
                ZipFile zipfile = null;
                try {
                    zipfile = new ZipFile(sourceDir);
                    Enumeration<?> entries = zipfile.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry entry = (ZipEntry) entries.nextElement();
                        String entryName = entry.getName();
                        if (entryName.startsWith("META-INF/jxdyfchannel")) {
                            ret = entryName.substring("jxdyfchannel".length() + 1);
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (zipfile != null) {
                        try {
                            zipfile.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                String[] split = ret.split("_");
                if (split != null && split.length >= 2) {
                    jxdyfchannel = ret.substring(split[0].length() + 1);
                } else {
                    jxdyfchannel = "jxdyf";
                }
                PreferenceManager.getDefaultSharedPreferences(context).edit().putString("jxdyfchannel", jxdyfchannel)
                        .commit();
            }
        }
        return jxdyfchannel;
    }

    private static String getMetaData(Context context, String key) {

        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);

            Object value = ai.metaData.get(key);
            if (value != null) {
                return value.toString();
            }
        } catch (Exception e) {

            //

        }

        return null;

    }

    /**
     * 获取屏幕的宽和高的工具类 WangQing 2013-8-12 DisplayMetrics
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getAppWidthAndHeight(Context context) {
        // 这个可以用于1.5
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }


    /**
     * 获取手机的相关信息 wangqing 2013-6-25 String 返回设备的信息
     *
     * @param context
     * @return
     */
    public static String getDeviceInfo(Context context) {

        String deviceName = Build.MANUFACTURER;
        if (!TextUtils.isEmpty(deviceName)) {
            return deviceName;
        } else {
            return "android";
        }

    }

    /**
     * @param context
     * @return
     */
    public static String getDeviceUA(Context context) {
        // UA号
        String UAInfo = "android_jx";
        try {
            UAInfo = "jxapp@" + "_" + getCurrentAppVersionName(context) + "_" + get_Channel_ID(context) + "_"
                    + getIMEI(context) // getDeviceTokenToMD5(context)
                    + "(android_OS_" + Build.VERSION.RELEASE + ";" + Build.MANUFACTURER + "_" + Build.MODEL + ")";
        } catch (Exception e) {
            e.printStackTrace();
            return "jxapp@" + "_" + getCurrentAppVersionName(context) + "_" + get_Channel_ID(context) + "_"
                    + getIMEI(context) + "(android;unknown)";
        }

        return UAInfo;
    }

    public static String getJXAPPUA(Context context) {
        String UAInfo = "JXAPP/" + getCurrentAppVersionName(context) + "/" + get_Channel_ID(context) + "/Android";

        return UAInfo;
    }

    /**
     * 获取当前手机分辨率
     *
     * @param context 上下文
     * @return 手机分辨率
     */
    public static String getPhoneDisplayMetrics(Context context) {
        String sPoneDisplayMetrics = null;

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        w.getDefaultDisplay().getMetrics(dm);
        sPoneDisplayMetrics = dm.widthPixels + " * " + dm.heightPixels;
        return sPoneDisplayMetrics;

    }

    /**
     * 根据路径,获取对应分区的总大小/可用大小
     *
     * @param path 要获取空间大小的路径
     * @return 手机内置存储空间大小数组, 第一个是总大小, 第二个是可用大小
     */
    public static long[] calcSize(String path) {
        if (externalMemoryAvailable()) {
            try {
                if (path == null) {
                    return null;
                }
                StatFs stat = new StatFs(path);
                long blockSize = stat.getBlockSize();
                long totalBlocks = stat.getBlockCount();
                long availableBlocks = stat.getAvailableBlocks();
                long totalSize = totalBlocks * blockSize;
                long availableSize = availableBlocks * blockSize;
                long[] size = new long[]{totalSize, availableSize};
                return size;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 外部存储是否可用
     *
     * @return
     */
    static public boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 将long类型的文件大小数值转换为带单位的String
     *
     * @param size long类型的文件大小数值,单位:字节
     * @return 带单位的文件大小String
     */
    public static String long2String(long size) {
        size = size / (1024 * 1024);
        if (size < 1024) {
            return size + " MB";
        } else if (size % 1024 == 0) {
            return size / 1024 + " GB";
        } else {
            return String.format("%.2f GB", size / 1024f);
        }
    }

    /**
     * 将long类型的文件大小数值转换为带单位的String,精确到Bytes与KB
     *
     * @return 带单位的文件大小String
     */
    public static String convertFileSize(long filesize) {

        String strUnit = "Bytes";
        String strAfterComma = "";

        int intDivisor = 1;
        if (filesize >= 1024 * 1024 * 1024) {
            strUnit = "GB";
            intDivisor = 1024 * 1024 * 1024;
        } else if (filesize >= 1024 * 1024) {
            strUnit = "MB";
            intDivisor = 1024 * 1024;
        } else if (filesize >= 1024) {
            strUnit = "KB";
            intDivisor = 1024;
        }

        if (intDivisor == 1) {
            return filesize + " " + strUnit;
        }
        strAfterComma = "" + 100 * (filesize % intDivisor) / intDivisor;
        if (strAfterComma == "") {
            strAfterComma = ".0";
        }

        return filesize / intDivisor + "." + strAfterComma + " " + strUnit;

    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSdCardPath() {
        File file = Environment.getExternalStorageDirectory();
        if (file == null || !file.exists()) {
            return null;
        }
        return file.getPath();
    }

    /**
     * 获取实际上的SD卡空间大小 <br>
     * <h1>注意:</h1>获取到的数据单位是字节,可通过同类静态方法long2String(long)来将此数值转换为带单位字符串
     *
     * @return long数组, 第一项是空间总大小, 第二项是可用大小
     */
    public static long[] getSdCardTotalSize() {
        return calcSize(getSdCardPath());
    }

    /**
     * 外部存储设备时候就绪
     *
     * @return true已挂载可用, false不可用
     */
    public static boolean isExternalMediaMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, int pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取当前是否允许重力自动旋转方向
     *
     * @param context 上下文
     * @return true允许, false不允许
     */
    public static boolean isRotationLocked(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1;
    }

    /**
     * 禁止重力自动旋转方向
     *
     * @param context 上下文
     */
    public static void lockAutoRotation(Context context) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
    }

    /**
     * 开启重力自动旋转方向
     *
     * @param context 上下文
     */
    public static void unlockAutoRotation(Context context) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 1);
    }

    /**
     * 获取手机顶部状态栏高度
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        return statusBarHeight;
    }

    /**
     * 获取大文件的MD5
     */
    public static String getMd5ByFile(File file) {
        String value = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    /**
     * 获取android当前可用内存大小
     */
    public static int getAvailMemory(Context context) {
        // 获取android当前可用内存大小
        String availMemory = null;
        int intAvailMemory = 1000;
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            MemoryInfo mi = new MemoryInfo();
            am.getMemoryInfo(mi);
            availMemory = Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
            // mi.availMem; 当前系统的可用内存
            if (!TextUtils.isEmpty(availMemory)) {
                if (availMemory.contains(".")) {
                    intAvailMemory = 100;
                } else {
                    intAvailMemory = Integer.parseInt(availMemory.replace("MB", "").trim());
                }
            }
        } catch (Exception e) {
        }

        return intAvailMemory;
    }

    /**
     * 获取普通文件和小文件的MD5
     */
    public static String getMd5BySmallFile(File file) {
        String value = null;
        FileInputStream in = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int length = -1;
            while ((length = in.read(buffer)) != -1) {
                md5.update(buffer, 0, length);
            }
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
            }
        }
        return value;
    }

    /**
     * @Title: getPkgNameByResource
     * @Description: 根据资源文件获取最新的包名
     * @param: @param ctx
     * @param: @return 设定文件
     * @return: String 返回类型
     * @date: 2014-2-28 下午5:54:40
     */
    public static String getPkgNameByResource(Context ctx) {
        return ctx.getResources().getResourcePackageName(R.string.app_name);
    }

    /**
     * 安装APK
     *
     * @param act
     * @param path
     */
    public static void installAPKFile(Activity act, String path) {
        File file = new File(path);
        if (file.exists()) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            act.startActivity(intent);
        }
    }

    /**
     * 复制指定文本到系统粘贴版
     *
     * @param text 指定文本
     */
    public static void copyTextToClipboard(Context ctx, String text) {
        ClipboardManager cm = (ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(text);
    }
}