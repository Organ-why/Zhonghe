package com.zhonghe.shiangou.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.zhonghe.shiangou.ui.adapter.HomeCategoryTitleAdapter;
import com.zhonghe.shiangou.ui.adapter.RecyAdapter;
import com.zhonghe.shiangou.ui.adapter.RecyHeaderAdapter;
import com.zhonghe.shiangou.ui.adapter.ViewHolder;
import com.zhonghe.shiangou.ui.baseui.BaseTopFragment;
import com.zhonghe.shiangou.ui.dialog.AppUpdataDialog;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.ui.widget.DynamicBanner;
import com.zhonghe.shiangou.ui.widget.FlowLayout;
import com.zhonghe.shiangou.ui.widget.HorizontalListView;

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
    @Bind(R.id.id_home_category_horizontal_title_hl)
    HorizontalListView horizontalListView;
    private RecyAdapter adapter;
    private LinearLayoutManager layoutManager;
    private boolean mShouldScroll;
    private int mToPosition;


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
        layoutManager = new LinearLayoutManager(mActivity);
        idRecyclerview.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
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
                // 第一个可见位置
                int firstItem = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(0));
                if (firstItem > 0) {
                    horizontalListView.setVisibility(View.VISIBLE);
                } else {
                    horizontalListView.setVisibility(View.GONE);
                }

                //在这里进行第二次滚动（最后的100米！）
//                if (mShouldScroll ){
//                    mShouldScroll = false;
//                    //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
//                    int first =layoutManager.findFirstVisibleItemPosition();
//                    int last = layoutManager.findLastVisibleItemPosition();
//                    if ( 0 <= first && first < recyclerView.getChildCount()){
//                        //获取要置顶的项顶部离RecyclerView顶部的距离
////                        int top = recyclerView.getChildAt(first).getTop();
//                        int top = idRecyclerview.getChildAt(last - first).getTop();
//                        idRecyclerview.smoothScrollBy(0, top);
//                        //最后的移动
////                        recyclerView.scrollBy(0, top);
//                    }
//                }
            }
        });

        adapter = new RecyAdapter(mActivity, categoryInfo, this);
        RecyHeaderAdapter<ViewHolder> headerAdapter = new RecyHeaderAdapter<>(adapter);
        headerAdapter.addHeaderView(header);
        idRecyclerview.setAdapter(headerAdapter);
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
                showBanner();
                showCategory();

            }
        });
        addRequest(request);
    }


    void showCategory() {
        HomeCategoryTitleAdapter horadapter = new HomeCategoryTitleAdapter(mActivity, categoryInfo);
        horizontalListView.setAdapter(horadapter);
        horizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RecyScrollTo(i);
            }
        });
        int screenWidth = Util.getScreenWidth(mActivity);
        //分类icon部分
        View viewCategoryTitle = LayoutInflater.from(mActivity).inflate(R.layout.layout_home_category, null);
        LinearLayout viewPoint = (LinearLayout) viewCategoryTitle.findViewById(R.id.id_home_point_ll);
        viewPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProDispatcher.goPointActivity(mActivity);
//                ProDispatcher.goPointUnlineDetailActivity(mActivity);
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
                    RecyScrollTo(finalI);
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

    void RecyScrollTo(int posi) {
        int position = adapter.getPosition(posi);
        int firstItem = layoutManager.findFirstVisibleItemPosition();
        int lastItem = layoutManager.findLastVisibleItemPosition();
        if (position <= firstItem) {
            idRecyclerview.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            int top = idRecyclerview.getChildAt(position - firstItem).getTop();
            idRecyclerview.smoothScrollBy(0, top);
        } else {
            idRecyclerview.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
