package com.stuur.stuur;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public PlaceholderFragment fragment;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class).
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        fragment = (PlaceholderFragment) object;
    }

    @Override
    public int getCount() {
        // Show 5 total pages.
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Settings";
            case 1:
                return "Friend List";
            case 2:
                return "Friends";
            case 3:
                return "Local";
            case 4:
                return "Global";
        }
        return null;
    }
}