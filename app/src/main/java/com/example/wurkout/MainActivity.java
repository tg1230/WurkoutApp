package com.example.wurkout;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.fragment.app.FragmentTransaction;

import com.example.wurkout.Chart.Chart_Main;
import com.example.wurkout.Gallery.Gallery_Main;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.wurkout.Reps.Reps_Main;
import com.example.wurkout.Workout.Workout_Main;

// if I were to redo this project, I would setup sqlite to save data and plan out the structures of classes and features better


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpDrawerNavigation();

        startWorkoutFragment();
    }

    public void setUpDrawerNavigation() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void startWorkoutFragment() {
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        Fragment wm_fragment = Workout_Main.newInstance();
        fragmentManager.replace(R.id.content_frame, wm_fragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // TODO: figure out what settings would be useful - kg to lbs?
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.file_paths.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

    // proper way of creating a newInstance of a fragment
    // can pass data through now if needed
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_first_layout) {
            Fragment wm_fragment = Workout_Main.newInstance();
            fragmentManager.replace(R.id.content_frame, wm_fragment).commit();

        }
        else if (id == R.id.nav_second_layout) {
            Fragment rm_fragment = Reps_Main.newInstance();
            fragmentManager.replace(R.id.content_frame, rm_fragment).commit();

        }
        else if (id == R.id.nav_third_layout) {
            Fragment cm_fragment = Chart_Main.newInstance();
            fragmentManager.replace(R.id.content_frame, cm_fragment).commit();
        }
        else if (id == R.id.nav_fourth_layout) {
            Fragment gf_fragment = Gallery_Main.newInstance();
            fragmentManager.replace(R.id.content_frame, gf_fragment).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
