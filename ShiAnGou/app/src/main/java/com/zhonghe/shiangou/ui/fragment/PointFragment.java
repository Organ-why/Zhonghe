package com.zhonghe.shiangou.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.HeaderGridView;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.baseui.BaseTopFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/9/5
 * desc:积分商城
 */

public class PointFragment extends BaseTopFragment {
    @BindView(R.id.id_point_gv)
    PullToRefreshGridView idPointGv;

    @Override
    protected void initTop() {
        setTitle(R.string.common_pointtitle_str);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_point);
        ButterKnife.bind(this, getView());
    }

    @Override
    protected void initViews() {
        View header = LayoutInflater.from(mActivity).inflate(R.layout.layout_point_header, null);
        HeaderGridView gridView = idPointGv.getRefreshableView();
        gridView.addHeaderView(header);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
