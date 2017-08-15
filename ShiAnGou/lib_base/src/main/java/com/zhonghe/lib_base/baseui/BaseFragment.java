

package com.zhonghe.lib_base.baseui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;

import java.util.HashMap;
import java.util.Map;

/**
 * whyang
 *
 */
public abstract class BaseFragment extends AbsFragment {
    //所属activity
    protected BaseActivity mActivity;
    //保存当前界面执行的任务
//    private List<Callback.Cancelable> mCallbackList;
    //保存注册的receiver
    private Map<String, FragmentReceiver> mReceiverMap;

    /**
     * 初始化界面构造选项
     */
    protected abstract long initOptions();

    @Override
    protected void init(Bundle savedInstanceState) {
        mActivity = getBaseActivity();
    }


//    //添加任务到销毁队列
//    public void addCallback(Callback.Cancelable cb) {
//        if (mCallbackList == null) {
//            mCallbackList = new ArrayList<>();
//        }
//        mCallbackList.add(cb);
//    }

//    //销毁队列里所有任务
//    public void destroyCallback() {
//        if (UtilList.isEmpty(mCallbackList)) {
//            for (Callback.Cancelable cb : mCallbackList) {
//                if (!cb.isCancelled())
//                    cb.cancel();
//            }
//            mCallbackList.clear();
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //退出界面，取消所有任务监听处理
//        destroyCallback();

        //取消广播监听
        if (mReceiverMap != null) {
            for (String key : mReceiverMap.keySet()) {
                LocalBroadcastManager.getInstance(getActivity())
                        .unregisterReceiver(mReceiverMap.get(key));
            }

            mReceiverMap.clear();
        }
    }


    /**
     * 获取相关联的基类fragment activity
     *
     * @return
     */
    public BaseActivity getBaseActivity() {
        FragmentActivity activity = getActivity();

        if (activity instanceof BaseActivity) {
            return (BaseActivity) activity;
        }

        return null;
    }

    /**
     * 注册广播的action
     *
     * @param action
     */
    public void registerAction(String action) {
//        if (UtilString.isBlank(action)) {
//            return;
//        }

        if (mReceiverMap == null) {
            mReceiverMap = new HashMap<String, FragmentReceiver>(4);
        }

        if (!mReceiverMap.containsKey(action)) {
            FragmentReceiver receiver = new FragmentReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(action);
            LocalBroadcastManager.getInstance(getActivity())
                    .registerReceiver(receiver, filter);
            mReceiverMap.put(action, receiver);
        }
    }

    /**
     * 注销广播的action
     *
     * @param action
     */
    public void unregisterAction(String action) {
//        if (UtilString.isBlank(action) || mReceiverMap == null) {
//            return;
//        }

        if (mReceiverMap.containsKey(action)) {
            LocalBroadcastManager.getInstance(getActivity())
                    .unregisterReceiver(mReceiverMap.get(action));
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
    private class FragmentReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
//                UtilLog.d(getClass().getSimpleName() + " action:" + intent.getAction());
                BaseFragment.this.onReceive(intent);
            }
        }
    }
}
