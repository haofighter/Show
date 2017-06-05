package com.myself.show.show.ViewUtil;

import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.widget.TextView;

/**
 * Created by ljx on 2016/8/12.
 */
public class TextUtils {

    public void setSelectChangeColor(TextView tv, @ColorInt int color, int start, int end, String str) {
        if (start > str.length() - 1 || end > str.length() || start >= end || start < 0 || end < 0) {
        }
        SpannableString msp = new SpannableString(str);
        msp.setSpan(new BackgroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(msp);
    }

    public void setCenterLine(TextView tv) {
        tv.getPaint().setAntiAlias(true);//抗锯齿
        tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
    }

    public void setBottomLine(TextView tv) {
        tv.getPaint().setAntiAlias(true);//抗锯齿
        tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
    }

    public void deleteLine(TextView tv) {
        tv.getPaint().setFlags(0);
    }
}
