package com.myself.show.show.net;



import java.util.concurrent.TimeUnit;


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

}
