package com.zhonghe.shiangou.ui.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilLog;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.BaseBannerInfo;
import com.zhonghe.shiangou.data.bean.HomeCategoryInfo;
import com.zhonghe.shiangou.data.bean.HomeData;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.adapter.RecyAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseTopFragment;
import com.zhonghe.shiangou.ui.dialog.AppUpdataDialog;
import com.zhonghe.shiangou.ui.listener.HomeScrollListener;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.ui.widget.DynamicBanner;
import com.zhonghe.shiangou.ui.widget.FlowLayout;
import com.zhonghe.shiangou.ui.widget.HomeCategoryListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date: 2017/7/4.
 * Author: whyang
 */
public class HomeFragmentRecy extends BaseTopFragment implements RecyAdapter.addCartListener {
    LinearLayout llContentTitle;
    LinearLayout llContentList;
    @Bind(R.id.id_recyclerview)
    RecyclerView idRecyclerview;

    List<HomeCategoryInfo> categoryInfo;
    List<BaseBannerInfo> bannerInfo;
    private RecyAdapter adapter;


    @Override
    protected void initLayout() {
        setContentView(R.layout.layout_recyclerview);
        ButterKnife.bind(this, getView());
    }

    @Override
    protected void initViews() {
        View header = LayoutInflater.from(mActivity).inflate(R.layout.layout_home_header, null);
        llContentTitle = (LinearLayout) header.findViewById(R.id.ll_content_title);
        llContentList = (LinearLayout) header.findViewById(R.id.ll_content_list);

        categoryInfo = new ArrayList<>();
        idRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));//这里用线性显示 类似于listview
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于grid view
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流
        idRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

//        idRecyclerview.scrollToPosition();
        adapter = new RecyAdapter(mActivity, categoryInfo, this);
        idRecyclerview.setAdapter(adapter);
//        idRecyclerview.addView(header);
        getHomeData();
        getVersionName();
    }

    void getHomeData() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getHomeData(mActivity, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mActivity, error);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                HomeData homeData = (HomeData) obj;
                bannerInfo = homeData.getBannerX();
                categoryInfo = homeData.getCategoryX();
                adapter.setData(categoryInfo);
                showCategory();
                showBanner();

            }
        });
        addRequest(request);
    }


    void showCategory() {
        int screenWidth = Util.getScreenWidth(mActivity);
        //分类icon部分
        View viewCategoryTitle = LayoutInflater.from(mActivity).inflate(R.layout.layout_home_category, null);
        LinearLayout viewPoint = (LinearLayout) viewCategoryTitle.findViewById(R.id.id_home_point_ll);
        viewPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProDispatcher.goPointActivity(mActivity);
            }
        });
        llContentTitle.addView(viewCategoryTitle);
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
//        new HomeScrollListener(cartIdLv, mActivity, horizontalListView, categoryInfo, childHeight).ListenScroll();

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
//                    ObjectAnimator xTranslate = ObjectAnimator.ofInt(refreshableView, "scrollX", 0);
//                    ObjectAnimator yTranslate = ObjectAnimator.ofInt(refreshableView, "scrollY", Util.dip2px(mActivity, 375) + finalChildHeight * finalI);
//
//                    AnimatorSet animators = new AnimatorSet();
//                    animators.setDuration(1000L);
//                    animators.playTogether(xTranslate, yTranslate);
//                    animators.start();
////                    refreshableView.scrollTo(0, Util.dip2px(mActivity, 300)+ finalChildHeight * finalI);
//                    if (finalI > 0) {
//                        horizontalListView.setVisibility(View.VISIBLE);
//                    } else {
//                        horizontalListView.setVisibility(View.GONE);
//                    }
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
                ProDispatcher.sendMainTabBroadcast(mActivity, 3);
                break;
            case R.id.title_msg_ivb:
                break;
            case R.id.id_category_title_tv:
                ProDispatcher.goSearchActivity(mActivity);
                break;
        }
    }

    @Override
    public void OnAddCart(final String goods_id) {
        if (ProjectApplication.mUser == null) {
            ProDispatcher.goLoginActivity(mActivity);
            return;
        }
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getAddCart(mActivity, goods_id, "", "1", new ResultListener() {

            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mActivity, error);
                Log.i("onFial", error);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                setWaitingDialog(false);
                Util.toast(mActivity, R.string.common_cart_add_success);
                ProDispatcher.sendAddCardBroadcast(mActivity, goods_id);
//                Log.i("onSuccess", obj.toString());
            }
        });
        addRequest(request);
    }

    void getVersionName() {
        Request<?> request = HttpUtil.getVersionCode(mActivity, new ResultListener() {
            @Override
            public void onFail(String error) {

            }

            @Override
            public void onSuccess(Object obj) {
                String version = (String) obj;
                String versionName = Util.getVersionName(mActivity);
                UtilLog.d(versionName + "..................");
                if (!version.equals(versionName)) {
                    AppUpdataDialog dialog = new AppUpdataDialog(mActivity, version);
                    dialog.show();
                }

            }
        });
        addRequest(request);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
