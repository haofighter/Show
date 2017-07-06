package com.myself.show.show.Ui.viewpage1.listener;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2017/7/5.
 */

public class FragmentInfo {
    Fragment fragment;
    String fragmentTitle;

    public FragmentInfo(Fragment fragment, String fragmentTitle) {
        this.fragment = fragment;
        this.fragmentTitle = fragmentTitle;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getFragmentTitle() {
        return fragmentTitle;
    }

    public void setFragmentTitle(String fragmentTitle) {
        this.fragmentTitle = fragmentTitle;
    }
}
