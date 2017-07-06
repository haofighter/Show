package com.myself.show.show.Ui.viewpage.listener;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2017/7/5.
 */

public class FragmentInfo {
    Fragment fragment;
    String fragmentTitle;
    int  iconId;
    int  checkIconId;

    public FragmentInfo(Fragment fragment, String fragmentTitle, int iconId) {
        this.fragment = fragment;
        this.fragmentTitle = fragmentTitle;
        this.iconId = iconId;
    }

    public FragmentInfo(Fragment fragment, int iconId) {
        this.fragment = fragment;
        this.iconId = iconId;
    }

    public FragmentInfo(Fragment fragment, int iconId, int checkIconId) {
        this.fragment = fragment;
        this.iconId = iconId;
        this.checkIconId = checkIconId;
    }

    public int getCheckIconId() {
        return checkIconId;
    }

    public void setCheckIconId(int checkIconId) {
        this.checkIconId = checkIconId;
    }

    public FragmentInfo(Fragment fragment, String fragmentTitle) {
        this.fragment = fragment;
        this.fragmentTitle = fragmentTitle;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
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
