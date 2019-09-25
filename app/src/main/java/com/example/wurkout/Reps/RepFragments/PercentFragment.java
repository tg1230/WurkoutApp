package com.example.wurkout.Reps.RepFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.*;

import com.example.wurkout.R;
import com.example.wurkout.Custom_RecyclerView.RecyclerView_Adapter;
import com.example.wurkout.Custom_RecyclerView.RecyclerView_Items;
import com.example.wurkout.Custom_RecyclerView.RV_Fragment;

// this and weightfragment very similar, could combine into one and change buildRecyclerView depending on a bundle value
public class PercentFragment extends RV_Fragment {

    private double weight, reps;

    public interface OnListItemClickListener {
        void onListItemClick(int position);
    }

    private OnListItemClickListener zItemClickListener;

    public PercentFragment() {
        // Required empty public constructor
    }

    // where values are taken in from creator and stored upon a new fragment created
    public static PercentFragment newInstance(double w, double r) {
        PercentFragment fragment = new PercentFragment();
        Bundle args = new Bundle();
        args.putDouble("w", w);
        args.putDouble("r", r);
        fragment.setArguments(args);
        return fragment;
    }

    // where values should be initialized
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weight = this.getArguments().getDouble("w");
        reps = this.getArguments().getDouble("r");

        setRetainInstance(true);

    }

    // where graphical interface initialized
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reps_list, container, false);
        buildRecyclerView(v);
        return v;
    }

    public void buildRecyclerView(View v) {
        zListView = v.findViewById(R.id.fragment_reps_list);
        zListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        zListAdapter = new RecyclerView_Adapter(zList, this);
        zListView.setAdapter(zListAdapter);

        createPercentageList();


    }

    public void createPercentageList() {
        // Brzycki Formula for onerm

        double onerm = weight * (36 / (37 - reps));
        double[] percents = new double[]{1.000, .972, .944, .917, .889, .861, .833,
                .806, .778, .75, .722, .694, .667, .639, .611};
        for (int i=14; i>=0; i--) {
            addItem(percents[i], i + 1, onerm);
        }

        zListAdapter.notifyDataSetChanged();

    }

    public void addItem(double percentage, double calculatedReps, double onerm) {
        String line1 = (int)(Math.rint(onerm * percentage)) + " lbs " + (int)calculatedReps + " RM";
        DecimalFormat df = new DecimalFormat("#.#");
        RecyclerView_Items item = new RecyclerView_Items(R.drawable.ic_fitness_black,
                R.drawable.ic_fitness_black, line1, (df.format(percentage*100)) + "%");
        zList.add(0, item);

    }

    public interface OnFragmentInteractionListener {
        // change parameters as needed
        void onFragmentInteraction(int fragment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


}
