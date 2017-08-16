package com.zhonghe.shiangou.data.bean;

import java.io.Serializable;

/**
 * Date: 2017/7/14.
 * Author: whyang
 */
public class BaseBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private int state;
    private String msg;
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
