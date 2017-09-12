package com.zhonghe.shiangou.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.baseui.MenuPopup;
import com.zhonghe.lib_base.baseui.MenuTxt;
import com.zhonghe.lib_base.baseui.UIOptions;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.BaseBannerInfo;
import com.zhonghe.shiangou.data.bean.HomeCategoryInfo;
import com.zhonghe.shiangou.data.bean.HomeData;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.adapter.RecyAdapter;
import com.zhonghe.shiangou.ui.adapter.RecyHeaderAdapter;
import com.zhonghe.shiangou.ui.adapter.ViewHolder;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.ui.widget.DynamicBanner;
import com.zhonghe.shiangou.ui.widget.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/9/12
 * desc:积分线下
 */

public class PointUnlineActivity extends BaseTopActivity implements RecyAdapter.addCartListener {
    @Bind(R.id.title_user_ivb)
    ImageButton titleUserIvb;
    @Bind(R.id.title_msg_ivb)
    ImageButton titleMsgIvb;
    @Bind(R.id.id_category_title_tv)
    TextView idCategoryTitleTv;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    @Bind(R.id.id_recyclerview)
    RecyclerView mRecyclerview;


    List<HomeCategoryInfo> categoryInfo;
    List<BaseBannerInfo> bannerInfo;
    private RecyAdapter adapter;
    private LinearLayoutManager layoutManager;
    private LinearLayout llContentTitle;
    private LinearLayout llContentList;

    @Override
    protected void initTop() {

    }

    @Override
    protected void initAppCustom() {
        setAppCustomLayout(R.layout.layout_custom_top);
    }

    @Override
    protected long initOptions() {
        return UIOptions.UI_OPTIONS_SYSTEMBAR | UIOptions.UI_OPTIONS_APPBAR_CUSTIOM | UIOptions.UI_OPTIONS_CONTENT_CUSTOM;
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.layout_recyclerview);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        rlTitle.setBackgroundResource(R.color.res_color_white);
        titleUserIvb.setImageResource(R.mipmap.common_nav_back);
        titleMsgIvb.setImageResource(R.mipmap.icon_zxing_black);
        idCategoryTitleTv.setText(R.string.search_unline_tip);
        idCategoryTitleTv.setBackgroundResource(R.drawable.circle_search_gray_bg);

        View header = LayoutInflater.from(mContext).inflate(R.layout.layout_home_header, null);
        llContentTitle = (LinearLayout) header.findViewById(R.id.ll_content_title);
        llContentList = (LinearLayout) header.findViewById(R.id.ll_content_list);

        categoryInfo = new ArrayList<>();
        layoutManager = new LinearLayoutManager(mContext);
        mRecyclerview.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于grid view
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流

        adapter = new RecyAdapter(mContext, categoryInfo, this);
        RecyHeaderAdapter<ViewHolder> headerAdapter = new RecyHeaderAdapter<>(adapter);
        headerAdapter.addHeaderView(header);
        mRecyclerview.setAdapter(headerAdapter);
        getHomeData();
    }

    void getHomeData() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getHomeData(mContext, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error);
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
//        HomeCategoryTitleAdapter horadapter = new HomeCategoryTitleAdapter(mContext, categoryInfo);
//        horizontalListView.setAdapter(horadapter);
//        horizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////                RecyScrollTo(i);
//            }
//        });
        int screenWidth = Util.getScreenWidth(mContext);
        //分类icon部分
        View viewCategoryTitle = LayoutInflater.from(mContext).inflate(R.layout.layout_home_category, null);
        LinearLayout viewPoint = (LinearLayout) viewCategoryTitle.findViewById(R.id.id_home_point_ll);
        viewPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProDispatcher.goPointActivity(mContext);
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
        LinearLayout.LayoutParams categoryParams = new LinearLayout.LayoutParams(screenWidth / 4, Util.dip2px(mContext, 75));


        //悬浮Title
//        new HomeScrollListener(cartIdLv, mContext, horizontalListView, categoryInfo, childHeight).ListenScroll();

        for (int i = 0; i < categoryInfo.size(); i++) {
            HomeCategoryInfo categoryInfo = this.categoryInfo.get(i);
            View item = LayoutInflater.from(mContext).inflate(R.layout.item_home_category_title, null);
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
//                    RecyScrollTo(finalI);
                }
            });
            cp.addView(item);
        }

    }

    void showBanner() {
        View BannerView = new DynamicBanner(mContext, LayoutInflater.from(mContext), 5000).initView(bannerInfo);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.dip2px(mContext, 175));
        BannerView.setLayoutParams(layoutParams);
        llContentTitle.addView(BannerView);
    }

    @Override
    public void OnAddCart(String goods_id) {

    }
}
