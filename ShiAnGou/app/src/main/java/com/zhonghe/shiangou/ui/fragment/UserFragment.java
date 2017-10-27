package com.zhonghe.shiangou.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.UserInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.baseui.BaseTopFragment;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date: 2017/7/4.
 * Author: whyang
 * 用户
 */
public class UserFragment extends BaseTopFragment {
    @BindView(R.id.id_user_order_rl)
    RelativeLayout idUserOrderRl;
    @BindView(R.id.id_user_unpay_ll)
    LinearLayout idUserUnpayLl;
    @BindView(R.id.id_user_unsend_ll)
    LinearLayout idUserUnsendLl;
    @BindView(R.id.id_user_wait_ll)
    LinearLayout idUserWaitLl;
    @BindView(R.id.id_user_unremark_ll)
    LinearLayout idUserUnremarkLl;
    @BindView(R.id.id_user_return_ll)
    LinearLayout idUserReturnLl;
    @BindView(R.id.id_user_like_rl)
    RelativeLayout idUserLikeRl;
    @BindView(R.id.id_user_contactus_rl)
    RelativeLayout idUserContactusRl;
    @BindView(R.id.id_user_header_iv)
    SimpleDraweeView idUserHeaderIv;
    @BindView(R.id.id_user_name_tv)
    TextView idUserNameTv;
    @BindView(R.id.id_user_point_rl)
    RelativeLayout idUserPointRl;
    @BindView(R.id.id_user_msg_rl)
    RelativeLayout idUserMsgRl;
    @BindView(R.id.id_user_setup_rl)
    RelativeLayout idUserSetupRl;

    @Override
    protected void initLayout() {
        setContentView(R.layout.fragment_user);
        ButterKnife.bind(this, getView());
        registerAction(CstProject.BROADCAST_ACTION.LOGIN_ACTION);
        registerAction(CstProject.BROADCAST_ACTION.LOGOUT_ACTION);
    }

    @Override
    protected void initViews() {

        if (ProjectApplication.mUser != null) {
            getUserMSG();
        } else {
            ProDispatcher.goLoginActivity(mActivity);
        }
    }


    @Override
    protected void initTop() {

    }

    @Override
    protected void initAppCustom() {
        setAppCustomLayout(R.layout.layout_user_top);
    }


    @OnClick({R.id.id_user_setup_rl, R.id.id_user_point_rl, R.id.id_user_msg_rl, R.id.id_user_header_iv, R.id.id_user_order_rl, R.id.id_user_unpay_ll, R.id.id_user_unsend_ll, R.id.id_user_wait_ll, R.id.id_user_unremark_ll, R.id.id_user_return_ll, R.id.id_user_like_rl, R.id.id_user_contactus_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_user_header_iv:
                if (ProjectApplication.mUser == null) {
                    ProDispatcher.goLoginActivity(mActivity);
                } else {
                    ProDispatcher.goUserActivity(mActivity);
                }
                break;
            case R.id.id_user_setup_rl:
                ProDispatcher.goSetupActivity(mActivity);
                break;
            case R.id.id_user_order_rl:
                ProDispatcher.goOrderManageActivity(mActivity, 0);
                break;
            case R.id.id_user_unpay_ll:
                ProDispatcher.goOrderManageActivity(mActivity, 1);
                break;
            case R.id.id_user_unsend_ll:
                ProDispatcher.goOrderManageActivity(mActivity, 2);
                break;
            case R.id.id_user_wait_ll:
                ProDispatcher.goOrderManageActivity(mActivity, 3);
                break;
            case R.id.id_user_unremark_ll:
                ProDispatcher.goOrderManageActivity(mActivity, 4);
                break;
            case R.id.id_user_return_ll:
                ProDispatcher.goRefundsActivity(mActivity);
//                ProDispatcher.goRefundsBeginActivity(mActivity,"188");
                break;
            case R.id.id_user_like_rl:
                ProDispatcher.goLikeActivity(mActivity);
                break;
            case R.id.id_user_contactus_rl:
                ProDispatcher.goAboutActivity(mActivity);
                break;
//            case R.id.id_user_help_rl:
//                ProDispatcher.goRemarkActivity(mActivity);
//                break;
            case R.id.id_user_point_rl:
                ProDispatcher.goPointActivity(mActivity);
                break;
            case R.id.id_user_msg_rl:
                break;
        }
    }


    @Override
    protected void onReceive(Intent intent) {
        super.onReceive(intent);
        switch (intent.getAction()) {
            case CstProject.BROADCAST_ACTION.LOGIN_ACTION:
                setUserMSG();
                break;
            case CstProject.BROADCAST_ACTION.LOGOUT_ACTION:
                idUserNameTv.setText("");
                idUserHeaderIv.setImageResource(R.mipmap.res_default_header_orange);
                ProjectApplication.mImageLoader.loadCircleImage(idUserHeaderIv, "file://");
                break;
        }
    }

    void setUserMSG() {
        if (ProjectApplication.mUser != null) {
            idUserNameTv.setText(ProjectApplication.mUser.getNick_name());
            ProjectApplication.mImageLoader.loadCircleImage(idUserHeaderIv, ProjectApplication.mUser.getUser_pic());
        }
    }


    void getUserMSG() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getUserMSG(mActivity, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                setUserMSG();
            }

            @Override
            public void onSuccess(Object obj) {
                UserInfo info = (UserInfo) obj;
                if (ProjectApplication.mUser != null) {
                    info.setToken_secret(ProjectApplication.mUser.getToken_secret());
                    ProjectApplication.mUser = info;
                    ProjectApplication.mDaoFactory.getUserDao().addUser(ProjectApplication.mUser);
                }
                setWaitingDialog(false);
                setUserMSG();
            }
        });
        addRequest(request);
    }

}
