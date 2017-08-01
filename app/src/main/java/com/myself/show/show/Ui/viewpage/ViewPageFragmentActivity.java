package com.myself.show.show.Ui.viewpage;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.myself.show.show.R;
import com.myself.show.show.Ui.viewpage.fagment.SearchOneFragment;
import com.myself.show.show.Ui.viewpage.fagment.SearchSecondFragment;
import com.myself.show.show.Ui.viewpage.fagment.SearchThridFragment;
import com.myself.show.show.Ui.viewpage.listener.FragmentInfo;
import com.myself.show.show.base.BaseActivity;
import com.myself.show.show.listener.OnFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


//TabLayout +ViewPager 实现界面滑动
public class ViewPageFragmentActivity extends BaseActivity implements OnFragmentInteractionListener {


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

        fragmentList.add(new FragmentInfo(new SearchOneFragment(),R.mipmap.checked_on,R.mipmap.checked_off));
        fragmentList.add(new FragmentInfo(new SearchSecondFragment(),R.mipmap.checked_off,R.mipmap.checked_on));
        fragmentList.add(new FragmentInfo(new SearchThridFragment(),R.mipmap.checked_off,R.mipmap.checked_on));


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
