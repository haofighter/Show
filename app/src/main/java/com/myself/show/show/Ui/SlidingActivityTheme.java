package com.myself.show.show.Ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;

import com.myself.show.show.R;
import com.myself.show.show.base.ThemeBaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SlidingActivityTheme extends ThemeBaseActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding,null);

    }

    @OnClick(R.id.fab)
    public void onClick() {
        Log.i("点击了","点击了 ");
    }
}
