package com.zhonghe.shiangou.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.HeaderGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.GoodsInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.ui.adapter.GoodsListAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseFullFragment;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/8/27
 * desc: 商品列表
 */

public class GridViewFragment extends BaseFullFragment implements PullToRefreshBase.OnRefreshListener2<HeaderGridView> {
    @Bind(R.id.id_default_gridview)
    PullToRefreshGridView idDefaultGridview;

    String listId, listKey, orderBy;
    GoodsListAdapter adapter;
    List<GoodsInfo> goodsInfos;
    int curpage = 1;
    int cursize = 10;

    @Override
    protected void initTop() {

    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_default_gridview);
        ButterKnife.bind(this, getView());
        Bundle bundle = getArguments();
        listId = bundle.getString(CstProject.KEY.ID);
        listKey = bundle.getString(CstProject.KEY.KEY);
        orderBy = bundle.getString(CstProject.KEY.ORDERBY);

        goodsInfos = new ArrayList<>();
        adapter = new GoodsListAdapter(mActivity, goodsInfos);
        idDefaultGridview.setAdapter(adapter);
        idDefaultGridview.setOnRefreshListener(this);
        idDefaultGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProDispatcher.goGoodsDetailActivity(mActivity, adapter.getData().get(i).getGoods_id());
            }
        });

        getGoodsList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    void getGoodsList() {
        Request<?> request = HttpUtil.getSearch(mActivity, listId, listKey, cursize, curpage, orderBy, new ResultListener() {
            @Override
            public void onFail(String error) {
                Util.toast(mActivity, error);
                idDefaultGridview.onRefreshComplete();
            }

            @Override
            public void onSuccess(Object obj) {
                List<GoodsInfo> list = (List<GoodsInfo>) obj;
                adapter.addList(list);
                idDefaultGridview.onRefreshComplete();
            }
        });
        addRequest(request);
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<HeaderGridView> refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<HeaderGridView> refreshView) {
        curpage = 1;
        getGoodsList();

    }
}