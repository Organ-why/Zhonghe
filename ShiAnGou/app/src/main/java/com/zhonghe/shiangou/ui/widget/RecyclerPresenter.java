package com.zhonghe.shiangou.ui.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.zhonghe.shiangou.data.bean.HomeCategoryInfo;
import com.zhonghe.shiangou.ui.adapter.RecyAdapter;
import com.zhonghe.shiangou.ui.adapter.RecyHeaderAdapter;
import com.zhonghe.shiangou.ui.adapter.ViewHolder;
import com.zhonghe.shiangou.ui.widget.xlistview.XListViewFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: whyang
 * date: 2017/9/18
 * desc:首页recycyler
 */

public class RecyclerPresenter extends BaseRecPrensenter {
    RecyAdapter recyAdapter;

    public RecyclerPresenter(RecyclerPresenterBuilder builder) {
        super(builder);
    }

    @Override
    protected void initViews() {
        recyAdapter = new RecyAdapter(mContext);
        headerAdapter = new RecyHeaderAdapter<>(recyAdapter);
    }

    public RecyAdapter getAdapter() {
        return recyAdapter;
    }
    public void setCarListener(RecyAdapter.addCartListener addCartListener) {
        recyAdapter.setAddCartListener(addCartListener);
    }

    @Override
    public void setData(List data) {
        recyAdapter.setData(data);
        headerAdapter.notifyDataSetChanged();
    }

    @Override
    public void addData(List data) {
        recyAdapter.setData(data);
        headerAdapter.notifyDataSetChanged();
    }


}
