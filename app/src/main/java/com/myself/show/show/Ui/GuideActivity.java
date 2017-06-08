package com.myself.show.show.Ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.myself.show.show.R;
import com.myself.show.show.View.GuideVideoView;
import com.myself.show.show.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class GuideActivity extends BaseActivity {

    @BindView(R.id.gvv)
    GuideVideoView gvv;
    @BindView(R.id.btn_start)
    Button btn_start;

    @BindView(R.id.activity_guide)
    RelativeLayout activity_guide;
    @BindView(R.id.layout_video)
    RelativeLayout layout_video;
    @BindView(R.id.guide_show)
    ImageView guide_show;

    private int guideType = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide,null);
        setContent();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET) != PackageManager.PERMISSION_DENIED) {
            // 申请CAMERA权限
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.CAMERA }, 3);
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (3 == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 授权

            } else {

            }
        }
    }


    @OnClick(R.id.btn_start)
    public void onClick(View v) {
        startActivity(MainActivity.class);
        finish();
    }

    /**
     * 填充界面
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setContent() {
        na_bar.setVisibility(View.GONE);

//if (guideType==1){
//    guide_show.setImageResource();
//}else {
        initViewVideo();
//}
    }


    /**
     * 初始化
     */
    private void initViewVideo() {

        layout_video.setVisibility(View.VISIBLE);
        //设置播放加载路径
        gvv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.overlay));
        //播放
        gvv.start();
        //循环播放
        gvv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                startActivity(MainActivity.class);
                finish();
            }
        });

    }

}
