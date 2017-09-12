package com.zhonghe.shiangou.ui.activity;

import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;

import com.zhonghe.lib_base.baseui.MenuPopup;
import com.zhonghe.lib_base.baseui.MenuTxt;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;

/**
 * auther: whyang
 * date: 2017/9/12
 * desc:
 */

public class PointUnlineDetailActivity extends BaseTopActivity {
    @Override
    protected void initTop() {
        setNavigation(R.mipmap.common_nav_back);
        final MenuTxt mMeunManager = new MenuTxt.MenuTxtBuilder(this)
                .setTitle(R.string.common_submit)
                .setListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return false;
                    }
                }).build();
        addMenu(mMeunManager);
        MenuPopup popup = new MenuPopup.MenuPopupBuilder(this).setListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    Util.toast(mContext, "menu01");
                    return true;
                } else if (id == R.id.navigation_dashboard) {
                    Util.toast(mContext, "menu02");
                    return true;
                } else if (id == R.id.navigation_notifications) {
                    Util.toast(mContext, "menu03");
                    return true;
                }
                return false;
            }
        }).setIcon(R.mipmap.icon_zxing_black).setMenuRes(R.menu.navigation).build();
        addMenu(popup);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.layout_recyclerview);
    }
}
