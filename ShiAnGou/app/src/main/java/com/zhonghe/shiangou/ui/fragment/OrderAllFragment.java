package com.zhonghe.shiangou.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.baseui.BaseFullFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Date: 2017/8/13.
 * Author: whyang
 */
public class OrderAllFragment extends BaseFullFragment implements PullToRefreshBase.OnRefreshListener2<ListView> {
    @Bind(R.id.id_default_listview)
    PullToRefreshListView idDefaultListview;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_default_listview);
        ButterKnife.bind(this, getView());
    }

    @Override
    protected void initTop() {

    }

    @Override
    protected void initViews() {
        idDefaultListview.setOnRefreshListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }
}
