package com.myself.show.show.Ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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
    CheckBox textBold;
    @BindView(R.id.text_italic)
    CheckBox textItalic;
    @BindView(R.id.text_strikethrough)
    CheckBox textStrikethrough;
    @BindView(R.id.text_blockquote)
    CheckBox textBlockquote;
    @BindView(R.id.text_h1)
    CheckBox textH1;
    @BindView(R.id.text_h2)
    CheckBox textH2;
    @BindView(R.id.text_h3)
    CheckBox textH3;
    @BindView(R.id.text_h4)
    CheckBox textH4;
    @BindView(R.id.ll_layout_font)
    LinearLayout llLayoutFont;
    @BindView(R.id.add_image)
    ImageView addImage;
    @BindView(R.id.add_link)
    ImageView addLink;
    @BindView(R.id.add_split)
    ImageView addSplit;
    @BindView(R.id.ll_layout_add)
    LinearLayout llLayoutAdd;
    @BindView(R.id.action_undo)
    ImageView actionUndo;
    @BindView(R.id.action_redo)
    ImageView actionRedo;
    @BindView(R.id.action_font)
    ImageView actionFont;
    @BindView(R.id.action_add)
    ImageView actionAdd;
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
                    flag1 = true;
                    isBold = true;
                } else {
                    flag1 = false;
                    isBold = false;
                }

                if (types.contains(RichEditor.Type.ITALIC)) {
                    flag2 = true;
                    isItalic = true;
                } else {
                    flag2 = false;
                    isItalic = false;
                }

                if (types.contains(RichEditor.Type.STRIKETHROUGH)) {
                    flag3 = true;
                    isStrikeThrough = true;
                } else {
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
                } else {
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
                } else {
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
                } else {
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
                } else {
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
                } else {
                    flag8 = false;
                    isclick = false;
                }
            }
        });
    }

    public final static int RICH_IMAGE_CODE = 0x33;

    @OnClick({R.id.text_bold, R.id.text_italic, R.id.text_strikethrough, R.id.text_blockquote, R.id.text_h1, R.id.text_h2, R.id.text_h3, R.id.text_h4, R.id.ll_layout_font, R.id.add_image, R.id.add_link, R.id.add_split, R.id.ll_layout_add, R.id.action_undo, R.id.action_redo, R.id.action_font, R.id.action_add, R.id.ll_layout_editor})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_bold: //粗体
                if (flag1) {
                    flag1 = false;
                    isBold = false;
                } else {
                    flag1 = true;
                    isBold = true;
                }
                editor.setBold();
                break;
            case R.id.text_italic: //斜体
                if (flag2) {
                    flag2 = false;
                    isItalic = false;
                } else {
                    flag2 = true;
                    isItalic = true;
                }
                editor.setItalic();
                break;
            case R.id.text_strikethrough://删除线
                if (flag3) {
                    flag3 = false;
                    isStrikeThrough = false;
                } else {
                    flag3 = true;
                    isStrikeThrough = true;
                }
                editor.setStrikeThrough();
                break;
            case R.id.text_blockquote://块引用
                if (flag4) {
                    flag4 = false;
                    isclick = false;
                } else {
                    flag4 = true;
                    flag5 = false;
                    flag6 = false;
                    flag7 = false;
                    flag8 = false;
                    isclick = true;
                }
                Log.e("BlockQuote", "isItalic:" + isItalic + "，isBold：" + isBold + "，isStrikeThrough:" + isStrikeThrough);
                editor.setBlockquote(isclick, isItalic, isBold, isStrikeThrough);
                break;
            case R.id.text_h1://H1字体
                if (flag5) {
                    flag5 = false;
                    isclick = false;

                    //使加粗灰显并去除效果
                    flag1 = false;
                    isBold = false;
                } else {
                    flag4 = false;
                    flag5 = true;
                    flag6 = false;
                    flag7 = false;
                    flag8 = false;
                    isclick = true;
                }
                editor.setHeading(1, isclick, isItalic, isBold, isStrikeThrough);
                break;
            case R.id.text_h2://H2字体
                if (flag6) {
                    flag6 = false;
                    isclick = false;

                    //使加粗灰显并去除效果
                    flag1 = false;
                    isBold = false;
                } else {
                    flag4 = false;
                    flag5 = false;
                    flag6 = true;
                    flag7 = false;
                    flag8 = false;

                    isclick = true;
                }
                editor.setHeading(2, isclick, isItalic, isBold, isStrikeThrough);
                break;
            case R.id.text_h3://H3字体
                if (flag7) {
                    flag7 = false;
                    isclick = false;

                    //使加粗灰显并去除效果
                    flag1 = false;
                    isBold = false;
                } else {
                    flag4 = false;
                    flag5 = false;
                    flag6 = false;
                    flag7 = true;
                    flag8 = false;
                    isclick = true;
                }
                editor.setHeading(3, isclick, isItalic, isBold, isStrikeThrough);
                break;
            case R.id.text_h4://H4字体
                if (flag8) {
                    flag8 = false;
                    isclick = false;

                    //使加粗灰显并去除效果
                    flag1 = false;
                    isBold = false;
                } else {
                    flag4 = false;
                    flag5 = false;
                    flag6 = false;
                    flag7 = false;
                    flag8 = true;
                    isclick = true;
                }
                editor.setHeading(4, isclick, isItalic, isBold, isStrikeThrough);
                break;
            case R.id.ll_layout_font:
                break;
            case R.id.add_image://插入图片
                Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picture, RICH_IMAGE_CODE);
                break;
            case R.id.add_link:
                showInsertLinkDialog();
                break;
            case R.id.add_split:
                editor.insertHr();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RICH_IMAGE_CODE && resultCode == Activity.RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String picturePath = c.getString(columnIndex);
            Log.i("dgs", "picturePath----" + picturePath);
            //插入图片
            editor.insertImage(picturePath, "图片");
            c.close();
            //获取图片并显示

        }


//        switch (requestCode) {
//            case RICH_IMAGE_CODE://富文本选择图片
//                if (data != null) {
//                    richpImage = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
//                }
//                if (richpImage != null) {
//                    showDialog("图片加载中。。。");
//                    requestYPU(richpImage.get(0));
//                }
//                break;
//        }
    }


    private AlertDialog linkDialog;
    /**
     * 插入链接Dialog
     */
    private void showInsertLinkDialog() {

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        linkDialog = adb.create();

        View view = getLayoutInflater().inflate(R.layout.dialog_insertlink, null);

        final EditText et_link_address = (EditText) view.findViewById(R.id.et_link_address);
        final EditText et_link_title = (EditText) view.findViewById(R.id.et_link_title);

        Editable etext = et_link_address.getText();
        Selection.setSelection(etext, etext.length());

        //点击确实的监听
        view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String linkAddress = et_link_address.getText().toString();
                String linkTitle = et_link_title.getText().toString();

                if (linkAddress.endsWith("http://") || TextUtils.isEmpty(linkAddress)) {
                    Toast.makeText(AddNoteActivity.this, "请输入超链接地址", Toast.LENGTH_SHORT);
                } else if (TextUtils.isEmpty(linkTitle)) {
                    Toast.makeText(AddNoteActivity.this, "请输入超链接标题", Toast.LENGTH_SHORT);
                } else {
                    editor.insertLink(linkAddress, linkTitle);
                    linkDialog.dismiss();
                }
            }
        });
        //点击取消的监听
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkDialog.dismiss();
            }
        });
        linkDialog.setCancelable(false);
        linkDialog.setView(view, 0, 0, 0, 0); // 设置 view
        linkDialog.show();
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
