package com.myself.show.show.Ui.baiduMap.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteLine;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteLine;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteLine;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.navisdk.adapter.BNCommonSettingParam;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.myself.show.show.R;
import com.myself.show.show.Ui.baiduMap.BNDemoGuideActivity;
import com.myself.show.show.Ui.baiduMap.BNEventHandler;
import com.myself.show.show.Ui.baiduMap.BaiduGuideActivity;
import com.myself.show.show.Ui.baiduMap.test.Utils.DrivingRouteOverlay;
import com.myself.show.show.base.App;
import com.myself.show.show.base.BaseActivity;
import com.myself.show.show.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType.WGS84;

public class TestMapActivity extends BaseActivity {

    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.loction_myself)
    TextView loctionMyself;
    @BindView(R.id.select_address)
    TextView selectAddress;
    private BitmapDescriptor mCurrentMarker;
    private LocationClient mLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_map);
        ButterKnife.bind(this);
        initMap();
        initSearch();
        initMapGuide();//初始化百度引擎
        //刷新定位位置
        mLocationClient = App.getInstance().refreshLocation();
    }




    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.stop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mLocationClient.restart();
    }

    RoutePlanSearch routePlanSearch;

    public void initSearch() {
        //路径规划搜索接口
        routePlanSearch = RoutePlanSearch.newInstance();
        routePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            /**
             * a.步行回调
             */
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                if (walkingRouteResult == null || walkingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    ToastUtils.showMessage("抱歉，未找到结果");
                    return;
                }
                List<WalkingRouteLine> routeLines = walkingRouteResult.getRouteLines();

            }

            /**
             * b.换乘路线结果回调
             * @param transitRouteResult
             */

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
                if (transitRouteResult == null || transitRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    ToastUtils.showMessage("抱歉，未找到结果");
                    return;
                }
                List<TransitRouteLine> routeLines = transitRouteResult.getRouteLines();

            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {
                if (massTransitRouteResult == null || massTransitRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    ToastUtils.showMessage("抱歉，未找到结果");
                    return;
                }
                List<MassTransitRouteLine> routeLines = massTransitRouteResult.getRouteLines();

            }

            /**
             * c.驾车路线结果回调
             */
            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
                if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    ToastUtils.showMessage("抱歉，未找到结果");
                    return;
                }
                List<DrivingRouteLine> routeLines = drivingRouteResult.getRouteLines();
                mapView.getMap().clear();
                DrivingRouteOverlay overlay = new DrivingRouteOverlay(mapView.getMap()) {
                    @Override
                    public BitmapDescriptor getStartMarker() {//设置起始点图标
                        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.location);
//                        return bitmapDescriptor;
                        return super.getStartMarker();
                    }

                    @Override
                    public int getLineColor() {//设置线路颜色
//                        return super.getLineColor();
                        return ContextCompat.getColor(TestMapActivity.this, R.color.Green);
                    }

                    @Override
                    public BitmapDescriptor getWayPointsMarker() {//设置路径点位图标
//                        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.location);
//                        return bitmapDescriptor;
                        return super.getWayPointsMarker();
                    }

                    @Override
                    public BitmapDescriptor getTerminalMarker() {//设置终点图标
//                        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.location);
//                        return bitmapDescriptor;
                        return super.getTerminalMarker();
                    }
                };
                overlay.removeFromMap();
                overlay.setData(routeLines.get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {
                if (indoorRouteResult == null || indoorRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    ToastUtils.showMessage("抱歉，未找到结果");
                    return;
                }

            }

            /**
             * d.骑行路线结果回调
             */
            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
                if (bikingRouteResult == null || bikingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    ToastUtils.showMessage("抱歉，未找到结果");
                    return;
                }
                List<BikingRouteLine> routeLines = bikingRouteResult.getRouteLines();

            }
        });
    }

    /**
     * 初始化地图控件
     */
    private void initMap() {
        try {
            // 隐藏百度的LOGO
            View child = mapView.getChildAt(1);
            if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
                child.setVisibility(View.INVISIBLE);
            }
        } catch (NullPointerException e) {
            e.getMessage();
        }
        // 不显示地图上比例尺
        mapView.showScaleControl(false);
        // 不显示地图缩放控件（按钮控制栏）
        mapView.showZoomControls(false);
        App.getInstance().setOnLocationListener(new LocationLisener() {
            @Override
            public void locSuc(BDLocation bdLocation) {
                mLocation = bdLocation;//存储定位位置
                if (isFristLoc) {//判断是否是第一次定位  第一次定位需要将定位位置置于地图中间
                    BaiduUtils.getInstance().showLocation(mapView, bdLocation, MyLocationConfiguration.LocationMode.FOLLOWING);
                } else {
                    BaiduUtils.getInstance().showLocation(mapView, bdLocation, MyLocationConfiguration.LocationMode.NORMAL);
                }
                if ("".equals(selectStartAdd) && bdLocation != null) {//防止第一次定位为空
                    selectStartAdd = bdLocation.getCity() + bdLocation.getStreet() + bdLocation.getStreetNumber();
                    loctionMyself.setText(selectStartAdd);
                    isFristLoc = false;
                } else {
                    loctionMyself.setText(selectStartAdd);
                }
            }
        });
    }

    private BDLocation mLocation;
    boolean isFristLoc = true;

    public final int GET_START = 1;
    public final int GET_END = 2;


    @OnClick({R.id.loction_myself, R.id.select_address, R.id.search_road, R.id.start_guide})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loction_myself:
                startActivityForResult(SlelectAddressActivity.class, GET_START);
                break;
            case R.id.select_address:
                startActivityForResult(SlelectAddressActivity.class, GET_END);
                break;
            case R.id.search_road://搜索路径
                LatLng slatlng;
                if (sSuggestionInfo != null) {
                    slatlng = new LatLng(sSuggestionInfo.pt.latitude, sSuggestionInfo.pt.longitude);
                } else if (mLocation != null) {
                    slatlng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
                } else {
                    ToastUtils.showMessage("定位失败,请手动选择起点位置!");
                    return;
                }
                LatLng elatlng;
                if (eSuggestionInfo != null) {
                    elatlng = new LatLng(eSuggestionInfo.pt.latitude, eSuggestionInfo.pt.longitude);
                } else {
                    ToastUtils.showMessage("请选择目的地");
                    return;
                }

                //驾车路线
                PlanNode fromPlanNode = PlanNode.withLocation(slatlng);
                PlanNode toPlanNode = PlanNode.withLocation(elatlng);
                routePlanSearch.drivingSearch(new DrivingRoutePlanOption().from(fromPlanNode).to(toPlanNode));

                //换乘公共交通工具的路线
                // routePlanSearch.transitSearch(new TransitRoutePlanOption().city(etCity.getText().toString()).from(fromPlanNode).to(toPlanNode));
                //步行路线
                //routePlanSearch.walkingSearch(new WalkingRoutePlanOption().from(fromPlanNode).to(toPlanNode));
                //骑行路线
                //routePlanSearch.bikingSearch(new BikingRoutePlanOption().from(fromPlanNode).to(toPlanNode));
                break;
            case R.id.start_guide:
                BNRoutePlanNode sNode;
                BNRoutePlanNode eNode;
                if (sSuggestionInfo != null) {
                    Log.i("坐标", sSuggestionInfo.pt.longitude + "----------" + sSuggestionInfo.pt.latitude);
                    sNode = new BNRoutePlanNode(sSuggestionInfo.pt.longitude, sSuggestionInfo.pt.latitude, sSuggestionInfo.city + sSuggestionInfo.key, null, WGS84);
                } else if (mLocation != null) {
                    Log.i("坐标", mLocation.getLongitude() + "----------" + mLocation.getLatitude());
                    sNode = new BNRoutePlanNode(mLocation.getLongitude(), mLocation.getLatitude(), mLocation.getCity() + mLocation.getAddrStr(), null, WGS84);
                } else {
                    ToastUtils.showMessage("定位失败,请手动选择起点位置!");
                    return;
                }
                if (eSuggestionInfo != null) {
                    Log.i("坐标", eSuggestionInfo.pt.longitude + "----------" + eSuggestionInfo.pt.latitude);
                    eNode = new BNRoutePlanNode(eSuggestionInfo.pt.longitude, eSuggestionInfo.pt.latitude, eSuggestionInfo.city + eSuggestionInfo.key, null, WGS84);
                } else {
                    ToastUtils.showMessage("请选择目的地");
                    return;
                }
                if (sNode != null && eNode != null) {
                    List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
                    list.add(sNode);
                    list.add(eNode);
                    BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new GuideRoutePlanListener(eNode));
                }
                break;
        }
    }


    public class GuideRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public GuideRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {

            Log.i("--------", "这个应该是算路成功的回调");
            Toast.makeText(TestMapActivity.this, "这个应该是算路成功的回调", Toast.LENGTH_SHORT).show();
        /*
         * 设置途径点以及resetEndNode会回调该接口
         */

            Intent intent = new Intent(TestMapActivity.this, MapGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("guideDate", (BNRoutePlanNode) mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);

        }

        @Override
        public void onRoutePlanFailed() {
            Log.i("--------", "算路失败");
            Toast.makeText(TestMapActivity.this, "算路失败", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        loctionMyself.setText(selectStartAdd);
        selectAddress.setText(selectEndAdd);
    }


    public String selectStartAdd = "";
    public String selectEndAdd = "";

    public SuggestionResult.SuggestionInfo sSuggestionInfo = null;
    public SuggestionResult.SuggestionInfo eSuggestionInfo = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        SuggestionResult.SuggestionInfo suggestionInfo = data.getParcelableExtra("selectAdd");
        if (requestCode == GET_START) {//搜索起点返回
            sSuggestionInfo = suggestionInfo;
            selectStartAdd = suggestionInfo.city + suggestionInfo.key;
            ;
        } else if (requestCode == GET_END) {//搜索终点返回
            eSuggestionInfo = suggestionInfo;
            selectEndAdd = suggestionInfo.city + suggestionInfo.key;
            BaiduUtils.getInstance().addMark(mapView, suggestionInfo, true);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //获取到SD卡的路径
    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    //判断时候初始化成功
    private boolean hasInitSuccess;

    //
    public void initMapGuide() {
        BaiduNaviManager.getInstance().init(this, getSdcardDir(), "百度定位", new BaiduNaviManager.NaviInitListener() {
            @Override
            public void onAuthResult(int status, final String msg) {
                if (0 == status) {
                    //key校检成功
                } else {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(TestMapActivity.this, "key校验失败, " + msg, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            public void initSuccess() {
                Toast.makeText(TestMapActivity.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                hasInitSuccess = true;
                initSetting();
            }

            public void initStart() {
                Toast.makeText(TestMapActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
            }

            public void initFailed() {
                Toast.makeText(TestMapActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
            }
        }, null, ttsHandler, ttsPlayStateListener);
    }


    /**
     * 内部TTS播报状态回调接口
     */
    private BaiduNaviManager.TTSPlayStateListener ttsPlayStateListener = new BaiduNaviManager.TTSPlayStateListener() {

        @Override
        public void playEnd() {
             ToastUtils.showMessage("TTSPlayStateListener : TTS play end");
        }

        @Override
        public void playStart() {
            ToastUtils.showMessage("TTSPlayStateListener : TTS play start");
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
                    ToastUtils.showMessage("Handler : TTS play start");
                    break;
                }
                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
                    ToastUtils.showMessage("Handler : TTS play end");
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
        bundle.putString(BNCommonSettingParam.TTS_APP_ID, "10019454");
        BNaviSettingManager.setNaviSdkParam(bundle);
    }
}
