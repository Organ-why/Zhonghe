package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * auther: whyang
 * date: 2017/8/24
 * desc:修改用户名
 */

public class ChangeNameActivity extends BaseTopActivity {
    @Bind(R.id.ids_register_code_et)
    EditText idsRegisterCodeEt;
    @Bind(R.id.id_register_regist_bt)
    Button idRegisterRegistBt;

    @Override
    protected void initTop() {

    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_changename);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        super.initViews();
    }


    @OnClick(R.id.id_register_regist_bt)
    public void onViewClicked() {

    }
}
