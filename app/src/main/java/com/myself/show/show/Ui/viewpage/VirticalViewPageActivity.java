package com.myself.show.show.Ui.viewpage;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.myself.show.show.R;
import com.myself.show.show.Ui.MainActivity;
import com.myself.show.show.Ui.home.AddNoteActivity;
import com.myself.show.show.Ui.viewpage.fagment.SearchOneFragment;
import com.myself.show.show.Ui.viewpage.fagment.SearchSecondFragment;
import com.myself.show.show.Ui.viewpage.fagment.SearchThridFragment;
import com.myself.show.show.Ui.viewpage.listener.FragmentInfo;
import com.myself.show.show.View.NavigationBar;
import com.myself.show.show.base.ThemeBaseActivity;
import com.myself.show.show.customview.verticaltablayout.VerticalTabLayout;
import com.myself.show.show.customview.verticaltabviewpage.VerticalViewPager;
import com.myself.show.show.listener.OnFragmentInteractionListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class VirticalViewPageActivity extends ThemeBaseActivity implements OnFragmentInteractionListener {

    private ArrayList<FragmentInfo> fragmentList;
    private FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        na_bar.setRightText("demo");
        na_bar.setListener(new NavigationBar.NavigationListener() {
            @Override
            public void onButtonClick(int button) {
                if (button == NavigationBar.RIGHT_VIEW) {
                    startActivity(MainActivity.class);
                }
            }
        });
        initView();
    }

    @Override
    public int getContentView() {
        return 0;
    }


    private void initView() {
        fragmentList = new ArrayList<>();

        fragmentList.add(new FragmentInfo(new SearchOneFragment(), "栏目一"));
        fragmentList.add(new FragmentInfo(new SearchSecondFragment(), "栏目二"));
        fragmentList.add(new FragmentInfo(new SearchThridFragment(), "栏目三"));


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
//        vertiacalViewpager.setAdapter(fragmentPagerAdapter);
//        vertiacalTab.setupWithViewPager(vertiacalViewpager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @OnClick(R.id.add_note)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_note:
                startActivity(AddNoteActivity.class, ActivityChangeAnimal.right);
                break;
        }
    }
}
