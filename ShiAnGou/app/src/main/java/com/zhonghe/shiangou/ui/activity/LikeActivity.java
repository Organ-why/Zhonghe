package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.GoodsInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.ui.adapter.GoodsListAdapter;
import com.zhonghe.shiangou.ui.adapter.LikeListAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LikeActivity extends BaseTopActivity implements PullToRefreshBase.OnRefreshListener2, LikeListAdapter.cancelLikeListener {
    @Bind(R.id.id_default_listview)
    PullToRefreshListView idDefaultListview;

    LikeListAdapter adapter;
    List<GoodsInfo> goodsInfos;
    int curpage = 1;
    int cursize = 10;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_default_listview);
        ButterKnife.bind(this);
    }

    @Override
    protected void initTop() {
        setTitle(R.string.common_user_item_like);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initViews() {
        goodsInfos = new ArrayList<>();
        adapter = new LikeListAdapter(mContext, goodsInfos);
        adapter.setCancelListListener(this);
        idDefaultListview.setOnRefreshListener(this);
        idDefaultListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProDispatcher.goGoodsDetailActivity(mContext, adapter.getItem(i).getGoods_id());
            }
        });


        idDefaultListview.setAdapter(adapter);
        setWaitingDialog(true);
        getGoodsList();
    }

    void getGoodsList() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getFollowGoodsList(mContext, curpage, cursize, new ResultListener() {
            @Override
            public void onFail(String error) {
                idDefaultListview.onRefreshComplete();
                setWaitingDialog(false);
            }

            @Override
            public void onSuccess(Object obj) {
                idDefaultListview.onRefreshComplete();
                setWaitingDialog(false);
                List<GoodsInfo> list = (List<GoodsInfo>) obj;
                if (list.size() > 0) {
                    curpage++;
                }
                adapter.addList(list);
            }
        });
        addRequest(request);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        curpage = 1;
        getGoodsList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getGoodsList();
    }

    @Override
    public void onCancel(String goodsId, final int position) {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getFollowGoods(mContext, goodsId, false, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                adapter.removeItem(position);
            }
        });
        addRequest(request);
    }
}
