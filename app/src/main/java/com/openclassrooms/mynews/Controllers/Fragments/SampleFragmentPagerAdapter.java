package com.openclassrooms.mynews.Controllers.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Class used to create the different tabs displayed on the action bar
 */
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[] { "TOP STORIES", "MOST POPULAR"};
    private Context context;
    private int PAGE_COUNT = 2;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1);
    }

    /**
     * Generate title based on item position
     * @param position Tab item position
     * @return Tab title
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
