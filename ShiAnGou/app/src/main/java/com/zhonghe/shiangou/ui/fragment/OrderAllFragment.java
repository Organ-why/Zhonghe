package com.zhonghe.shiangou.ui.fragment;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.baseui.BaseFullFragment;

import butterknife.ButterKnife;

/**
 * Date: 2017/8/13.
 * Author: whyang
 */
public class OrderAllFragment extends BaseFullFragment {
    @Override
    protected void initLayout() {
        setContentView(R.layout.fragment_order_all);
        ButterKnife.bind(this, getView());
    }

    @Override
    protected void initTop() {

    }
}
