package com.zhonghe.lib_base.baseui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author : whyang
 * @date   :
 * @desc   :抽象fragment，将fragment的oncreateview生命周期细分为
 *           init：初始化
 *           initBaseViews：初始化基类View
 *           initLayout：初始化布局
 *           initViews：初始化视图
 */
public abstract class AbsFragment extends Fragment {
    private View mViewCache;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        mViewCache= null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(mViewCache == null) {
            mViewCache = initBaseViews();
            initLayout();
            initViews();
        } else {
            ViewGroup group = (ViewGroup) mViewCache.getParent();
            if(group != null) {
                group.removeView(mViewCache);
            }
        }

        return mViewCache;
    }


    /**
     * 获取fragment设置的view
     * @return
     */
    public View getView() {
        return mViewCache;
    }


    /**
     * 初始化
     */
    protected abstract void init(Bundle savedInstanceState);

    /**
     * 初始化基类View
     */
    protected abstract View initBaseViews();

    /**
     * 初始化布局
     */
    protected abstract void initLayout();

    /**
     * 初始化视图
     */
    protected abstract void initViews();
}
