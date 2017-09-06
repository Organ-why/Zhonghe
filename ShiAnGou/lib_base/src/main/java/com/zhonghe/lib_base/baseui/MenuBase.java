package com.zhonghe.lib_base.baseui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.MenuItem;

/**
 * @desc : menu抽象基类
 */
public abstract class MenuBase {
    //
    private Context context;
    //id
    private int menuId;
    //显示优先级
    private int order;
    //标题
    private String title;
    //show as action
    private int showAsAction;

    public MenuBase(@NonNull Context context) {
        this.context = context;
        this.showAsAction = MenuItem.SHOW_AS_ACTION_IF_ROOM;
    }

    public int getMenuId() {
        return menuId;
    }

    void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitle(@StringRes int txtRes) {
        this.title = context.getString(txtRes);
    }

    public int getShowAsAction() {
        return showAsAction;
    }

    void setShowAsAction(int showAsAction) {
        this.showAsAction = showAsAction;
    }
}
