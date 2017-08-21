package com.zhonghe.shiangou.data.baseres;

import com.zhonghe.shiangou.data.bean.BaseBean;

import org.json.JSONObject;

/**
 * Created by a on 2017/8/14.
 */

public class BaseRes extends BaseBean {
    int state;
    String msg;
    Object datas;

    public Object getDatas() {
        return datas;
    }

    public void setDatas(Object datas) {
        this.datas = datas;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
