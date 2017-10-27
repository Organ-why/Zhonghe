package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.GoodsInfo;
import com.zhonghe.shiangou.data.bean.OrderUnline;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.ui.adapter.UnlineOrderAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.id.list;

/**
 * 线下订单
 */
public class UnlineOrderActivity extends BaseTopActivity implements PullToRefreshBase.OnRefreshListener2 {
    UnlineOrderAdapter adapter;
    int curpage = 1;
    int cursize = 10;
    @BindView(R.id.id_default_listview)
    PullToRefreshListView idDefaultListview;
    private List<OrderUnline> mData;

    @Override
    protected void initTop() {
        setTitle(R.string.order_unline_title);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_default_listview);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        mData = new ArrayList<>();
        adapter = new UnlineOrderAdapter(mContext, mData);
        idDefaultListview.setAdapter(adapter);
        idDefaultListview.setOnRefreshListener(this);
        getOrderList();
    }

    private void getOrderList() {
        {
            setWaitingDialog(true);
            Request<?> request = HttpUtil.getUnlineOrder(mContext,  cursize, curpage,new ResultListener() {
                @Override
                public void onFail(String error) {
                    idDefaultListview.onRefreshComplete();
                    setWaitingDialog(false);
                }

                @Override
                public void onSuccess(Object obj) {
                    idDefaultListview.onRefreshComplete();
                    setWaitingDialog(false);
                    mData = (List<OrderUnline>) obj;

                    if (curpage == 1) {
                        adapter.setList(mData);
                    } else {
                        adapter.addList(mData);
                    }
                    if (mData.size() > 0) {
                        curpage++;
                    }

                }
            });
            addRequest(request);
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        curpage = 1;
        getOrderList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getOrderList();
    }
}
