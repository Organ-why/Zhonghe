package com.zhonghe.lib_base.baseui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Date: 2017/8/3.
 * Author: whyang
 */
public abstract class AbsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        initBaseViews();
        initLayout();
        initViews();
    }

    /**
     * 初始化
     */
    protected abstract void init(Bundle savedInstanceState);

    /**
     * 初始化基类View
     */
    protected abstract void initBaseViews();

    /**
     * 初始化布局
     */
    protected abstract void initLayout();

    /**
     * 初始化视图
     */
    protected abstract void initViews();
}
