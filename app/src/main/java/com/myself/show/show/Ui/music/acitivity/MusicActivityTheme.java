package com.myself.show.show.Ui.music.acitivity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.myself.show.show.R;
import com.myself.show.show.Ui.music.adpter.MusicItemAdapter;
import com.myself.show.show.View.Twink.RefreshListenerAdapter;
import com.myself.show.show.View.Twink.TwinklingRefreshLayout;
import com.myself.show.show.base.BaseActivity;
import com.myself.show.show.net.RetrofitManager;
import com.myself.show.show.net.responceBean.WySearchInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

//网易云音乐  API  http://www.jianshu.com/p/97e35fa456ce


public class MusicActivityTheme extends BaseActivity {

    @BindView(R.id.search_result_show)
    RecyclerView searchResultShow;
    @BindView(R.id.refresh)
    TwinklingRefreshLayout refresh;
    @BindView(R.id.search_content)
    EditText searchContent;
    private MusicItemAdapter musicItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ButterKnife.bind(this);
        setStatuBarColor(R.color.colorPrimaryDark);
        initView();
    }

    /**
     * 初始化界面及完成逻辑
     */
    public void initView() {
        refresh.setEnableRefresh(false);
        refresh.setOverScrollTopShow(false);
        refresh.setEnableOverScroll(false);//禁止界面回弹  可去掉刷新效果
        refresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                loadDate();
            }
        });

         //设置布局管理器
        searchResultShow.setLayoutManager(new LinearLayoutManager(this));
        //设置增加或删除条目的动画
        searchResultShow.setItemAnimator(new DefaultItemAnimator());

        musicItemAdapter = new MusicItemAdapter(this);
        searchResultShow.setAdapter(musicItemAdapter);
    }

    public void initDate(WySearchInfo wySearchInfo) {
        if(page!=1){
            musicItemAdapter.addDate(wySearchInfo.getResult().getSongs());
        }else{
            musicItemAdapter.setDate(wySearchInfo.getResult().getSongs());
        }
        musicItemAdapter.notifyDataSetChanged();;
    }

    private String musicType = "1";
    private int page = 1;
    private int limit = 10;

    public void loadDate() {
        if (searchContent.getText().toString().equals("")) {
            return;
        }
        RetrofitManager.builder(this).wyYun(searchContent.getText().toString(), page, limit, musicType).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WySearchInfo>() {
                    @Override
                    public void call(WySearchInfo wySearchInfo) {
                        initDate(wySearchInfo);
                        Toast.makeText(MusicActivityTheme.this, "成功" + wySearchInfo.toString(), Toast.LENGTH_SHORT).show();
                        refresh.finishLoadmore();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("错误", throwable.toString());
                        Toast.makeText(MusicActivityTheme.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @OnClick(R.id.search)
    public void onClick() {
        page=1;
        loadDate();
    }
}
