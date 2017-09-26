package com.zhonghe.shiangou.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.HeaderGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilList;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.ShopImgInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.ui.adapter.ImgRoundedAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GridRemarkActivity extends BaseTopActivity implements PullToRefreshBase.OnRefreshListener2 {

    @Bind(R.id.id_default_gridview)
    PullToRefreshGridView idDefaultGridview;
    private ImgRoundedAdapter adapter;
    private List<String> imgList;
    private String shopId;
    private int curpage = 1;
    private int cursize = 10;

    @Override
    protected void initTop() {
        setNavigation(R.mipmap.common_nav_back);
        setTitle(R.string.unline_shop_img);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_default_gridview);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        shopId = intent.getStringExtra(CstProject.KEY.ID);
        HeaderGridView gridView = idDefaultGridview.getRefreshableView();
        int padin = Util.dip2px(mContext, 10);
        gridView.setHorizontalSpacing(padin);
        gridView.setVerticalSpacing(padin);
        gridView.setPadding(padin, padin, padin, padin);
        idDefaultGridview.setOnRefreshListener(this);

        imgList = new ArrayList<>();
        adapter = new ImgRoundedAdapter(mContext, imgList);
        idDefaultGridview.setAdapter(adapter);
        getImgList();
    }

    void getImgList() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getUnlineShopImg(mContext, shopId, curpage, cursize, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                idDefaultGridview.onRefreshComplete();
            }

            @Override
            public void onSuccess(Object obj) {
                idDefaultGridview.onRefreshComplete();
                setWaitingDialog(false);
                List<ShopImgInfo> imgs = (List<ShopImgInfo>) obj;
                imgList.clear();
                if (UtilList.getCount(imgs) > 0) {
                    for (ShopImgInfo info :
                            imgs) {
                        imgList.add(info.getPhoto_path());
                    }
                }

                if (curpage == 1) {
                    adapter.setList(imgList);
                } else {
                    adapter.addList(imgList);
                }
                if (UtilList.getCount(imgs) > 0) {
                    curpage++;
                }
            }
        });
        addRequest(request);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        curpage = 1;
        getImgList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getImgList();
    }
}

