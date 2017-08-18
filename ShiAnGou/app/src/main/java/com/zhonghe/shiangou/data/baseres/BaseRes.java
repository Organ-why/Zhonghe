package com.zhonghe.shiangou.data.baseres;

import com.zhonghe.shiangou.data.bean.BaseBean;

/**
 * Created by a on 2017/8/14.
 */

public class BaseRes extends BaseBean {
    int state;
    String msg;

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
