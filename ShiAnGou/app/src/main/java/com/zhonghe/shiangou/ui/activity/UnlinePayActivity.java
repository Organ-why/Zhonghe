package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.zhonghe.shiangou.data.bean.UserInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * auther: whyang
 * date: 2017/9/25
 * desc:线下支付
 */

public class UnlinePayActivity extends BaseTopActivity {

    @Bind(R.id.id_pay_pointnum_tv)
    TextView idPayPointnumTv;
    @Bind(R.id.id_pay_total_ev)
    EditText idPayTotalEv;
    @Bind(R.id.id_pay_lack_tv)
    TextView idPayLackTv;
    @Bind(R.id.radiogroup)
    RadioGroup radiogroup;
    @Bind(R.id.money_pay_ll)
    LinearLayout moneyPayLl;
    @Bind(R.id.confirm_pay)
    Button confirmPay;


    double rankPoint = 0;
    double totalPay = 0;
    private UserInfo userInfo;

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
        idPayTotalEv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                    String s = idPayTotalEv.getText().toString().trim();
                    if (UtilString.isBlank(s)) {
                        Util.toast(mContext, R.string.unline_pay_total_tip);
                        return true;
                    }
                    //如果actionId是搜索的id，则进行下一步的操作
                    totalPay = Double.valueOf(s);
                    if (totalPay > rankPoint) {
                        moneyPayLl.setVisibility(View.VISIBLE);
                        idPayLackTv.setText(String.format(getString(R.string.unline_pay_lack), Util.formatPrice(Math.abs(totalPay - rankPoint))));
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


    @OnClick(R.id.confirm_pay)
    public void onViewClicked() {
    }
}
