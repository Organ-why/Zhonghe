package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * auther: whyang
 * date: 2017/8/21
 * desc:兑换返回结果
 */

public class PointExchangeResultActivity extends BaseTopActivity {
    @BindView(R.id.id_point_torecord_bt)
    Button idPointTorecordBt;
    @BindView(R.id.id_point_gobuy_bt)
    Button idPointGobuyBt;

    @Override
    protected void initTop() {
        setTitle(R.string.point_submit_result_title_success);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_point_submit_result);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.id_point_torecord_bt, R.id.id_point_gobuy_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_point_torecord_bt:
                ProDispatcher.goPointExchangeRecord(mContext);
                break;
            case R.id.id_point_gobuy_bt:
                ProDispatcher.goPointActivity(mContext);
                break;
        }
    }
}
