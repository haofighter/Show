package com.myself.show.show.Ui;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.myself.show.show.R;
import com.myself.show.show.Tools.StatusBarUtil;
import com.myself.show.show.Ui.baiduMap.BaiduGuideActivity;
import com.myself.show.show.Ui.baiduMap.test.TestMapActivity;
import com.myself.show.show.Ui.download.DownloadMoreActivity;
import com.myself.show.show.Ui.home.HomeActivity;
import com.myself.show.show.Ui.imageCorrelation.GetCustomImageAcitivity;
import com.myself.show.show.Ui.music.activity.MusicActivity;
import com.myself.show.show.Ui.viewpage.ViewPageFragmentActivity;
import com.myself.show.show.base.ThemeBaseActivity;
import com.myself.show.show.customview.ShadowLayout;
import com.myself.show.show.net.RetrofitManager;
import com.myself.show.show.net.Service;
import com.myself.show.show.net.responceBean.BaseResponse;
import com.myself.show.show.net.upload.FileRequestBody;
import com.myself.show.show.net.download.FileResponseBody;
import com.myself.show.show.net.upload.FileSubscribe;
import com.myself.show.show.net.download.ProgressListener;
import com.myself.show.show.test.TestBehaviorActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends ThemeBaseActivity {

    @BindView(R.id.view_shadow)
    ShadowLayout view_shadow;
    @BindView(R.id.button)
    View button;
    @BindView(R.id.show_layout)
    LinearLayout showLayout;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    @BindView(R.id.first)
    TextView first;
    @BindView(R.id.search)
    Button search;
    @BindView(R.id.upload_progress)
    ProgressBar uploadProgress;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main, null);
        ButterKnife.bind(this);
        na_bar.setTitle("标题栏");
        na_bar.setLeftBack(this);
        view_shadow.setIsShadowed(true);//是否显示阴影
