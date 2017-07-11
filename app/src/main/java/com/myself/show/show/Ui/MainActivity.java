package com.myself.show.show.Ui;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myself.show.show.R;
import com.myself.show.show.Tools.StatusBarUtil;
import com.myself.show.show.Ui.home.HomeActivity;
import com.myself.show.show.Ui.imageCorrelation.GetCustomImageAcitivity;
import com.myself.show.show.Ui.music.activity.MusicActivity;
import com.myself.show.show.Ui.viewpage.ViewPageFragmentActivity;
import com.myself.show.show.Ui.viewpage1.ViewPageFragment1Activity;
import com.myself.show.show.base.ThemeBaseActivity;
import com.myself.show.show.customview.ShadowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends ThemeBaseActivity {

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
    @BindView(R.id.search)
    Button search;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main, null);
        ButterKnife.bind(this);
        na_bar.setTitle("标题栏");
        na_bar.setLeftBack(this);
        view_shadow.setIsShadowed(true);//是否显示阴影
//        ActivityManager.RunningServiceInfo();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.first, R.id.search, R.id.button, R.id.viewpage_test, R.id.image_control, R.id.vertical_viewpager})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first:
                startActivity(ViewPageFragmentActivity.class,ActivityChangeAnimal.left);
                break;
            case R.id.vertical_viewpager:
                startActivity(HomeActivity.class,ActivityChangeAnimal.left);
                break;
            case R.id.search:
                startActivity(MusicActivity.class,ActivityChangeAnimal.left);
                break;
            case R.id.button:
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

                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });
                    }
                });
                break;
            case R.id.viewpage_test:
                startActivity(ViewPageFragment1Activity.class,ActivityChangeAnimal.left);
                break;
            case R.id.image_control:
                startActivity(GetCustomImageAcitivity.class,ActivityChangeAnimal.left);
                break;
        }
    }

}
