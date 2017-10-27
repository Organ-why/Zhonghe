package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/9/8
 * desc:
 */

public class RecyclerViewActivity extends BaseTopActivity {
    @BindView(R.id.id_mrecyclerview)
    RecyclerView idRecyclerview;

    @Override
    protected void initTop() {

    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.layout_recyclerview);
        ButterKnife.bind(this);

    }

}
