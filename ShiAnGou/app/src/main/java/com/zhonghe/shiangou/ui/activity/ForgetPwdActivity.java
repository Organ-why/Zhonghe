package com.zhonghe.shiangou.ui.activity;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;

/**
 * Date: 2017/8/13.
 * Author: whyang
 */
public class ForgetPwdActivity extends BaseTopActivity{
    @Override
    protected void initTop() {
        setTitle(R.string.setup_forgetpwd_text);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_register);
    }
}
