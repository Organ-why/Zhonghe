package com.zhonghe.shiangou.ui.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zhonghe.shiangou.data.bean.GoodsInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.ui.baseui.BaseTopFragment;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.BaseBannerInfo;
import com.zhonghe.shiangou.data.bean.HomeCategoryInfo;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.ui.widget.DynamicBanner;
import com.zhonghe.shiangou.ui.widget.FlowLayout;
import com.zhonghe.shiangou.ui.widget.HomeCategoryListView;
import com.zhonghe.shiangou.ui.widget.HorizontalListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Date: 2017/7/4.
 * Author: whyang
 */
public class HomeFragment extends BaseTopFragment {
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
        final ScrollView refreshableView = cartIdLv.getRefreshableView();
        getHomeData();
//        adapter = new Productadapter(mActivity);
//        commonDataXlistview.setAdapter(adapter);
        List<bannerInfo> mlist = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            bannerInfo banner = new bannerInfo();
            banner.setId(i);
            banner.setImgUrl("imgurl");
            mlist.add(banner);
        }
        View BannerView = new DynamicBanner(mActivity, LayoutInflater.from(mActivity), 10000).initView(mlist);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.dip2px(mActivity, 175));

        BannerView.setLayoutParams(layoutParams);
        llContentTitle.addView(BannerView);

        View viewCategoryTitle = LayoutInflater.from(mActivity).inflate(R.layout.layout_home_category, null);

        llContentTitle.addView(viewCategoryTitle);

        List<HomeCategoryInfo> itemList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            HomeCategoryInfo homeCategoryInfo = new HomeCategoryInfo();
            List<GoodsInfo> proList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                GoodsInfo proInfo = new GoodsInfo();
                proList.add(proInfo);
            }
            homeCategoryInfo.setList(proList);
            itemList.add(homeCategoryInfo);
        }

        new HomeCategoryListView(mActivity).initView(itemList, llContentList);

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
        LinearLayout.LayoutParams categoryParams = new LinearLayout.LayoutParams(width / 4, Util.dip2px(mActivity, 75));
        for (int i = 0; i < 7; i++) {
            View item = LayoutInflater.from(mActivity).inflate(R.layout.item_home_category_title, null);
            item.setLayoutParams(categoryParams);
            final int finalChildHeight = childHeight;
            final int finalI = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
        //悬浮Title
//        new HomeScrollListener(refreshableView, mActivity, horizontalListView, itemList, childHeight).ListenScroll();


    }

    void getHomeData(){
        Request<?> request = HttpUtil.getHomeData(mActivity, new ResultListener() {

            @Override
            public void onFail(String error) {
                Log.i("onFial", error);
            }

            @Override
            public void onSuccess(Object obj) {
//                Gson gson = new Gson();
//                gson.fromJson(obj.toString(), );
                Log.i("onSuccess", obj.toString());
            }
        });
        addRequest(request);
    }
    class bannerInfo extends BaseBannerInfo {

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

}
