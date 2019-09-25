package com.example.wurkout.Reps.RepFragments;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.wurkout.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class RepsTabAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.rep_text_1, R.string.rep_text_2};
    private final Context mContext;
    private double weight, reps;

    public RepsTabAdapter(Context context, FragmentManager fm, double w, double r) {
        super(fm);
        mContext = context;
        weight = w;
        reps = r;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = WeightFragment.newInstance(weight, reps);
                break;

            case 1:
                fragment = PercentFragment.newInstance(weight, reps);
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}