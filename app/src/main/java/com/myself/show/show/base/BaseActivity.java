package com.myself.show.show.base;


import android.animation.Animator;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.widget.FrameLayout;

import com.myself.show.show.R;
import com.myself.show.show.Tools.StatusBarUtil;
import com.myself.show.show.View.NavigationBar;
import com.myself.show.show.ViewUtil.FlowingDraw.FlowingDrawer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    @BindView(R.id.na_bar)
    protected NavigationBar na_bar;

    @BindView(R.id.base)
    public FlowingDrawer base;


    protected FrameLayout content_layout;
    private FrameLayout flowing_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public void setContentView(View v) {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        content_layout = (FrameLayout) view.findViewById(R.id.content_layout);
        flowing_content = (FrameLayout) view.findViewById(R.id.flowing_content);
        content_layout.addView(v);
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    protected enum ActivityBarType {
        normal,//正常
        all_none,//无状态栏 ,无标题
        none_state,//无状态栏
        none_na,//无标题栏
        steep_srate_none_na,//状态栏沉浸 无标题
        steep_srate_have_na,//状态栏沉浸 有标题
    }

    public void setContentView(int id, ActivityBarType activityBarType) {
        View content = LayoutInflater.from(this).inflate(id, null);
        setContentView(content);
        setToolbarColor(-1);
        switch (activityBarType) {
            case normal:

                break;
            case all_none:
                StatusBarUtil.setStateBarShow(false,this);
                na_bar.setVisibility(View.GONE);
                break;
            case none_state:
                StatusBarUtil.setStateBarShow(false,this);
                break;
            case none_na:
                na_bar.setVisibility(View.GONE);
                break;
            case steep_srate_none_na:
                setStatuBarSteep();
                na_bar.setVisibility(View.GONE);
                break;
            case steep_srate_have_na:
                setStatuBarSteep();
                break;
            default:
                break;
        }

    }

    public void setContentView(int id) {
        setContentView(id, ActivityBarType.normal);
    }

    /**
     * 设置状态栏沉浸
     */
    public void  setStatuBarSteep(){
        na_bar.setPadding(0,StatusBarUtil.getStatusBarHight(this),0,0 );
        StatusBarUtil.setTranslucent(this);
    }




    public void setToolbarColor(int color) {
        if (color != -1) {
            AppConstant.AppBackColor = color;
        }
        if (AppConstant.AppBackColor == -1) {
            StatusBarUtil.changeViewColor(na_bar, ContextCompat.getColor(this, R.color.app));
            return;
        }
        StatusBarUtil.changeViewColor(na_bar, AppConstant.AppBackColor);
    }



    /**
     * 设置揭示动画
     *
     * @param v        揭示动画效果的VIEW
     * @param activity 用于获取屏幕宽高
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setAnimal(View v, Activity activity) {
        final int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        final int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        final float radius = (float) Math.sqrt(width * width + height * height) / 2;//半径
        setAnimal(v, width / 2, height / 2
                , radius, 0, 2000);
    }

    /**
     * @param v          揭示动画效果的VIEW
     * @param width      揭示效果的宽
     * @param height     揭示效果的高
     * @param radius     揭示动画圆的起始半径
     * @param endrRadius 揭示动画圆的结束半径
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setAnimal(View v, int width, int height, float radius, float endrRadius, int time) {
        Animator animator = ViewAnimationUtils.createCircularReveal(v, width, height, radius, endrRadius);
        animator.setDuration(time);
        animator.start();
    }

}

