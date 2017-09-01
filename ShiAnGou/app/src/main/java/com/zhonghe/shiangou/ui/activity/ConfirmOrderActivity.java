package com.zhonghe.shiangou.ui.activity;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilLog;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.AddressInfo;
import com.zhonghe.shiangou.data.bean.CharPay;
import com.zhonghe.shiangou.data.bean.ConfirmRspInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.dialog.PayDialog;
import com.zhonghe.shiangou.ui.listener.ConfirmGoodsList;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.utile.UtilPay;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date: 2017/8/12.
 * Author: whyang
 */
public class ConfirmOrderActivity extends BaseTopActivity implements PayDialog.payClickListener {
    @Bind(R.id.id_confirmorder_address_rl)
    RelativeLayout idConfirmorderAddressRl;
    List<String> ids;

    ConfirmRspInfo mData;
    @Bind(R.id.id_confirmorder_goodslist_ll)
    LinearLayout idConfirmorderGoodslistLl;
    @Bind(R.id.cart_id_all_cb)
    TextView cartIdAllCb;
    @Bind(R.id.cart_id_totalpay_tv)
    TextView cartIdTotalpayTv;
    @Bind(R.id.cart_id_total_tv)
    TextView cartIdTotalTv;
    @Bind(R.id.cart_id_total_ll)
    LinearLayout cartIdTotalLl;
    @Bind(R.id.cart_id_tobuy_bt)
    Button cartIdTobuyBt;
    @Bind(R.id.cart_id_del_bt)
    Button cartIdDelBt;
    @Bind(R.id.cart_id_pay_rl)
    RelativeLayout cartIdPayRl;
    @Bind(R.id.id_confirm_address_name_tv)
    TextView idConfirmAddressNameTv;
    @Bind(R.id.id_confirm_address_phone_tv)
    TextView idConfirmAddressPhoneTv;
    @Bind(R.id.id_confirm_address_area_tv)
    TextView idConfirmAddressAreaTv;
    @Bind(R.id.id_confirm_address_ll)
    LinearLayout idConfirmAddressLl;
    @Bind(R.id.id_confirm_num_tv)
    TextView idConfirmNumTv;
    @Bind(R.id.id_confirm_)
    EditText idConfirm;
    @Bind(R.id.id_confirm_total_tv)
    TextView idConfirmTotalTv;

    //选择地址
    private static final int REQUEST_ADDRESS_CODE = 0x10 + 1;

    String orderCode = "";
    CharPay.DataBean orderMsg;
    //支付dialog
    private PayDialog dialog;

    @Override
    protected void initTop() {
        setTitle(R.string.confirmorder_title_text);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_confirmorder);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        registerAction(CstProject.BROADCAST_ACTION.PAY_RESULT_ACTION);
        Intent intent = getIntent();
        ids = intent.getStringArrayListExtra(CstProject.KEY.ID);
        getConfirmData();
    }

    //确认商品
    void getConfirmData() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getConfirmGoods(mContext, ids, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);

                mData = (ConfirmRspInfo) obj;
                setDataShow();

            }
        });
        addRequest(request);
    }

    //生成订单
    void submitOrder() {
        if (UtilString.isEmpty(mData.getDefault_add().getArea_address())) {
            Util.toast(mContext, R.string.confirmorder_receiptmsg_text);
            return;
        }
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getSubmitOrder(mContext, ids, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error);
            }

            @Override
            public void onSuccess(Object obj) {
                orderCode = (String) obj;
                setWaitingDialog(false);
//                Util.toast(mContext, R.string.common_success_tip);
//                setDataShow();
//                goPay(orderCode);
                if (UtilString.isNotEmpty(orderCode)) {
                    UtilLog.d("submitOrder_success");
                    if (dialog == null) {
                        dialog = new PayDialog(mContext, ConfirmOrderActivity.this);
                    }
                    dialog.showAtLocation(cartIdTobuyBt, Gravity.BOTTOM, 0, 0);
                } else {
                    Util.toast(mContext, R.string.confirmorder_order_fail);
                }
            }
        });
        addRequest(request);
    }

    //支付凭证
    void goPay(String orderCode, final int payType) {
        setWaitingDialog(true);

        Request<?> request = HttpUtil.getPay(mContext, orderCode, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error);
            }
            @Override
            public void onSuccess(Object obj) {
                CharPay charPay = (CharPay) obj;
                orderMsg = charPay.getData();
                setWaitingDialog(false);
                UtilPay.sartPay(payType, orderMsg);
            }
        });
        addRequest(request);
    }

    @OnClick({R.id.id_confirmorder_address_rl, R.id.id_confirm_address_ll, R.id.cart_id_tobuy_bt})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.id_confirmorder_address_rl:
                ProDispatcher.goSelectAddressActivity(this, REQUEST_ADDRESS_CODE);
                break;
            case R.id.id_confirm_address_ll:
                ProDispatcher.goSelectAddressActivity(this, REQUEST_ADDRESS_CODE);
//                ProDispatcher.goSelectAddressActivity(this);
                break;
            case R.id.cart_id_tobuy_bt:
                submitOrder();
                break;
        }
    }

    void setDataShow() {
        new ConfirmGoodsList(mData.getCom_goods(), mContext).initView(idConfirmorderGoodslistLl);
        setAddressShow(mData.getDefault_add());
        idConfirmNumTv.setText(String.format(getResources().getString(R.string.confirmorder_goodscount_text),
                mData.getCom_count()));
        cartIdTotalpayTv.setText(String.valueOf(mData.getTotal()));
    }

    //    设置地址显示
    void setAddressShow(AddressInfo default_add) {
        if (UtilString.isEmpty(default_add.getAddress_id())) {
            idConfirmorderAddressRl.setVisibility(View.VISIBLE);
            idConfirmAddressLl.setVisibility(View.GONE);
        } else {
            idConfirmorderAddressRl.setVisibility(View.GONE);
            idConfirmAddressLl.setVisibility(View.VISIBLE);
            idConfirmAddressNameTv.setText(String.format(getResources().getString(R.string.confirmorder_name), default_add.getConsignee()));
            idConfirmAddressAreaTv.setText(String.format(getResources().getString(R.string.confirmorder_addre), default_add.getArea_address() + default_add.getAddress()));
            idConfirmAddressPhoneTv.setText(String.format(getResources().getString(R.string.confirmorder_phone), default_add.getMobile()));
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
                        mData.setDefault_add(address);
                        setAddressShow(mData.getDefault_add());
                    }
                    break;
            }
        }
    }

    @Override
    protected void onReceive(Intent intent) {
        switch (intent.getAction()) {
            case CstProject.BROADCAST_ACTION.PAY_RESULT_ACTION:

                break;
        }
    }

    @Override
    public void OnClickPay(int payType) {
        goPay(orderCode, payType);
    }
}
