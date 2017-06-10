package com.myself.show.show.base;


import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.widget.FrameLayout;

import com.myself.show.show.R;
import com.myself.show.show.Tools.StatusBarUtil;
import com.myself.show.show.View.FlowingDraw.ElasticDrawer;
import com.myself.show.show.View.FlowingDraw.FlowingDrawer;
import com.myself.show.show.View.NavigationBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 基于部分界面公用部分第二级自定义标题栏的界面
 */
public class ThemeBaseActivity extends BaseActivity {

    @BindView(R.id.na_bar)
    protected NavigationBar na_bar;

    @BindView(R.id.flowingDrawer_base)
    public FlowingDrawer base;


    protected FrameLayout content_layout;
    private FrameLayout flowing_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
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

    public void  setFlowingShow(boolean show){
        if(show){
            base.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        }else{
            base.setTouchMode(ElasticDrawer.TOUCH_MODE_NONE);
        }

    }



    public void setContentView(View v) {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_theme_base, null);
        content_layout = (FrameLayout) view.findViewById(R.id.content_layout);
        flowing_content = (FrameLayout) view.findViewById(R.id.flowing_content);
        content_layout.addView(v);
        super.setContentView(view);
        ButterKnife.bind(this);
        setToolbarColor(-1);
        base.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        setActivityBar();
    }

    protected void setFlowingLayout(int layoutId) {
        View view = LayoutInflater.from(this).inflate(layoutId, null);
        setFlowingLayout(view);
    }

    protected void setFlowingLayout(View layout) {
        if (flowing_content != null) {
            flowing_content.addView(layout);
        } else {
            throw new NullPointerException("侧滑布局是空的,请确实是否初始化了布局!");
        }
    }


    //设置侧滑栏的颜色
    protected  void  setFLowingLayoutBackGround(int color){
        StatusBarUtil.changeViewColor(flowing_content, color);
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
        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.traslant));
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


    protected void startActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }


}

