package com.zhonghe.shiangou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.RefundsDetailInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 退货详情
 * whyang
 * 2017/8/16.
 */

public class RefundsDetailActivity extends BaseTopActivity {
    @Bind(R.id.id_refund_begin_iv)
    ImageView idRefundBeginIv;
    @Bind(R.id.id_refund_begin_ttv)
    TextView idRefundBeginTtv;
    @Bind(R.id.id_refund_begin_tmv)
    TextView idRefundBeginTmv;
    @Bind(R.id.id_refund_ing_iv)
    ImageView idRefundIngIv;
    @Bind(R.id.id_refund_ing_ttv)
    TextView idRefundIngTtv;
    @Bind(R.id.id_refund_ing_tmv)
    TextView idRefundIngTmv;
    @Bind(R.id.id_refund_success_iv)
    ImageView idRefundSuccessIv;
    @Bind(R.id.id_refund_success_ttv)
    TextView idRefundSuccessTtv;
    @Bind(R.id.id_refund_success_tmv)
    TextView idRefundSuccessTmv;
    @Bind(R.id.id_refund_msg_code_tv)
    TextView idRefundMsgCodeTv;
    @Bind(R.id.id_refund_msg_total_tv)
    TextView idRefundMsgTotalTv;
    @Bind(R.id.id_refund_msg_explain_tv)
    TextView idRefundMsgExplainTv;
    @Bind(R.id.id_refund_msg_date_tv)
    TextView idRefundMsgDateTv;
    private String refundsId;
    private RefundsDetailInfo mData;

    @Override
    protected void initTop() {
        setNavigation(R.mipmap.common_nav_back);
        setTitle(R.string.prodetail_title_refundsdetail);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_refunds_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        refundsId = intent.getStringExtra(CstProject.KEY.ID);
        getRefundsDetail();
    }

    void getRefundsDetail() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getRefundsDetail(this, refundsId, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                mData = (RefundsDetailInfo) obj;
                if (mData != null) {
                    setShowMsg();
                }
            }
        });
        addRequest(request);
    }

    private void setShowMsg() {
        for (RefundsDetailInfo.SalesStateBean salesInfo : mData.getSales_state()) {
            switch (salesInfo.getStage()) {
                case "1":
                    idRefundBeginIv.setImageResource(R.mipmap.icon_cb_redcircle);
                    idRefundBeginTtv.setTextColor(mContext.getResources().getColor(R.color.common_red));
                    idRefundBeginTmv.setText(salesInfo.getDatetime());
                    idRefundMsgDateTv.setText(String.format(mContext.getString(R.string.prodetail_refunds_date_format), salesInfo.getDatetime()));
                    break;
                case "2":
                    idRefundIngIv.setImageResource(R.mipmap.icon_cb_redcircle);
                    idRefundIngTtv.setTextColor(mContext.getResources().getColor(R.color.common_red));
                    idRefundIngTmv.setText(salesInfo.getDatetime());
                    break;
                case "3":
                    idRefundSuccessIv.setImageResource(R.mipmap.icon_cb_redcircle);
                    idRefundSuccessTtv.setTextColor(mContext.getResources().getColor(R.color.common_red));
                    idRefundSuccessTmv.setText(salesInfo.getDatetime());
                    break;
            }
        }
        idRefundMsgCodeTv.setText(String.format(mContext.getString(R.string.prodetail_refunds_code_format), mData.getOrder_sn()));
        idRefundMsgExplainTv.setText(String.format(mContext.getString(R.string.prodetail_refunds_explain_format), mData.getExplain()));

        int childClass = mData.getPay_class();
        if (childClass == 0) {
            idRefundMsgTotalTv.setText(Util.formatPoint(mData.getTotal_price()));
        } else {
            idRefundMsgTotalTv.setText(Util.formatPrice(mData.getTotal_price()));
        }
    }

}
