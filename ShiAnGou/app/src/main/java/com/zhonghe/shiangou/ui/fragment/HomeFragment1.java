package com.zhonghe.shiangou.ui.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.facebook.drawee.view.SimpleDraweeView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.BaseBannerInfo;
import com.zhonghe.shiangou.data.bean.HomeCategoryInfo;
import com.zhonghe.shiangou.data.bean.HomeData;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.baseui.BaseTopFragment;
import com.zhonghe.shiangou.ui.listener.HomeScrollListener;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.ui.widget.DynamicBanner;
import com.zhonghe.shiangou.ui.widget.FlowLayout;
import com.zhonghe.shiangou.ui.widget.HomeCategoryListView;
import com.zhonghe.shiangou.ui.widget.HorizontalListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date: 2017/7/4.
 * Author: whyang
 */
public class HomeFragment1 extends BaseTopFragment {
    @Bind(R.id.cart_id_lv)
    PullToRefreshScrollView cartIdLv;
    @Bind(R.id.id_home_category_horizontal_title_hl)
    HorizontalListView horizontalListView;
    @Bind(R.id.ll_content)
    LinearLayout ll_content;
    @Bind(R.id.ll_content_title)
    LinearLayout llContentTitle;
    @Bind(R.id.ll_content_list)
    LinearLayout llContentList;
//    @Bind(R.id.common_data_xlistview)
//    NXListViewNO commonDataXlistview;
//    private Productadapter adapter;


    @Bind(R.id.title_user_ivb)
    ImageButton titleUserIvb;
    @Bind(R.id.title_msg_ivb)
    ImageButton titleMsgIvb;
    @Bind(R.id.id_category_title_tv)
    TextView idCategoryTitleTv;
    List<HomeCategoryInfo> categoryInfo;
    List<BaseBannerInfo> bannerInfo;

    @Override
    protected void initLayout() {
        setContentView(R.layout.fragment_home);
        ButterKnife.bind(this, getView());

    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        setStatusBarColor(mActivity.getResources().getColor(R.color.res_color_apptheme));
//    }
//
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        setStatusBarColor(mActivity.getResources().getColor(R.color.res_color_apptheme));
//    }

    @Override
    protected void initViews() {
        getHomeData();
    }

    void getHomeData() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getHomeData(mActivity, new ResultListener() {

            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mActivity, error);
                Log.i("onFial", error);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                HomeData homeData = (HomeData) obj;
                bannerInfo = homeData.getBannerX();
                categoryInfo = homeData.getCategoryX();
                showBanner();
                showCategory();
                Log.i("onSuccess", obj.toString());
            }
        });
        addRequest(request);
    }

    void showCategory() {
        int screenWidth = Util.getScreenWidth(mActivity);
        //ScrollView
        final ScrollView refreshableView = cartIdLv.getRefreshableView();
        //分类icon部分
        View viewCategoryTitle = LayoutInflater.from(mActivity).inflate(R.layout.layout_home_category, null);
        llContentTitle.addView(viewCategoryTitle);
        //分类具体商品列表
        new HomeCategoryListView(mActivity).initView(categoryInfo, llContentList);
        //每次类别商品列表所占高度
        int childWidth = 0;
        int childHeight = 0;
        int childCount = llContentList.getChildCount();
        if (childCount > 0) {
            View childView = llContentList.getChildAt(0);

            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            childView.measure(w, h);
            childHeight = childView.getMeasuredHeight();
            childWidth = childView.getMeasuredWidth();
        }
        //首页内Title
        FlowLayout cp = (FlowLayout) viewCategoryTitle.findViewById(R.id.id_home_categroy_title_cp);
        LinearLayout.LayoutParams categoryParams = new LinearLayout.LayoutParams(screenWidth / 4, Util.dip2px(mActivity, 75));


        //悬浮Title
        new HomeScrollListener(cartIdLv, mActivity, horizontalListView, categoryInfo, childHeight).ListenScroll();

        for (int i = 0; i < categoryInfo.size(); i++) {
            HomeCategoryInfo categoryInfo = this.categoryInfo.get(i);
            View item = LayoutInflater.from(mActivity).inflate(R.layout.item_home_category_title, null);
            SimpleDraweeView iconimg = (SimpleDraweeView) item.findViewById(R.id.id_item_home_category_title_iv);
            TextView icontv = (TextView) item.findViewById(R.id.id_item_home_category_title_name);
            ProjectApplication.mImageLoader.loadImage(iconimg, categoryInfo.getCat_thumb());
            icontv.setText(categoryInfo.getCat_name());
            item.setLayoutParams(categoryParams);
            final int finalChildHeight = childHeight;
            final int finalI = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //属性动态   自动滑行到指定位置
                    ObjectAnimator xTranslate = ObjectAnimator.ofInt(refreshableView, "scrollX", 0);
                    ObjectAnimator yTranslate = ObjectAnimator.ofInt(refreshableView, "scrollY", Util.dip2px(mActivity, 300) + finalChildHeight * finalI);

                    AnimatorSet animators = new AnimatorSet();
                    animators.setDuration(1000L);
                    animators.playTogether(xTranslate, yTranslate);
                    animators.start();
//                    refreshableView.scrollTo(0, Util.dip2px(mActivity, 300)+ finalChildHeight * finalI);
                    if (finalI > 0) {
                        horizontalListView.setVisibility(View.VISIBLE);
                    } else {
                        horizontalListView.setVisibility(View.GONE);
                    }
                }
            });
            cp.addView(item);
        }

    }

    void showBanner() {
        View BannerView = new DynamicBanner(mActivity, LayoutInflater.from(mActivity), 5000).initView(bannerInfo);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.dip2px(mActivity, 175));
        BannerView.setLayoutParams(layoutParams);
        llContentTitle.addView(BannerView);
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


    @OnClick({R.id.title_user_ivb, R.id.title_msg_ivb, R.id.id_category_title_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_user_ivb:
                break;
            case R.id.title_msg_ivb:
                break;
            case R.id.id_category_title_tv:
                ProDispatcher.goSearchActivity(mActivity);
                break;
        }
    }
}
