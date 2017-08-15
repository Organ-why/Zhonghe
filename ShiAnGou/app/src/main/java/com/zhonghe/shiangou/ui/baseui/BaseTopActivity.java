package com.zhonghe.shiangou.ui.baseui;

import com.android.volley.Request;
import com.zhonghe.lib_base.baseui.BaseUIActivity;
import com.zhonghe.lib_base.baseui.UIOptions;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.system.global.ProjectApplication;

/**
 * Date: 2017/8/12.
 * Author: whyang
 */
public abstract class BaseTopActivity extends BaseUIActivity {
    protected abstract void initTop();

    @Override
    protected void initAppCustom() {
        setAppCustomLayout(R.layout.layout_cart_top);
    }

    @Override
    protected void initAppToobar() {
        initTop();
    }

    @Override
    protected long initOptions() {
        return UIOptions.UI_OPTIONS_APPBAR_TOOLBAR | UIOptions.UI_OPTIONS_CONTENT_CUSTOM;
    }
    void addRequest(Request request) {
        ProjectApplication.proReqestQueue.addRequest( request, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ProjectApplication.proReqestQueue.cancleRequest(  this);
    }
}
