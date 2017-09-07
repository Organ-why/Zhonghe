package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
 * 修改密码
 */
public class ChangePwdActivity extends BaseTopActivity {


    @Bind(R.id.ids_register_pwdag_et)
    EditText idsRegisterPwdagEt;
    @Bind(R.id.ids_register_code_et)
    EditText idsRegisterCodeEt;
    @Bind(R.id.id_register_regist_bt)
    Button idRegisterRegistBt;

    @Override
    protected void initTop() {
        setTitle(R.string.setup_changepwd_text);
        setNavigation(R.mipmap.common_nav_back);
    }


    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_changepwd);
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
                break;
            case R.id.id_register_regist_bt:
                break;
        }
    }

    //检查信息后进行注册
    void toChangePwd() {
        String oldpwd = idsRegisterPwdagEt.getText().toString();
        String newpwd = idsRegisterCodeEt.getText().toString();
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getChangePWD(this, oldpwd, newpwd, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext,error);
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

    // 获取验证码


    @OnClick(R.id.id_register_regist_bt)
    public void onViewClicked() {
    }
}
