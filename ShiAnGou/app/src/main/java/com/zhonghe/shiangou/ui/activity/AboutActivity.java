package com.zhonghe.shiangou.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.utile.UtilPay;
import com.zhonghe.shiangou.utile.UtilPro;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseTopActivity {


    @Bind(R.id.id_user_point_rl)
    RelativeLayout idUserPointRl;

    @Override
    protected void initTop() {
        setTitle(R.string.about_title);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.id_user_point_rl)
    public void onViewClicked() {
        UtilPro.CallPhone(mContext, mContext.getString(R.string.about_phone));
    }
}
