package com.zhonghe.shiangou.ui.activity;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;

/**
 * auther: whyang
 * date: 2017/8/21
 * desc: 积分详情
 */

public class PointDetailActivity extends BaseTopActivity {
    @Override
    protected void initTop() {
        setTitle(R.string.common_point_detail_title_str);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_point_pro_detail);
    }
}
