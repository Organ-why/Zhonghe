package com.zhonghe.shiangou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.baseui.MenuPopup;
import com.zhonghe.lib_base.baseui.MenuTxt;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilList;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.UnlineShopDetailInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.adapter.ShopRemarkAdapter;
import com.zhonghe.shiangou.ui.adapter.UnlineShopAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.ui.widget.RatingBar;
import com.zhonghe.shiangou.ui.widget.xlistview.NXListViewNO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/9/12
 * desc:
 */

public class PointUnlineDetailActivity extends BaseTopActivity implements NXListViewNO.IXListViewListener, View.OnClickListener {
    @Bind(R.id.xlistview)
    NXListViewNO xlistview;

    SimpleDraweeView idShopIv;
    TextView idImgNum;
    TextView idShopTitle;
    RatingBar ratingBar;
    TextView idShopmsgTv;
    TextView idUnlineDetailName;
    TextView idUnlineDetailAddre;
    TextView idUnlineDetailRemarknum;
    private String shopId;
    private LinearLayout llContentTitle;
    private LinearLayout llContentList;
    private ShopRemarkAdapter adapter;
    private UnlineShopDetailInfo info;
    private View footer;

    @Override
    protected void initTop() {
        setTitle(R.string.unline_shop_detail);
        setNavigation(R.mipmap.common_nav_back);
//        final MenuTxt mMeunManager = new MenuTxt.MenuTxtBuilder(this)
//                .setTitle(R.string.common_submit)
//                .setListener(new MenuItem.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        return false;
//                    }
//                }).build();
//        addMenu(mMeunManager);
//        MenuPopup popup = new MenuPopup.MenuPopupBuilder(this).setListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                int id = item.getItemId();
//                if (id == R.id.navigation_home) {
//                    Util.toast(mContext, "menu01");
//                    return true;
//                } else if (id == R.id.navigation_dashboard) {
//                    Util.toast(mContext, "menu02");
//                    return true;
//                } else if (id == R.id.navigation_notifications) {
//                    Util.toast(mContext, "menu03");
//                    return true;
//                }
//                return false;
//            }
//        }).setIcon(R.mipmap.icon_zxing_black).setMenuRes(R.menu.navigation).build();
//        addMenu(popup);
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

        View header = LayoutInflater.from(mContext).inflate(R.layout.layout_home_header, null);
        footer = LayoutInflater.from(mContext).inflate(R.layout.layout_footer_more, null);
        View headerdesc = LayoutInflater.from(mContext).inflate(R.layout.layout_header_unlinedetail, null);
        ButterKnife.bind(headerdesc);
        idShopIv = ButterKnife.findById(headerdesc, R.id.id_shop_iv);
        idImgNum = ButterKnife.findById(headerdesc, R.id.id_img_num);
        idShopTitle = ButterKnife.findById(headerdesc, R.id.id_shop_title);
        ratingBar = ButterKnife.findById(headerdesc, R.id.ratingBar);
        idShopmsgTv = ButterKnife.findById(headerdesc, R.id.id_shopmsg_tv);
        idUnlineDetailName = ButterKnife.findById(headerdesc, R.id.id_shop_title);
        idUnlineDetailAddre = ButterKnife.findById(headerdesc, R.id.id_unline_detail_addre);
        idUnlineDetailRemarknum = ButterKnife.findById(headerdesc, R.id.id_unline_detail_remarknum);

        footer.setOnClickListener(this);
        idUnlineDetailRemarknum.setOnClickListener(this);

        llContentTitle = (LinearLayout) header.findViewById(R.id.ll_content_title);
        llContentList = (LinearLayout) header.findViewById(R.id.ll_content_list);
        llContentList.addView(headerdesc);
        xlistview.setPullRefreshEnable(false);
        xlistview.setPullLoadEnable(false);
        xlistview.setXListViewListener(this);
        xlistview.addHeaderView(header);
        xlistview.addFooterView(footer);
        xlistview.setDividerHeight(0);
        adapter = new ShopRemarkAdapter(mContext);
        xlistview.setAdapter(adapter);
        getDetail();
    }

    void getDetail() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getUnlineShopDetail(mContext, "4", new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                info = (UnlineShopDetailInfo) obj;
                setDataShow(info);
            }
        });
        addRequest(request);
    }

    void setDataShow(UnlineShopDetailInfo data) {
        adapter.setList(data.getCommentlist());
        if (UtilList.getCount(data.getCommentlist()) == 0)
            footer.setVisibility(View.GONE);
        ProjectApplication.mImageLoader.loadImage(idShopIv, data.getMerchant_thumb());
        idShopTitle.setText(UtilString.nullToEmpty(data.getMerchant_name()));
        idImgNum.setText(UtilString.nullToEmpty(data.getPhoto_num()));
        ratingBar.setStar(data.getGrade());
        idUnlineDetailAddre.setText(UtilString.nullToEmpty(data.getIntro()));
        idShopmsgTv.setText(String.format(getString(R.string.unline_shop_msg), Util.formatPrice(data.getAverage()), data.getIntro(), data.getIntro()));
        idUnlineDetailRemarknum.setText(String.format(getString(R.string.unline_remark), data.getComment_num()));
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_unline_detail_remarknum:
                if (info != null)
                    ProDispatcher.goPointUnlineShopReamrk(mContext, info.getMerchant_id());
                break;
            case R.id.id_goodsdetail_more_tv:
                if (info != null)
                    ProDispatcher.goPointUnlineShopReamrk(mContext, info.getMerchant_id());
                break;
        }
    }
}
