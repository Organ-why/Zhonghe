package com.zhonghe.lib_base.baseui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.MenuItem;

/**
 * @desc : 文本menu
 */
public class MenuTxt extends MenuBase {
    //点击监听事件
    private MenuItem.OnMenuItemClickListener listener;

    public MenuTxt(@NonNull Context context) {
        super(context);
    }

    public MenuItem.OnMenuItemClickListener getListener() {
        return listener;
    }

    public void setListener(MenuItem.OnMenuItemClickListener listener) {
        this.listener = listener;
    }


    public static class MenuTxtBuilder {
        private Context context;
        private int order;
        private String title;
        private MenuItem.OnMenuItemClickListener listener;

        public MenuTxtBuilder(@NonNull Context context) {
            this.context = context;
        }

        public MenuTxtBuilder setOrder(int order) {
            this.order = order;
            return this;
        }

        public MenuTxtBuilder setTitle(@StringRes int txtRes) {
            return setTitle(context.getString(txtRes));
        }

        public MenuTxtBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public MenuTxtBuilder setListener(MenuItem.OnMenuItemClickListener listener) {
            this.listener = listener;
            return this;
        }

        public MenuTxt build() {
            MenuTxt menu = new MenuTxt(context);
            menu.setTitle(title);
            menu.setOrder(order);
            menu.setListener(listener);
            return menu;
        }
    }
}
