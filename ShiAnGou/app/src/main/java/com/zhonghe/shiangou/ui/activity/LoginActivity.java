package com.zhonghe.shiangou.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.zhonghe.lib_base.utils.Util;
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

    void goLogin() {
        String phone = idLoginNameEt.getText().toString();
        String pwd = idLoginPwdEt.getText().toString();

        if (!Util.isPhone(phone)) {
            Util.toast(this, R.string.title_register_phone_tip);
            return;
        }
        if (!Util.isPwd(pwd)) {
            Util.toast(this, R.string.title_register_pwd_tip);
            return;
        }

        setWaitingDialog(true);
        Request<?> request = HttpUtil.getLogin(this, phone, pwd, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(LoginActivity.this, error);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                UserInfo user = (UserInfo) obj;
                ProjectApplication.mUser = user;
                ProjectApplication.mDaoFactory.getUserDao().addUser(user);
                ProjectApplication.mPrefrence.setUserId(user.getUser_id());
                ProDispatcher.sendLoginBroadcast(mContext);
                finish();
            }
        });
        addRequest(request);

    }

    @OnClick({R.id.id_login_register_ll, R.id.id_login_forgetpwd_tv, R.id.id_login_log_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_login_register_ll:
                ProDispatcher.goRegisterActivity(this, "注册");
                break;
            case R.id.id_login_forgetpwd_tv:
                ProDispatcher.goForgetPwdActivity(this);
                break;
            case R.id.id_login_log_bt:
                goLogin();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (ProjectApplication.mUser == null) {
//            ProDispatcher.sendMainTabBroadcast(mContext, 0);
//        }
//
    }
}
