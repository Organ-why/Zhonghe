package com.zhonghe.shiangou.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhonghe.lib_base.baseui.BaseUIActivity;
import com.zhonghe.lib_base.baseui.UIOptions;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;

public class SplashActivity extends BaseTopActivity {
    @Override
    protected void initTop() {

    }

    @Override
    protected long initOptions() {
        return UIOptions.UI_OPTIONS_CONTENT_CUSTOM;
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void initViews() {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                ProDispatcher.goMainActivity(SplashActivity.this);
                finish();
            }
        };
        handler.sendMessageDelayed(new Message(), 1500);
    }
}
