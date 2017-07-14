package com.myself.show.show.Ui.viewpage.fagment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myself.show.show.R;
import com.myself.show.show.Ui.music.adpter.MusicItemAdapter;
import com.myself.show.show.View.Twink.RefreshListenerAdapter;
import com.myself.show.show.View.Twink.TwinklingRefreshLayout;
import com.myself.show.show.base.BackCall;
import com.myself.show.show.listener.OnFragmentInteractionListener;
import com.myself.show.show.net.RetrofitManager;
import com.myself.show.show.net.responceBean.WySearchInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SearchSecondFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.search_result_show)
    RecyclerView searchResultShow;
    @BindView(R.id.refresh)
    TwinklingRefreshLayout refresh;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SearchSecondFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchOneFragment.
     */
    public static SearchSecondFragment newInstance(String param1, String param2) {
        SearchSecondFragment fragment = new SearchSecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        onHiddenChanged(isVisibleToUser);
    }


    private String musicType = "1";
    private int page = 1;
    private int limit = 10;
    private MusicItemAdapter musicItemAdapter;
    public void initDate(WySearchInfo wySearchInfo) {
        if (page != 1) {
            musicItemAdapter.addDate(wySearchInfo.getResult().getSongs());
        } else {
            musicItemAdapter.setDate(wySearchInfo.getResult().getSongs());
        }
        musicItemAdapter.notifyDataSetChanged();
    }

    public void loadDate() {
        RetrofitManager.builder(getActivity()).wyYun("汪苏泷", page, limit, musicType).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WySearchInfo>() {
                    @Override
                    public void call(WySearchInfo wySearchInfo) {
                        initDate(wySearchInfo);
                        refresh.finishLoadmore();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("错误", throwable.toString());
                    }
                });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_one, container, false);
        ButterKnife.bind(this, v);
        name.setText("第二个");
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

        musicItemAdapter=new MusicItemAdapter(getActivity(), new BackCall() {
            @Override
            public void backCall(int tag, Object... obj) {

            }
        });
        //设置布局管理器
        searchResultShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置增加或删除条目的动画
        searchResultShow.setItemAnimator(new DefaultItemAnimator());
        searchResultShow.setAdapter(musicItemAdapter);
        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        Log.i("TAG", "Second   onAttach");
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onStart() {
        Log.i("TAG", "Second===Start");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i("TAG", "Second=====onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i("TAG", "Second=====onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i("TAG", "Second=====onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.i("TAG", "Second=====onDestroy");
        super.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.i("TAG", "Second=====onHiddenChanged=====" + hidden);
        super.onHiddenChanged(hidden);
        if(hidden){
            loadDate();
        }
    }

    @Override
    public void onDestroyView() {
        Log.i("TAG", "Second=====onDestroyView=====");
        super.onDestroyView();
    }
}
