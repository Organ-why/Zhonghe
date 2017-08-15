package com.zhonghe.shiangou.ui.baseui;

import android.os.Bundle;

import com.android.volley.Request;
import com.zhonghe.lib_base.baseui.BaseUIFragment;
import com.zhonghe.lib_base.baseui.UIOptions;
import com.zhonghe.shiangou.system.global.ProjectApplication;

/**
 * Date: 2017/8/4.
 * Author: whyang
 */
public abstract class BaseTopFragment extends BaseUIFragment {

    protected abstract void initTop();

    @Override
    protected void initAppToobar() {
        initTop();
    }

    @Override
    protected long initOptions() {
        return UIOptions.UI_OPTIONS_APPBAR_CUSTIOM;
    }


    protected void addRequest(Request request) {
        ProjectApplication.proReqestQueue.addRequest(request, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ProjectApplication.proReqestQueue.cancleRequest(this);
    }
}
