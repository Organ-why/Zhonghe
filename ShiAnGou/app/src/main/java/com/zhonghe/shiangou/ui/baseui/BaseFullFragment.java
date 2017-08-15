package com.zhonghe.shiangou.ui.baseui;

import com.zhonghe.lib_base.baseui.BaseUIFragment;
import com.zhonghe.lib_base.baseui.UIOptions;

/**
 * Date: 2017/8/13.
 * Author: whyang
 */
public abstract class BaseFullFragment extends BaseTopFragment{
    @Override
    protected long initOptions() {
        return UIOptions.UI_OPTIONS_CONTENT_CUSTOM;

    }
}
