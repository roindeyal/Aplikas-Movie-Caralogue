package com.svafvel.software.production.moviecataloguelocalstorage.localstorage.navigation;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.svafvel.software.production.moviecataloguelocalstorage.FavoriteFragment;


public class SectionsPagerAdapterFavorite extends FragmentPagerAdapter {

    private final Context mContext;

    public SectionsPagerAdapterFavorite(Context context, FragmentManager fm){
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = FavoriteFragment.newInstance(position + 1);
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
