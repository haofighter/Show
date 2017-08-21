package com.myself.show.show.Ui.baiduMap;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.adapter.BNCommonSettingParam;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.myself.show.show.R;
import com.myself.show.show.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaiduGuideActivity extends AppCompatActivity {

    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.reset)
    Button reset;
    private LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_guide);
        ButterKnife.bind(this);
        initMap();
        initLocation();
    }

    private String mSDCardPath = getSdcardDir();
    private static final String APP_FOLDER_NAME = "百度定位";

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    String authinfo = "";
    private boolean hasInitSuccess = false;

    private void initMap() {
        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME, new BaiduNaviManager.NaviInitListener() {
            @Override
            public void onAuthResult(int status, String msg) {
                if (0 == status) {
                    authinfo = "key校验成功!";
                } else {
                    authinfo = "key校验失败, " + msg;
                }
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(BaiduGuideActivity.this, authinfo, Toast.LENGTH_LONG).show();
                    }
                });
            }

            public void initSuccess() {
                Toast.makeText(BaiduGuideActivity.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                hasInitSuccess = true;
                initSetting();
            }

            public void initStart() {
                Toast.makeText(BaiduGuideActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
            }

            public void initFailed() {
                Toast.makeText(BaiduGuideActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
            }

        }, null, ttsHandler, ttsPlayStateListener);


    }


    /**
     * 内部TTS播报状态回调接口
     */
    private BaiduNaviManager.TTSPlayStateListener ttsPlayStateListener = new BaiduNaviManager.TTSPlayStateListener() {

        @Override
        public void playEnd() {
            // showToastMsg("TTSPlayStateListener : TTS play end");
        }

        @Override
        public void playStart() {
            // showToastMsg("TTSPlayStateListener : TTS play start");
        }
    };


    /**
     * 内部TTS播报状态回传handler
     */
    private Handler ttsHandler = new Handler() {
        public void handleMessage(Message msg) {
            int type = msg.what;
            switch (type) {
                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
                    // showToastMsg("Handler : TTS play start");
                    break;
                }
                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
                    // showToastMsg("Handler : TTS play end");
                    break;
                }
                default:
                    break;
            }
        }
    };

    private void initSetting() {
        // BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_DAY);
        BNaviSettingManager
                .setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        // BNaviSettingManager.setPowerSaveMode(BNaviSettingManager.PowerSaveMode.DISABLE_MODE);
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
        BNaviSettingManager.setIsAutoQuitWhenArrived(true);
        Bundle bundle = new Bundle();
        // 必须设置APPID，否则会静音
        bundle.putString(BNCommonSettingParam.TTS_APP_ID, "9354030");
        BNaviSettingManager.setNaviSdkParam(bundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    BDLocation mlocation;
    BitmapDescriptor mCurrentMarker;

    private void initLocation() {
        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.mipmap.font);
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                mlocation = location;
                //获取定位结果
                location.getTime();    //获取定位时间
                location.getLocationID();    //获取定位唯一ID，v7.2版本新增，用于排查定位问题
                location.getLocType();    //获取定位类型
                location.getLatitude();    //获取纬度信息
                location.getLongitude();    //获取经度信息
                location.getRadius();    //获取定位精准度
                location.getAddrStr();    //获取地址信息
                location.getCountry();    //获取国家信息
                location.getCountryCode();    //获取国家码
                location.getCity();    //获取城市信息
                location.getCityCode();    //获取城市码
                location.getDistrict();    //获取区县信息
                location.getStreet();    //获取街道信息
                location.getStreetNumber();    //获取街道码
                location.getLocationDescribe();    //获取当前位置描述信息
                location.getPoiList();    //获取当前位置周边POI信息
                location.getBuildingID();    //室内精准定位下，获取楼宇ID
                location.getBuildingName();    //室内精准定位下，获取楼宇名称
                location.getFloor();    //室内精准定位下，获取当前位置所处的楼层信息

                ToastUtils.showMessage(location.getTime() + "==" + location.getCountry() + "===" + location.getCity() + "" + location.getLongitude());
                Log.i("===", location.getTime() + "==" + location.getCountry() + "===" + location.getCity() + "" + location.getLongitude());
                //TODO  定位地点显示
//                BitmapDescriptor bitmap = BitmapDescriptorFactory
//                        .fromResource(R.mipmap.ic_launcher);
//                LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
//                OverlayOptions option1 = new MarkerOptions()
//                        .position(point).icon(bitmap)
//                        .title(location.getCity()).zIndex(0);
//
//                mapView.getMap().addOverlay(option1);
//                mapView.getMap().setMapStatus(MapStatusUpdateFactory
//                        .zoomTo(20));
//                MapStatusUpdate msu = MapStatusUpdateFactory
//                        .newLatLng(point);
//                mapView.getMap().animateMapStatus(msu);

                MyLocationData locData = new MyLocationData.Builder().latitude(mlocation.getLatitude())
                        .longitude(mlocation.getLongitude()).build();
// 设置定位数据
                mapView.getMap().setMyLocationData(locData);
// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）

                MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, mCurrentMarker);
                mapView.getMap().setMyLocationConfiguration(config);

            }
        });


        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

//        option.setIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

//        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

