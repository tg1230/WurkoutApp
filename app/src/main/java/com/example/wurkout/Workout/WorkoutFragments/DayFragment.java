package com.example.wurkout.Workout.WorkoutFragments;

import android.content.Context;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wurkout.Custom_RecyclerView.RecyclerView_Adapter;
import com.example.wurkout.Custom_RecyclerView.RecyclerView_Items;
import com.example.wurkout.Custom_RecyclerView.RV_Fragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class DayFragment extends RV_Fragment {

    private String id;

    public DayFragment() {
        // Required empty public constructor
    }


    // take in the current workout given the position from the adapter
    public static DayFragment newInstance(String ident) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putString("ident", ident);
        fragment.setArguments(args);

        return fragment;
    }

    // where values should be initialized
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = this.getArguments().getString("ident");
        setRetainInstance(true);

    }

    // where graphical interface initialized
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        checkData();
        super.buildRecyclerView(v);
        OnItemClickListener(v);
        return v;
    }

    public void onPause() {

        super.onPause();

        String savedList = new Gson().toJson(zList);

        String fileName = "Workouts" + id + ".txt";
        super.saveData("Workouts", fileName, savedList);

    }

    public void checkData() {

        String fileName = "Workouts" + id + ".txt";
        String data = super.checkData("Workouts", fileName);
        if (!data.equals("[]")) {
            Gson gson = new Gson();
            Type typeToken = new TypeToken<ArrayList<RecyclerView_Items>>() {
            }.getType();
            zList = gson.fromJson(data, typeToken);
        }
    }


    public void OnItemClickListener(final View v) {

        zListAdapter.OnItemClickListener(new RecyclerView_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItem(position, v);
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }

        });
        zAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNameAddItem(view);
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

}
