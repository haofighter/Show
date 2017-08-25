package com.myself.show.show.base;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.myself.show.show.Ui.baiduMap.MyOrientationListener;
import com.myself.show.show.Ui.baiduMap.MyOrientationListener.OnOrientationListener;
import com.myself.show.show.Ui.baiduMap.test.LocationLisener;
import com.myself.show.show.Ui.music.musicService.MusicService;
import com.myself.show.show.net.responceBean.WySearchInfo;
import com.myself.show.show.sql.DaoMaster;
import com.myself.show.show.sql.DaoSession;
import com.myself.show.show.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class App extends Application {

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private MusicService musicService;
    private static App mInstance = null;
    private List<WySearchInfo.ResultBean.SongsBean> songsList = new ArrayList<>();
    private LocationClient mLocationClient;

    public List<WySearchInfo.ResultBean.SongsBean> getSongsList() {
        return songsList;
    }


    /**
     * getInstance
     */
    public static App getInstance() {
        if (mInstance == null) {
            ToastUtils.showMessage("APP是个空的");
            mInstance = new App();
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        setDatabase();

        SDKInitializer.initialize(getApplicationContext());

//        createMusicServie(getApplicationContext());//此服务与定位服务有冲突 需后续进行改进

        //此处调用用于设置罗盘初始化时的角度
        getCompass();
    }

    //罗盘的角度
    private float mCurrentX;

    //获取到罗盘的角度
    public float getCurrentX() {
        return mCurrentX;
    }

    /**
     * 获取到罗盘信息
     */
    private void getCompass() {
        MyOrientationListener myOrientationListener = new MyOrientationListener(this);
        myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX = x;
                if (onLocationListener != null || mLocation != null) {
                    onLocationListener.locSuc(mLocation);
                }
            }
        });
        myOrientationListener.start();
    }


    //定位刷新监听
    LocationLisener onLocationListener;

    public void setOnLocationListener(LocationLisener onLocationListener) {
        this.onLocationListener = onLocationListener;
    }

    /**
     * 刷新定位
     */
    public LocationClient refreshLocation() {
        if (mLocationClient != null) {
            mLocationClient.stop();
            mLocationClient.start();
            Log.i("开启定位", "" + mLocationClient.isStarted());
        } else {
            startGetLoction();
        }
        return mLocationClient;
    }

    private BDLocation mLocation;

    public BDLocation getLoction() {
        return mLocation;
    }

    BDLocationListener bdLocationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            mLocation = bdLocation;
            Log.d("定位成功", "bdLocation==" + bdLocation.getCity());
            onLocationListener.locSuc(mLocation);
        }
    };

    /**
     * 开始定位
     */
    public void startGetLoction() {
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(bdLocationListener);

        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(LocationClientOption.MIN_SCAN_SPAN);
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
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }


    public void createMusicServie(Context context) {
        musicService = new MusicService();
        bindServiceConnection(context);
    }

    public MusicService getMusicServie(Context context) {
        if (musicService == null) {
            createMusicServie(context);
        }
        return musicService;
    }

    private ServiceConnection sc = new ServiceConnection() {

        public MusicService musicService;

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            musicService = ((MusicService.MusicBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicService = null;
        }
    };

    private void bindServiceConnection(Context context) {
        Intent intent = new Intent(context, MusicService.class);
        startService(intent);
        bindService(intent, sc, this.BIND_AUTO_CREATE);
    }

    public WySearchInfo.ResultBean.SongsBean getRunMusicInfo() {
        WySearchInfo.ResultBean.SongsBean songsBean = null;
        try {
            songsBean = this.getSongsList().get(musicService.getRunIndex());
        } catch (Exception e) {
            return null;
        }
        return songsBean;
    }


    //程序终止时
    @Override
    public void onTerminate() {
        unbindService(sc);
        mLocationClient.unRegisterLocationListener(bdLocationListener);
        super.onTerminate();
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    MusicService musicMediaSever;

    public void setMusicMediaSever(MusicService musicMediaSever) {
        this.musicMediaSever = musicMediaSever;
    }


}

