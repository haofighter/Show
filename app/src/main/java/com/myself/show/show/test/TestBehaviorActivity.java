package com.myself.show.show.test;

import android.os.Bundle;

import com.myself.show.show.R;
import com.myself.show.show.base.ThemeBaseActivity;

/**
 * Created by Administrator on 2017/8/10.
 */

public class TestBehaviorActivity extends ThemeBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_behavior_layout,ActivityBarType.none_na);
    }

    @Override
    public int getContentView() {
        return 0;
    }


}
