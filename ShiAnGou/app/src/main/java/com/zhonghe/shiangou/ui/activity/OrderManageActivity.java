package com.zhonghe.shiangou.ui.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.baseui.BaseTabActivity;
import com.zhonghe.shiangou.ui.fragment.OrderAllFragment;

public class OrderManageActivity extends BaseTabActivity {


    @Override
    protected void initTop() {
        setTitle(R.string.common_user_order_title);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initTabs() {
        addAppTab(R.string.common_user_order_all, OrderAllFragment.class);
        addAppTab(R.string.common_user_order_unpay, OrderAllFragment.class);
        addAppTab(R.string.common_user_order_unsend, OrderAllFragment.class);
        addAppTab(R.string.common_user_order_wait, OrderAllFragment.class);
        addAppTab(R.string.common_user_order_unremark, OrderAllFragment.class);
        setAppTabIndex(0);
        setTabTextColors(ContextCompat.getColor(this, R.color.common_font_deep), ContextCompat.getColor(this, R.color.common_font_normal));
        setAppTabSelectedColor(ContextCompat.getColor(this, R.color.res_color_apptheme));
    }
}
