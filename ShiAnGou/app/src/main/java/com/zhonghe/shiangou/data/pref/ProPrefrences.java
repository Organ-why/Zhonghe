package com.zhonghe.shiangou.data.pref;

import android.content.Context;

import com.zhonghe.shiangou.system.constant.CstProject;

/**
 * auther: whyang
 * date: 2017/8/21
 * desc:
 */

public class ProPrefrences extends BasePrefrences {
    // 文件名
    private static final String PREFERENCES_NAME = CstProject.PROJECT;
    //当前用户id
    private static final String KEY_USER_ID = "userId";

    public ProPrefrences(Context context) {
        super(context, PREFERENCES_NAME);
    }



    /**
     * 设置当前用户id
     *
     * @param uid
     */
    public void setUserId(String uid) {
        putString(KEY_USER_ID, uid);
    }

    /**
     * 获取当前用户id
     *
     * @return
     */
    public String getUserId() {
        return getString(KEY_USER_ID, "");
    }
}
