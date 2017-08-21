package com.zhonghe.shiangou.ui.activity;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;

/**
 * auther: whyang
 * date: 2017/8/21
 * desc:
 */

public class PointExchangeResultActivity extends BaseTopActivity {
    @Override
    protected void initTop() {
        setTitle(R.string.point_submit_result_title_success);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_point_submit_result);
    }
}
