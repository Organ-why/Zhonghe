package com.zhonghe.shiangou.data.pref;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * auther: whyang
 * date: 2017/8/21
 * desc:
 */

public abstract class BasePrefrences {
    SharedPreferences mPref;

    public BasePrefrences(Context context, String fileName) {
        mPref = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
    }

    /**
     * 保存字符串数据
     *
     * @param key
     * @param value
     */
    public void putString(String key, String value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取字符串数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public String getString(String key, String defValue) {
        return mPref.getString(key, defValue);
    }


    /**
     * 保存整型数据
     *
     * @param key
     * @param value
     */
    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 保存整型数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public int getInt(String key, int defValue) {
        return mPref.getInt(key, defValue);
    }


    /**
     * 保存长整型数据
     *
     * @param key
     * @param value
     */
    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 保存长整型数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public long getLong(String key, long defValue) {
        return mPref.getLong(key, defValue);
    }

    /**
     * 保存布尔值数据
     *
     * @param key
     * @param value
     */
    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 保存布尔值数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public boolean getBoolean(String key, boolean defValue) {
        return mPref.getBoolean(key, defValue);
    }


    /**
     * 保存浮点型数据
     *
     * @param key
     * @param value
     */
    public void putFloat(String key, float value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    /**
     * 保存浮点型数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public float getFloat(String key, float defValue) {
        return mPref.getFloat(key, defValue);
    }

    /**
     * 清空
     */
    public void clear() {
        SharedPreferences.Editor editor = mPref.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 移除指定的KEY
     *
     * @param key
     */
    public void remove(String key) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.remove(key);
        editor.commit();
    }


    /**
     * 注册sp内容改变监听器
     *
     * @param listener
     */
    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        if (listener != null) {
            mPref.registerOnSharedPreferenceChangeListener(listener);
        }
    }

    /**
     * 取消sp内容改变监听器
     *
     * @param listener
     */
    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        if (listener != null) {
            mPref.unregisterOnSharedPreferenceChangeListener(listener);
        }
    }
}
