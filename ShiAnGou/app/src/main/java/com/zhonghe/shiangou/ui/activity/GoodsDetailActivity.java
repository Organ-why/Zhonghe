package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.GoodsdetailInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.dialog.PayDialog;
import com.zhonghe.shiangou.ui.dialog.SkuSelectDialog;
import com.zhonghe.shiangou.ui.listener.ResultListener;

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

    SkuSelectDialog mDialog;

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
                if (data != null) setShowMsg();

            }
        });
        addRequest(request);
    }

    void setShowMsg() {
        idGoodsdetailTitleTv.setText(data.getGoods_name());

    }


    public void selectPicture(View parent) {
        if (mDialog == null) {
//            mCropParams.enable = true;
//            mCropParams.compress = false;
            mDialog = new SkuSelectDialog(this, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    mCropParams.refreshUri();
//                    int id = view.getId();
//                    if (id == R.id.photo_id_camera) {//拍照
//                        Intent intent = CropHelper.buildCameraIntent(mCropParams);
//                        startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
//                    } else if (id == R.id.photo_id_picked) {//相册选择图片
//                        Intent intent = CropHelper.buildGalleryIntent(mCropParams);
//                        startActivityForResult(intent, CropHelper.REQUEST_PICK);
//                    }
                }
            });
        }

        mDialog.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    @OnClick({R.id.id_goodsdetail_buynow_bt, R.id.id_goodsdetail_addcart_bt, R.id.id_goodsdetail_like_ib})
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
        }
    }

}
