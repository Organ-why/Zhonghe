package com.zhonghe.shiangou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.socialize.UMShareAPI;
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
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.dialog.ImgDialog;
import com.zhonghe.shiangou.ui.dialog.ShareDialog;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.ui.widget.RatingBar;
import com.zhonghe.shiangou.ui.widget.xlistview.NXListViewNO;
import com.zhonghe.shiangou.utile.UtilPro;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * auther: whyang
 * date: 2017/9/12
 * desc:商户详情
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
    @Bind(R.id.id_shop_share)
    LinearLayout idShopShare;
    @Bind(R.id.id_shop_reamrk)
    LinearLayout idShopReamrk;
    @Bind(R.id.id_shop_pay)
    TextView idShopPay;
    private String shopId;
    private LinearLayout llContentTitle;
    private LinearLayout llContentList;
    private ShopRemarkAdapter adapter;
    private UnlineShopDetailInfo info;
    private View footer;
    private ImgDialog imgDialog;
    private ImageButton phoneIb;
    private ShareDialog shareDialog;

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
        setContentView(R.layout.activity_unline_shop_detail);
        ButterKnife.bind(this);
        setOnRetryListener(new OnRetryListener() {
            @Override
            public void onRetry() {
                getDetail();
                setRetry(false);
            }
        });
    }

    @Override
    protected void initViews() {
        registerAction(CstProject.BROADCAST_ACTION.REMARK_ACTION);
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
        phoneIb = ButterKnife.findById(headerdesc, R.id.id_unline_phone);
        idUnlineDetailName = ButterKnife.findById(headerdesc, R.id.id_shop_title);
        idUnlineDetailAddre = ButterKnife.findById(headerdesc, R.id.id_unline_detail_addre);
        idUnlineDetailRemarknum = ButterKnife.findById(headerdesc, R.id.id_unline_detail_remarknum);
        idShopIv.setOnClickListener(this);

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

        adapter.setImgClick(new ShopRemarkAdapter.OnImgClick() {
            @Override
            public void OnClickItem(List<String> imgs, int position) {
                imgDialog = new ImgDialog(mContext,imgs,position);
                imgDialog.showAtLocation(idShopPay, Gravity.BOTTOM, 0, 0);
            }
        });
        xlistview.setAdapter(adapter);
        getDetail();
        setOnRetryListener(new OnRetryListener() {
            @Override
            public void onRetry() {
                getDetail();
            }
        });
    }

    void getDetail() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getUnlineShopDetail(mContext, shopId, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error);
                setRetryText(R.string.res_string_retry);
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
        ProjectApplication.mImageLoader.loadRoundedImage(idShopIv, UtilPro.getImgHttpUrl(UtilString.nullToEmpty(data.getMerchant_thumb())));
        idShopTitle.setText(UtilString.nullToEmpty(data.getMerchant_name()));
        idImgNum.setText(UtilString.nullToEmpty(data.getPhoto_num()));
        ratingBar.setStar(data.getGrade());
        idUnlineDetailAddre.setText(UtilString.nullToEmpty(data.getAddress()));
        idUnlineDetailAddre.setOnClickListener(this);
        phoneIb.setOnClickListener(this);

        idShopmsgTv.setText(String.format(getString(R.string.unline_shop_msg), Util.formatPrice(data.getAverage()), data.getArea_name(), data.getIntro()));
        idUnlineDetailRemarknum.setText(String.format(getString(R.string.unline_remark), data.getComment_num()));
    }
    void getShare(View view) {
        if (shareDialog == null) {
            shareDialog = new ShareDialog(mContext, info.getShare_url(), UtilPro.getImgHttpUrl(info.getMerchant_thumb()),
                    info.getMerchant_name(), info.getIntro());
        }
        shareDialog.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }
    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @OnClick({R.id.id_shop_share, R.id.id_shop_reamrk, R.id.id_shop_pay})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.id_unline_detail_remarknum:
                if (info != null)
                    ProDispatcher.goPointUnlineShopReamrk(mContext, info.getMerchant_id());
                break;
            case R.id.id_goodsdetail_more_tv:
                if (info != null)
                    ProDispatcher.goPointUnlineShopReamrk(mContext, info.getMerchant_id());
                break;
            case R.id.id_shop_iv:
                if (info != null)
                    ProDispatcher.goShopImgActivity(mContext, info.getMerchant_id());
                break;
            case R.id.id_unline_detail_addre:
                if (info != null)
                    ProDispatcher.goLocationActivity(mContext, info.getLongitude(), info.getLatitude());
                break;
            case R.id.id_unline_phone:
                if (info != null)
                    UtilPro.CallPhone(mContext, info.getPhone());
                break;
            case R.id.id_shop_pay:
                ProDispatcher.goUnlinePayActivity(mContext, shopId);
                break;
            case R.id.id_shop_share:
                getShare(idShopShare);
                break;
            case R.id.id_shop_reamrk:
                ProDispatcher.goShopRemarkActivity(mContext, shopId);
                break;
        }
    }

    @Override
    protected void onReceive(Intent intent) {
        super.onReceive(intent);
        switch (intent.getAction()) {
            case CstProject.BROADCAST_ACTION.REMARK_ACTION:
                getDetail();
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
