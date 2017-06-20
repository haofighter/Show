package com.myself.show.show.Ui.music.MusicService;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.myself.show.show.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class MusicService extends Service {

    public final IBinder binder = new MusicBinder();
    private int musicIndex;
    private static List<String> musicDir = new ArrayList<>();

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    public static MediaPlayer mp = new MediaPlayer();

    public MusicService() {
        try {
            musicIndex = 0;
            if (musicDir.size() != 0)
                mp.setDataSource(musicDir.get(musicIndex));
            mp.prepare();
        } catch (Exception e) {
            Log.d("hint", "can't get to the song");
            e.printStackTrace();
        }
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
            musicDir.add(0, str);
            mp.setDataSource(musicDir.get(0));
            mp.prepare();
        } catch (Exception e) {
            Log.d("hint", "can't get to the song");
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

    public void nextMusic() {
        if (mp != null && musicIndex < 3) {
            mp.stop();
            try {
                mp.reset();
                if (musicIndex+1<musicDir.size()) {
                    mp.setDataSource(musicDir.get(musicIndex++));
                }else{
                    ToastUtils.showMessage("亲,列表中没有了哦!");
                }
                musicIndex++;
                mp.prepare();
                mp.seekTo(0);
                mp.start();
            } catch (Exception e) {
                Log.d("hint", "can't jump next music");
                e.printStackTrace();
            }
        }
    }

    public void preMusic() {
        if (mp != null && musicIndex > 0) {
            mp.stop();
            try {
                mp.reset();
                if (musicIndex>0) {
                    mp.setDataSource(musicDir.get(musicIndex--));
                }else{
                    ToastUtils.showMessage("亲,前面没有哦!");
                }
                musicIndex--;
                mp.prepare();
                mp.seekTo(0);
                mp.start();
            } catch (Exception e) {
                Log.d("hint", "can't jump pre music");
                e.printStackTrace();
            }
        }
    }
}
