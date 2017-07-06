package com.myself.show.show.Ui.viewpage1.fagment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myself.show.show.R;
import com.myself.show.show.Ui.music.adpter.MusicItemAdapter;
import com.myself.show.show.Ui.viewpage.listener.FragmentInfo;
import com.myself.show.show.Ui.viewpage1.listener.OnFragmentInteractionListener;
import com.myself.show.show.net.RetrofitManager;
import com.myself.show.show.net.responceBean.WySearchInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SearchSecondFragment extends Fragment {


    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp_content)
    ViewPager vpContent;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Unbinder unbinder;

    public SearchSecondFragment() {
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

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("错误", throwable.toString());
                    }
                });
    }
    private List<FragmentInfo> fragmentList;
    private FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_view_page_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        fragmentList = new ArrayList<>();

        fragmentList.add(new FragmentInfo(new SearchOneFragment(),"栏目一"));

        fragmentList.add(new FragmentInfo(new SearchThridFragment(),"栏目三"));


        fragmentPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position).getFragment();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return fragmentList.get(position).getFragmentTitle();
            }


            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
        vpContent.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(vpContent);

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

    }

    @Override
    public void onDestroyView() {
        Log.i("TAG", "Second=====onDestroyView=====");
        super.onDestroyView();
        unbinder.unbind();
    }
}
