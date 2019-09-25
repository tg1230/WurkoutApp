package com.example.wurkout.Reps.RepFragments;

import android.os.Bundle;

import com.example.wurkout.MainActivity;
import com.example.wurkout.R;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wurkout.MainActivity;

public class MainRepsActivity extends AppCompatActivity
        implements WeightFragment.OnFragmentInteractionListener,
        PercentFragment.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpRepsFragment(savedInstanceState);

    }

    public void setUpRepsFragment(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_reps_main);

        double weight = getIntent().getDoubleExtra("weight", -1);
        double reps = getIntent().getDoubleExtra("reps", -1);

        ViewPager viewPager = findViewById(R.id.view_pager);
        RepsTabAdapter repsTabAdapter = new RepsTabAdapter(this, getSupportFragmentManager(), weight, reps);
        viewPager.setAdapter(repsTabAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void onFragmentInteraction(int frag) {
        // can leave it empty
    }
}