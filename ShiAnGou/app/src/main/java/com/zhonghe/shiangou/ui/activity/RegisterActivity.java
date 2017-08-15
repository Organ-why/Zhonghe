package com.zhonghe.shiangou.ui.activity;

import android.content.res.Resources;
import android.view.MenuItem;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;

/**
 * Date: 2017/8/12.
 * Author: whyang
 */
public class RegisterActivity extends BaseTopActivity {
    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void initTop() {
        setTitle(R.string.title_register_title);
        setNavigation(R.mipmap.common_nav_back);
    }
}
