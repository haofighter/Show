package com.myself.show.show.Ui.viewpage;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.myself.show.show.R;
import com.myself.show.show.Ui.viewpage.fagment.SearchOneFragment;
import com.myself.show.show.Ui.viewpage.fagment.SearchSecondFragment;
import com.myself.show.show.Ui.viewpage.fagment.SearchThridFragment;
import com.myself.show.show.Ui.viewpage.listener.FragmentInfo;
import com.myself.show.show.Ui.viewpage.listener.OnFragmentInteractionListener;
import com.myself.show.show.base.BaseFragmentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


//TabLayout +ViewPager 实现界面滑动
public class ViewPageFragmentActivity extends BaseFragmentActivity implements OnFragmentInteractionListener {


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

        fragmentList.add(new FragmentInfo(new SearchOneFragment(),R.mipmap.before,R.mipmap.file_attach_icon_normal));
        fragmentList.add(new FragmentInfo(new SearchSecondFragment(),R.mipmap.icon_arror_down,R.mipmap.dui_icon));
        fragmentList.add(new FragmentInfo(new SearchThridFragment(),R.mipmap.icon_arror_up,R.mipmap.icon_more));


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


        for (int i = 0; i <tabLayout.getTabCount() ; i++) {
            tabLayout.getTabAt(i).setIcon(fragmentList.get(i).getIconId());
            tabLayout.getTabAt(i).setTag(i);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (fragmentList.get((Integer) tab.getTag()).getCheckIconId()!=0){
                    tab.setIcon(fragmentList.get((Integer) tab.getTag()).getCheckIconId());
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (fragmentList.get((Integer) tab.getTag()).getIconId()!=0){
                    tab.setIcon(fragmentList.get((Integer) tab.getTag()).getIconId());
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.getTabAt(1).select();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
