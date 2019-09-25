package com.example.wurkout.Workout;

import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.text.InputType;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import android.app.ActionBar;

import com.example.wurkout.Custom_RecyclerView.RecyclerView_Adapter;
import com.example.wurkout.Custom_RecyclerView.RecyclerView_Items;
import com.example.wurkout.R;
import com.example.wurkout.Custom_RecyclerView.RV_Fragment;
import com.example.wurkout.Workout.WorkoutFragments.MainWorkoutActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class Workout_Main extends RV_Fragment {

    // image overlay for list
    private ImageView zAddItem;
    private int id;
    private int workoutDays;

    public static Workout_Main newInstance() {
        return new Workout_Main();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // where graphical interface initialized
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        checkData();
        super.buildRecyclerView(v);
        zAddItem = v.findViewById(R.id.imageView3);
        OnItemClickListener();

        return v;
    }

    public void onPause() {
        super.onPause();

        String savedList = new Gson().toJson(zList);

        super.saveData("Workouts", "Workouts.txt", savedList);

    }

    public void checkData() {
        String data = super.checkData("Workouts", "Workouts.txt");
        if (!data.equals("[]")) {
            Gson gson = new Gson();
            Type typeToken = new TypeToken<ArrayList<RecyclerView_Items>>() {
            }.getType();
            zList = gson.fromJson(data, typeToken);
        }
    }
 
    public void OnItemClickListener() {
        if (zListAdapter != null) {
            zListAdapter.OnItemClickListener(new RecyclerView_Adapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if (zList.get(position).getWorkoutDays() == 0) {
                        getNumberDays(position);
                    } else {

                        // needed to make sure each item had a unique identifier to save properly
                        Intent intent = new Intent(getActivity(), MainWorkoutActivity.class);
                        id = zList.get(position).getIdentifier();
                        workoutDays = zList.get(position).getWorkoutDays();
                        intent.putExtra("id", id);
                        intent.putExtra("workdays", workoutDays);
                        startActivity(intent);

                    }

                }

                
                @Override
                public void onDeleteClick(int position) {
                    id = zList.get(position).getIdentifier();
                    removeItem(position);
                }

            });
        }

        zAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNameAddItem(v);
            }
        });
    }

    public void getNumberDays(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Enter Number of Workout Days (1-7)");
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!input.getText().toString().equals("") && Integer.parseInt(input.getText().toString()) < 8
                && Integer.parseInt(input.getText().toString()) > 0) {
                    zList.get(position).setWorkoutDays(Integer.parseInt(input.getText().toString()));
                    updateDays(position);

                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        builder.show();
    }

    public void updateDays(int position) {
        String newDescription = zList.get(position).getWorkoutDays() + " day plan";
        zList.get(position).changeText2(newDescription);
        zListAdapter.notifyItemChanged(position);
    }

    public void removeItem(int position) {
        int numDays = zList.get(position).getWorkoutDays();

        // handles removing item from current list and letting user know
        super.removeItem(position);

        // removes all data of current workout
        for (int i=0; i<numDays; i++) {
            String fileToBeDeleted = "Workouts"+id+""+(i+1)+".txt";
            super.deleteData("Workouts", fileToBeDeleted);

        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

}
