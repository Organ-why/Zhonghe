package com.zhonghe.shiangou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.zhonghe.lib_base.baseui.activity.BaseNavActivity;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilLog;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.dialog.AppUpdataDialog;
import com.zhonghe.shiangou.ui.fragment.CartExpandableFragment;
import com.zhonghe.shiangou.ui.fragment.CartFragment;
import com.zhonghe.shiangou.ui.fragment.CategoryFragment;
import com.zhonghe.shiangou.ui.fragment.HomeFragment;
import com.zhonghe.shiangou.ui.fragment.HomeFragment1;
import com.zhonghe.shiangou.ui.fragment.HomeFragmentRecy;
import com.zhonghe.shiangou.ui.fragment.PointFragment;
import com.zhonghe.shiangou.ui.fragment.UserFragment;
import com.zhonghe.shiangou.ui.listener.ResultListener;

public class MainActivity extends BaseNavActivity {

    @Override
    protected void initNavs() {
        //首页
//        addNavTab(R.string.main_tabs_home, R.drawable.main_tab_home, HomeFragment1.class);
        addNavTab(R.string.main_tabs_home, R.drawable.main_tab_home, HomeFragmentRecy.class);
        //分类
        addNavTab(R.string.main_tabs_category, R.drawable.main_tab_category, CategoryFragment.class);
        //购物车
        addNavTab(R.string.main_tabs_cart, R.drawable.main_tab_cart, CartExpandableFragment.class);
        //我的
        addNavTab(R.string.main_tabs_user, R.drawable.main_tab_user, UserFragment.class);


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
            case CstProject.BROADCAST_ACTION.LOCATION_ACTION:
                ProjectApplication.mLocationService.stop();
                UtilLog.d("location successedlocation successedlocation successedlocation successed");
                break;
        }
    }

    @Override
    public void onTabChanged(String tabId) {
    }
}
