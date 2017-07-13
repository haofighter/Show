package com.myself.show.show.customview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.myself.show.show.R;
import com.myself.show.show.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */

public class CheckedButtonLayout extends LinearLayout {

    private int maxChecked;
    Context context;

    public CheckedButtonLayout(Context context) {
        this(context, null);

    }

    public CheckedButtonLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        initAttrs(attrs);

    }

    public CheckedButtonLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(attrs);
    }

    public void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CheckedButtonLayout);
        maxChecked = typedArray.getInteger(R.styleable.CheckedButtonLayout_maxCheck, 1);
    }

    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            checkedBoxs.clear();
            for (int i = 0; i < checkBoxs.size(); i++) {
                if (checkBoxs.get(i).isChecked()) {
                    if(checkedBoxs.contains(checkBoxs.get(i))){
                        checkedBoxs.remove(checkBoxs.get(i));
                    }
                    checkedBoxs.add(checkBoxs.get(i));
                }
            }

            if (((CheckBox) v).isChecked()) {
                checkedBoxs.remove(v);
            }

            Log.i("=====", checkedBoxs.size() + "=====maxChecked" + maxChecked);

            if (checkedBoxs.size() >= maxChecked) {
                if (checkedBoxs.size() > 0) {
                    checkedBoxs.get(0).setChecked(false);
                    checkedBoxs.remove(0);
                }
                checkedBoxs.add((CheckedBox) v);
            } else {
                checkedBoxs.add((CheckedBox) v);
            }

        }
    };

    List<View> childView = new ArrayList<>();
    List<CheckedBox> checkBoxs = new ArrayList<>();//所有的checkbox
    List<CheckedBox> checkedBoxs = new ArrayList<>();//选中的


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
    }

    private void init() {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setTag(i);
            childView.add(getChildAt(i));
            if (getChildAt(i) instanceof CheckedBox) {
                checkBoxs.add((CheckedBox) getChildAt(i));
                getChildAt(i).setOnClickListener(onClickListener);
            }
        }
    }
}
