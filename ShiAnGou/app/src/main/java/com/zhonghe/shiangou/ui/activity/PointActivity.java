package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.handmark.pulltorefresh.library.HeaderGridView;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/8/21
 * desc: 积分商城
 */

public class PointActivity extends BaseTopActivity {
    @Bind(R.id.id_point_gv)
    PullToRefreshGridView idPointGv;

    @Override
    protected void initTop() {
        setTitle(R.string.common_pointtitle_str);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_point);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        View header = LayoutInflater.from(this).inflate(R.layout.layout_point_header, null);
        HeaderGridView gridView = idPointGv.getRefreshableView();
        gridView.addHeaderView(header);
    }


}
