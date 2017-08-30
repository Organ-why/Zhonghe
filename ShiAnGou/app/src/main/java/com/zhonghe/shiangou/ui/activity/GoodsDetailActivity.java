package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.lib_base.utils.Utilm;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.BaseBannerInfo;
import com.zhonghe.shiangou.data.bean.GoodsdetailInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.dialog.SkuSelectDialog;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.ui.widget.DynamicBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by a on 2017/8/15.
 */

public class GoodsDetailActivity extends BaseTopActivity {
    @Bind(R.id.id_goodsdetail_banner_ll)
    LinearLayout idGoodsdetailBannerLl;
    @Bind(R.id.id_goodsdetail_title_tv)
    TextView idGoodsdetailTitleTv;
    @Bind(R.id.id_goodsdetail_desc_tv)
    TextView idGoodsdetailDescTv;
    @Bind(R.id.id_goodsdetail_price_tv)
    TextView idGoodsdetailPriceTv;
    @Bind(R.id.id_goodsdetail_soldnum_tv)
    TextView idGoodsdetailSoldnumTv;
    @Bind(R.id.id_item_remark_header_img)
    SimpleDraweeView idItemRemarkHeaderImg;
    @Bind(R.id.id_item_remark_name_tv)
    TextView idItemRemarkNameTv;
    @Bind(R.id.id_item_remark_date_tv)
    TextView idItemRemarkDateTv;
    @Bind(R.id.id_item_remark_desc_tv)
    TextView idItemRemarkDescTv;
    @Bind(R.id.id_goodsdetail_buynow_bt)
    Button idGoodsdetailBuynowBt;
    @Bind(R.id.id_goodsdetail_addcart_bt)
    Button idGoodsdetailAddcartBt;
    @Bind(R.id.id_goodsdetail_sku_rl)
    RelativeLayout skurl;
    @Bind(R.id.id_layout_remark)
    RelativeLayout remark;

    SkuSelectDialog mDialog;
    List<BaseBannerInfo> bannerInfo;
    String goodsId;
    GoodsdetailInfo data;
    @Bind(R.id.id_goodsdetail_like_ib)
    ImageButton idGoodsdetailLikeIb;
    @Bind(R.id.id_goodsdetail_more_tv)
    TextView idGoodsdetailMoreTv;

    //添加商品数量
    private int mCount = 1;
    private String mSKU;
    //SKU 选择Dialog
    SkuSelectDialog skudialog;

    boolean isFollow = true;

    @Override
    protected void initTop() {
        setTitle(R.string.prodetail_act_title);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_goodsdetail);
        ButterKnife.bind(this);
        goodsId = getIntent().getStringExtra(CstProject.KEY.ID);
        bannerInfo = new ArrayList<>();
    }

    @Override
    protected void initViews() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getGoodsDetail(this, goodsId, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                data = (GoodsdetailInfo) obj;
                if (data != null) {
                    setShowMsg();
                    setShowBanner();
                }
            }
        });
        addRequest(request);
    }

    //商品信息
    void setShowMsg() {
        idGoodsdetailTitleTv.setText(data.getGoods().getGoods_name());
        idGoodsdetailDescTv.setText(data.getGoods().getGoods_desc());
        idGoodsdetailPriceTv.setText(data.getGoods().getShop_price());
        idGoodsdetailSoldnumTv.setText(data.getGoods().getWarn_number());
        if (data.getGoods_ping() != null) {
            String img = data.getGoods_ping().getImg();
            ProjectApplication.mImageLoader.loadCircleImage(idItemRemarkHeaderImg, UtilString.nullToEmpty(img));
            idItemRemarkNameTv.setText(UtilString.nullToEmpty(data.getGoods_ping().getUser_name()));
            idItemRemarkDateTv.setText(UtilString.nullToEmpty(data.getGoods_ping().getAdd_time()));
            idItemRemarkDescTv.setText(UtilString.nullToEmpty(data.getGoods_ping().getContent()));
        } else {
            remark.setVisibility(View.GONE);
        }
    }

    //商品图片
    void setShowBanner() {

        for (String imgurl : data.getGoods_img()) {
            BaseBannerInfo baseBannerInfo = new BaseBannerInfo();
            baseBannerInfo.setBanner_images(imgurl);
            bannerInfo.add(baseBannerInfo);
        }
        if (bannerInfo.size() > 0) {
            View BannerView = new DynamicBanner(this, LayoutInflater.from(this), 5000).initView(bannerInfo);
//
//            Utilm.dip2px(this, 175)
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  Utilm.getScreenWidth(mContext));
            BannerView.setLayoutParams(layoutParams);
            idGoodsdetailBannerLl.addView(BannerView);
        }
    }
