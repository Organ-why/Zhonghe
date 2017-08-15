package com.zhonghe.shiangou.ui.fragment;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zhonghe.shiangou.ui.baseui.BaseTopFragment;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.system.global.ProDispatcher;

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
    @Bind(R.id.id_user_help_rl)
    RelativeLayout idUserHelpRl;
    @Bind(R.id.id_user_setup_ib)
    ImageButton idUserSetupIb;
    @Bind(R.id.id_user_header_iv)
    ImageView idUserHeaderIv;

//    @Override
//    public void onStart() {
//        super.onStart();
//        setStatusBarColor(mActivity.getResources().getColor(R.color.res_color_apptheme));
//    }
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        setStatusBarColor(mActivity.getResources().getColor(R.color.res_color_apptheme));
//    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.fragment_user);
        ButterKnife.bind(this, getView());
    }

    @Override
    protected void initViews() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    protected void initTop() {

    }

    @Override
    protected void initAppCustom() {
        setAppCustomLayout(R.layout.layout_user_top);
    }
    //    private void mGetProductList(){
//        Callback.Cancelable cancelable = HandsupApplication.mXUtil.getProduct(new Callback.CacheCallback<Product>() {
//            @Override
//            public void onSuccess(Product result) {
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//
//            @Override
//            public boolean onCache(Product result) {
//                return false;
//            }
//        });
//        addCallback(cancelable);
//    }


    @OnClick({R.id.id_user_setup_ib, R.id.id_user_header_iv, R.id.id_user_order_rl, R.id.id_user_unpay_ll, R.id.id_user_unsend_ll, R.id.id_user_wait_ll, R.id.id_user_unremark_ll, R.id.id_user_return_ll, R.id.id_user_like_rl, R.id.id_user_contactus_rl, R.id.id_user_help_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_user_header_iv:
                ProDispatcher.goLoginActivity(mActivity);
                break;
            case R.id.id_user_setup_ib:
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
                ProDispatcher.goRefundsActivity(mActivity);
                break;
            case R.id.id_user_like_rl:
                ProDispatcher.goLikeActivity(mActivity);
                break;
            case R.id.id_user_contactus_rl:
                break;
            case R.id.id_user_help_rl:
                break;
        }
    }
}
