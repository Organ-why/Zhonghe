package com.zhonghe.shiangou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zhonghe.lib_base.baseui.activity.BaseNavActivity;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.ui.dialog.AppUpdataDialog;
import com.zhonghe.shiangou.ui.fragment.CartExpandableFragment;
import com.zhonghe.shiangou.ui.fragment.CartFragment;
import com.zhonghe.shiangou.ui.fragment.CategoryFragment;
import com.zhonghe.shiangou.ui.fragment.HomeFragment;
import com.zhonghe.shiangou.ui.fragment.HomeFragment1;
import com.zhonghe.shiangou.ui.fragment.UserFragment;

public class MainActivity extends BaseNavActivity {

    @Override
    protected void initNavs() {
        //首页
        addNavTab(R.string.main_tabs_home, R.drawable.main_tab_home, HomeFragment1.class);
        //分类
        addNavTab(R.string.main_tabs_category, R.drawable.main_tab_category, CategoryFragment.class);
        //购物车
        addNavTab(R.string.main_tabs_cart, R.drawable.main_tab_cart, CartExpandableFragment.class);
        //我的
        addNavTab(R.string.main_tabs_user, R.drawable.main_tab_user, UserFragment.class);

        AppUpdataDialog dialog = new AppUpdataDialog(MainActivity.this);
        dialog.show();

    }

    @Override
    protected void initViews() {
        registerAction(CstProject.BROADCAST_ACTION.MAINTAB_CHECK_ACTION);
    }

    @Override
    protected void onReceive(Intent intent) {
        switch (intent.getAction()) {
            case CstProject.BROADCAST_ACTION.MAINTAB_CHECK_ACTION:
                int index = intent.getIntExtra(CstProject.KEY.INDEX, 0);
                setOnTabChanged(index);
                break;
        }
    }

    @Override
    public void onTabChanged(String tabId) {
    }
}
