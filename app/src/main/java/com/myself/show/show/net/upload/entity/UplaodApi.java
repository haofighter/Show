package com.myself.show.show.net.upload.entity;


import com.myself.show.show.net.upload.http.HttpService;
import com.myself.show.show.net.upload.listener.HttpOnNextListener;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * 上传请求api
 * Created by WZG on 2016/10/20.
 */

public class UplaodApi extends BaseEntity {
    /*需要上传的文件*/
    private MultipartBody.Part part;


    public UplaodApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);
        setShowProgress(true);
    }

    public MultipartBody.Part getPart() {
        return part;
    }

    public void setPart(MultipartBody.Part part) {
        this.part = part;
    }

    @Override
    public Observable getObservable(HttpService methods) {
        RequestBody uid= RequestBody.create(MediaType.parse("text/plain"), "4811420");
        RequestBody key = RequestBody.create(MediaType.parse("text/plain"), "21f8d9bcc50c6ac1ae1020ce12f5f5a7");
        return methods.uploadImage(uid,key,getPart());
    }
}
