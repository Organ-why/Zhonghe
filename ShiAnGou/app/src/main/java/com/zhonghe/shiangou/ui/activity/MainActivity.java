package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zhonghe.lib_base.baseui.activity.BaseNavActivity;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.fragment.CartExpandableFragment;
import com.zhonghe.shiangou.ui.fragment.CartFragment;
import com.zhonghe.shiangou.ui.fragment.CategoryFragment;
import com.zhonghe.shiangou.ui.fragment.HomeFragment;
import com.zhonghe.shiangou.ui.fragment.UserFragment;

public class MainActivity extends BaseNavActivity {

    @Override
    protected void initNavs() {
        //首页
        addNavTab(R.string.main_tabs_home, R.drawable.main_tab_home, HomeFragment.class);
        //分类
        addNavTab(R.string.main_tabs_category, R.drawable.main_tab_category, CategoryFragment.class);
        //购物车
        addNavTab(R.string.main_tabs_cart, R.drawable.main_tab_cart, CartExpandableFragment.class);
        //我的
        addNavTab(R.string.main_tabs_user, R.drawable.main_tab_user, UserFragment.class);
    }
}
