package com.myself.show.show.base;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.myself.show.show.R;
import com.myself.show.show.Ui.music.MusicService.MusicService;
import com.myself.show.show.Ui.music.acitivity.MusicActivityTheme;


/**
 * 基于所有页面的BaseActivity
 */

public class BaseActivity extends AppCompatActivity {
    private MusicService musicService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        musicService = new MusicService();
        App.getInstance().setMediaPlayerServer(musicService);
        bindServiceConnection();
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
        Intent intent = new Intent(BaseActivity.this, MusicService.class);
        startService(intent);
        bindService(intent, sc, this.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(sc);
        super.onDestroy();
    }

    @Override
    public void setContentView(View view) {
        View base = LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        RelativeLayout relativeLayout = (RelativeLayout) base.findViewById(R.id.show_content);
        relativeLayout.addView(view);
        super.setContentView(base);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }


    /**
     * 设置状态栏颜色
     * @param colorResId
     */
    protected void  setStatuBarColor(@ColorRes int colorResId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,colorResId));
        }
    }




}
