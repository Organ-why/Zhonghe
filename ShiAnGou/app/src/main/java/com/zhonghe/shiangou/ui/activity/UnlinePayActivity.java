package com.zhonghe.shiangou.ui.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.CharPay;
import com.zhonghe.shiangou.data.bean.UserInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.utile.UtilPay;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * auther: whyang
 * date: 2017/9/25
 * desc:线下支付
 */

public class UnlinePayActivity extends BaseTopActivity {

    @BindView(R.id.id_pay_pointnum_tv)
    TextView idPayPointnumTv;
    @BindView(R.id.id_pay_total_ev)
    EditText idPayTotalEv;
    @BindView(R.id.id_pay_lack_tv)
    TextView idPayLackTv;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.money_pay_ll)
    LinearLayout moneyPayLl;
    @BindView(R.id.confirm_pay)
    Button confirmPay;

    private UserInfo userInfo;
    CharPay.DataBean orderMsg;
    //用户积分
    double rankPoint = 0;
    //总支付金额
    double totalPay = 0;
    //最终需要支付的金额
    private double actualMoney = 0;
    //最终需要的积分
    private double actualPoint = 0;

    private String shopId;
    //支付类型
    private int payType = 1;

    @Override
    protected void initTop() {
        setTitle(R.string.unline_pay_title);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_unline_pay);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        registerAction(CstProject.BROADCAST_ACTION.PAY_RESULT_ACTION);
        Intent intent = getIntent();
        shopId = intent.getStringExtra(CstProject.KEY.ID);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radio_bt_ali:
                        payType = 1;
                        break;
                    case R.id.radio_bt_wehcat:
                        payType = 2;
                        break;
                }
            }
        });
        idPayTotalEv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                    String s = idPayTotalEv.getText().toString().trim();
                    //如果actionId是搜索的id，则进行下一步的操作
                    totalPay = Double.valueOf(s);
                    if (UtilString.isBlank(s) || totalPay == 0) {
                        Util.toast(mContext, R.string.unline_pay_total_tip);
                        return true;
                    }

                    if (totalPay > rankPoint) {
                        moneyPayLl.setVisibility(View.VISIBLE);
                        idPayLackTv.setText(String.format(getString(R.string.unline_pay_lack), Util.formatPrice(Math.abs(totalPay - rankPoint))));
                    } else {
                        moneyPayLl.setVisibility(View.GONE);
                    }
                    return false;
                }
                return true;
            }
        });
        setOnRetryListener(new OnRetryListener() {
            @Override
            public void onRetry() {
                setRetry(false);
                getUserPoint();
            }
        });
        getUserPoint();
    }

    void getUserPoint() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getUserMSGPoint(mContext, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                setRetryText(R.string.res_string_retry);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                userInfo = (UserInfo) obj;
                rankPoint = userInfo.getRank_points();
                idPayPointnumTv.setText(String.format(mContext.getString(R.string.unline_pay_pointnum), Util.formatPrice(userInfo.getRank_points())));
            }
        });
        addRequest(request);
    }

    void selectPayType() {
        if (totalPay <= 0) {
            Util.toast(mContext, R.string.unline_pay_total_tip);
            return;
        }
        if (totalPay > rankPoint) {
            actualMoney = Math.abs(totalPay - rankPoint);
            actualPoint = rankPoint;
            getPay(payType);
        } else {
            actualMoney = 0;
            actualPoint = totalPay;
            payType = 3;
            getPay(payType);
        }
    }

    /**
     * 付款类型 1为支付宝，2为微信，3为现金(即只消费积分)
     *
     * @param payType
     */
    void getPay(final int payType) {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getUnlinePay_Ali(mContext, totalPay, actualMoney, actualPoint, shopId, payType, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error);
//                setRetryText(R.string.res_string_retry);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                orderMsg = (CharPay.DataBean) obj;
                if (orderMsg.getPay_type() == 3) {
                    ProDispatcher.goUnlineOrderActivity(mContext);
                    Util.toast(mContext, R.string.common_pay_success);
                    finish();
                    return;
                }
                if (orderMsg.getPay_type() == 1) {
                    orderMsg.setSign(orderMsg.getAli_secret());
                }
                UtilPay.sartPay(mContext, payType == 1 ? UtilPay.PAY_TYPE_ALIPAY : UtilPay.PAY_TYPE_WECHAT, orderMsg);
//                info = (UnlineShopDetailInfo) obj;
//                setDataShow(info);
            }
        });
        addRequest(request);
    }


    @OnClick(R.id.confirm_pay)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.confirm_pay:
                selectPayType();
                break;
        }
    }

    @Override
    protected void onReceive(Intent intent) {
        switch (intent.getAction()) {
            case CstProject.BROADCAST_ACTION.PAY_RESULT_ACTION:
                //支付返回结果
                int intExtra = intent.getIntExtra(CstProject.KEY.CODE, -1);
                if (intExtra == 0) {
                    getUserPoint();
                } else {
                    Util.toast(mContext, R.string.common_pay_fail);
                }
                break;
        }
    }

}
