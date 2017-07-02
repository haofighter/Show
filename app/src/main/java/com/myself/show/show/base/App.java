package com.myself.show.show.base;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.NonNull;

import com.myself.show.show.Ui.music.musicService.MusicService;
import com.myself.show.show.net.responceBean.WySearchInfo;
import com.myself.show.show.sql.DaoMaster;
import com.myself.show.show.sql.DaoSession;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


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
    private List<WySearchInfo.ResultBean.SongsBean> songsList = new List<WySearchInfo.ResultBean.SongsBean>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] ts) {
            return null;
        }

        @Override
        public boolean add(WySearchInfo.ResultBean.SongsBean songsBean) {
            for (int i = 0; i <this.size() ; i++) {
                if(this.get(i).getId()==songsBean.getId()){
                    return false;
                }
            }
            return true;
        }


        @Override
        public boolean remove(Object o) {
            return true;
        }

        @Override
        public boolean addAll(@NonNull Collection collection) {
            return true;
        }

        @Override
        public boolean addAll(int i, @NonNull Collection collection) {
            return true;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public WySearchInfo.ResultBean.SongsBean get(int i) {
            return null;
        }


        @Override
        public WySearchInfo.ResultBean.SongsBean set(int i, WySearchInfo.ResultBean.SongsBean songsBean) {
            return null;
        }

        @Override
        public void add(int i, WySearchInfo.ResultBean.SongsBean songsBean) {

        }

        @Override
        public WySearchInfo.ResultBean.SongsBean remove(int i) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator listIterator(int i) {
            return null;
        }

        @NonNull
        @Override
        public List subList(int i, int i1) {
            return null;
        }

        @Override
        public boolean retainAll(@NonNull Collection collection) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection collection) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection collection) {
            return false;
        }

    };

    public List<WySearchInfo.ResultBean.SongsBean> getSongsList() {
        return songsList;
    }

    /**
     * getInstance
     */
    public static App getInstance() {
        if (mInstance == null)
            mInstance = new App();
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        setDatabase();
        createMusicServie();
    }


    public void createMusicServie() {
        musicService = new MusicService();
        bindServiceConnection();
    }

    public MusicService getMusicServie() {
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

    private void bindServiceConnection() {
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        bindService(intent, sc, this.BIND_AUTO_CREATE);
    }

    public void getRunMusicInfo(){
        this.getSongsList().get(musicService.getRunIndex());
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

