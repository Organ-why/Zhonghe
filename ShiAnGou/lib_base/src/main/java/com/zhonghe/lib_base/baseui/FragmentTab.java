
package com.zhonghe.lib_base.baseui;

import android.os.Bundle;

/**
 * @desc :
 */
public class FragmentTab {
    private String title;
    private int icon;
    private BaseUIFragment fragment;
    private Class<? extends BaseUIFragment> fragmentClazz;
    private Bundle bundle;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public BaseUIFragment getFragment() {
        return fragment;
    }

    public void setFragment(BaseUIFragment fragment) {
        this.fragment = fragment;
    }

    public Class<? extends BaseUIFragment> getFragmentClazz() {
        return fragmentClazz;
    }

    public void setFragmentClazz(Class<? extends BaseUIFragment> fragmentClazz) {
        this.fragmentClazz = fragmentClazz;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
