package com.myself.show.show.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017/7/27.
 */

public class MyViewGroup extends LinearLayout {


    public MyViewGroup(Context context) {
        this(context, null);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getSystemScareen();//获取到系统屏幕的宽高
    }

//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        int viewl = 0;
//        int viewt = 0;
//        int viewr = 0;
//        int viewb = 0;
//        for (int index = 0; index < getChildCount(); index++) {
//            View v = getChildAt(index);
//            Log.i("绘制", "l=" + l + "  t=" + t + "  r=" + r + "   b=" + b);
//            viewl += l;
//            viewt += t;
//            viewr += r;
//            viewb += b;
//            v.layout(viewl, viewt, viewr, viewb);
//        }
//    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int cWidth = 0;
        int cHeight = 0;
        LayoutParams cParams = null;


        int measureHight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (LayoutParams) childView.getLayoutParams();

            if (cParams.height == LayoutParams.MATCH_PARENT) {
                cHeight = systemHight;
            }
            measureHight = +cHeight;
        }
        setMeasuredDimension(sizeWidth, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight : measureHight);

//        for(int index = 0; index < getChildCount(); index++){
//
//            child.measure(MeasureSpec.makeMeasureSpec(childSize, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(childSize, MeasureSpec.AT_MOST));
//        }
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    int systemWidth;
    int systemHight;


    public int getViewHight() {
        int allHight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            allHight += getChildAt(i).getHeight();
        }
        return allHight;
    }

    public void getSystemScareen() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        systemWidth = wm.getDefaultDisplay().getWidth();
        systemHight = wm.getDefaultDisplay().getHeight();
    }

    int downY;
    int moveY;
    int scollY;
    boolean isScoll;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                isScoll = true;
                Log.i("触摸", "移动了" + "  moveY:" + moveY + "     systemHight:" + systemHight + "  getViewHight()" + getViewHight());
                if (moveY + systemHight <= getViewHight()) {
                    scollY = -(int) (event.getY() - downY + moveY - 1);
                    scrollTo(0, scollY < 0 ? 0 : scollY);
                    Log.i("触摸", "移动了" + "手指位置:" + event.getY() + "         按下位置:" + downY + "      移动过得位置=" + moveY);
                    isScoll = true;
                } else {
                    Log.i("触摸", "滑动到了底部");
                    isScoll = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isScoll) {
                    moveY += (event.getY() - downY);
                } else {
                    if (scollY < 0) {
                        moveY = 0;
                    } else {
                        moveY = getViewHight() - systemHight;
                    }
                }
                break;
        }
        return true;
    }


}
