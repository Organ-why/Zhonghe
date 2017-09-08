package com.zhonghe.shiangou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilLog;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.AddressInfo;
import com.zhonghe.shiangou.data.bean.ExchangeResultInfo;
import com.zhonghe.shiangou.data.bean.PointDetailInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * auther: whyang
 * date: 2017/8/21
 * desc: 积分详情
 */

public class PointDetailActivity extends BaseTopActivity {
    @Bind(R.id.id_point_detail_name_tv)
    TextView idPointDetailNameTv;
    @Bind(R.id.id_point_detail_point_tv)
    TextView idPointDetailPointTv;
    @Bind(R.id.id_point_detail_stock_tv)
    TextView idPointDetailStockTv;
    @Bind(R.id.id_user_address_rl)
    RelativeLayout idUserAddressRl;
    @Bind(R.id.id_confirm_address_name_tv)
    TextView idConfirmAddressNameTv;
    @Bind(R.id.id_confirm_address_phone_tv)
    TextView idConfirmAddressPhoneTv;
    @Bind(R.id.id_confirm_address_area_tv)
    TextView idConfirmAddressAreaTv;
    @Bind(R.id.id_confirm_address_ll)
    LinearLayout idConfirmAddressLl;
    @Bind(R.id.id_point_detail_msgname_tv)
    TextView idPointDetailMsgnameTv;
    @Bind(R.id.id_point_detail_type_tv)
    TextView idPointDetailTypeTv;
    @Bind(R.id.id_point_detail_phone_tv)
    TextView idPointDetailPhoneTv;
    @Bind(R.id.id_point_detail_exchange_bt)
    Button idPointDetailExchangeBt;
    @Bind(R.id.id_point_iv)
    SimpleDraweeView idPointIv;
    private PointDetailInfo mData;
    private String goodsId;
    //选择地址
    private static final int REQUEST_ADDRESS_CODE = 0x10 + 1;

    @Override
    protected void initTop() {
        setTitle(R.string.common_point_detail_title_str);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_point_pro_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        goodsId = intent.getStringExtra(CstProject.KEY.ID);
        getGoodsList();
    }

    void getGoodsList() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getPointGoodsDetail(mContext, goodsId, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                mData = (PointDetailInfo) obj;
                setDataShow();
            }
        });
        addRequest(request);
    }

    void getExchangeGoods() {
        if (mData.getAddress() == null || UtilString.isEmpty(mData.getAddress().getAddress_id())) {
            Util.toast(mContext, R.string.confirmorder_receiptmsg_text);
            return;
        }
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getExchangeGoods(mContext, goodsId, mData.getAddress().getAddress_id(), new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                ExchangeResultInfo info = (ExchangeResultInfo) obj;
                ProjectApplication.mUser.setRank_points(info.getRank());
                ProDispatcher.goPointExchangeResutl(mContext);
                ProDispatcher.sendExchangeBroadcast(mContext);
                finish();
            }
        });
        addRequest(request);
    }

    //    设置地址显示
    void setAddressShow(AddressInfo default_add) {
        if (default_add == null || UtilString.isEmpty(default_add.getAddress_id())) {
            idUserAddressRl.setVisibility(View.VISIBLE);
            idConfirmAddressLl.setVisibility(View.GONE);
        } else {
            idUserAddressRl.setVisibility(View.GONE);
            idConfirmAddressLl.setVisibility(View.VISIBLE);
            idConfirmAddressNameTv.setText(String.format(getResources().getString(R.string.confirmorder_name), UtilString.nullToEmpty(default_add.getConsignee())));
            idConfirmAddressAreaTv.setText(String.format(getResources().getString(R.string.confirmorder_addre), UtilString.nullToEmpty(default_add.getArea_address()) + UtilString.nullToEmpty(default_add.getAddress())));
            idConfirmAddressPhoneTv.setText(String.format(getResources().getString(R.string.confirmorder_phone), UtilString.nullToEmpty(default_add.getMobile())));
        }
    }

    void setDataShow() {
        setAddressShow(mData.getAddress());
        ProjectApplication.mImageLoader.loadImage(idPointIv, mData.getGoods().getGoods_thumb());
        idPointDetailPointTv.setText(UtilString.nullToEmpty(Util.formatPrice(mData.getGoods().getExchange_integral())));
        idPointDetailNameTv.setText(UtilString.nullToEmpty(mData.getGoods().getGoods_name()));
        idPointDetailStockTv.setText(String.format(getString(R.string.point_msg_stock), mData.getGoods().getGoods_number()));
        idPointDetailMsgnameTv.setText(String.format(getString(R.string.point_msg_name), UtilString.nullToEmpty(mData.getGoods().getGoods_name())));
        idPointDetailTypeTv.setText(String.format(getString(R.string.point_msg_type), UtilString.nullToEmpty(mData.getGoods().getCat_name())));
        idPointDetailPhoneTv.setText(String.format(getString(R.string.point_msg_phone), UtilString.nullToEmpty(mData.getGoods().getPhone_number())));

    }

    @OnClick({R.id.id_user_address_rl, R.id.id_confirm_address_ll, R.id.id_point_detail_exchange_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_user_address_rl:
                ProDispatcher.goSelectAddressActivity(this, REQUEST_ADDRESS_CODE);
                break;
            case R.id.id_confirm_address_ll:
                ProDispatcher.goSelectAddressActivity(this, REQUEST_ADDRESS_CODE);
                break;
            case R.id.id_point_detail_exchange_bt:
                getExchangeGoods();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UtilLog.i(requestCode + "...." + resultCode + "....");
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //选择地址
                case REQUEST_ADDRESS_CODE:
                    if (null != data) {
                        AddressInfo address = (AddressInfo) data.getSerializableExtra(CstProject.KEY.DATA);
                        mData.setAddress(address);
                        setAddressShow(mData.getAddress());
                    }
                    break;
            }
        }
    }

}
