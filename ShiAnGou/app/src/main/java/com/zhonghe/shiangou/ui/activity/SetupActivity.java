package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetupActivity extends BaseTopActivity {

    @BindView(R.id.id_setup_setheader_sv)
    SimpleDraweeView idSetupSetheaderSv;
    @BindView(R.id.id_setup_setname_rl)
    RelativeLayout idSetupSetnameRl;
    @BindView(R.id.id_setup_address_rl)
    RelativeLayout idSetupAddressRl;
    @BindView(R.id.id_setup_changepwd_rl)
    RelativeLayout idSetupChangepwdRl;
    @BindView(R.id.id_setup_fgvpwd_rl)
    RelativeLayout idSetupFgvpwdRl;
    @BindView(R.id.id_logout_bt)
    Button idLogoutBt;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_setup);
        ButterKnife.bind(this);
    }

    @Override
    protected void initTop() {
        setTitle(R.string.setup_title_text);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initViews() {
        setData();

    }

    void setData() {
        if (UtilString.isNotEmpty(ProjectApplication.mUser.getUser_pic())) {
            ProjectApplication.mImageLoader.loadCircleImage(idSetupSetheaderSv, ProjectApplication.mUser.getUser_pic());
        }
    }

    @OnClick({R.id.id_setup_setname_rl, R.id.id_setup_address_rl, R.id.id_setup_changepwd_rl, R.id.id_setup_fgvpwd_rl, R.id.id_logout_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_setup_setname_rl:
                ProDispatcher.goChangeNameActivity(mContext);
                break;
            case R.id.id_setup_address_rl:
                ProDispatcher.goSelectAddressActivity(mContext);
                break;
            case R.id.id_setup_changepwd_rl:
                ProDispatcher.goChangePwdActivity(mContext);
                break;
            case R.id.id_setup_fgvpwd_rl:
                ProDispatcher.goForgetPwdActivity(mContext);
                break;
            case R.id.id_logout_bt:
                ProjectApplication.mUser = null;
                ProjectApplication.mPrefrence.setUserId(null);
                ProDispatcher.sendLogoutBroadcast(mContext);
                ProDispatcher.goLoginActivity(mContext);
                finish();
                break;
        }
    }
}
