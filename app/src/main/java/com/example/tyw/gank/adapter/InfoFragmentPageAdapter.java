package com.example.tyw.gank.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tyw.gank.ui.GankFragment;

/**
 * Created by tangyiwu on 2016/11/24.
 */

public class InfoFragmentPageAdapter extends FragmentPagerAdapter {
    private String[] types;

    public InfoFragmentPageAdapter(FragmentManager fm, @NonNull String[] typeArray) {
        super(fm);
        this.types = typeArray;
    }

    @Override
    public Fragment getItem(int position) {
        return GankFragment.newFragment(types[position]);
    }

    @Override
    public int getCount() {
        return types.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return types[position];
    }
}
