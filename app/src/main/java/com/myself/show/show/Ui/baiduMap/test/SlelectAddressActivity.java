package com.myself.show.show.Ui.baiduMap.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.myself.show.show.R;
import com.myself.show.show.View.NavigationBar;
import com.myself.show.show.base.App;
import com.myself.show.show.base.BackCall;
import com.myself.show.show.base.ThemeBaseActivity;
import com.myself.show.show.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class SlelectAddressActivity extends ThemeBaseActivity {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.search)
    TextView search;
    @BindView(R.id.search_content)
    EditText searchContent;
    private GeoCoder mSearch;
    private SuggestionSearch mSuggestionSearch;

    public String searchCity;
    private SearchAddAdapter searchAddAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchCity = App.getInstance().getLoction() == null ? "<请选择>" : "<" + App.getInstance().getLoction().getCity() + ">";
        na_bar.setTitle("搜索地点" + searchCity);
        na_bar.setTitleColor(ContextCompat.getColor(this, R.color.white));
        na_bar.setLeftImage(R.mipmap.back);
        na_bar.setListener(new NavigationBar.NavigationListener() {
            @Override
            public void onButtonClick(int button) {
                switch (button) {
                    case NavigationBar.LEFT_VIEW:
                        break;
                    case NavigationBar.RIGHT_VIEW:
                        break;
                    case NavigationBar.MIDDLE_VIEW:
                        ToastUtils.showMessage("进入城市选择界面,目前暂未处理");
                        searchCity = "武汉";
                        break;
                }
            }
        });
        inntTools();
        initView();
    }

    private void inntTools() {
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(new OnGetSuggestionResultListener() {
            @Override
            public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                dimissLoadingDialog();
                isSearch = false;
                searchAddAdapter.setDate(suggestionResult.getAllSuggestions());
                searchAddAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        searchAddAdapter = new SearchAddAdapter(this, backCall);
        //设置布局管理器
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        //设置增加或删除条目的动画
        recycleView.setItemAnimator(new DefaultItemAnimator());
        recycleView.setAdapter(searchAddAdapter);
    }

    BackCall backCall = new BackCall() {
        @Override
        public void backCall(int tag, Object... obj) {
            SuggestionResult.SuggestionInfo suggestionInfo=(SuggestionResult.SuggestionInfo)obj[0];
            Intent intent=new Intent(SlelectAddressActivity.this,TestMapActivity.class);
            intent.putExtra("selectAdd",suggestionInfo);
            setResult(1,intent);
            finish();
        }
    };

    @Override
    public int getContentView() {
        return R.layout.activity_slelect_address;
    }

    //用于判断是否搜索完成
    private boolean isSearch = false;

    @OnClick(R.id.search)
    public void onClick() {
        if (!searchContent.getText().toString().equals("") && !isSearch) {
            mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                    .keyword(searchContent.getText().toString()).city(searchCity));
            isSearch = true;
            showLoadingDialog();
        } else {
            ToastUtils.showMessage("检索中,请稍后...");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSuggestionSearch.destroy();
    }
}
