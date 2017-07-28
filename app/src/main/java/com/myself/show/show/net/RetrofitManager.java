package com.myself.show.show.net;



import com.myself.show.show.net.download.FileResponseBody;
import com.myself.show.show.net.upload.FileSubscribe;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by laucherish on 16/3/15.
 */
public class RetrofitManager {

    private static final String baseUrl="http://119.97.150.30/";

    public static Retrofit retrofit;
    private static OkHttpClient okHttpClient = new OkHttpClient();

    static {
        okHttpClient.newBuilder().connectTimeout(5000, TimeUnit.MILLISECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    //常规请求
    public static <T> T builder(Class<T> clazz) {
        if (retrofit == null) {
            synchronized (RetrofitManager.class) {
                Retrofit.Builder builder = new Retrofit.Builder();
                retrofit = builder.addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .baseUrl(baseUrl)
                        .client(okHttpClient)
                        .build();
            }
        }
        return retrofit.create(clazz);
    }

    //常规请求  自定义baseUrl
    public static <T> T builder(Class<T> clazz,String baseUrl) {
        if (retrofit == null) {
            synchronized (RetrofitManager.class) {
                Retrofit.Builder builder = new Retrofit.Builder();
                retrofit = builder.addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .baseUrl(baseUrl)
                        .client(okHttpClient)
                        .build();
            }
        }
        return retrofit.create(clazz);
    }

    //下载进度监听
    public static <T> T builder(Class<T> tClass, final FileSubscribe callback) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder().body(
                        new FileResponseBody(originalResponse.body(), callback))
                        .build();
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(clientBuilder.build())
                .build();
        return   retrofit.create(tClass);
    }

    //下载进度监听  自定义基础URL
    public static <T> T builder(Class<T> tClass,final FileSubscribe callback,String  baseUrl) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response response = chain.proceed(chain.request());
                //将ResponseBody转换成我们需要的FileResponseBody
                return response.newBuilder().body(new FileResponseBody(response.body(), callback)).build();
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
        return   retrofit.create(tClass);
    }

}
