package com.szbm.wh.x.bmlogg.ui.common;


import android.os.Bundle;

import com.szbm.wh.x.bmlogg.ui.ui.bh.BhBasicFragment;
import com.szbm.wh.x.bmlogg.ui.ui.bh.BhBeginFragment;
import com.szbm.wh.x.bmlogg.ui.ui.bh.BhEndFragment;
import com.szbm.wh.x.bmlogg.ui.ui.bh.RockSoilFragment;
import com.szbm.wh.x.bmlogg.ui.ui.bh.SampleFragment;
import com.szbm.wh.x.bmlogg.ui.ui.bh.SptFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    public static class Builder{
        public static FragmentAdapter build(long iid_bh,FragmentManager fragmentManager,List<String> list){
            List<Fragment> fragments = new ArrayList<>();
            Bundle bundle = new Bundle();
            bundle.putLong(CSKeys.cs_bh_iid,iid_bh);

            BhBeginFragment bhBeginFragment = new BhBeginFragment();
            bhBeginFragment.setArguments(bundle);
            fragments.add(bhBeginFragment);

            RockSoilFragment rockSoilFragment = new RockSoilFragment();
            rockSoilFragment.setArguments(bundle);
            fragments.add(rockSoilFragment);

            SampleFragment sampleFragment = new SampleFragment();
            sampleFragment.setArguments(bundle);
            fragments.add(sampleFragment);

            SptFragment sptFragment = new SptFragment();
            sptFragment.setArguments(bundle);
            fragments.add(sptFragment);

            BhEndFragment bhEndFragment = new BhEndFragment();
            bhEndFragment.setArguments(bundle);
            fragments.add(bhEndFragment);

            return new FragmentAdapter(fragmentManager,fragments,list);
        }
    }
}
