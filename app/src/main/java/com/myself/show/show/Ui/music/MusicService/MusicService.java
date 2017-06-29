package com.myself.show.show.Ui.music.MusicService;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.myself.show.show.base.App;
import com.myself.show.show.net.RetrofitManager;
import com.myself.show.show.net.responceBean.MusicPath;
import com.myself.show.show.net.responceBean.WySearchInfo;
import com.myself.show.show.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MusicService extends Service {

    public final IBinder binder = new MusicBinder();
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(mp==null){
            mp = new MediaPlayer();
        }
    }

    public static MediaPlayer mp = new MediaPlayer();

    public void playOrPause() {
        if (mp.isPlaying()) {
            mp.pause();
        } else {
            mp.start();
        }
    }

    public void playMusic(String str) {
        try {
            mp.reset();
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            mp.setDataSource(str);
            mp.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (mp != null) {
            mp.stop();
            try {
                mp.prepare();
                mp.seekTo(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void next(){

    }

    public void before(){

    }


    public  void  GetMusicUrlPlay(int index){
        ;
        RetrofitManager.builder(this).musicPath(App.getInstance().getSongsList().get(index).getId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MusicPath>() {
                    @Override
                    public void call(MusicPath musicPath) {
                    playMusic(musicPath.getData().getUrl());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("错误", throwable.toString());
                        ToastUtils.showMessage("网络连接失败");
                    }
                });
    }

}
