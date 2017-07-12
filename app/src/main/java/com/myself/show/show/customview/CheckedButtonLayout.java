package com.myself.show.show.customview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
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
        super(context);
        init();
    }

    public CheckedButtonLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        init();
    }

    public CheckedButtonLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        initAttrs(attrs);
        init();
    }

    public void initAttrs(AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CheckedButtonLayout);
        maxChecked = typedArray.getInteger(R.styleable.CheckedButtonLayout_maxCheck, 1);
    }

    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int checkedNum = 0;
            for (int i = 0; i < checkBoxs.size(); i++) {
                if (checkBoxs.get(i).isChecked()) {
                    checkedNum++;
                }
            }
            Log.e("==============","选中的button"+v.getTag());
            if (checkedNum > maxChecked) {
                ToastUtils.showMessage("最多选中"+maxChecked+"个选项");
                return;
            }
        }
    };

    List<View> childView = new ArrayList<>();
    List<CheckBox> checkBoxs = new ArrayList<>();

    private void init() {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setTag(i);
            childView.add(getChildAt(i));
            if (getChildAt(i) instanceof CheckBox) {
                checkBoxs.add((CheckBox)getChildAt(i));
                getChildAt(i).setOnClickListener(onClickListener);
            }
        }
    }
}
