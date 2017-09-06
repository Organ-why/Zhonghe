package com.zhonghe.shiangou.ui.activity;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;

import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/9/4
 * desc:
 */

public class OrderDetailActivity extends BaseTopActivity {
    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void initTop() {
        setTitle(R.string.order_detail_title);
        setNavigation(R.mipmap.common_nav_back);
    }
}
