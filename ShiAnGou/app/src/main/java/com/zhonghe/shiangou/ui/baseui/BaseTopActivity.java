package com.zhonghe.shiangou.ui.baseui;

import android.view.View;

import com.android.volley.Request;
import com.zhonghe.lib_base.baseui.BaseUIActivity;
import com.zhonghe.lib_base.baseui.UIOptions;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.system.global.ProjectApplication;

import static com.zhonghe.lib_base.baseui.UIOptions.UI_OPTIONS_CONTENT_CUSTOM;

/**
 * Date: 2017/8/12.
 * Author: whyang
 */
public abstract class BaseTopActivity extends BaseUIActivity implements View.OnClickListener{
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
        return UIOptions.UI_OPTIONS_APPBAR_TOOLBAR | UI_OPTIONS_CONTENT_CUSTOM;
    }
    protected void addRequest(Request request) {
        ProjectApplication.proReqestQueue.addRequest( request, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ProjectApplication.proReqestQueue.cancleRequest(  this);
    }

    @Override
    public void onClick(View v) {

    }
}
