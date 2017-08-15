
package com.zhonghe.shiangou.ui.baseui;


import com.android.volley.Request;
import com.zhonghe.lib_base.baseui.UIOptions;
import com.zhonghe.shiangou.system.global.ProjectApplication;

/**
 * @desc : 带有tab的界面
 */
public abstract class BaseTabActivity extends BaseTopActivity {
    /**
     * 初始化Tabs
     * public void addAppTab(@StringRes int txtRes, Class<? extends BaseUIFragment> cls)
     * public void addAppTab(String title, Class<? extends BaseUIFragment> cls)
     * public void addAppTab(@StringRes int txtRes, Class<? extends BaseUIFragment> cls, Bundle args)
     * public void addAppTab(String title, Class<? extends BaseUIFragment> cls, Bundle args)
     *
     */
    protected abstract void initTabs();

    @Override
    protected void initAppTabs() {
        initTabs();
    }

    @Override
    protected long initOptions() {
        return UIOptions.UI_OPTIONS_SCREEN_TOP_TABS;
    }

    @Override
    protected final void initLayout() {

    }

    @Override
    protected final void initViews() {

    }


}
