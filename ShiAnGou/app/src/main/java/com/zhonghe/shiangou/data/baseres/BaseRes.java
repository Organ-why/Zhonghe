package com.zhonghe.shiangou.data.baseres;

import com.zhonghe.shiangou.data.bean.BaseBean;

/**
 * Created by a on 2017/8/14.
 */

public class BaseRes<T> extends BaseBean {
    private static final long serialVersionUID = 1L;
    int state;
    String msg;
    Class<T> datas;

    public BaseRes() {
    }
}
