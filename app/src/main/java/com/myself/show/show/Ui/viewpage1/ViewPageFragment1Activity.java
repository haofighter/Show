package com.myself.show.show.Ui.viewpage1;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.myself.show.show.R;
import com.myself.show.show.Ui.viewpage.listener.FragmentInfo;
import com.myself.show.show.Ui.viewpage1.listener.OnFragmentInteractionListener;
import com.myself.show.show.Ui.viewpage1.fagment.SearchOneFragment;
import com.myself.show.show.Ui.viewpage1.fagment.SearchSecondFragment;
import com.myself.show.show.Ui.viewpage1.fagment.SearchThridFragment;
import com.myself.show.show.base.BaseFragmentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPageFragment1Activity extends BaseFragmentActivity implements OnFragmentInteractionListener {


    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    private List<FragmentInfo> fragmentList;
    private FragmentPagerAdapter fragmentPagerAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page_fragment);
        ButterKnife.bind(this);

        fragmentList = new ArrayList<>();

        fragmentList.add(new FragmentInfo(new SearchOneFragment(),"栏目一"));
        fragmentList.add(new FragmentInfo(new SearchSecondFragment(),"栏目二"));
        fragmentList.add(new FragmentInfo(new SearchThridFragment(),"栏目三"));


        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
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


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
