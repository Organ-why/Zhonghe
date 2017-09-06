package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;

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
        idsRegisterCodeEt.setText(ProjectApplication.mUser.getNick_name());
    }


    @OnClick(R.id.id_register_regist_bt)
    public void onViewClicked() {
        final String newName = idsRegisterCodeEt.getText().toString();
        if (UtilString.isEmpty(newName)) {
            Util.toast(mContext, R.string.title_register_name_hint);
            return;
        }
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getChangeNickName(mContext, newName, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                ProDispatcher.sendLoginBroadcast(mContext);
                ProjectApplication.mUser.setNick_name(newName);
                finish();
            }
        });
        addRequest(request);
    }
}
