package com.myself.show.show.Ui.baiduMap.test;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.myself.show.show.R;
import com.myself.show.show.base.App;

/**
 * Created by Administrator on 2017/8/23.
 */

public class BaiduUtils {
    private static BaiduUtils baiduUtils = new BaiduUtils();

    public static BaiduUtils getInstance() {
        if (baiduUtils == null) {
            baiduUtils = new BaiduUtils();
        }
        return baiduUtils;
    }


    //设置定位位置  可自己设置图标
    private void showLocation(MapView mapView, BDLocation mbdLocation, BitmapDescriptor mCurrentMarker, MyLocationConfiguration.LocationMode locationMode) {
        if (mbdLocation == null) {
            return;
        }

        mapView.getMap().setMyLocationEnabled(true);
        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(mbdLocation.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(App.getInstance().getCurrentX()).latitude(mbdLocation.getLatitude())
                .longitude(mbdLocation.getLongitude()).build();
        // 设置定位数据
        mapView.getMap().setMyLocationData(locData);
        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        if (locationMode == MyLocationConfiguration.LocationMode.FOLLOWING) {
            mapView.getMap().setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(17).build()));//设置缩放级别
        }
        MyLocationConfiguration config = new MyLocationConfiguration(locationMode, true, mCurrentMarker);
        mapView.getMap().setMyLocationConfiguration(config);
    }

    //设置定位位置  图标默认为location
    public void showLocation(MapView mapView, BDLocation mbdLocation, MyLocationConfiguration.LocationMode locationMode) {
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.location);
        //定位的图标
        showLocation(mapView, mbdLocation, null, locationMode);
    }

    //设置定位位置  图标默认为location
    public void showLocation(MapView mapView, BDLocation mbdLocation, int icon, MyLocationConfiguration.LocationMode locationMode) {
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(icon);
        //定位的图标
        showLocation(mapView, mbdLocation, null, locationMode);
    }


    //添加地图覆盖物  是否只显示一个
    public void addMark(MapView mapView, SuggestionResult.SuggestionInfo suggestionInfo, boolean onlyOne) {
        if (onlyOne) {
            mapView.getMap().clear();
        }
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.end);
        LatLng point = new LatLng(suggestionInfo.pt.latitude, suggestionInfo.pt.longitude);
        OverlayOptions option = new MarkerOptions()
                .position(point).icon(bitmap)
                .title(suggestionInfo.key).zIndex(0);
        mapView.getMap().addOverlay(option);

        mapView.getMap().setMapStatus(MapStatusUpdateFactory
                .zoomTo(17));
        MapStatusUpdate msu = MapStatusUpdateFactory
                .newLatLng(point);
        mapView.getMap().setMapStatus(msu);
    }
}