//    void showTag(){
//        for (String strTag:data.getGoods().
//             ) {
//
//        }
//        View tagView = LayoutInflater.from(this).inflate(R.layout.item_tag_goods, null);
//    }

    @OnClick({R.id.id_goodsdetail_more_tv, R.id.id_goodsdetail_sku_rl, R.id.id_goodsdetail_buynow_bt, R.id.id_goodsdetail_addcart_bt, R.id.id_goodsdetail_like_ib})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_goodsdetail_buynow_bt:
                if (ProjectApplication.mUser == null) {
                    ProDispatcher.goLoginActivity(this);
                    break;
                }
                showSkuDialog(view, false);

                break;
            case R.id.id_goodsdetail_addcart_bt:
                if (ProjectApplication.mUser == null) {
                    ProDispatcher.goLoginActivity(this);
                    break;
                }
                showSkuDialog(view, true);
//                PayDialog dialog = new PayDialog(this);
//                dialog.showAtLocation(view, Gravity.CENTER, 0, 0);
//                dialog.show();
                break;
            case R.id.id_goodsdetail_like_ib:
                if (ProjectApplication.mUser == null) {
                    ProDispatcher.goLoginActivity(this);
                    break;
                }
                setWaitingDialog(true);
                Request<?> request = HttpUtil.getFollowGoods(mContext, data.getGoods().getGoods_id(), isFollow, new ResultListener() {
                    @Override
                    public void onFail(String error) {
                        setWaitingDialog(false);
                    }

                    @Override
                    public void onSuccess(Object obj) {
                        setWaitingDialog(false);
                        if (isFollow) {
                            idGoodsdetailLikeIb.setImageResource(R.mipmap.res_follow_selected);
                        } else {
                            idGoodsdetailLikeIb.setImageResource(R.mipmap.res_follow_nomal);
                        }
                        isFollow = !isFollow;
                    }
                });
                addRequest(request);
                break;
            case R.id.id_goodsdetail_sku_rl:
                if (ProjectApplication.mUser == null) {
                    ProDispatcher.goLoginActivity(this);
                    break;
                }
                showSkuDialog(view, true);
//                dialog.show();
                break;
            case R.id.id_goodsdetail_more_tv:
                ProDispatcher.goRemarkActivity(this);
                break;
        }
    }

    void showSkuDialog(View view, boolean justSku) {
        if (skudialog == null) {
            skudialog = new SkuSelectDialog(this, data, mCount, mSKU, new SkuSelectDialog.SkuSelectListener() {
                @Override
                public void onCountChange(int count) {
                    mCount = count;
                }

                @Override
                public void onCheckSKU(String sku) {
                    mSKU = sku;
                }

                @Override
                public void onAddCart(String sku) {
                    setWaitingDialog(true);
                    Request<?> request = HttpUtil.getAddCart(mContext, goodsId, UtilString.nullToString(mSKU), mCount + "", new ResultListener() {
                        @Override
                        public void onFail(String error) {
                            setWaitingDialog(false);
//                            skudialog.dismiss();
                        }

                        @Override
                        public void onSuccess(Object obj) {
                            setWaitingDialog(false);
                            skudialog.dismiss();
                            Utilm.toast(mContext, R.string.common_cart_add_success);
                        }
                    });
                    addRequest(request);
                }

                @Override
                public void onBuyNow(String sku) {
                    skudialog.dismiss();
                    ArrayList<String> list = new ArrayList<>();
                    list.add(data.getGoods().getGoods_id());
                    ProDispatcher.goConfirmOrderActivity(mContext, list);
                }
            });
        }
        skudialog.setDialogType(justSku);
        skudialog.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }


}
