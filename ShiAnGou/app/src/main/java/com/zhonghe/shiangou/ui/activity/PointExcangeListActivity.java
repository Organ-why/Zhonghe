package com.zhonghe.shiangou.ui.activity;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;

/**
 * auther: whyang
 * date: 2017/8/21
 * desc: 换购列表
 */

public class PointExcangeListActivity extends BaseTopActivity {
    @Override
    protected void initTop() {
        setTitle(R.string.point_excange_title);
        setNavigation(R.mipmap.common_nav_back);
    }
    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_default_listview);
    }
}
