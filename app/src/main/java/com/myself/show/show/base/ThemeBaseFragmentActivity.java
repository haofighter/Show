package com.myself.show.show.base;


import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.FrameLayout;

import com.myself.show.show.R;
import com.myself.show.show.Tools.StatusBarUtil;
import com.myself.show.show.View.NavigationBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 基于部分界面公用部分第二级自定义标题栏的界面
 */
public class ThemeBaseFragmentActivity extends BaseFragmentActivity {

    @BindView(R.id.na_bar)
    protected NavigationBar na_bar;

    protected FrameLayout content_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        List<ActivityManager.RunningServiceInfo> runningServiceInfos = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE);
//
//        for (int i = 0; i < runningServiceInfos.size(); i++) {
//            Log.i("正在运行的服务", runningServiceInfos.get(i).service.getPackageName()+"========"+getApplicationContext().getPackageName());
//            if(runningServiceInfos.get(i).service.getPackageName().equals(getApplicationContext().getPackageName())){
//                Log.i("============", runningServiceInfos.get(i).service.toString());
//            }
//
//        }
    }


    /**
     * 设置状态栏的样式
     */
    protected void setActivityBar() {
        switch (activityBarType) {
            case normal:

                break;
            case all_none:
                StatusBarUtil.setStateBarShow(false, this);
                na_bar.setVisibility(View.GONE);
                break;
            case none_state:
                StatusBarUtil.setStateBarShow(false, this);
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


    public void setContentView(View v) {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_theme_base, null);
        content_layout = (FrameLayout) view.findViewById(R.id.content_layout);
        content_layout.addView(v);
        super.setContentView(view);
        ButterKnife.bind(this);
        setToolbarColor(-1);
        setActivityBar();
    }


    protected void setContentView(int layoutResID, ActivityBarType activityBarType) {
        if (activityBarType != null)
            this.activityBarType = activityBarType;
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(view);
    }

    //状态栏的分类
    public enum ActivityBarType {
        normal,//正常
        all_none,//无状态栏 ,无标题
        none_state,//无状态栏
        none_na,//无标题栏
        steep_srate_none_na,//状态栏沉浸 无标题
        steep_srate_have_na,//状态栏沉浸 有标题
    }


    private ActivityBarType activityBarType = ActivityBarType.normal;


    /**
     * 设置状态栏沉浸
     */
    protected void setStatuBarSteep() {
        na_bar.setPadding(0, StatusBarUtil.getStatusBarHight(this), 0, 0);
        StatusBarUtil.setTranslucent(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.traslant));
    }


    //设置导航栏的颜色
    protected void setToolbarColor(int color) {
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
    protected void setAnimal(View v, Activity activity) {
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
    protected void setAnimal(View v, int width, int height, float radius, float endrRadius, int time) {
        Animator animator = ViewAnimationUtils.createCircularReveal(v, width, height, radius, endrRadius);
        animator.setDuration(time);
        animator.start();
    }




}

