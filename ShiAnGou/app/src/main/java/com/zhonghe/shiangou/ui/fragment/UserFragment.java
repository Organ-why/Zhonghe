package com.zhonghe.shiangou.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.baseui.BaseTopFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date: 2017/7/4.
 * Author: whyang
 */
public class UserFragment extends BaseTopFragment {
    @Bind(R.id.id_user_order_rl)
    RelativeLayout idUserOrderRl;
    @Bind(R.id.id_user_unpay_ll)
    LinearLayout idUserUnpayLl;
    @Bind(R.id.id_user_unsend_ll)
    LinearLayout idUserUnsendLl;
    @Bind(R.id.id_user_wait_ll)
    LinearLayout idUserWaitLl;
    @Bind(R.id.id_user_unremark_ll)
    LinearLayout idUserUnremarkLl;
    @Bind(R.id.id_user_return_ll)
    LinearLayout idUserReturnLl;
    @Bind(R.id.id_user_like_rl)
    RelativeLayout idUserLikeRl;
    @Bind(R.id.id_user_contactus_rl)
    RelativeLayout idUserContactusRl;
    @Bind(R.id.id_user_header_iv)
    SimpleDraweeView idUserHeaderIv;
    @Bind(R.id.id_user_name_tv)
    TextView idUserNameTv;
    @Bind(R.id.id_user_point_rl)
    RelativeLayout idUserPointRl;
    @Bind(R.id.id_user_msg_rl)
    RelativeLayout idUserMsgRl;
    @Bind(R.id.id_user_setup_rl)
    RelativeLayout idUserSetupRl;

    //    @Override
//    public void onStart() {
//        super.onStart();
//        setStatusBarColor(mActivity.getResources().getColor(R.color.res_color_apptheme));
//    }
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
////        setStatusBarColor(mActivity.getResources().getColor(R.color.res_color_apptheme));
//        if (!hidden && ProjectApplication.mUser != null) {
//            idUserNameTv.setText(ProjectApplication.mUser.getUser_name());
//        }
//    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.fragment_user);
        ButterKnife.bind(this, getView());
    }

    @Override
    protected void initViews() {
        registerAction(CstProject.BROADCAST_ACTION.LOGIN_ACTION);
        registerAction(CstProject.BROADCAST_ACTION.LOGOUT_ACTION);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
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
                ProDispatcher.goOrderManageActivity(mActivity);
                break;
            case R.id.id_user_unpay_ll:
                ProDispatcher.goOrderManageActivity(mActivity);
                break;
            case R.id.id_user_unsend_ll:
                ProDispatcher.goOrderManageActivity(mActivity);
                break;
            case R.id.id_user_wait_ll:
                ProDispatcher.goOrderManageActivity(mActivity);
                break;
            case R.id.id_user_unremark_ll:
                ProDispatcher.goOrderManageActivity(mActivity);
                break;
            case R.id.id_user_return_ll:
//                ProDispatcher.goRefundsActivity(mActivity);
                ProDispatcher.goRefundsBeginActivity(mActivity);
                break;
            case R.id.id_user_like_rl:
                ProDispatcher.goLikeActivity(mActivity);
                break;
            case R.id.id_user_contactus_rl:
                break;
//            case R.id.id_user_help_rl:
//                ProDispatcher.goRemarkActivity(mActivity);
//                break;
            case R.id.id_user_point_rl:
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
                idUserNameTv.setText(ProjectApplication.mUser.getUser_name());
                ProjectApplication.mImageLoader.loadCircleImage(idUserHeaderIv, ProjectApplication.mUser.getUser_pic());
                break;
            case CstProject.BROADCAST_ACTION.LOGOUT_ACTION:
                break;
        }
    }
}
