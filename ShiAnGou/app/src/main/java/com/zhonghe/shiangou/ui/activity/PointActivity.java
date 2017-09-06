package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.HeaderGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.BaseBannerInfo;
import com.zhonghe.shiangou.data.bean.PointGoodsInfo;
import com.zhonghe.shiangou.data.bean.PointItemInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.adapter.PointAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.ui.widget.DynamicBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/8/21
 * desc: 积分商城
 */

public class PointActivity extends BaseTopActivity implements PullToRefreshBase.OnRefreshListener2, View.OnClickListener {
    @Bind(R.id.id_point_gv)
    PullToRefreshGridView idPointGv;
    PointAdapter adapter;
    List<PointItemInfo> goodsInfos;
    int curpage = 1;
    int cursize = 10;
    LinearLayout idPointBannerLl;
    LinearLayout idPointnumLl;
    LinearLayout idPointrecordLl;
    TextView idPointZeroTv;
    TextView idPointTodayTv;
    TextView idPointCommonTv;
    TextView idPointAllTv;
    private List<BaseBannerInfo> bannerInfo;
    private TextView idPointnumTv;

    @Override
    protected void initTop() {
        setTitle(R.string.common_pointtitle_str);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_point);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        goodsInfos = new ArrayList<>();
        bannerInfo = new ArrayList<>();
        idPointGv.setOnRefreshListener(this);
        adapter = new PointAdapter(mContext, goodsInfos);
        View header = LayoutInflater.from(this).inflate(R.layout.layout_point_header, null);
        HeaderGridView gridView = idPointGv.getRefreshableView();
        gridView.addHeaderView(header);
        idPointGv.setAdapter(adapter);
        idPointBannerLl = (LinearLayout) header.findViewById(R.id.id_point_banner_ll);
        idPointnumLl = (LinearLayout) header.findViewById(R.id.id_point_num_ll);
        idPointnumLl.setOnClickListener(this);
        idPointrecordLl = (LinearLayout) header.findViewById(R.id.id_point_record_ll);
        idPointrecordLl.setOnClickListener(this);
        idPointZeroTv = (TextView) header.findViewById(R.id.id_point_zero_tv);
        idPointZeroTv.setOnClickListener(this);
        idPointTodayTv = (TextView) header.findViewById(R.id.id_point_today_tv);
        idPointCommonTv = (TextView) header.findViewById(R.id.id_point_common_tv);
        idPointAllTv = (TextView) header.findViewById(R.id.id_point_all_tv);
        idPointnumTv = (TextView) header.findViewById(R.id.id_point_num_tv);
        idPointnumTv.setText(UtilString.nullToEmpty(ProjectApplication.mUser.getRank_points()));
        getPointGoodsMsg();
    }

    void getPointGoodsMsg() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getPointGoods(mContext, curpage, cursize, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error);
                idPointGv.onRefreshComplete();
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                PointGoodsInfo pointGoodsInfo = (PointGoodsInfo) obj;
                goodsInfos = pointGoodsInfo.getData();
                if (curpage == 1) {
                    adapter.setList(goodsInfos);
                    bannerInfo.clear();
                    for (String imgurl : pointGoodsInfo.getBanner()) {
                        BaseBannerInfo info = new BaseBannerInfo();
                        info.setBanner_images(imgurl);
                        bannerInfo.add(info);
                        showBanner();
                    }
                } else {
                    adapter.addList(goodsInfos);
                }
                if (goodsInfos.size() > 0) {
                    curpage++;
                }

                idPointGv.onRefreshComplete();
            }
        });
        addRequest(request);
    }

    void showBanner() {
        idPointBannerLl.removeAllViews();
        View BannerView = new DynamicBanner(mContext, LayoutInflater.from(mContext), 5000).initView(bannerInfo);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.dip2px(mContext, 175));
        BannerView.setLayoutParams(layoutParams);
        idPointBannerLl.addView(BannerView);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        curpage = 1;
        getPointGoodsMsg();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getPointGoodsMsg();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_point_zero_tv:
                break;
            case R.id.id_point_today_tv:
                break;
            case R.id.id_point_common_tv:
                break;
            case R.id.id_point_all_tv:
                break;
            case R.id.id_point_num_ll:
                break;
            case R.id.id_point_record_ll:
                break;
        }
    }
}
