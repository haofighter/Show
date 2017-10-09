package com.myself.show.show.camera;

import android.content.Intent;
import android.hardware.Camera;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.myself.show.show.R;

public class CameraTestActivity extends AppCompatActivity {

    private CameraPreview mPreview;
    private Button buttonCaptureVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);
        initCamera();
        findViewById(R.id.button_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.camera_preview, new SettingsFragment()).addToBackStack(null).commit();
            }
        });
        final ImageView mediaPreview = (ImageView) findViewById(R.id.media_preview);
        findViewById(R.id.button_capture_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreview.takePicture(mediaPreview);
            }
        });
        buttonCaptureVideo = (Button) findViewById(R.id.button_capture_video);
        buttonCaptureVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPreview.isRecording()) {
                    mPreview.stopRecording(mediaPreview);
                    buttonCaptureVideo.setText("录像");
                } else {
                    if (mPreview.startRecording()) {
                        buttonCaptureVideo.setText("停止");
                    }
                }
            }
        });
        mediaPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraTestActivity.this, ShowPhotoVideo.class);
                intent.setDataAndType(mPreview.getOutputMediaFileUri(), mPreview.getOutputMediaFileType());
                startActivityForResult(intent, 0);
            }
        });

        findViewById(R.id.zoom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoom = zoom + 10;
                Camera.Parameters parameters = mPreview.getCameraInstance().getParameters();
                parameters.setZoom(zoom);
                mPreview.getCameraInstance().setParameters(parameters);
            }
        });
    }

    int zoom = 0;

    private void initCamera() {
        mPreview = new CameraPreview(this);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        SettingsFragment.passCamera(mPreview.getCameraInstance());
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SettingsFragment.setDefault(PreferenceManager.getDefaultSharedPreferences(this));
        SettingsFragment.init(PreferenceManager.getDefaultSharedPreferences(this));
    }

    public void onPause() {
        super.onPause();
        mPreview = null;
    }

    public void onResume() {
        super.onResume();
        if (mPreview == null) {
            initCamera();
        }
    }

}
