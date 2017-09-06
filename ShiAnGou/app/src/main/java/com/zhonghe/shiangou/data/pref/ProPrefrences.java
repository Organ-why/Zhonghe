package com.zhonghe.shiangou.data.pref;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.utile.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
    //搜索
    private static final String KEY_SEARCH_ID = "searchKey";

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

    /**
     * search
     *
     * @param searchmap
     */
    public void setSearch(String searchmap) {
        putString(KEY_SEARCH_ID, searchmap);
    }

    /**
     * search
     *
     * @return
     */
    public List<String> getSearch() {
        String string = getString(KEY_SEARCH_ID, "");
        if (UtilString.isEmpty(string))
            return new ArrayList<>();
        try {
            JSONArray json = new JSONArray(string);
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i <json.length() ; i++) {
                Object o = json.get(i);
                list.add(o.toString());
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }


    }
}
