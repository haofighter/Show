package com.myself.show.show.Ui.baiduMap.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BitmapDescriptor;
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
import com.myself.show.show.R;
import com.myself.show.show.Ui.baiduMap.test.Utils.DrivingRouteOverlay;
import com.myself.show.show.base.App;
import com.myself.show.show.base.BaseActivity;
import com.myself.show.show.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        RoutePlanSearch routePlanSearch = RoutePlanSearch.newInstance();
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
                DrivingRouteOverlay overlay = new DrivingRouteOverlay(mapView.getMap());
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
                    loctionMyself.setText(bdLocation.getCity() + bdLocation.getStreet() + bdLocation.getStreetNumber());
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


    @OnClick({R.id.loction_myself, R.id.select_address, R.id.search_road})
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
                if (selectStartAdd != null) {
                    slatlng = new LatLng(sSuggestionInfo.pt.latitude, sSuggestionInfo.pt.longitude);
                } else if (mLocation != null) {
                    slatlng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
                } else {
                    ToastUtils.showMessage("定位失败,请手动选择起点位置!");
                    return;
                }
                LatLng elatlng;
                if (selectEndAdd != null) {
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
            sSuggestionInfo=suggestionInfo;
            selectStartAdd = suggestionInfo.city + suggestionInfo.key;;
        } else if (requestCode == GET_END) {//搜索终点返回
            eSuggestionInfo=suggestionInfo;
            selectEndAdd = suggestionInfo.city + suggestionInfo.key;
            BaiduUtils.getInstance().addMark(mapView, suggestionInfo, true);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
