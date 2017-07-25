package com.myself.show.show.net.upload;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/7/25.
 */

public abstract class RetrofitCallback<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccess()) {
            onSuccess(call, response);
        } else {
            onFailure(call, new Throwable(response.message()));
        }
    }
    public abstract void onSuccess(Call<T> call, Response<T> response);
    public void onLoading(long total, long progress) {
    }
}