package com.myself.show.show.net;


import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.myself.show.show.net.responceBean.BaseResponse;
import com.myself.show.show.net.responceBean.HangYeBean;
import com.myself.show.show.net.responceBean.LoginResponse;
import com.myself.show.show.net.responceBean.response;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by laucherish on 16/3/15.
 */
public class RetrofitManager {

    public static final String BASE_ZHIHU_URL = "http://192.168.11.216:8090/";
    private static OkHttpClient mOkHttpClient;
    private final Service mService;

    //    private  String  token;
    public static RetrofitManager builder(Context context) {
        return new RetrofitManager(context);
    }

    private RetrofitManager(Context context) {
//        token= PreferenceUtils.getString(context, Constants.TOKEN);
        initOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_ZHIHU_URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = retrofit.create(Service.class);
    }

    private void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                //
                .addNetworkInterceptor(new StethoInterceptor())
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
    }

//    public Observable<LoginBean> register(String mobile, String password, String sex, String age, String city, String head_portrait, String industry_id, String user_name) {
//        return mService.register(mobile, password, sex, age, city, head_portrait, industry_id, user_name);
//    }

    public Observable<HangYeBean> getHangYe() {
        return mService.getHangYe();
    }

    public Observable<BaseResponse> getToken() {
        return mService.getToken();
    }

//    public Observable<LoginBean> SaveImage(String s) {
//        return mService.SaveImage(s);
//    }

    public Observable<response> WriteInfo(String user_id, String sign, String nick_name, String avatar) {
        return mService.WriteInfo(user_id, sign, nick_name, avatar);
    }

    public Observable<response> WriteInfo1(String nick_name, String avatar) {
        return mService.WriteInfo1(nick_name, avatar);
    }


    public Observable<LoginResponse> login(String nick_name, String avatar) {


        return mService.login(nick_name, avatar);
    }

}
