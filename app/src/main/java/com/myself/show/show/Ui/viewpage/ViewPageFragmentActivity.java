package com.myself.show.show.Ui.viewpage;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.myself.show.show.R;
import com.myself.show.show.Ui.viewpage.fagment.SearchOneFragment;
import com.myself.show.show.Ui.viewpage.fagment.SearchSecondFragment;
import com.myself.show.show.Ui.viewpage.fagment.SearchThridFragment;
import com.myself.show.show.Ui.viewpage.listener.OnFragmentInteractionListener;
import com.myself.show.show.base.BaseFragmentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPageFragmentActivity extends BaseFragmentActivity implements OnFragmentInteractionListener {


    @BindView(R.id.vp_content)
    ViewPager vpContent;
    private List<Fragment> fragmentList;
    private FragmentPagerAdapter fragmentPagerAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page_fragment);
        ButterKnife.bind(this);

        fragmentList = new ArrayList<>();
        fragmentList.add(new SearchOneFragment());
        fragmentList.add(new SearchSecondFragment());
        fragmentList.add(new SearchThridFragment());


        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
        vpContent.setAdapter(fragmentPagerAdapter);

        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("TAG","state==="+state);

            }
        });


        vpContent.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
