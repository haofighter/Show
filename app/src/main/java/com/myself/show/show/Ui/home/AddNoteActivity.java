package com.myself.show.show.Ui.home;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.myself.show.show.R;
import com.myself.show.show.base.ThemeBaseActivity;
import com.myself.show.show.customview.richeditor.RichEditor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNoteActivity extends ThemeBaseActivity {

    @BindView(R.id.editor)
    RichEditor editor;
    @BindView(R.id.text_bold)
    ImageButton textBold;
    @BindView(R.id.text_italic)
    ImageButton textItalic;
    @BindView(R.id.text_strikethrough)
    ImageButton textStrikethrough;
    @BindView(R.id.text_blockquote)
    ImageButton textBlockquote;
    @BindView(R.id.text_h1)
    ImageButton textH1;
    @BindView(R.id.text_h2)
    ImageButton textH2;
    @BindView(R.id.text_h3)
    ImageButton textH3;
    @BindView(R.id.text_h4)
    ImageButton textH4;
    @BindView(R.id.ll_layout_font)
    LinearLayout llLayoutFont;
    @BindView(R.id.add_image)
    ImageButton addImage;
    @BindView(R.id.add_link)
    ImageButton addLink;
    @BindView(R.id.add_split)
    ImageButton addSplit;
    @BindView(R.id.ll_layout_add)
    LinearLayout llLayoutAdd;
    @BindView(R.id.action_undo)
    ImageButton actionUndo;
    @BindView(R.id.action_redo)
    ImageButton actionRedo;
    @BindView(R.id.action_font)
    ImageButton actionFont;
    @BindView(R.id.action_add)
    ImageButton actionAdd;
    @BindView(R.id.ll_layout_editor)
    LinearLayout llLayoutEditor;


    boolean isclick = true;
    boolean isItalic;//是否斜体
    boolean isBold;//是否加粗
    boolean isStrikeThrough;//是否有删除线
    //富文本图片保存的集合
    private ArrayList<String> selectedRichImage = new ArrayList<>();

    private boolean flag1, flag2, flag3, flag4, flag5, flag6, flag7, flag8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.bind(this);
        initNaBar();
        init();
    }

    private void initNaBar() {
        na_bar.setLeftBack(this);
        na_bar.setRightText("完成");
        na_bar.setRightTextColor(R.color.white);
    }

    public void init() {
        editor.setEditorFontSize(15);
        editor.setPadding(10, 10, 10, 50);
        editor.setPlaceholder("填写笔记内容");
        /**
         *获取点击出文本的标签类型
         */
        editor.setOnDecorationChangeListener(new RichEditor.OnDecorationStateListener() {
            @Override
            public void onStateChangeListener(String text, List<RichEditor.Type> types) {

                if (types.contains(RichEditor.Type.BOLD)) {
                    textBold.setImageResource(R.mipmap.bold_l);
                    flag1 = true;
                    isBold = true;
                } else {
                    textBold.setImageResource(R.mipmap.bold_d);
                    flag1 = false;
                    isBold = false;
                }

                if (types.contains(RichEditor.Type.ITALIC)) {
                    textItalic.setImageResource(R.mipmap.italic_l);
                    flag2 = true;
                    isItalic = true;
                } else {
                    textItalic.setImageResource(R.mipmap.italic_d);
                    flag2 = false;
                    isItalic = false;
                }

                if (types.contains(RichEditor.Type.STRIKETHROUGH)) {
                    textStrikethrough.setImageResource(R.mipmap.strikethrough_l);
                    flag3 = true;
                    isStrikeThrough = true;
                } else {
                    textStrikethrough.setImageResource(R.mipmap.strikethrough_d);
                    flag3 = false;
                    isStrikeThrough = false;
                }

                //块引用
                if (types.contains(RichEditor.Type.BLOCKQUOTE)) {
                    flag4 = true;
                    flag5 = false;
                    flag6 = false;
                    flag7 = false;
                    flag8 = false;
                    isclick = true;
                    textBlockquote.setImageResource(R.mipmap.blockquote_l);
                    textH1.setImageResource(R.mipmap.h1_d);
                    textH2.setImageResource(R.mipmap.h2_d);
                    textH3.setImageResource(R.mipmap.h3_d);
                    textH4.setImageResource(R.mipmap.h4_d);
                } else {
                    textBlockquote.setImageResource(R.mipmap.blockquote_d);
                    flag4 = false;
                    isclick = false;
                }


                if (types.contains(RichEditor.Type.H1)) {
                    flag4 = false;
                    flag5 = true;
                    flag6 = false;
                    flag7 = false;
                    flag8 = false;

                    isclick = true;
                    textBlockquote.setImageResource(R.mipmap.blockquote_d);
                    textH1.setImageResource(R.mipmap.h1_l);
                    textH2.setImageResource(R.mipmap.h2_d);
                    textH3.setImageResource(R.mipmap.h3_d);
                    textH4.setImageResource(R.mipmap.h4_d);
                } else {
                    textH1.setImageResource(R.mipmap.h1_d);
                    flag5 = false;
                    isclick = false;
                }

                if (types.contains(RichEditor.Type.H2)) {
                    flag4 = false;
                    flag5 = false;
                    flag6 = true;
                    flag7 = false;
                    flag8 = false;

                    isclick = true;
                    textBlockquote.setImageResource(R.mipmap.blockquote_d);
                    textH1.setImageResource(R.mipmap.h1_d);
                    textH2.setImageResource(R.mipmap.h2_l);
                    textH3.setImageResource(R.mipmap.h3_d);
                    textH4.setImageResource(R.mipmap.h4_d);
                } else {
                    textH2.setImageResource(R.mipmap.h2_d);
                    flag6 = false;
                    isclick = false;
                }

                if (types.contains(RichEditor.Type.H3)) {
                    flag4 = false;
                    flag5 = false;
                    flag6 = false;
                    flag7 = true;
                    flag8 = false;
                    isclick = true;
                    textBlockquote.setImageResource(R.mipmap.blockquote_d);
                    textH1.setImageResource(R.mipmap.h1_d);
                    textH2.setImageResource(R.mipmap.h2_d);
                    textH3.setImageResource(R.mipmap.h3_l);
                    textH4.setImageResource(R.mipmap.h4_d);
                } else {
                    textH4.setImageResource(R.mipmap.h3_d);
                    flag7 = false;
                    isclick = false;
                }

                if (types.contains(RichEditor.Type.H4)) {
                    flag4 = false;
                    flag5 = false;
                    flag6 = false;
                    flag7 = false;
                    flag8 = true;
                    isclick = true;
                    textBlockquote.setImageResource(R.mipmap.blockquote_d);
                    textH1.setImageResource(R.mipmap.h1_d);
                    textH2.setImageResource(R.mipmap.h2_d);
                    textH3.setImageResource(R.mipmap.h3_d);
                    textH4.setImageResource(R.mipmap.h4_l);
                } else {
                    textH4.setImageResource(R.mipmap.h4_d);
                    flag8 = false;
                    isclick = false;
                }
            }
        });
    }

    @OnClick({R.id.text_bold, R.id.text_italic, R.id.text_strikethrough, R.id.text_blockquote, R.id.text_h1, R.id.text_h2, R.id.text_h3, R.id.text_h4, R.id.ll_layout_font, R.id.add_image, R.id.add_link, R.id.add_split, R.id.ll_layout_add, R.id.action_undo, R.id.action_redo, R.id.action_font, R.id.action_add, R.id.ll_layout_editor})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_bold:
                break;
            case R.id.text_italic:
                break;
            case R.id.text_strikethrough:
                break;
            case R.id.text_blockquote:
                break;
            case R.id.text_h1:
                break;
            case R.id.text_h2:
                break;
            case R.id.text_h3:
                break;
            case R.id.text_h4:
                break;
            case R.id.ll_layout_font:
                break;
            case R.id.add_image:
                break;
            case R.id.add_link:
                break;
            case R.id.add_split:
                break;
            case R.id.ll_layout_add:
                break;
            case R.id.action_undo:
                editor.undo();
                break;
            case R.id.action_redo:
                editor.redo();
                break;
            case R.id.action_font:
                if (llLayoutFont.getVisibility() == View.VISIBLE) {
                    llLayoutFont.setVisibility(View.GONE);
                } else {
                    if (llLayoutAdd.getVisibility() == View.VISIBLE) {
                        llLayoutAdd.setVisibility(View.GONE);
                    }
                    llLayoutFont.setVisibility(View.VISIBLE);
                    startAnimation(llLayoutFont);
                }
                break;
            case R.id.action_add:
                break;
            case R.id.ll_layout_editor:
                break;
        }
    }

    // 执行动画效果
    public void startAnimation(View mView) {

        AlphaAnimation aa = new AlphaAnimation(0.4f, 1.0f); // 0完全透明 1 完全不透明
        // 以(0%,0.5%)为基准点，从0.5缩放至1
        ScaleAnimation sa = new ScaleAnimation(0.5f, 1, 0.5f, 1,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0.5f);

        // 添加至动画集合
        AnimationSet as = new AnimationSet(false);
        as.addAnimation(aa);
        as.addAnimation(sa);
        as.setDuration(500);
        // 执行动画
        mView.startAnimation(as);
    }
}
