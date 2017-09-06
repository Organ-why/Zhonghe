package com.zhonghe.shiangou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhonghe.lib_base.baseui.MenuTxt;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.GoodsInfo;
import com.zhonghe.shiangou.data.bean.RemarkInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.ui.adapter.CartProdcutAdapter;
import com.zhonghe.shiangou.ui.adapter.RemarkAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/9/5
 * desc: 评论列表
 */

public class RemarkListActivity extends BaseTopActivity implements PullToRefreshBase.OnRefreshListener2 {
    @Bind(R.id.id_default_listview)
    PullToRefreshListView idDefaultListview;
    private RemarkAdapter adapter;
    List<RemarkInfo> mList;
    int curpage = 1;
    int cursize = 10;
    private String goodsId;

    @Override
    protected void initTop() {
        setTitle(R.string.remark_list_title);
        setNavigation(R.mipmap.common_nav_back);

    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_default_listview);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        goodsId = intent.getStringExtra(CstProject.KEY.ID);
        mList = new ArrayList<>();
        adapter = new RemarkAdapter(mContext, mList);
        idDefaultListview.setAdapter(adapter);
        idDefaultListview.setOnRefreshListener(this);
        getRemarkList();
    }

    void getRemarkList() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getCommentList(mContext, goodsId, curpage, cursize, new ResultListener() {
            @Override
            public void onFail(String error) {
                idDefaultListview.onRefreshComplete();
                setWaitingDialog(false);
            }

            @Override
            public void onSuccess(Object obj) {
                idDefaultListview.onRefreshComplete();
                setWaitingDialog(false);
                List<RemarkInfo> list = (List<RemarkInfo>) obj;

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


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        curpage = 1;
        setWaitingDialog(true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        setWaitingDialog(true);
    }
}
