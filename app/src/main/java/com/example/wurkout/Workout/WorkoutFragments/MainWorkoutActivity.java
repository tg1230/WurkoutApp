package com.example.wurkout.Workout.WorkoutFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import androidx.fragment.app.Fragment;

import com.example.wurkout.Custom_RecyclerView.RecyclerView_Items;
import com.example.wurkout.MainActivity;
import com.example.wurkout.R;
import com.example.wurkout.Reps.RepFragments.PercentFragment;
import com.example.wurkout.Reps.RepFragments.RepsTabAdapter;
import com.example.wurkout.Reps.RepFragments.WeightFragment;
import com.example.wurkout.Workout.Workout_Main;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainWorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpWorkoutFragment();

    }

    public void setUpWorkoutFragment() {

        setContentView(R.layout.fragment_workout_main);

        ViewPager viewPager = findViewById(R.id.view_pager);

        int identifier = getIntent().getIntExtra("id", -1);
        int workoutDays = getIntent().getIntExtra("workdays", -1);

        WorkoutTabAdapter workoutTabAdapter = new WorkoutTabAdapter(this, getSupportFragmentManager(), identifier, workoutDays);

        viewPager.setAdapter(workoutTabAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

}
