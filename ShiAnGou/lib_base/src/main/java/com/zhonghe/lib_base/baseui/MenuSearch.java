package com.zhonghe.lib_base.baseui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.SearchView;

/**
 * @desc : 搜索menu
 */
public class MenuSearch extends MenuBase {
    //监听事件
    private SearchView.OnQueryTextListener listener;

    public MenuSearch(@NonNull Context context) {
        super(context);
    }

    public SearchView.OnQueryTextListener getListener() {
        return listener;
    }

    public void setListener(SearchView.OnQueryTextListener listener) {
        this.listener = listener;
    }

    public static class MenuSearchBuilder {
        private Context context;
        private int order;
        private String title;
        private SearchView.OnQueryTextListener listener;

        public MenuSearchBuilder(@NonNull Context context) {
            this.context = context;
        }


        public MenuSearchBuilder setOrder(int order) {
            this.order = order;
            return this;
        }

        public MenuSearchBuilder setTitle(@StringRes int txtRes) {
            return setTitle(context.getString(txtRes));
        }

        public MenuSearchBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public MenuSearchBuilder setListener(SearchView.OnQueryTextListener listener) {
            this.listener = listener;
            return this;
        }

        public MenuSearch build() {
            MenuSearch menu = new MenuSearch(context);
            menu.setTitle(title);
            menu.setOrder(order);
            menu.setListener(listener);
            return menu;
        }
    }
}
