package com.zhonghe.lib_base.baseui;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.MenuItem;

/**
 * @desc : 图片menu
 */
public class MenuImg extends MenuBase {
    //图标
    private int icon;
    //点击监听事件
    private MenuItem.OnMenuItemClickListener listener;

    public MenuImg(@NonNull Context context) {
        super(context);
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(@DrawableRes int icon) {
        this.icon = icon;
    }

    public MenuItem.OnMenuItemClickListener getListener() {
        return listener;
    }

    public void setListener(MenuItem.OnMenuItemClickListener listener) {
        this.listener = listener;
    }

    public static class MenuImgBuilder {
        private Context context;
        private int order;
        private String title;
        private int icon;
        private MenuItem.OnMenuItemClickListener listener;

        public MenuImgBuilder(@NonNull Context context) {
            this.context = context;
        }

        public MenuImgBuilder setOrder(int order) {
            this.order = order;
            return this;
        }

        public MenuImgBuilder setTitle(@StringRes int txtRes) {
            return setTitle(context.getString(txtRes));
        }

        public MenuImgBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public MenuImgBuilder setIcon(@DrawableRes int icon) {
            this.icon = icon;
            return this;
        }

        public MenuImgBuilder setListener(MenuItem.OnMenuItemClickListener listener) {
            this.listener = listener;
            return this;
        }

        public MenuImg build() {
            MenuImg menu = new MenuImg(context);
            menu.setTitle(title);
            menu.setIcon(icon);
            menu.setOrder(order);
            menu.setListener(listener);
            return menu;
        }
    }
}
