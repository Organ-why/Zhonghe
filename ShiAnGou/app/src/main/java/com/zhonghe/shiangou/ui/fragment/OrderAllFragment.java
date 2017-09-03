package com.zhonghe.shiangou.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.GoodsInfo;
import com.zhonghe.shiangou.data.bean.OrderInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.ui.adapter.GoodsListAdapter;
import com.zhonghe.shiangou.ui.adapter.OrderAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseFullFragment;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Date: 2017/8/13.
 * Author: whyang
 */
public class OrderAllFragment extends BaseFullFragment implements PullToRefreshBase.OnRefreshListener2<ListView> {
    @Bind(R.id.id_default_listview)
    PullToRefreshListView idDefaultListview;
    String orderBy;
    OrderAdapter adapter;
    int curpage = 1;
    int cursize = 10;
    List<OrderInfo> newData;

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
        adapter = new OrderAdapter(mActivity, newData);
        idDefaultListview.setAdapter(adapter);
        idDefaultListview.setOnRefreshListener(this);
        Bundle bundle = getArguments();
        orderBy = bundle.getString(CstProject.KEY.ORDERBY);
        getGoodsList();
    }

    void getGoodsList() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getOrderList(mActivity, orderBy, cursize, curpage, new ResultListener() {
            @Override
            public void onFail(String error) {
                Util.toast(mActivity, error);
                setWaitingDialog(false);
                idDefaultListview.onRefreshComplete();
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                List<OrderInfo> list = (List<OrderInfo>) obj;
                if (curpage == 1) {
                    adapter.setList(list);
                } else {
                    adapter.addList(list);
                }
                curpage++;
                idDefaultListview.onRefreshComplete();
            }
        });
        addRequest(request);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        curpage = 1;
        getGoodsList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        getGoodsList();
    }

}
