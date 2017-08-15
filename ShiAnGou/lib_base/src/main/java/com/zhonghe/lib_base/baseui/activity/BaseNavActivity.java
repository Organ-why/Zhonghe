package com.zhonghe.lib_base.baseui.activity;

import com.zhonghe.lib_base.baseui.BaseUIActivity;
import com.zhonghe.lib_base.baseui.UIOptions;

/**
 * Date: 2017/8/3.
 * Author: whyang
 * tabhost 页面
 */
public abstract class BaseNavActivity extends BaseUIActivity {
    protected long initOptions() {
        return UIOptions.UI_OPTIONS_NAVBAR_TABS|UIOptions.UI_OPTIONS_CONTENT_CUSTOM;
    }
    protected abstract void initNavs();

    @Override
    protected void initNavTabs() {
        initNavs();
    }
}
