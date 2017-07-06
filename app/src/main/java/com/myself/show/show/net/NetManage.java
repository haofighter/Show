package com.myself.show.show.net;

import android.content.Context;
import android.util.Log;

import com.myself.show.show.net.requestBean.MusicSearchBean;
import com.myself.show.show.net.responceBean.WySearchInfo;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/4.
 */

public class NetManage {
    static  Context mContext;
    static  NetManage netManage;
    private NetManage() {

    }

    public static NetManage getInstance(Context context) {
        if(netManage==null){
            netManage=new NetManage();
        }
        mContext = context;
        return netManage;
    }

    /**
     * 获取到搜索到的歌曲列表
     * @param musicSearchBean 搜索参数
     * @param action1 成功后的回调
     * @return
     */
    public void loadMusicDate(MusicSearchBean musicSearchBean, Action1<WySearchInfo> action1) {
        RetrofitManager.builder(mContext).wyYun(musicSearchBean.getSearchName(), musicSearchBean.getPage(), musicSearchBean.getLimit(), musicSearchBean.getType()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("错误", throwable.toString());
                    }
                });
    }
}
