package com.myself.show.show.Ui.music.musicService;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.myself.show.show.base.App;
import com.myself.show.show.net.RetrofitManager;
import com.myself.show.show.net.responceBean.MusicPath;
import com.myself.show.show.utils.ToastUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MusicService extends Service {

    public final IBinder binder = new MusicBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public MusicService setRunIndex(int index) {
        this.index = index;
        return this;
    }

    public int getRunIndex() {
        return index;
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mp == null) {
            mp = new MediaPlayer();
        }
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                index++;
                GetMusicUrlPlay();
            }
        });
    }

    private static MediaPlayer mp = new MediaPlayer();

    //获取到APP内的播放器
    public MediaPlayer getMediaPlayer() {
        return mp;
    }


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

    public void next() {
        index++;
        if (index >= App.getInstance().getSongsList().size()) {
            index = 0;
        }
    }

    public void before() {
        index--;
        if (index == -1) {
            ToastUtils.showMessage("亲,前面没有了哦!");
            return;
        }
        GetMusicUrlPlay();
    }

    int index;


    //播放歌曲 通过存在的标识来播放
    public void GetMusicUrlPlay() {
        if (App.getInstance().getSongsList().size() <= index) {
            throw new ArrayIndexOutOfBoundsException("播放的歌曲标识越界了");
        }
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
