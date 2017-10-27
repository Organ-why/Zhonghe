package com.zhonghe.shiangou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;

import com.android.volley.Request;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.ShopRemarkInfo;
import com.zhonghe.shiangou.data.bean.UnlineShopDetailInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.ui.adapter.ShopRemarkAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.dialog.ImgDialog;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.ui.widget.xlistview.NXListViewNO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/9/21
 * desc: 商户评论列表
 */

public class PointUnlineShopRemarkActivity extends BaseTopActivity implements NXListViewNO.IXListViewListener {
    @BindView(R.id.xlistview)
    NXListViewNO xlistview;
    private ShopRemarkAdapter adapter;
    private List<ShopRemarkInfo> mData;
    private String shopId = "4";
    private int cursize = 10;
    private int curpage = 1;

    @Override
    protected void initTop() {
        setTitle(R.string.remark_list_title);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_default_xlistview);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        shopId = intent.getStringExtra(CstProject.KEY.ID);
        xlistview.setXListViewListener(this);
        xlistview.setDividerHeight(0);
        xlistview.setPullLoadEnable(true);
        xlistview.setPullLoadEnable(true);
        adapter = new ShopRemarkAdapter(mContext);
        adapter.setImgClick(new ShopRemarkAdapter.OnImgClick() {
            @Override
            public void OnClickItem(List<String> imgs, int position) {
                ImgDialog imgDialog = new ImgDialog(mContext, imgs, position);
                imgDialog.showAtLocation(xlistview, Gravity.BOTTOM, 0, 0);
            }
        });
        xlistview.setAdapter(adapter);
        getRemarkList();
    }

    void getRemarkList() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getUnlineShopRemarkList(mContext, shopId, curpage, cursize, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error);
                xlistview.stopLoadMore();
                xlistview.stopRefresh();
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                xlistview.stopLoadMore();
                xlistview.stopRefresh();
                mData = (List<ShopRemarkInfo>) obj;
                if (curpage == 1) {
                    adapter.setList(mData);
                } else {
                    adapter.addList(mData);
                }
                if (mData.size() > 0)
                    curpage++;
                xlistview.stopLoadMore();
                xlistview.stopRefresh();
            }
        });
        addRequest(request);
    }

    @Override
    public void onRefresh() {
        curpage = 1;
        getRemarkList();
    }

    @Override
    public void onLoadMore() {
        getRemarkList();
    }
}
