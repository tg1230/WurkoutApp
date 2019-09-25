package com.example.wurkout.Reps.RepFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wurkout.R;
import com.example.wurkout.Custom_RecyclerView.RecyclerView_Adapter;
import com.example.wurkout.Custom_RecyclerView.RecyclerView_Items;
import com.example.wurkout.Custom_RecyclerView.RV_Fragment;

public class WeightFragment extends RV_Fragment  {

    private double weight, reps;

    public interface OnListItemClickListener {
        void onListItemClick(int position);
    }

    private OnListItemClickListener zItemClickListener;

    public WeightFragment() {
        // Required empty public constructor
    }

    public static WeightFragment newInstance(double w, double r) {
        WeightFragment fragment = new WeightFragment();
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

    }

    // where graphical interface initialized
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_weight_list, container, false);
        buildRecyclerView(v);
        return v;
    }

    public void buildRecyclerView(View v) {
        zListView = v.findViewById(R.id.fragment_weight_list);
        zListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        zListAdapter = new RecyclerView_Adapter(zList, this);
        zListView.setAdapter(zListAdapter);

        createPercentageList();


    }

    public void createPercentageList() {
        // Brzycki Formula for onerm

        double onerm = weight * (36 / (37 - reps));
        for (int i=17; i>=0; i--) {
            double percentage = 1.25 - 0.05 * i;
            addItem(percentage, 0, onerm);
        }

        zListAdapter.notifyDataSetChanged();

    }

    public void addItem(double percentage, double calculatedReps, double onerm) {
        String line1 = (int)(Math.rint(onerm * percentage)) + " lbs"; // " for " + calculatedReps
        RecyclerView_Items item = new RecyclerView_Items(R.drawable.ic_fitness_black,
                R.drawable.ic_fitness_black, line1, (int)Math.round(percentage*100.0) + "%");
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
