package com.zhonghe.shiangou.ui.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.system.constant.CstProject;
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
        Bundle bundle1 = new Bundle();
        bundle1.putString(CstProject.KEY.ORDERBY, "0");
        addAppTab(R.string.common_user_order_all, OrderAllFragment.class, bundle1);
        Bundle bundle2 = new Bundle();
        bundle2.putString(CstProject.KEY.ORDERBY, "1");
        addAppTab(R.string.common_user_order_unpay, OrderAllFragment.class, bundle2);
        Bundle bundle3 = new Bundle();
        bundle3.putString(CstProject.KEY.ORDERBY, "2");
        addAppTab(R.string.common_user_order_unsend, OrderAllFragment.class, bundle3);
        Bundle bundle4 = new Bundle();
        bundle4.putString(CstProject.KEY.ORDERBY, "3");
        addAppTab(R.string.common_user_order_wait, OrderAllFragment.class, bundle4);
        Bundle bundle5 = new Bundle();
        bundle5.putString(CstProject.KEY.ORDERBY, "4");
        addAppTab(R.string.common_user_order_unremark, OrderAllFragment.class, bundle5);
        setAppTabIndex(0);
        setTabTextColors(ContextCompat.getColor(this, R.color.common_font_deep), ContextCompat.getColor(this, R.color.common_font_normal));
        setAppTabSelectedColor(ContextCompat.getColor(this, R.color.res_color_apptheme));
    }
}
