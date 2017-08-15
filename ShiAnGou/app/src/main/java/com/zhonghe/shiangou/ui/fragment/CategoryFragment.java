package com.zhonghe.shiangou.ui.fragment;

import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhonghe.shiangou.ui.baseui.BaseTopFragment;
import com.zhonghe.shiangou.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Date: 2017/7/4.
 * Author: whyang
 */
public class CategoryFragment extends BaseTopFragment {
    @Bind(R.id.rl_title)
    RelativeLayout rl_bg;
    @Bind(R.id.title_user_ivb)
    ImageButton titleUserIvb;
    @Bind(R.id.title_msg_ivb)
    ImageButton titleMsgIvb;
    @Bind(R.id.id_category_title_tv)
    TextView idCategoryTitleTv;

    @Override
    protected void initLayout() {
        setContentView(R.layout.fragment_category);
        ButterKnife.bind(this, getView());

    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        setStatusBarColor(mActivity.getResources().getColor(R.color.common_white));
//    }
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        setStatusBarColor(mActivity.getResources().getColor(R.color.common_white));
//    }
    @Override
    protected void initViews() {
        rl_bg.setBackgroundResource(R.color.res_color_white);
        titleUserIvb.setImageResource(R.mipmap.icon_user_black);
        titleMsgIvb.setImageResource(R.mipmap.icon_msg_black);
        idCategoryTitleTv.setBackgroundResource(R.drawable.circle_search_gray_bg);
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
        setAppCustomLayout(R.layout.layout_custom_top);
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
}
