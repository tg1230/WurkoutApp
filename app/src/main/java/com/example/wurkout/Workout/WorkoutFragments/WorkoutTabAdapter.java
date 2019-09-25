package com.example.wurkout.Workout.WorkoutFragments;

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
public class WorkoutTabAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.work_text_1, R.string.work_text_2,
            R.string.work_text_3, R.string.work_text_4, R.string.work_text_5,
            R.string.work_text_6, R.string.work_text_7};
    private final Context mContext;

    // will hold all workouts for this activity
    private int id;
    private int workoutDays;

    public WorkoutTabAdapter(Context context, FragmentManager fm, int ident, int wdays) {
        super(fm);
        mContext = context;
        id = ident;
        workoutDays = wdays;
    }

    // each fragment needs its own unique id
    // there must be a clear way of dynamically creating fragment tabs but this works
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        fragment = getDayFragment(position);

        return fragment;
    }

    public Fragment getDayFragment(int position) {

        Fragment fragment = null;

        if (workoutDays==1) {
            fragment = DayFragment.newInstance(id + "1");

        }
        if (workoutDays==2) {
            switch (position) {
                case 0:
                    fragment = DayFragment.newInstance(id + "1");
                    break;
                case 1:
                    fragment = DayFragment.newInstance(id + "2");
                    break;
            }

        }

        if (workoutDays==3) {
            switch (position) {
                case 0:
                    fragment = DayFragment.newInstance(id + "1");
                    break;
                case 1:
                    fragment = DayFragment.newInstance(id + "2");
                    break;
                case 2:
                    fragment = DayFragment.newInstance(id + "3");
                    break;
            }

        }
        if (workoutDays==4) {
            switch (position) {
                case 0:
                    fragment = DayFragment.newInstance(id + "1");
                    break;
                case 1:
                    fragment = DayFragment.newInstance(id + "2");
                    break;
                case 2:
                    fragment = DayFragment.newInstance(id + "3");
                    break;
                case 3:
                    fragment = DayFragment.newInstance(id + "4");
                    break;
            }

        }
        if (workoutDays==5) {
            switch (position) {
                case 0:
                    fragment = DayFragment.newInstance(id + "1");
                    break;
                case 1:
                    fragment = DayFragment.newInstance(id + "2");
                    break;
                case 2:
                    fragment = DayFragment.newInstance(id + "3");
                    break;
                case 3:
                    fragment = DayFragment.newInstance(id + "4");
                    break;
                case 4:
                    fragment = DayFragment.newInstance(id + "5");
                    break;
            }

        }
        if (workoutDays==6) {
            switch (position) {
                case 0:
                    fragment = DayFragment.newInstance(id + "1");
                    break;
                case 1:
                    fragment = DayFragment.newInstance(id + "2");
                    break;
                case 2:
                    fragment = DayFragment.newInstance(id + "3");
                    break;
                case 3:
                    fragment = DayFragment.newInstance(id + "4");
                    break;
                case 4:
                    fragment = DayFragment.newInstance(id + "5");
                    break;
                case 5:
                    fragment = DayFragment.newInstance(id + "6");
                    break;
            }

        }
        if (workoutDays==7) {
            switch (position) {
                case 0:
                    fragment = DayFragment.newInstance(id + "1");
                    break;
                case 1:
                    fragment = DayFragment.newInstance(id + "2");
                    break;
                case 2:
                    fragment = DayFragment.newInstance(id + "3");
                    break;
                case 3:
                    fragment = DayFragment.newInstance(id + "4");
                    break;
                case 4:
                    fragment = DayFragment.newInstance(id + "5");
                    break;
                case 5:
                    fragment = DayFragment.newInstance(id + "6");
                    break;
                case 6:
                    fragment = DayFragment.newInstance(id + "7");
                    break;
            }

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
        return workoutDays;
    }
}