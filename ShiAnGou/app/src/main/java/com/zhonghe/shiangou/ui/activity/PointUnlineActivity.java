package com.zhonghe.shiangou.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.zhonghe.lib_base.baseui.UIOptions;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilLog;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.BaseBannerInfo;
import com.zhonghe.shiangou.data.bean.HomeCategoryInfo;
import com.zhonghe.shiangou.data.bean.ShopCatInfo;
import com.zhonghe.shiangou.data.bean.UnlineHomeInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.adapter.UnlineShopAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.ui.widget.FlowLayout;
import com.zhonghe.shiangou.ui.widget.xlistview.NXListViewNO;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * auther: whyang
 * date: 2017/9/12
 * desc:积分线下
 */

public class PointUnlineActivity extends BaseTopActivity implements NXListViewNO.IXListViewListener {
    @Bind(R.id.title_user_ivb)
    ImageButton titleUserIvb;
    @Bind(R.id.title_msg_ivb)
    ImageButton titleMsgIvb;
    @Bind(R.id.id_category_title_tv)
    TextView idCategoryTitleTv;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    @Bind(R.id.xlistview)
    NXListViewNO xlistview;


    private UnlineShopAdapter adapter;
    private LinearLayout llContentTitle;
    private LinearLayout llContentList;
    private int curpage = 1;
    private FlowLayout flowLayout;
    private UnlineHomeInfo homeData;

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
        setContentView(R.layout.activity_default_xlistview);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        rlTitle.setBackgroundResource(R.color.res_color_white);
        titleUserIvb.setImageResource(R.mipmap.common_nav_back);
        titleMsgIvb.setImageResource(R.mipmap.icon_exchange_record);
        idCategoryTitleTv.setText(R.string.search_unline_tip);
        idCategoryTitleTv.setBackgroundResource(R.drawable.circle_search_gray_bg);

        View header = LayoutInflater.from(mContext).inflate(R.layout.layout_home_header, null);
        View headerdesc = LayoutInflater.from(mContext).inflate(R.layout.layout_header_unline, null);
        llContentTitle = (LinearLayout) header.findViewById(R.id.ll_content_title);
        llContentList = (LinearLayout) header.findViewById(R.id.ll_content_list);
        llContentList.addView(headerdesc);

//        TextView foodTv = (TextView) headerdesc.findViewById(R.id.id_unline_food);
//        TextView liveTv = (TextView) headerdesc.findViewById(R.id.id_unline_live);
//        TextView playTv = (TextView) headerdesc.findViewById(R.id.id_unline_play);
        flowLayout = (FlowLayout) headerdesc.findViewById(R.id.id_home_categroy_title_cp);

        xlistview.setPullRefreshEnable(false);
        xlistview.setPullLoadEnable(false);
        xlistview.setXListViewListener(this);
        xlistview.addHeaderView(header);
        adapter = new UnlineShopAdapter(mContext, null);
        xlistview.setAdapter(adapter);
        getHomeData();
    }

    void setShopCat() {
        int screenWidth = Util.getScreenWidth(mContext);
        LinearLayout.LayoutParams categoryParams = new LinearLayout.LayoutParams(screenWidth / 3, Util.dip2px(mContext, 95));
//        for (int i = 0; i < homeData.getCat().size(); i++) {
        for (final ShopCatInfo cat : homeData.getCat()) {
            View item = LayoutInflater.from(mContext).inflate(R.layout.item_unlin_type, null);
            SimpleDraweeView iconimg = (SimpleDraweeView) item.findViewById(R.id.id_item_home_category_title_iv);
            TextView icontv = (TextView) item.findViewById(R.id.id_item_home_category_title_name);
            ProjectApplication.mImageLoader.loadImage(iconimg, cat.getType_img());
            icontv.setText(cat.getType_name());
            item.setLayoutParams(categoryParams);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProDispatcher.goPointUnlineListActivity(mContext, cat.getType_id());
                }
            });
            flowLayout.addView(item);
        }

    }

    void getHomeData() {
        setWaitingDialog(true);
        if (ProjectApplication.mLocation == null) {
            ProjectApplication.mLocationService.start();
            return;
        }
        Request<?> request = HttpUtil.getUnlineHome(mContext, curpage, ProjectApplication.mLocation.getLongitude(), ProjectApplication.mLocation.getLatitude(), new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                homeData = (UnlineHomeInfo) obj;
                if (curpage == 1) {
                    showBanner(homeData.getBanner());
                    setShopCat();
                    adapter.setList(homeData.getList());
                } else {
                    adapter.addList(homeData.getList());
                }
            }
        });
        addRequest(request);
    }

    void showBanner(String imgUrl) {
        SimpleDraweeView imageView = new SimpleDraweeView(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.dip2px(mContext, 175));
        imageView.setLayoutParams(layoutParams);
        ProjectApplication.mImageLoader.loadImage(imageView, imgUrl);
        llContentTitle.addView(imageView);
    }


    @OnClick({R.id.title_user_ivb, R.id.title_msg_ivb, R.id.id_category_title_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_user_ivb:
                finish();
//                Bitmap bitmap = EncodingUtils.createQRCode("http://blog.csdn.net/a_zhon/", 500, 500, logo);
                break;
            case R.id.title_msg_ivb:
                //需要以带返回结果的方式启动扫描界面
                new IntentIntegrator(this)
                        .setOrientationLocked(false)
                        .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                        .initiateScan(); // 初始化扫描
                break;
            case R.id.id_category_title_tv:
                ProDispatcher.goSearchShopActivity(mContext);
                break;
            case R.id.id_unline_food:
                ProDispatcher.goPointUnlineListActivity(mContext, "");
                break;
            case R.id.id_unline_live:
                ProDispatcher.goPointUnlineListActivity(mContext, "");
                break;
            case R.id.id_unline_play:
                ProDispatcher.goPointUnlineListActivity(mContext, "");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(this, "内容为空", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "扫描成功", Toast.LENGTH_LONG).show();
                // ScanResult 为 获取到的字符串
                String ScanResult = intentResult.getContents();
                UtilLog.d(ScanResult);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    protected void onReceive(Intent intent) {
        super.onReceive(intent);
        switch (intent.getAction()) {
            case CstProject.BROADCAST_ACTION.LOCATION_ACTION:
                ProjectApplication.mLocationService.stop();
                getHomeData();
                break;
        }
    }
}
