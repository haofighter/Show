package com.myself.show.show.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myself.show.show.R;
import com.myself.show.show.Tools.StatusBarUtil;
import com.myself.show.show.View.CircleImageView;
import com.myself.show.show.View.FlowingDraw.ElasticDrawer;
import com.myself.show.show.View.FlowingDraw.FlowingDrawer;

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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    //容纳侧滑的中音乐布局的holder
    MusicItemHolder musicItemHolder = null;

    @Override
    public void setContentView(View view) {
        View base = LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        RelativeLayout relativeLayout = (RelativeLayout) base.findViewById(R.id.show_content);
        flowView = (FlowingDrawer) base.findViewById(R.id.flowingDrawer_base);
        flowing_content = (FrameLayout) base.findViewById(R.id.flowing_content);
        musicItemHolder = new MusicItemHolder(flowing_content);
        if (flowing_content == null) {
            throw new NullPointerException("侧滑栏初始失败!");
        }
        relativeLayout.addView(view);
        flowView.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        super.setContentView(base);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View content = LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(content);
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



//    public void musicClick(View view) {
//        switch (view.getId()) {
//            case R.id.before:
//                App.getInstance().getMusicServie().before();
//                break;
//            case R.id.playorpouse:
//                App.getInstance().getMusicServie().playOrPause();
//                break;
//            case R.id.next:
//                App.getInstance().getMusicServie().next();
//                break;
//        }
//    }


    //将侧滑设置成默认界面
    protected void setDefaultFlowingLayout() {
        isSetMySelfFlowingLayout = false;
        if (flowing_content != null) {
            flowing_content.removeAllViews();
            flowing_content.addView(LayoutInflater.from(this).inflate(R.layout.flowing_content_layout, null));
        } else {
            throw new NullPointerException("侧滑布局是空的,请确实是否初始化了布局!");
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

}
