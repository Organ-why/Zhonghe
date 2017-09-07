package com.zhonghe.shiangou.ui.activity;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date: 2017/8/13.
 * Author: whyang
 */
public class ForgetPwdActivity extends BaseTopActivity {

    @Bind(R.id.id_register_getcode_tv)
    TextView idRegisterGetcodeTv;
    @Bind(R.id.ids_register_phone_et)
    EditText idsRegisterPhoneEt;
    @Bind(R.id.ids_register_code_et)
    EditText idsRegisterCodeEt;
    @Bind(R.id.ids_register_pwd_et)
    EditText idsRegisterPwdEt;
    @Bind(R.id.ids_register_pwdag_et)
    EditText idsRegisterPwdagEt;
    @Bind(R.id.id_register_regist_bt)
    Button idRegisterRegistBt;
    private static int mEndTimes = 60 * 1000;
    private CountDownTimer mDownTimer;

    @Override
    protected void initTop() {
        setTitle(R.string.setup_forgetpwd_text);
        setNavigation(R.mipmap.common_nav_back);
    }


    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

    }

    @Override
    protected void initViews() {
        idRegisterRegistBt.setText(R.string.common_confirm);
    }

    @OnClick({R.id.id_register_getcode_tv, R.id.id_register_regist_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_register_getcode_tv:
                getPhoneCode();
                break;
            case R.id.id_register_regist_bt:
                toRegiste();
                break;
        }
    }

    //检查信息后进行注册
    void toRegiste() {
        String phone = idsRegisterPhoneEt.getText().toString();
        String code = idsRegisterCodeEt.getText().toString();
        String pwd = idsRegisterPwdEt.getText().toString();
        if (Util.isRegiste(this, idsRegisterPhoneEt, idsRegisterCodeEt, idsRegisterPwdEt, idsRegisterPwdagEt)) {
            setWaitingDialog(true);
            Request<?> request = HttpUtil.getForgetPWD(this, phone, code, pwd, new ResultListener() {
                @Override
                public void onFail(String error) {
                    setWaitingDialog(false);
                }

                @Override
                public void onSuccess(Object obj) {
                    setWaitingDialog(false);
                    Util.toast(mContext, R.string.common_success_tip);
                    finish();
                }
            });
            addRequest(request);
        }
        ;
//        Util.
    }

    // 获取验证码
    void getPhoneCode() {
        String phone = idsRegisterPhoneEt.getText().toString();

        if (!Util.isPhone(phone)) {
            Util.toast(this, R.string.title_register_phone_tip);
            return;
        }
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getPhoneCode(this, phone, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error.toString());
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                String result = (String) obj;
                Util.toast(mContext, result);
                setTime();
            }

        });
        addRequest(request);
    }

    /**
     * 设置获取验证码是否可用
     *
     * @param isClick
     */
    private void setTextClickable(boolean isClick) {
        idRegisterGetcodeTv.setClickable(isClick);
        idRegisterGetcodeTv.setEnabled(isClick);
    }

    // 倒计时
    public void setTime() {
        if (mDownTimer != null) {
            mDownTimer.cancel();
        }
        setTextClickable(false);
        mDownTimer = new CountDownTimer(mEndTimes, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                idRegisterGetcodeTv.setText(
                        getString(R.string.title_register_code_time, millisUntilFinished / 1000 < 10 ? "0"
                                + millisUntilFinished / 1000 : millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                //registerGetsecuritycode.setText(R.string.register_to_get_securitycode);
                idRegisterGetcodeTv.setText(R.string.register_to_get_securitycode);
                setTextClickable(true);
            }
        }.start();
    }
}
