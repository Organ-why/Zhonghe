package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.zhonghe.lib_base.utils.Utilm;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date: 2017/8/12.
 * Author: whyang
 */
public class RegisterActivity extends BaseTopActivity {
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

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @Override
    protected void initTop() {
        setTitle(R.string.title_register_title);
        setNavigation(R.mipmap.common_nav_back);
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

    void toRegiste() {
        String phone = idsRegisterPhoneEt.getText().toString();
        String code= idsRegisterCodeEt.getText().toString();
        String pwd = idsRegisterPwdEt.getText().toString();
        if (Utilm.isRegiste(this, idsRegisterPhoneEt, idsRegisterCodeEt, idsRegisterPwdEt, idsRegisterPwdagEt)) {
            setWaitingDialog(true);
            Request<?> request = HttpUtil.getRegiste(this, phone, code, pwd, new ResultListener() {
                @Override
                public void onFail(String error) {
                    setWaitingDialog(false);
                }

                @Override
                public void onSuccess(Object obj) {
                    setWaitingDialog(false);
                }
            });
            addRequest(request);
        }
        ;
//        Utilm.
    }

    void getPhoneCode() {
        String phone = idsRegisterPhoneEt.getText().toString();

        if (!Utilm.isPhone(phone)) {
            Utilm.toast(this, R.string.title_register_phone_tip);
            return;
        }

        setWaitingDialog(true);
        Request<?> request = HttpUtil.getPhoneCode(this, phone, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Utilm.toast(RegisterActivity.this, error);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                Utilm.toast(RegisterActivity.this, obj.toString());
            }

        });
        addRequest(request);
    }
}
