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
import com.zhonghe.lib_base.utils.Utilm;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.BaseBannerInfo;
import com.zhonghe.shiangou.data.bean.GoodsdetailInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.dialog.PayDialog;
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

    SkuSelectDialog mDialog;
    List<BaseBannerInfo> bannerInfo;
    String goodsId;
    GoodsdetailInfo data;
    @Bind(R.id.id_goodsdetail_like_ib)
    ImageButton idGoodsdetailLikeIb;

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
        Request<?> request = HttpUtil.getGoodsDetail(this, "115", new ResultListener() {
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

    void setShowMsg() {
        idGoodsdetailTitleTv.setText(data.getGoods().getGoods_name());
        idGoodsdetailDescTv.setText(data.getGoods().getGoods_desc());
        idGoodsdetailPriceTv.setText(data.getGoods().getShop_price());
        idGoodsdetailSoldnumTv.setText(data.getGoods().getWarn_number());

        ProjectApplication.mImageLoader.loadImage(idItemRemarkHeaderImg,data.getGoods_ping().getImg());
        idItemRemarkNameTv.setText(data.getGoods_ping().getUser_name());
        idItemRemarkDateTv.setText(data.getGoods_ping().getAdd_time());
        idItemRemarkDescTv.setText(data.getGoods_ping().getContent());
    }

    void setShowBanner() {

        for (String imgurl : data.getGoods_img()) {
            BaseBannerInfo baseBannerInfo = new BaseBannerInfo();
            baseBannerInfo.setImgUrl(imgurl);
            bannerInfo.add(baseBannerInfo);
        }
        if (bannerInfo.size() > 0) {
            View BannerView = new DynamicBanner(this, LayoutInflater.from(this), 5000).initView(bannerInfo);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utilm.dip2px(this, 175));
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

    @OnClick({R.id.id_goodsdetail_sku_rl,R.id.id_goodsdetail_buynow_bt, R.id.id_goodsdetail_addcart_bt, R.id.id_goodsdetail_like_ib})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_goodsdetail_buynow_bt:
                if (ProjectApplication.mUser == null) {
                    ProDispatcher.goLoginActivity(this);
                    break;
                }

                ProDispatcher.goRemarkActivity(this);
                break;
            case R.id.id_goodsdetail_addcart_bt:
                if (ProjectApplication.mUser == null) {
                    ProDispatcher.goLoginActivity(this);
                    break;
                }
                PayDialog dialog = new PayDialog(this);
                dialog.showAtLocation(view, Gravity.CENTER, 0, 0);
//                dialog.show();
                break;
            case R.id.id_goodsdetail_like_ib:
                if (ProjectApplication.mUser == null) {
                    ProDispatcher.goLoginActivity(this);
                    break;
                }
                break;
            case R.id.id_goodsdetail_sku_rl:
                if (ProjectApplication.mUser == null) {
                    ProDispatcher.goLoginActivity(this);
                    break;
                }
                SkuSelectDialog skudialog = new SkuSelectDialog(this);
                skudialog.showAtLocation(view, Gravity.BOTTOM, 0, 0);
//                dialog.show();
                break;
        }
    }

}
