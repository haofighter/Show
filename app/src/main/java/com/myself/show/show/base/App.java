package com.myself.show.show.base;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;

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

    public List<WySearchInfo.ResultBean.SongsBean> getSongsList() {
        return songsList;
    }


    /**
     * getInstance
     */
    public static App getInstance() {
        if (mInstance == null){
            ToastUtils.showMessage("APP是个空的");
            mInstance = new App();}
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        setDatabase();
        createMusicServie(getApplicationContext());
    }


    public void createMusicServie(Context context) {
        musicService = new MusicService();
        bindServiceConnection(context);
    }

    public MusicService getMusicServie(Context context) {
        if (musicService==null){
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

    public WySearchInfo.ResultBean.SongsBean getRunMusicInfo(){
        WySearchInfo.ResultBean.SongsBean songsBean=null;
        try{
            songsBean= this.getSongsList().get(musicService.getRunIndex());
        }catch (Exception e){
            return null;
        }
       return songsBean;
    }


    //程序终止时
    @Override
    public void onTerminate() {
        unbindService(sc);
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

