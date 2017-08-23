package com.myself.show.show.Ui.baiduMap.test;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.myself.show.show.R;
import com.myself.show.show.base.ThemeBaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SlelectAddressActivity extends ThemeBaseActivity {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.search)
    TextView search;
    private GeoCoder mSearch;
    private SuggestionSearch mSuggestionSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        na_bar.setTitle("搜索地点");
        na_bar.setLeftImage(R.mipmap.back);
        inntTools();
        initView();
    }

    private void inntTools() {
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(new OnGetSuggestionResultListener() {
            @Override
            public void onGetSuggestionResult(SuggestionResult suggestionResult) {

                suggestionResult.getAllSuggestions();
            }
        });
    }

    private void initView() {

    }

    @Override
    public int getContentView() {
        return R.layout.activity_slelect_address;
    }

    @OnClick(R.id.search)
    public void onClick() {
        mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                .keyword("百度").city("武汉"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSuggestionSearch.destroy();
    }
}
