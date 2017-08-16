package com.zhonghe.shiangou.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;

public class RemarkActivity extends BaseTopActivity {


    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_remark);
    }

    @Override
    protected void initTop() {
        setTitle(R.string.remark_title);
        setNavigation(R.mipmap.common_nav_back);
    }
}
