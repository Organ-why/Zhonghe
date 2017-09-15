package com.zhonghe.shiangou.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.zhonghe.lib_base.utils.UtilLog;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ProLocationListener;
import com.zhonghe.shiangou.utile.overlayutil.DrivingRouteOverlay;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.FOLLOWING;


public class LocationActivity extends BaseTopActivity implements OnGetRoutePlanResultListener {

    @Bind(R.id.bmapView)
    MapView mMapView;
    private BaiduMap mBaiduMap;

    // 搜索相关
    RoutePlanSearch mSearch = null;    // 搜索模块，也可去掉地图模块独立使用
    private DrivingRouteLine route;
    private DrivingRouteOverlay routeOverlay;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        registerAction(CstProject.BROADCAST_ACTION.LOCATION_ACTION);
    }

    @Override
    protected void initTop() {
        setTitle("地图");
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initViews() {
        mBaiduMap = mMapView.getMap();
//普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//卫星地图
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
////空白地图, 基础地图瓦片将不会被渲染。在地图类型中设置为NONE，将不会使用流量下载基础地图瓦片图层。使用场景：与瓦片图层一起使用，节省流量，提升自定义瓦片图下载速度。
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
        //开启交通图
//        mBaiduMap.setTrafficEnabled(true);

    /*    //定义Maker坐标点
        LatLng point = new LatLng(39.963175, 116.400244);
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_logo);
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)//位置
                .icon(bitmap)//icon
                .zIndex(9)  //设置marker所在层级
                .draggable(true);  //设置手势拖拽
        //在地图上添加Marker，并显示
        Marker marker = (Marker) (mBaiduMap.addOverlay(option));*/

//        marker.remove();
//        mBaiduMap.addOverlay(option);
        //构建文字Option对象，用于在地图上添加文字
//        OverlayOptions textOption = new TextOptions()
//                .bgColor(0xAAFFFF00)
//                .fontSize(24)
//                .fontColor(0xFFFF00FF)
//                .text("百度地图SDK")
//                .rotate(-30)
//                .position(point);
//在地图上添加该文字对象并显示
//        mBaiduMap.addOverlay(textOption);


        //调用BaiduMap对象的setOnMarkerDragListener方法设置marker拖拽的监听
        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            public void onMarkerDrag(Marker marker) {
                //拖拽中
            }

            public void onMarkerDragEnd(Marker marker) {
                //拖拽结束
            }

            public void onMarkerDragStart(Marker marker) {
                //开始拖拽
            }
        });

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 开启室内图
//        mBaiduMap.setIndoorEnable(true);

        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);

//开始定位
        ProjectApplication.mLocationService.registerListener(new ProLocationListener(mContext));
        ProjectApplication.mLocationService.start();
    }

    @Override
    protected void onReceive(Intent intent) {
        switch (intent.getAction()) {
            case CstProject.BROADCAST_ACTION.LOCATION_ACTION:
                ProjectApplication.mLocationService.stop();
                int code = intent.getIntExtra(CstProject.KEY.CODE, BDLocation.TypeServerError);
                if (code != BDLocation.TypeServerError) {
                    BDLocation location = ProjectApplication.mLocation;
                    // 构造定位数据
                    MyLocationData locData = new MyLocationData.Builder()
                            .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                            .direction(100).latitude(location.getLatitude())
                            .longitude(location.getLongitude()).build();
// 设置定位数据
                    mBaiduMap.setMyLocationData(locData);
// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
//                    BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
//                            .fromResource(R.mipmap.icon_logo);
//                    MyLocationConfiguration.LocationMode mCurrentMode = FOLLOWING;
//                    MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
//                    mBaiduMap.setMyLocationConfiguration(config);
// 当不需要定位图层时关闭定位图层
//                    mBaiduMap.setMyLocationEnabled(false);

                    LatLng startPoint = new LatLng(location.getLatitude(), location.getLongitude());
                    LatLng endPoint = new LatLng(39.963175, 116.400244);
                    PlanNode stNode = PlanNode.withLocation(startPoint);
                    PlanNode enNode = PlanNode.withLocation(endPoint);
//                    PlanNode enNode = PlanNode.withCityNameAndPlaceName("北京", "天安门");

                    mSearch.drivingSearch((new DrivingRoutePlanOption())
                            .from(stNode).to(enNode));
                }
                break;
        }
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
            UtilLog.d("抱歉，未找到结果");
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {

//            if (result.getRouteLines().size() > 1) {
            route = result.getRouteLines().get(0);
            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
            routeOverlay = overlay;
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();

//                nowResultdrive = result;
//                if (!hasShownDialogue) {
//                    MyTransitDlg myTransitDlg = new MyTransitDlg(RoutePlanDemo.this,
//                            result.getRouteLines(),
//                            RouteLineAdapter.Type.DRIVING_ROUTE);
//                    myTransitDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                            hasShownDialogue = false;
//                        }
//                    });
//                    myTransitDlg.setOnItemInDlgClickLinster(new OnItemInDlgClickListener() {
//                        public void onItemClick(int position) {
//                            route = nowResultdrive.getRouteLines().get(position);
//                            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaidumap);
//                            mBaidumap.setOnMarkerClickListener(overlay);
//                            routeOverlay = overlay;
//                            overlay.setData(nowResultdrive.getRouteLines().get(position));
//                            overlay.addToMap();
//                            overlay.zoomToSpan();
//                        }
//
//                    });
//                    myTransitDlg.show();
//                    hasShownDialogue = true;
//            }
        }
//        else if (result.getRouteLines().size() == 1) {
//            route = result.getRouteLines().get(0);
//            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaidumap);
//            routeOverlay = overlay;
//            mBaidumap.setOnMarkerClickListener(overlay);
//            overlay.setData(result.getRouteLines().get(0));
//            overlay.addToMap();
//            overlay.zoomToSpan();
////                mBtnPre.setVisibility(View.VISIBLE);
////                mBtnNext.setVisibility(View.VISIBLE);
//        }
        else {
            Log.d("route result", "结果数<0");
            return;
        }

    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }


    // 定制RouteOverly
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
//            if (useDefaultIcon) {
//            return BitmapDescriptorFactory.fromResource(R.mipmap.icon_logo);
//            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
//            if (useDefaultIcon) {
//            return BitmapDescriptorFactory.fromResource(R.mipmap.icon_logo);
//            }
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ProjectApplication.mLocationService.stop();
    }
}
