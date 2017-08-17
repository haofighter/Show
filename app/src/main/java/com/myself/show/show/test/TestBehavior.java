package com.myself.show.show.test;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.myself.show.show.R;

/**
 * Created by Administrator on 2017/8/10.
 */

public class TestBehavior extends CoordinatorLayout.Behavior<View> {

    private float targetY = -1;

    public TestBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child,
                                       View directTargetChild, View target, int nestedScrollAxes) {
        if(targetY == -1){
            targetY = target.getY();
        }
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target,
                                  int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        float scrooY = targetY - Math.abs(target.getY());
        float scaleY = scrooY / targetY;
        Log.i("滑动的位置  ",targetY+"------"+target.getY()+" ===="+child.getHeight() +"滑动的位置"+ scaleY);
        child.setTranslationX(child.getWidth() * scaleY);
    }
}
