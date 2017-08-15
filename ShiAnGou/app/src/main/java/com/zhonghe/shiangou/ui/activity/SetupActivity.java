package com.zhonghe.shiangou.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;

public class SetupActivity extends BaseTopActivity {

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_setup);
    }

    @Override
    protected void initTop() {
        setTitle(R.string.setup_title_text);
        setNavigation(R.mipmap.common_nav_back);
    }
}