//        ActivityManager.RunningServiceInfo();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
    }

    String picPath = Environment.getExternalStorageDirectory() + "/test.jpg";

    @OnClick({R.id.pop_map,R.id.guide_test, R.id.guide, R.id.behavior_test, R.id.downloadmore, R.id.download1, R.id.first, R.id.search, R.id.button, R.id.viewpage_test, R.id.image_control, R.id.vertical_viewpager, R.id.upload, R.id.upload_more, R.id.download})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first:
                startActivity(ViewPageFragmentActivity.class);
                break;
            case R.id.behavior_test:
                startActivity(TestBehaviorActivity.class);
                break;
            case R.id.vertical_viewpager:
                startActivity(HomeActivity.class);
                break;
            case R.id.search:
                startActivity(MusicActivity.class);
                break;
            case R.id.button:
                button.post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        System.out.println("图片各个角Left：" + content_layout.getLeft() + "Right：" + content_layout.getRight() + "Top：" + content_layout.getTop() + "Bottom：" + content_layout.getBottom());
                        System.out.println("图片各个角Left：" + content_layout.getWidth() + "========" + content_layout.getHeight());
                        StatusBarUtil.setAnimal(button, 2000l, false, new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {

                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });
                    }
                });
                break;
            case R.id.viewpage_test:
                startActivity(OrderMuneActivity.class);
                break;
            case R.id.image_control:
                startActivity(GetCustomImageAcitivity.class);
                break;
            case R.id.upload://上传单个文件
                File file1 = new File(picPath);
                FileRequestBody fileRequestMore = new FileRequestBody(file1, fileSubscribeSingle);
                MultipartBody.Part part1 = MultipartBody.Part.createFormData("file", file1.getName(), fileRequestMore);
                RetrofitManager.builder(Service.class).uploadFile1(part1).subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(fileSubscribeSingle);
                break;
            case R.id.upload_more://批量传
                File file = new File(picPath);
                Map<String, RequestBody> requestBodyMap = new HashMap<>();
                FileRequestBody fileRequestBody = new FileRequestBody(file, new FileSubscribe() {
                    @Override
                    public void onLoading(long total, long progress) {
                        super.onLoading(total, progress);
                    }
                });
                requestBodyMap.put("file\"; filename=\"1" + file.getName(), fileRequestBody);
                FileRequestBody fileRequestBody1 = new FileRequestBody(file, new FileSubscribe() {
                    @Override
                    public void onLoading(long total, long progress) {
                        Log.i("1", "上传的进度" + total + "==" + progress);
                    }
                });
                requestBodyMap.put("file\"; filename=\"2" + file.getName(), fileRequestBody1);
                FileRequestBody fileRequestBody2 = new FileRequestBody(file, new FileSubscribe() {
                    @Override
                    public void onLoading(long total, long progress) {
                        Log.i("2", "上传的进度" + total + "==" + progress);
                    }
                });
                requestBodyMap.put("file\"; filename=\"3" + file.getName(), fileRequestBody2);
                FileRequestBody fileRequestBody3 = new FileRequestBody(file, new FileSubscribe() {
                    @Override
                    public void onLoading(long total, long progress) {
                        Log.i("3", "上传的进度" + total + "==" + progress);
                    }
                });
                requestBodyMap.put("file\"; filename=\"4" + file.getName(), fileRequestBody3);
                FileRequestBody fileRequestBody4 = new FileRequestBody(file, new FileSubscribe() {
                    @Override
                    public void onLoading(long total, long progress) {
                        Log.i("4", "上传的进度" + total + "==" + progress);
                    }
                });
                requestBodyMap.put("file\"; filename=\"5" + file.getName(), fileRequestBody4);

                RetrofitManager.builder(Service.class).uploadFileInfo(requestBodyMap).subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {

                            }
                        });
                break;
            case R.id.download:
                RetrofitManager.builder(Service.class, new FileSubscribe() {
                    @Override
                    public void onLoading(long total, long progress) {
                        Log.i("4", "上传的进度" + total + "==" + progress);
                    }
                }, "http://msoftdl.360.cn")
                        .downloadFile()
                        .subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                Log.i("", "完成");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i("", "完成" + e.toString());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                Log.i("", "完成" + responseBody.contentLength());
                            }
                        });

                break;
            case R.id.download1:
                Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("http://msoftdl.360.cn");

                OkHttpClient.Builder builder = new OkHttpClient.Builder();

                //添加拦截器，自定义ResponseBody，添加下载进度
                builder.networkInterceptors().add(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder().body(
                                new FileResponseBody(originalResponse.body(), new ProgressListener() {
                                    @Override
                                    public void onProgress(long progress, long total, boolean done) {
                                        Log.i("下载的进度", "progress:" + progress + "    total:" + total + "      done:" + done);
                                    }
                                }))
                                .build();

                    }
                });

                Service retrofit = retrofitBuilder
                        .client(builder.build())
                        .build().create(Service.class);

                Call<ResponseBody> call = retrofit.retrofitDownload();
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        writeResponseBodyToDisk(response.body());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                break;
            case R.id.downloadmore:
                startActivity(DownloadMoreActivity.class);
                break;
            case R.id.guide:
                startActivity(BaiduGuideActivity.class);
                break;
            case R.id.guide_test:
                startActivity(TestMapActivity.class);
                break;
            case R.id.pop_map:
                initMapWindow();
                break;
        }

    }



    // 地图弹出框
    private void initMapWindow() {
        PopupWindow myPopWindow;
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        // POP的布局
        View layout = inflater.inflate(R.layout.map_pop_show, null);
        MapView mapView = (MapView) layout.findViewById(R.id.map);

        myPopWindow = new PopupWindow(layout);
        myPopWindow.setFocusable(true); // 加上这个popupwindow中的ListView才可以接收点击事件
        // 控制popupwindow 的宽度和高度
        myPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        myPopWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        // 控制popupwindow 点击屏幕其他地方消失
        myPopWindow.setBackgroundDrawable(this.getResources().getDrawable(
                R.color.traslant)); // 设置背景图片,不能在布局中设置，要通过代码来设置
        myPopWindow.setOutsideTouchable(true); // 触摸popupwindow
        myPopWindow.showAsDropDown(na_bar);
    }


    private boolean writeResponseBodyToDisk(ResponseBody body) {
        File futureStudioIconFile = new File(Environment.getExternalStorageDirectory() + "/123.MP3");
        if (!futureStudioIconFile.exists()) {
            try {
                futureStudioIconFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Log.i("6464", "body=" + body.contentLength() + "fileSizeDownloaded:" + fileSizeDownloaded + " 进度:" + fileSizeDownloaded * 100 / body.contentLength());
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }

    }


    FileSubscribe<BaseResponse> fileSubscribeSingle = new FileSubscribe<BaseResponse>() {
        @Override
        public void onLoading(long total, long progress) {
            super.onLoading(total, progress);
            uploadProgress.setProgress((int) (progress * 100 / total));
            Log.i("有木有", "total==" + total + "-------progress===" + progress);
        }

        @Override
        public void onStart() {
            Log.i("有木有", "开始了");
            super.onStart();
        }

        @Override
        public void onCompleted() {
            Log.i("有木有", "完成了");
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            Log.i("有木有", "出错了" + e.getMessage());
            super.onError(e);
        }

        @Override
        public void onNext(BaseResponse o) {
            Log.i("有木有", "请求完成" + o.toString());
            super.onNext(o);
        }
    };


}