//        option.setWifiValidTime(5*60*1000);
        //可选，7.2版本新增能力，如果您设置了这个接口，首次启动定位时，会先判断当前WiFi是否超出有效期，超出有效期的话，会先重新扫描WiFi，然后再定位
        mLocationClient.setLocOption(option);
        geo = BitmapDescriptorFactory.fromResource(R.mipmap.font);
        MyLocationConfiguration configuration = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING, true, geo);
        mapView.getMap().setMyLocationConfigeration(configuration);// 设置定位模式
        mapView.getMap().setMyLocationEnabled(true);// 打开定位图层

        MyOrientationListener myOrientationListener = new MyOrientationListener(this);

        myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX = x;
                Log.i("角度问题", mCurrentX + "=====");

                if (mlocation != null) {
                    // 开启定位图层
                    mapView.getMap().setMyLocationEnabled(true);
// 构造定位数据
                    MyLocationData locData = new MyLocationData.Builder()
                            .accuracy(mlocation.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                            .direction(mCurrentX).latitude(mlocation.getLatitude())
                            .longitude(mlocation.getLongitude()).build();
// 设置定位数据
                    mapView.getMap().setMyLocationData(locData);
// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
                    mapView.getMap().setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(17).build()));//设置缩放级别
                    MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, mCurrentMarker);
                    mapView.getMap().setMyLocationConfiguration(config);
                }
            }
        });
        myOrientationListener.start();

        mLocationClient.start();

    }

    float mCurrentX;

    private BitmapDescriptor geo;

    @OnClick({R.id.reset, R.id.guide})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reset:
                mLocationClient.restart();
                break;
            case R.id.guide:
                BNRoutePlanNode node = new BNRoutePlanNode(116.300821, 40.050969, "百度大厦", null, BNRoutePlanNode.CoordinateType.WGS84);
                routeplanToNavi(BNRoutePlanNode.CoordinateType.WGS84);
                break;
            case R.id.guide_guoce:
                BNRoutePlanNode node_guoce = new BNRoutePlanNode(116.300821, 40.050969, "百度大厦", null, BNRoutePlanNode.CoordinateType.GCJ02);
                routeplanToNavi(BNRoutePlanNode.CoordinateType.GCJ02);
                break;
        }
    }

    private final static String authComArr[] = {Manifest.permission.READ_PHONE_STATE};

    private boolean hasCompletePhoneAuth() {

        PackageManager pm = this.getPackageManager();
        for (String auth : authComArr) {
            if (pm.checkPermission(auth, this.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private final static int authComRequestCode = 2;
    private BNRoutePlanNode.CoordinateType mCoordinateType = null;
    private boolean hasRequestComAuth = false;

    private void routeplanToNavi(BNRoutePlanNode.CoordinateType coType) {
        mCoordinateType = coType;
        if (!hasInitSuccess) {
            Toast.makeText(BaiduGuideActivity.this, "还未初始化!", Toast.LENGTH_SHORT).show();
        }
        // 权限申请
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            // 保证导航功能完备
            if (!hasCompletePhoneAuth()) {
                if (!hasRequestComAuth) {
                    hasRequestComAuth = true;
                    this.requestPermissions(authComArr, authComRequestCode);
                    return;
                } else {
                    Toast.makeText(BaiduGuideActivity.this, "没有完备的权限!", Toast.LENGTH_SHORT).show();
                }
            }

        }
        BNRoutePlanNode sNode = null;
        BNRoutePlanNode eNode = null;
        switch (coType) {
            case GCJ02: {
                sNode = new BNRoutePlanNode(116.30142, 40.05087, "百度大厦", null, coType);
                eNode = new BNRoutePlanNode(116.39750, 39.90882, "北京天安门", null, coType);
                break;
            }
            case WGS84: {
                sNode = new BNRoutePlanNode(mlocation.getLongitude(), mlocation.getLatitude(), "我的位置", null, coType);
                eNode = new BNRoutePlanNode(116.397491, 39.908749, "北京天安门", null, coType);
                break;
            }
            case BD09_MC: {
                sNode = new BNRoutePlanNode(12947471, 4846474, "百度大厦", null, coType);
                eNode = new BNRoutePlanNode(12958160, 4825947, "北京天安门", null, coType);
                break;
            }
            case BD09LL: {
                sNode = new BNRoutePlanNode(116.30784537597782, 40.057009624099436, "百度大厦", null, coType);
                eNode = new BNRoutePlanNode(116.40386525193937, 39.915160800132085, "北京天安门", null, coType);
                break;
            }
            default:
        }
        if (sNode != null && eNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
            list.add(sNode);
            list.add(eNode);
            BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new DemoRoutePlanListener(eNode), eventListerner);

        }
    }

    BaiduNaviManager.NavEventListener eventListerner = new BaiduNaviManager.NavEventListener() {

        @Override
        public void onCommonEventCall(int what, int arg1, int arg2, Bundle bundle) {
            BNEventHandler.getInstance().handleNaviEvent(what, arg1, arg2, bundle);
        }
    };


    public static final String ROUTE_PLAN_NODE = "routePlanNode";

    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {

            Log.i("--------", "这个应该是算路成功的回调");
            Toast.makeText(BaiduGuideActivity.this, "这个应该是算路成功的回调", Toast.LENGTH_SHORT).show();
        /*
         * 设置途径点以及resetEndNode会回调该接口
         */

            Intent intent = new Intent(BaiduGuideActivity.this, BNDemoGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);

        }

        @Override
        public void onRoutePlanFailed() {
            Toast.makeText(BaiduGuideActivity.this, "算路失败", Toast.LENGTH_SHORT).show();
        }
    }
}
