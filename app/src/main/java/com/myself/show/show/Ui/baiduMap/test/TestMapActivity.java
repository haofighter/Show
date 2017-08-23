package com.myself.show.show.Ui.baiduMap.test;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.TextureMapView;
import com.myself.show.show.R;
import com.myself.show.show.base.App;
import com.myself.show.show.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestMapActivity extends BaseActivity {

    @BindView(R.id.map_view)
    TextureMapView mapView;
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
                if (isFristLoc) {//判断是否是第一次定位  第一次定位需要将定位位置置于地图中间
                    BaiduUtils.getInstance().showLocation(mapView, bdLocation, MyLocationConfiguration.LocationMode.FOLLOWING);
                    isFristLoc=false;
                } else {
                    BaiduUtils.getInstance().showLocation(mapView, bdLocation, MyLocationConfiguration.LocationMode.NORMAL);
                }
                if ("".equals(selectStartAdd)) {
                    selectStartAdd = bdLocation.getCity() + bdLocation.getStreet() + bdLocation.getStreetNumber();
                }
                loctionMyself.setText(selectStartAdd);
            }
        });
    }

    boolean isFristLoc = true;
    private String selectStartAdd = "";
    private String selectEndAdd = "";

    @OnClick({R.id.loction_myself, R.id.select_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loction_myself:
                startActivity(SlelectAddressActivity.class);
                break;
            case R.id.select_address:
                startActivity(SlelectAddressActivity.class);
                break;
        }
    }
}
