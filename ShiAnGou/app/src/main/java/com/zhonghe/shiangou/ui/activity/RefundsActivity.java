package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RefundsActivity extends BaseTopActivity implements PullToRefreshBase.OnRefreshListener2{

    @Bind(R.id.id_default_listview)
    PullToRefreshListView idDefaultListview;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_default_listview);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        idDefaultListview.setOnRefreshListener(this);
    }

    @Override
    protected void initTop() {
        setTitle(R.string.common_user_order_return);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

    }
}
