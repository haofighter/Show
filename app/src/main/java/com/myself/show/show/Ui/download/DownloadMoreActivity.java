package com.myself.show.show.Ui.download;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.myself.show.show.R;
import com.myself.show.show.base.BackCall;
import com.myself.show.show.net.download.DownloadManager;
import com.myself.show.show.net.downloaddemo.DownInfo;
import com.myself.show.show.net.downloaddemo.DownLoadListener.HttpProgressOnNextListener;
import com.myself.show.show.net.downloaddemo.HttpDownManager;
import com.myself.show.show.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DownloadMoreActivity extends AppCompatActivity {

    @BindView(R.id.search_result_show)
    RecyclerView searchResultShow;
    @BindView(R.id.upload_progress)
    ProgressBar uploadProgress;
    @BindView(R.id.state)
    TextView state;
    private DownloadAdapter downloadAdapter;
    private DownloadManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_more);
        ButterKnife.bind(this);
        manager = DownloadManager.getInstance();
        init();
        initDate();
    }

    List<DownInfo> listData;

    private void initDate() {
        listData = new ArrayList<>();
        String[] downUrl = new String[]{"http://msoftdl.360.cn/mobilesafe/shouji360/360safesis/360MobileSafe_6.2.3.1060.apk","http://msoftdl.360.cn/mobilesafe/shouji360/360safesis/360MobileSafe_6.2.3.1060.apk",
                "http://msoftdl.360.cn/mobilesafe/shouji360/360safesis/360MobileSafe_6.2.3.1060.apk"};
        for (int i = 0; i < downUrl.length; i++) {
            File outputFile = new File(Environment.getExternalStorageDirectory(),
                    "test" + i + ".apk");
            DownInfo apkApi = new DownInfo(downUrl[i]);
            apkApi.setSavePath(outputFile.getAbsolutePath());
//            apkApi.setListener(httpProgressOnNextListener);
            listData.add(apkApi);
        }
        downloadAdapter.setDate(listData);
    }

    private void init() {
        //设置布局管理器
        searchResultShow.setLayoutManager(new LinearLayoutManager(this));
        //设置增加或删除条目的动画
        searchResultShow.setItemAnimator(new DefaultItemAnimator());
        downloadAdapter = new DownloadAdapter(this, new BackCall() {
            @Override
            public void backCall(int tag, Object... obj) {

            }
        });
        searchResultShow.setAdapter(downloadAdapter);
    }

//    @OnClick({R.id.down, R.id.pause, R.id.state})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.down:
//                manager.startDown(listData.get(0));
//                break;
//            case R.id.pause:
//                manager.pause(listData.get(0));
//                break;
//            case R.id.state:
//                break;
//        }
//    }
//
//
//    /*下载回调*/
//    HttpProgressOnNextListener<DownInfo> httpProgressOnNextListener = new HttpProgressOnNextListener<DownInfo>() {
//        @Override
//        public void onNext(DownInfo baseDownEntity) {
//            ToastUtils.showMessage(baseDownEntity.getSavePath());
//        }
//
//        @Override
//        public void onStart() {
//            state.setText("提示:开始下载");
//        }
//
//        @Override
//        public void onComplete() {
//            state.setText("提示：下载完成");
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            super.onError(e);
//            state.setText("失败:" + e.toString());
//        }
//
//
//        @Override
//        public void onPuase() {
//            super.onPuase();
//            state.setText("提示:暂停");
//        }
//
//        @Override
//        public void onStop() {
//            super.onStop();
//        }
//
//        @Override
//        public void updateProgress(long readLength, long countLength) {
//            state.setText("提示:下载中");
//            uploadProgress.setMax((int) countLength);
//            uploadProgress.setProgress((int) readLength);
//        }
//    };

}
