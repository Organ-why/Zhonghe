package com.zhonghe.lib_base.baseui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.zhonghe.lib_base.R;
import com.zhonghe.lib_base.utils.UtilString;

import java.util.HashMap;
import java.util.Map;

/**
 * Date: 2017/8/3.
 * Author: whyang
 */
public abstract class BaseActivity extends AbsActivity {
    //保存注册的receiver
    private Map<String, ActivityReceiver> mReceiverMap;

    /**
     * 初始化界面构造选项
     */
    protected abstract long initOptions();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消广播监听
        if (mReceiverMap != null) {
            for (String key : mReceiverMap.keySet()) {
//                LocalBroadcastManager.getInstance(this)
//                        .unregisterReceiver(mReceiverMap.get(key));
                unregisterReceiver(mReceiverMap.get(key));

            }

            mReceiverMap.clear();
        }
    }

    /**
     * 注册广播的action
     *
     * @param action
     */
    public void registerAction(String action) {
        if (UtilString.isBlank(action)) {
            return;
        }

        if (mReceiverMap == null) {
            mReceiverMap = new HashMap<String, ActivityReceiver>(4);
        }

        if (!mReceiverMap.containsKey(action)) {
            ActivityReceiver receiver = new ActivityReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(action);
            //自定义广播接收器，只接收自定义广播
//            LocalBroadcastManager.getInstance(this)
//                    .registerReceiver(receiver, filter);
            //系统广播接收器，能接受系统广播
            registerReceiver(receiver, filter);
            mReceiverMap.put(action, receiver);
        }
    }

    /**
     * 注销广播的action
     *
     * @param action
     */
    public void unregisterAction(String action) {
        if (UtilString.isBlank(action) || mReceiverMap == null) {
            return;
        }

        if (mReceiverMap.containsKey(action)) {
            //自定义广播接收器，只取消绑定自定义广播
//            LocalBroadcastManager.getInstance(this)
//                    .unregisterReceiver(mReceiverMap.get(action));
            //系统广播接收器，取消绑定所有广播
            unregisterReceiver(mReceiverMap.get(action));
            mReceiverMap.remove(action);
        }
    }

    /**
     * 处理广播消息
     */
    protected void onReceive(Intent intent) {

    }

    /**
     * 广播接收器
     *
     * @author zhenshui.xia
     */
    private class ActivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
//                UtilLog.d(getClass().getSimpleName() + " action:" + intent.getAction());
                BaseActivity.this.onReceive(intent);
            }
        }
    }
}
