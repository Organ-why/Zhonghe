package com.zhonghe.shiangou.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilLog;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.BaseBannerInfo;
import com.zhonghe.shiangou.data.bean.HomeCategoryInfo;
import com.zhonghe.shiangou.data.bean.HomeData;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.activity.CustomScanActivity;
import com.zhonghe.shiangou.ui.adapter.HomeCategoryTitleAdapter;
import com.zhonghe.shiangou.ui.adapter.RecyAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseTopFragment;
import com.zhonghe.shiangou.ui.dialog.AppUpdataDialog;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.ui.widget.BannerPresenter;
import com.zhonghe.shiangou.ui.widget.BaseRecPrensenter;
import com.zhonghe.shiangou.ui.widget.FlowLayout;
import com.zhonghe.shiangou.ui.widget.HorizontalListView;
import com.zhonghe.shiangou.ui.widget.RecyclerPresenter;
import com.zhonghe.shiangou.utile.UtilPro;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Date: 2017/7/4.
 * Author: whyang
 */
public class HomeFragmentRecy extends BaseTopFragment implements RecyAdapter.addCartListener, BaseRecPrensenter.OnRecyRefreshListener {
    LinearLayout llContentTitle;
    LinearLayout llContentList;

    List<HomeCategoryInfo> categoryInfo;
    List<BaseBannerInfo> bannerInfo;
    @BindView(R.id.id_home_category_horizontal_title_hl)
    HorizontalListView horizontalListView;
    @BindView(R.id.sfl)
    SwipeRefreshLayout sfl;
    @BindView(R.id.id_mrecyclerview)
    RecyclerView idMrecyclerview;
    Unbinder unbinder;
    //    private RecyAdapter adapter;
    private LinearLayoutManager layoutManager;
    private boolean mShouldScroll;
    private int mToPosition;
    private RecyclerPresenter presenter;


    @Override
    protected void initTop() {
    }

    @Override
    protected void initAppCustom() {
        setAppCustomLayout(R.layout.layout_custom_top);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.layout_recyclerview);
        unbinder = ButterKnife.bind(this, getView());
        setOnRetryListener(new OnRetryListener() {
            @Override
            public void onRetry() {
                setRetry(false);
                getHomeData();
            }
        });
    }

    @Override
    protected void initViews() {
        registerAction(CstProject.BROADCAST_ACTION.LOCATION_ACTION);
        View header = LayoutInflater.from(mActivity).inflate(R.layout.layout_home_header, null);
        llContentTitle = (LinearLayout) header.findViewById(R.id.ll_content_title);
        llContentList = (LinearLayout) header.findViewById(R.id.ll_content_list);

        categoryInfo = new ArrayList<>();
        layoutManager = new LinearLayoutManager(mActivity);
//        idMrecyclerview = ButterKnife.findById(getView(), R.id.id_mrecyclerview);
        idMrecyclerview.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于grid view
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流
        presenter = new RecyclerPresenter.RecyclerPresenterBuilder(mActivity, idMrecyclerview).setmSFL(sfl)
                .setHeader(header)
                .setListener(this).build();
        presenter.setCarListener(this);
        presenter.setIsLoadMore(false);
        presenter.setIsRefresh(true);
//        adapter = new RecyAdapter(mActivity, categoryInfo);
//        RecyHeaderAdapter<ViewHolder> headerAdapter = new RecyHeaderAdapter<>(adapter);
//        headerAdapter.addHeaderView(header);
//        idRecyclerview.setAdapter(headerAdapter);
        getHomeData();
        getVersionName();
    }

    void getHomeData() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getHomeData(mActivity, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                setRetry(true);
                Util.toast(mActivity, error);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                HomeData homeData = (HomeData) obj;
                bannerInfo = homeData.getBannerX();
                categoryInfo = homeData.getCategoryX();
//                adapter.setData(categoryInfo);
                presenter.setData(categoryInfo);
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
        ImageView onlineView = (ImageView) viewCategoryTitle.findViewById(R.id.id_point_online);
        ImageView unlineView = (ImageView) viewCategoryTitle.findViewById(R.id.id_point_unline);
        unlineView.setOnClickListener(this);
        onlineView.setOnClickListener(this);
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
//        View BannerView = new DynamicBanner(mActivity, LayoutInflater.from(mActivity), 5000).initView(bannerInfo);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.dip2px(mActivity, 175));
        View view = new BannerPresenter(mActivity, 5000, new BannerPresenter.OnItemVpClick() {
            @Override
            public void OnVpClick(int position) {
                BaseBannerInfo info = bannerInfo.get(position);
                ProDispatcher.goGoodsDetailActivity(mActivity, info.getGoods_id());
            }
        }).setPosition(false).initView(bannerInfo);
        view.setLayoutParams(layoutParams);
        llContentTitle.addView(view);
    }

    void RecyScrollTo(int posi) {
        int position = presenter.getAdapter().getPosition(posi);
        int firstItem = layoutManager.findFirstVisibleItemPosition();
        int lastItem = layoutManager.findLastVisibleItemPosition();
        if (position <= firstItem) {
            idMrecyclerview.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            int top = idMrecyclerview.getChildAt(position - firstItem).getTop();
            idMrecyclerview.smoothScrollBy(0, top);
        } else {
            idMrecyclerview.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }


    @OnClick({R.id.title_user_ivb, R.id.title_msg_ivb, R.id.id_category_title_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_user_ivb:
                ProDispatcher.sendMainTabBroadcast(mActivity, 3);
                break;
            case R.id.title_msg_ivb:
                int REQUEST_CODE = 0x0000c0de; // Only use bottom 16 bits
                Intent intent = new Intent(mActivity, CustomScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                //需要以带返回结果的方式启动扫描界面
//                new IntentIntegrator(getActivity())
//                        .setOrientationLocked(false)
//                        .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
//                        .initiateScan(); // 初始化扫描
                break;
            case R.id.id_category_title_tv:
                ProDispatcher.goSearchActivity(mActivity);
                break;

        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.id_point_unline:
                //线下商城
                if (!UtilPro.getLocationPermission(mActivity)) {
                    Util.toast(mActivity, R.string.location_nopermission);
                } else {
                    ProDispatcher.goPointUnlineActivity(mActivity);
                }
//                ProDispatcher.goPointUnlineDetailActivity(mActivity);
                break;
            case R.id.id_point_online:
                //积分商城
                ProDispatcher.goPointActivity(mActivity);
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
    public void OnRefresh() {
        presenter.RefreshComplet();
        llContentTitle.removeAllViews();
        llContentList.removeAllViews();
        getHomeData();
    }

    @Override
    public void OnRecScroll(RecyclerView recyclerView, int dx, int dy) {
        // 第一个可见位置
        int firstItem = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(0));
        if (firstItem > 0) {
            horizontalListView.setVisibility(View.VISIBLE);
        } else {
            horizontalListView.setVisibility(View.GONE);
        }
    }

    @Override
    public void OnLoadMore() {
//        presenter.LoadMoreComplet();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(mActivity, "内容为空", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mActivity, "扫描成功", Toast.LENGTH_LONG).show();
                // ScanResult 为 获取到的字符串
                String ScanResult = intentResult.getContents();
                UtilLog.d(ScanResult);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    protected void onReceive(Intent intent) {
        super.onReceive(intent);
        switch (intent.getAction()) {
            case CstProject.BROADCAST_ACTION.LOCATION_ACTION:
                setWaitingDialog(false);
                ProjectApplication.mLocationService.stop();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
