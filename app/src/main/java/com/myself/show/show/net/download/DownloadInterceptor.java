package com.myself.show.show.net.download;

import com.myself.show.show.net.downloaddemo.DownLoadListener.DownloadProgressListener;
import com.myself.show.show.net.downloaddemo.DownLoadListener.DownloadResponseBody;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 成功回调处理
 * Created by WZG on 2016/10/20.
 */
public class DownloadInterceptor implements Interceptor {

    private ProgressListener listener;

    public DownloadInterceptor(ProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        okhttp3.Response response = chain.proceed(chain.request());
        //将ResponseBody转换成我们需要的FileResponseBody
        return response.newBuilder().body(new FileResponseBody(response.body(), listener)).build();
    }
}
