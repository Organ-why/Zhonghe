package com.zhonghe.shiangou.ui.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;

public class LogisticsActivity extends BaseTopActivity {


    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_logistics);
    }

    @Override
    protected void initTop() {
        setTitle(R.string.order_title_logistics);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initViews() {
//        mEmptyDraw = ContextCompat.getDrawable(mAct, R.mipmap.common_empty_cart);
//        mEmptyDraw.setBounds(0, 0, mEmptyDraw.getMinimumWidth(), mEmptyDraw.getMinimumHeight());
    }
}
