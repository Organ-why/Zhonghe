package com.zhonghe.shiangou.ui.listener;

/**
 * Date: 2017/8/13.
 * Author: whyang
 */
public interface ResultListener<T> {
    void onFial(String error);

    void onSuccess(Object obj);
}
