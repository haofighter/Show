package com.myself.show.show.Ui;

import android.animation.Animator;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.myself.show.show.R;
import com.myself.show.show.Tools.StatusBarUtil;
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
import com.myself.show.show.net.upload.FileResponseBody;
import com.myself.show.show.net.upload.RetrofitCallback;
import com.myself.show.show.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.first, R.id.search, R.id.button, R.id.viewpage_test, R.id.image_control, R.id.vertical_viewpager, R.id.upload})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first:
                startActivity(ViewPageFragmentActivity.class);
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

                break;
            case R.id.image_control:
                startActivity(GetCustomImageAcitivity.class);
                break;
            case R.id.upload:
                RetrofitCallback<BaseResponse> callback = new RetrofitCallback<BaseResponse>() {
                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        //进度更新结束
                        ToastUtils.showMessage("失败");
                    }

                    @Override
                    public void onSuccess(Call<BaseResponse> call, Response<BaseResponse> response) {
                        //进度更新结束
                        ToastUtils.showMessage("成功");
                    }

                    @Override
                    public void onLoading(long total, long progress) {
                        ToastUtils.showMessage("上传===" + total + "==" + progress);
                        //此处进行进度更新
                        super.onLoading(total, progress);

                    }
                };

                File file = new File(Environment.getExternalStorageDirectory()+"/storage/emulated/0/Pictures/Screenshots/Screenshot_2016-07-28-09-26-14.jpeg");

                RequestBody body = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
                FileRequestBody fileRequestBody = new FileRequestBody(body, callback);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileRequestBody);
                Call<BaseResponse> call = getRetrofitService(callback).uploadFile(part);
                call.enqueue(callback);
                break;
        }

    }


    //从Sdk中获取文件
    private List<File> getFile(String str) {
        File mfile = new File(str);
        // 图片列表
        List<File> fileList = new ArrayList<File>();
//        // 得到sd卡内路径
//        String imagePath =
//                Environment.getExternalStorageDirectory().toString();
        // 得到该路径文件夹下所有的文件
        if (mfile.isDirectory()) {
            File[] files = mfile.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    getFile(files[i].getAbsolutePath());
                } else {
                    fileList.add(files[i]);
                }
            }
        } else {
            fileList.add(mfile);
        }
        ToastUtils.showMessage("fileList==="+fileList.size());
        return fileList;
    }

    // 检查扩展名，得到图片格式的文件
    private boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;

        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("gif")
                || FileEnd.equals("png") || FileEnd.equals("jpeg")
                || FileEnd.equals("bmp")) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }

        return isImageFile;
    }

    private <T> Service getRetrofitService(final RetrofitCallback<T> callback) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response response = chain.proceed(chain.request());
//将ResponseBody转换成我们需要的FileResponseBody
                return response.newBuilder().body(new FileResponseBody<T>(response.body(), callback)).build();
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://119.97.150.30/")
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        return service ;
    }
}
