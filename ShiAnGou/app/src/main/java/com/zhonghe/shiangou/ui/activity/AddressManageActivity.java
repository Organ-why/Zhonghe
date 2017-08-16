package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressManageActivity extends BaseTopActivity {


    @Bind(R.id.id_addressmng_add_bt)
    Button idAddressmngAddBt;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_address_manage);
        ButterKnife.bind(this);
    }

    @Override
    protected void initTop() {
        setNavigation(R.mipmap.common_nav_back);
        setTitle(R.string.address_titlemng_text);
    }


    @OnClick(R.id.id_addressmng_add_bt)
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_addressmng_add_bt:
                ProDispatcher.goChangeAddressActivity(this);
                break;
        }
    }
}
