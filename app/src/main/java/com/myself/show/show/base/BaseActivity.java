package com.myself.show.show.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myself.show.show.R;
import com.myself.show.show.Tools.StatusBarUtil;
import com.myself.show.show.Ui.music.listener.OnSongChangeListener;
import com.myself.show.show.View.CircleImageView;
import com.myself.show.show.View.FlowingDraw.ElasticDrawer;
import com.myself.show.show.View.FlowingDraw.FlowingDrawer;

import net.qiujuer.genius.blur.StackBlur;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 基于所有页面的BaseActivity   加入侧滑界面
 */

public class BaseActivity extends AppCompatActivity {

    private FrameLayout flowing_content;
    protected FlowingDrawer flowView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);//用于告诉Window页面切换需要使用动画
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    //容纳侧滑的中音乐布局的holder
    MusicItemHolder musicItemHolder = null;

    @Override
    public void setContentView(View view) {
        View base = LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        RelativeLayout relativeLayout = (RelativeLayout) base.findViewById(R.id.show_content);
        flowView = (FlowingDrawer) base.findViewById(R.id.flowingDrawer_base);
        flowing_content = (FrameLayout) base.findViewById(R.id.flowing_content);
        if (flowing_content == null) {
            throw new NullPointerException("侧滑栏初始失败!");
        }
        relativeLayout.addView(view);
        flowView.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        super.setContentView(base);
        App.getInstance().getMusicServie(getApplicationContext()).setOnSongChangeListener(new OnSongChangeListener() {
            @Override
            public void onChange() {
                if (!isSetMySelfFlowingLayout) {
                    initDefaultFlow();
                }
            }
        });
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View content = LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(content);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDefaultFlowingLayout();
    }

    /**
     * 设置状态栏颜色
     *
     * @param colorResId
     */
    protected void setStatuBarColor(@ColorRes int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, colorResId));
        }
    }

    private boolean isSetMySelfFlowingLayout = false;

    //给侧滑栏重新填充布局
    protected void setFlowingLayout(View layout) {
        isSetMySelfFlowingLayout = true;
        if (flowing_content != null) {
            flowing_content.removeAllViews();
            flowing_content.addView(layout);
        } else {
            throw new NullPointerException("侧滑布局是空的,请确实是否初始化了布局!");
        }
    }

    //将侧滑设置成默认界面
    public void setDefaultFlowingLayout() {
        isSetMySelfFlowingLayout = false;
        if (flowing_content != null) {
            flowing_content.removeAllViews();
            flowing_content.addView(LayoutInflater.from(this).inflate(R.layout.flowing_content_layout, null));
            musicItemHolder = new MusicItemHolder(flowing_content);
            initDefaultFlow();
        } else {
            throw new NullPointerException("侧滑布局是空的,请确实是否初始化了布局!");
        }
    }


    //设置默认播放界面数据
    private void initDefaultFlow() {
        if (App.getInstance().getRunMusicInfo() != null) {
            musicItemHolder.flowing_bg.setImageBitmap(StackBlur.blurNativelyPixels(BitmapFactory.decodeResource(getResources(), R.mipmap.bg_1), 20, false));
            Glide.with(BaseActivity.this).load(App.getInstance().getRunMusicInfo().getAlbum().getBlurPicUrl()).into(musicItemHolder.music_image_show);
            Glide.with(BaseActivity.this).load(R.mipmap.pause).into(musicItemHolder.playorpouse);
            musicItemHolder.sing_song_arthur.setText(App.getInstance().getRunMusicInfo().getArtists().get(0).getName());
            musicItemHolder.sing_song_name.setText(App.getInstance().getRunMusicInfo().getName());
            App.getInstance().getMusicServie(getApplicationContext()).getMediaPlayer().setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    Log.i("", "缓存的进度" + percent);
                    musicItemHolder.musicProgress.setSecondaryProgress(percent);
                }
            });
        } else {
            Log.e("", "未获取到歌曲的信息");
        }

        MyMusicUIRefreshTask myMusicUIRefreshTask = null;
        if (App.getInstance().getMusicServie(getApplicationContext()).getMediaPlayer().isPlaying()) {
            Log.e("......", "播放状态");
            Glide.with(BaseActivity.this).load(R.mipmap.pause).into(musicItemHolder.playorpouse);
        } else {
            Log.e("......", "暂停状态");
            if (myMusicUIRefreshTask != null)
                myMusicUIRefreshTask.cancel();
            Glide.with(BaseActivity.this).load(R.mipmap.play).into(musicItemHolder.playorpouse);
        }
        if (timer == null)
            timer = new Timer();

        if (myMusicUIRefreshTask != null)
            myMusicUIRefreshTask.cancel();
        myMusicUIRefreshTask = new MyMusicUIRefreshTask();
        timer.schedule(myMusicUIRefreshTask, 0, 1000);
    }

    int progress = 0;
    Timer timer = new Timer() {
    };


    //实时修改音乐播放进度
    class MyMusicUIRefreshTask extends TimerTask {
        @Override
        public void run() {
            if (App.getInstance().getMusicServie(getApplicationContext()).getMediaPlayer().isPlaying()) {
                progress = App.getInstance().getMusicServie(getApplicationContext()).getMediaPlayer().getCurrentPosition() * 1000 / App.getInstance().getMusicServie(getApplicationContext()).getMediaPlayer().getDuration();
            }
            runOnUiThread(new TimerTask() {
                @Override
                public void run() {
                    musicItemHolder.musicProgress.setProgress(progress);
                }
            });
        }
    }


    public void musicClick(View view) {
        switch (view.getId()) {
            case R.id.before:
                App.getInstance().getMusicServie(getApplicationContext()).before();
                break;
            case R.id.playorpouse:
                App.getInstance().getMusicServie(getApplicationContext()).playOrPause();
                break;
            case R.id.next:
                App.getInstance().getMusicServie(getApplicationContext()).next();
                break;
        }
    }


    //设置侧滑栏的颜色
    protected void setFLowingLayoutBackGround(int color) {
        StatusBarUtil.changeViewColor(flowing_content, color);
    }


    //自定义侧滑界面 填充布局
    protected void setFlowingLayout(int layoutId) {
        View view = LayoutInflater.from(this).inflate(layoutId, null);
        setFlowingLayout(view);
    }


    /**
     * 设置侧滑是否显示
     *
     * @param show
     */
    protected void setFlowingShow(boolean show) {
        if (show) {
            flowView.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        } else {
            flowView.setTouchMode(ElasticDrawer.TOUCH_MODE_NONE);
        }

    }


    public class MusicItemHolder extends RecyclerView.ViewHolder {
        //侧滑的背景
        @BindView(R.id.flowing_bg)
        ImageView flowing_bg;
        //音乐进度
        @BindView(R.id.music_progress)
        ProgressBar musicProgress;
        //循环方式
        @BindView(R.id.play_style)
        ImageView play_style;
        //上一曲
        @BindView(R.id.before)
        ImageView before;
        //暂停播放
        @BindView(R.id.playorpouse)
        ImageView playorpouse;
        //下一曲
        @BindView(R.id.next)
        ImageView next; //以后添加的按钮
        @BindView(R.id.future)
        ImageView future;
        //用户头像
        @BindView(R.id.user_avatar)
        CircleImageView user_avatar;
        //用户名
        @BindView(R.id.user_name)
        TextView user_name; //用户名
        //正在播放过得歌曲名称
        @BindView(R.id.sing_song_name)
        TextView sing_song_name;
        //正在播放过得歌曲作者
        @BindView(R.id.sing_song_arthur)
        TextView sing_song_arthur;
        //正在播放的音乐图片
        @BindView(R.id.music_image_show)
        CircleImageView music_image_show;

        public MusicItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public enum ActivityChangeAnimal {
        top, bottom, left, right
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void startActivity(Class<? extends Activity> activity, ActivityChangeAnimal activityChangeAnimal) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP || activityChangeAnimal == null) {
            Intent intent = new Intent(this, activity);
            startActivity(intent);
        } else {
            Transition exitexplode = TransitionInflater.from(this).inflateTransition(R.transition.slide_right);
            Transition enterexplode = TransitionInflater.from(this).inflateTransition(R.transition.slide_left);

            switch (activityChangeAnimal) {
                case top:
                    exitexplode = TransitionInflater.from(this).inflateTransition(R.transition.slide_bottom);
                    enterexplode = TransitionInflater.from(this).inflateTransition(R.transition.slide_bottom);
                    break;
                case bottom:
                    exitexplode = TransitionInflater.from(this).inflateTransition(R.transition.slide_top);
                    enterexplode = TransitionInflater.from(this).inflateTransition(R.transition.slide_top);
                    break;
                case left:
                    exitexplode = TransitionInflater.from(this).inflateTransition(R.transition.slide_right);
                    enterexplode = TransitionInflater.from(this).inflateTransition(R.transition.slide_right);
                    break;
                case right:
                    exitexplode = TransitionInflater.from(this).inflateTransition(R.transition.slide_left);
                    enterexplode = TransitionInflater.from(this).inflateTransition(R.transition.slide_left);
                    break;
            }

            //第一次进入时使用
            getWindow().setEnterTransition(enterexplode);
            //退出时使用
            getWindow().setExitTransition(exitexplode);


            Intent intent = new Intent(this, activity);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

        }
    }

}
