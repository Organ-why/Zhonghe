package com.zhonghe.shiangou.ui.activity;

import android.content.Intent;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.PointGoodsInfo;
import com.zhonghe.shiangou.data.bean.PointItemInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.ui.adapter.PointListAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/8/21
 * desc: 换购列表
 */

public class PointExcangeListActivity extends BaseTopActivity implements PullToRefreshBase.OnRefreshListener2 {
    @Bind(R.id.id_default_listview)
    PullToRefreshListView idDefaultListview;
    PointListAdapter adapter;
    private int type;
    List<PointItemInfo> goodsInfos;
    int curpage = 1;
    int cursize = 10;

    @Override
    protected void initTop() {
        Intent intent = getIntent();
        type = intent.getIntExtra(CstProject.KEY.TYPE, 4);
        switch (type) {
            case 1:
                setTitle(R.string.point_excange_title);
                break;
            case 2:
                setTitle(R.string.point_common_str);
                break;
            case 3:
                setTitle(R.string.point_today_str);
                break;
            case 4:
                setTitle(R.string.point_all_str);
                break;
        }

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
        adapter = new PointListAdapter(mContext, goodsInfos);
        idDefaultListview.setAdapter(adapter);
        getPointGoodsMsg();
    }

    void getPointGoodsMsg() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getPointGoods(mContext, type, curpage, cursize, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error);
                idDefaultListview.onRefreshComplete();
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                PointGoodsInfo pointGoodsInfo = (PointGoodsInfo) obj;
                goodsInfos = pointGoodsInfo.getData();
                if (curpage == 1) {
                    adapter.setList(goodsInfos);
                } else {
                    adapter.addList(goodsInfos);
                }
                if (goodsInfos.size() > 0) {
                    curpage++;
                }

                idDefaultListview.onRefreshComplete();
            }
        });
        addRequest(request);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        curpage = 1;
        getPointGoodsMsg();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getPointGoodsMsg();
    }
}
