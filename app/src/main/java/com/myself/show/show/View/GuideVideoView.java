package com.myself.show.show.View;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.VideoView;

/**
 * Created by Administrator on 2017/4/12 0012.
 */

public class GuideVideoView extends VideoView {
    public GuideVideoView(Context context) {
        super(context);
    }

    public GuideVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GuideVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 重新计算宽高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //我们重新计算高度
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    public void setOnPreparedListener(MediaPlayer.OnPreparedListener l) {
        super.setOnPreparedListener(l);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
