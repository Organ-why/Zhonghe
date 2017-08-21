package com.zhonghe.shiangou.ui.activity;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;

/**
 * auther: whyang
 * date: 2017/8/21
 * desc: 换购记录
 */

public class PointExcangeRecordActivity extends BaseTopActivity {
    @Override
    protected void initTop() {
        setTitle(R.string.point_excange_record_title);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_default_listview);
    }
}
