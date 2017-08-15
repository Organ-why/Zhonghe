package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date: 2017/8/12.
 * Author: whyang
 */
public class ConfirmOrderActivity extends BaseTopActivity {
    @Bind(R.id.id_confirmorder_address_rl)
    RelativeLayout idConfirmorderAddressRl;

    @Override
    protected void initTop() {
        setTitle(R.string.confirmorder_title_text);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_confirmorder);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.id_confirmorder_address_rl)
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.id_confirmorder_address_rl:
                ProDispatcher.goSelectAddressActivity(this);
                break;
        }
    }
}
