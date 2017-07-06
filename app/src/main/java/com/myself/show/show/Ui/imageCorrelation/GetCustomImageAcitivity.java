package com.myself.show.show.Ui.imageCorrelation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.myself.show.show.R;

import net.qiujuer.genius.blur.StackBlur;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GetCustomImageAcitivity extends AppCompatActivity {

    @BindView(R.id.get_image)
    TextView getImage;
    @BindView(R.id.image_show)
    ImageView imageShow;
    @BindView(R.id.image_custon)
    ImageView imageCuston;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_custom_image_acitivity);
        ButterKnife.bind(this);
        imageShow.setImageResource(R.mipmap.img_head);
    }

    @OnClick(R.id.get_image)
    public void onClick() {
        imageCuston.setImageBitmap(StackBlur.blurNativelyPixels(BitmapFactory.decodeResource(getResources() ,R.mipmap.img_head), 20, false));
    }
}
