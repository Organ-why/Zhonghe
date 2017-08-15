package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseTopActivity {

    @Bind(R.id.ids_icon_input3)
    ImageView idsIconInput3;
    @Bind(R.id.id_login_name_et)
    EditText idLoginNameEt;
    @Bind(R.id.ids_icon_input4)
    ImageView idsIconInput4;
    @Bind(R.id.id_login_pwd_et)
    EditText idLoginPwdEt;
    @Bind(R.id.id_login_register_ll)
    LinearLayout idLoginRegisterLl;
    @Bind(R.id.id_login_forgetpwd_tv)
    TextView idLoginFogavepwdTv;
    @Bind(R.id.id_login_log_bt)
    Button idLoginLogBt;

    @Override
    protected void initTop() {
        setTitle(R.string.title_login_title);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.id_login_register_ll, R.id.id_login_forgetpwd_tv, R.id.id_login_log_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_login_register_ll:
                ProDispatcher.goRegisterActivity(this);
                break;
            case R.id.id_login_forgetpwd_tv:
                ProDispatcher.goForgetPwdActivity(this);
                break;
            case R.id.id_login_log_bt:

                break;
        }
    }


}
