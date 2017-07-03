package com.myself.show.show.Ui.music.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.myself.show.show.R;
import com.myself.show.show.Ui.music.musicService.MusicService;
import com.myself.show.show.Ui.music.adpter.MusicItemAdapter;
import com.myself.show.show.View.Twink.RefreshListenerAdapter;
import com.myself.show.show.View.Twink.TwinklingRefreshLayout;
import com.myself.show.show.base.App;
import com.myself.show.show.base.BackCall;
import com.myself.show.show.base.BaseActivity;
import com.myself.show.show.net.RetrofitManager;
import com.myself.show.show.net.responceBean.WySearchInfo;
import com.myself.show.show.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

//网易云音乐  API  http://www.jianshu.com/p/97e35fa456ce


public class MusicActivity extends BaseActivity {

    @BindView(R.id.search_result_show)
    RecyclerView searchResultShow;
    @BindView(R.id.refresh)
    TwinklingRefreshLayout refresh;
    @BindView(R.id.search_content)
    EditText searchContent;

    private MusicItemAdapter musicItemAdapter;
    private MusicService musicService;

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
        musicItemAdapter = new MusicItemAdapter(this, backCall);
        searchResultShow.setAdapter(musicItemAdapter);
    }

    BackCall backCall = new BackCall() {
        @Override
        public void backCall(int tag, Object... obj) {
            App.getInstance().getSongsList().add(musicItemAdapter.getDate().get((int) obj[0]));
            Log.i("列表中的的歌曲数", App.getInstance().getSongsList().size() + "");
            App.getInstance().getMusicServie(MusicActivity.this).setRunIndex(App.getInstance().getSongsList().size() - 1).GetMusicUrlPlay();
        }
    };

    public void initDate(WySearchInfo wySearchInfo) {
        if (page != 1) {
            musicItemAdapter.addDate(wySearchInfo.getResult().getSongs());
        } else {
            musicItemAdapter.setDate(wySearchInfo.getResult().getSongs());
        }
        musicItemAdapter.notifyDataSetChanged();
    }

    private String musicType = "1";
    private int page = 1;
    private int limit = 10;

    public void loadDate() {
        String searchStr = "";
        if (searchContent.getText().toString().equals("") || searchContent.getText().toString().equals(searchStr)) {
            return;
        }
        searchStr = searchContent.getText().toString();
        RetrofitManager.builder(this).wyYun(searchContent.getText().toString(), page, limit, musicType).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WySearchInfo>() {
                    @Override
                    public void call(WySearchInfo wySearchInfo) {
                        initDate(wySearchInfo);
                        Toast.makeText(MusicActivity.this, "成功" + wySearchInfo.toString(), Toast.LENGTH_SHORT).show();
                        refresh.finishLoadmore();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("错误", throwable.toString());
                        Toast.makeText(MusicActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @OnClick({R.id.search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                page = 1;
                loadDate();
                break;
        }
    }
}
