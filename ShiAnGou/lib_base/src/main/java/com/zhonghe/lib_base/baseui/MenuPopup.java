package com.zhonghe.lib_base.baseui;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.PopupMenu;



/**
 * @desc : popup menu
 */
public class MenuPopup extends MenuBase {
    //图标
    private int icon;
    //menus
    private int menuRes;
    //点击监听事件
    private PopupMenu.OnMenuItemClickListener listener;

    public MenuPopup(@NonNull Context context) {
        super(context);
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getMenuRes() {
        return menuRes;
    }

    public void setMenuRes(@MenuRes int menuRes) {
        this.menuRes = menuRes;
    }

    public PopupMenu.OnMenuItemClickListener getListener() {
        return listener;
    }

    public void setListener(PopupMenu.OnMenuItemClickListener listener) {
        this.listener = listener;
    }


    public static class MenuPopupBuilder {
        private Context context;
        private int order;
        private String title;
        private int icon;
        private int menuRes;
        private PopupMenu.OnMenuItemClickListener listener;

        public MenuPopupBuilder(@NonNull Context context) {
            this.context = context;
        }

        public MenuPopupBuilder setOrder(int order) {
            this.order = order;
            return this;
        }

        public MenuPopupBuilder setTitle(@StringRes int txtRes) {
            return setTitle(context.getString(txtRes));
        }

        public MenuPopupBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public MenuPopupBuilder setIcon(@DrawableRes int icon) {
            this.icon = icon;
            return this;
        }

        public MenuPopupBuilder setMenuRes(@MenuRes int menuRes) {
            this.menuRes = menuRes;
            return this;
        }

        public MenuPopupBuilder setListener(PopupMenu.OnMenuItemClickListener listener) {
            this.listener = listener;
            return this;
        }

        public MenuPopup build() {
            MenuPopup menu = new MenuPopup(context);
            menu.setTitle(title);
            menu.setIcon(icon);
            menu.setOrder(order);
            menu.setMenuRes(menuRes);
            menu.setListener(listener);
            return menu;
        }
    }
}
