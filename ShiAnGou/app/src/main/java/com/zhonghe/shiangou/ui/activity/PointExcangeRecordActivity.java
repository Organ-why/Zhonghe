package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.PointOrderInfo;
import com.zhonghe.shiangou.data.bean.RefundsItemInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.ui.adapter.PointOrderAdapter;
import com.zhonghe.shiangou.ui.adapter.RefundsAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/8/21
 * desc: 换购记录
 */

public class PointExcangeRecordActivity extends BaseTopActivity implements PullToRefreshBase.OnRefreshListener2, PointOrderAdapter.pointOrderDelListener {
    @Bind(R.id.id_default_listview)
    PullToRefreshListView idDefaultListview;
    private PointOrderAdapter adapter;
    List<PointOrderInfo> mList;
    int curpage = 1;
    int cursize = 10;

    @Override
    protected void initTop() {
        setTitle(R.string.point_excange_record_title);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_default_listview);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        idDefaultListview.setOnRefreshListener(this);
        mList = new ArrayList<>();
        adapter = new PointOrderAdapter(mContext, mList);
        adapter.setOndelListener(this);
        idDefaultListview.setAdapter(adapter);
        setWaitingDialog(true);
        getPointRecordList();
    }

    void getPointRecordList() {

        Request<?> request = HttpUtil.getPointRecordList(mContext, curpage, cursize, new ResultListener() {
            @Override
            public void onFail(String error) {
                idDefaultListview.onRefreshComplete();
                setWaitingDialog(false);
            }

            @Override
            public void onSuccess(Object obj) {
                idDefaultListview.onRefreshComplete();
                setWaitingDialog(false);
                List<PointOrderInfo> list = (List<PointOrderInfo>) obj;

                if (curpage == 1) {
                    adapter.setList(list);
                } else {
                    adapter.addList(list);
                }
                if (list.size() > 0) {
                    curpage++;
                }
            }
        });
        addRequest(request);
    }

    void getDelRecord(final int position) {
        PointOrderInfo item = adapter.getItem(position);
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getDelExchangeRecord(mContext, item.getOrder_id(), new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                adapter.removeItem(position);
            }
        });
        addRequest(request);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        curpage = 1;
        getPointRecordList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getPointRecordList();
    }

    @Override
    public void OnDel(int position) {
        getDelRecord(position);
    }
}
