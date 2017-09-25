package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/9/25
 * desc:线下支付
 */

public class UnlinePayActivity extends BaseTopActivity {
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.id_user_point_rl)
    RelativeLayout idUserPointRl;
    @Bind(R.id.id_pay_pointnum_tv)
    TextView idPayPointnumTv;
    @Bind(R.id.id_pay_total_ev)
    EditText idPayTotalEv;
    @Bind(R.id.id_pay_lack_tv)
    TextView idPayLackTv;

    @Override
    protected void initTop() {
        setTitle(R.string.unline_pay_title);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_unline_pay);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
    }

}
