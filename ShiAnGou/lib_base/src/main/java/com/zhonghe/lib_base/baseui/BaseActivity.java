package com.zhonghe.lib_base.baseui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.zhonghe.lib_base.R;

/**
 * Date: 2017/8/3.
 * Author: whyang
 */
public abstract class BaseActivity extends AbsActivity {

    /**
     * 初始化界面构造选项
     */
    protected abstract long initOptions();


}
