package com.zhonghe.shiangou.ui.baseui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.android.volley.Request;
import com.zhonghe.lib_base.baseui.BaseUIActivity;
import com.zhonghe.lib_base.baseui.BaseUIFragment;
import com.zhonghe.lib_base.baseui.UIOptions;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.system.global.ProjectApplication;

public abstract class BaseSystemActivity extends BaseUIActivity {
    /**
     * 初始化systembar
     * 提供如下方法调用：
     * public void setSystemBarEnabled(boolean enabled)
     * public void setStatusBarColor(int color)
     * public void setStatusBarResource(@ColorRes int colorRes)
     * public void setNavigationBarColor(int color)
     * public void setNavigationBarResource(@ColorRes int colorRes)
     */
    protected abstract void initSystem();

//    @Override
//    protected void initSystemBar() {
//        initSystem();
//    }

    @Override
    protected long initOptions() {
        return UIOptions.UI_OPTIONS_SCREEN_SYSTEM;
    }

    /**
     * 获取内容布局视图的根ID
     */
    public int getContentId() {
        return R.id.base_id_content_root;
    }

//    /**
//     * 添加片段
//     * @param fragmentClzz
//     */
//    public void addFragment(Class<? extends BaseUIFragment> fragmentClzz) {
//        addFragment(fragmentClzz, null);
//    }

    //    /**
//     * 添加片段
//     * @param fragmentClzz
//     * @param bundle
//     */
//    public void addFragment(Class<? extends BaseUIFragment> fragmentClzz, Bundle bundle) {
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//
//        if (fm.getFragments() != null) {
//            ft.addToBackStack(null);
//        }
//
//        ft.setCustomAnimations(R.anim.res_translate_left_in,
//                R.anim.res_translate_left_out,
//                R.anim.res_translate_right_in,
//                R.anim.res_translate_right_out);
//
//        try {
//            BaseUIFragment fragment = (BaseUIFragment)fragmentClzz.newInstance();
//            if(bundle != null) {
//                fragment.setArguments(bundle);
//            }
//
//            ft.replace(getContentId(), fragment).commit();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
    protected void addRequest(Request request) {
        ProjectApplication.proReqestQueue.addRequest(request, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ProjectApplication.proReqestQueue.cancleRequest(this);
    }
}
