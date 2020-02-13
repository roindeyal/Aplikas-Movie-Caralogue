package com.svafvel.software.production.moviecataloguelocalstorage.navigation;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.svafvel.software.production.moviecataloguelocalstorage.HomeFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm){
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = HomeFragment.newInstance(position + 1);
        return fragment;
    }




    @Override
    public int getCount() {
        return 2;
    }
}
