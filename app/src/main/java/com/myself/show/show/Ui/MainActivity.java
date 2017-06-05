package com.myself.show.show.Ui;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myself.show.show.R;
import com.myself.show.show.Tools.StatusBarUtil;
import com.myself.show.show.Ui.Login.LoginActivity;
import com.myself.show.show.base.BaseActivity;
import com.myself.show.show.customview.ShadowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {

    @BindView(R.id.view_shadow)
    ShadowLayout view_shadow;
    @BindView(R.id.button)
    View button;
    @BindView(R.id.show_layout)
    LinearLayout showLayout;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    @BindView(R.id.first)
    TextView first;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main,null);
        setFlowingLayout(R.layout.activity_login);
        setFLowingLayoutBackGround(ContextCompat.getColor(this,R.color.colorPrimaryDark));
        ButterKnife.bind(this);
        na_bar.setTitle("标题栏");
        na_bar.setLeftBack();
        view_shadow.setIsShadowed(true);//是否显示阴影

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {

        super.onResume();
    }

    @OnClick(R.id.button)
    public void onClick(View view) {
        button.post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                System.out.println("图片各个角Left：" + content_layout.getLeft() + "Right：" + content_layout.getRight() + "Top：" + content_layout.getTop() + "Bottom：" + content_layout.getBottom());
                System.out.println("图片各个角Left：" + content_layout.getWidth() + "========" + content_layout.getHeight());
                StatusBarUtil.setAnimal(button, 2000l, false, new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        startActivity(LoginActivity.class);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
//                StatusBarUtil.setAnimal(content_layout,2000l,false);
            }
        });

    }

    @OnClick(R.id.first)
    public void onClick() {
        startActivity(SlidingActivity.class);
    }
}
