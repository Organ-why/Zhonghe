package com.zhonghe.shiangou.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.PlanNode;
import com.zhonghe.lib_base.baseui.BaseUIActivity;
import com.zhonghe.lib_base.baseui.UIOptions;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ProLocationListener;

public class SplashActivity extends BaseTopActivity {
    @Override
    protected void initTop() {

    }

    @Override
    protected long initOptions() {
        return UIOptions.UI_OPTIONS_CONTENT_CUSTOM;
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_splash);
    }
    Handler delayedHandler;
    @Override
    protected void initViews() {
        registerAction(CstProject.BROADCAST_ACTION.LOCATION_ACTION);

//开始定位
        ProjectApplication.mLocationService.registerListener(new ProLocationListener(mContext));
        ProjectApplication.mLocationService.start();
        delayedHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                ProDispatcher.goMainActivity(SplashActivity.this);
                finish();
            }
        };
//        delayedHandler.sendMessageDelayed(new Message(), 1500);
    }

    @Override
    protected void onReceive(Intent intent) {
        switch (intent.getAction()) {
            case CstProject.BROADCAST_ACTION.LOCATION_ACTION:
                ProjectApplication.mLocationService.stop();
                delayedHandler.sendMessageDelayed(new Message(), 1500);
//                int code = intent.getIntExtra(CstProject.KEY.CODE, BDLocation.TypeServerError);
//                if (code != BDLocation.TypeServerError) {
//                    BDLocation location = ProjectApplication.mLocation;
//                    // 构造定位数据
//                    MyLocationData locData = new MyLocationData.Builder()
//                            .accuracy(location.getRadius())
//                            // 此处设置开发者获取到的方向信息，顺时针0-360
//                            .direction(100).latitude(location.getLatitude())
//                            .longitude(location.getLongitude()).build();
//// 设置定位数据
//                    mBaiduMap.setMyLocationData(locData);
//// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
////                    BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
////                            .fromResource(R.mipmap.icon_logo);
////                    MyLocationConfiguration.LocationMode mCurrentMode = FOLLOWING;
////                    MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
////                    mBaiduMap.setMyLocationConfiguration(config);
//// 当不需要定位图层时关闭定位图层
////                    mBaiduMap.setMyLocationEnabled(false);
//
//                    LatLng startPoint = new LatLng(location.getLatitude(), location.getLongitude());
//                    LatLng endPoint = new LatLng(39.963175, 116.400244);
//                    PlanNode stNode = PlanNode.withLocation(startPoint);
//                    PlanNode enNode = PlanNode.withLocation(endPoint);
////                    PlanNode enNode = PlanNode.withCityNameAndPlaceName("北京", "天安门");
//
//                    mSearch.drivingSearch((new DrivingRoutePlanOption())
//                            .from(stNode).to(enNode));
//                }
                break;
        }
    }
}
