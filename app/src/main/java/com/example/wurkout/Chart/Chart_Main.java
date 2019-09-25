package com.example.wurkout.Chart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.wurkout.Custom_RecyclerView.RV_Fragment;
import com.example.wurkout.Custom_RecyclerView.RecyclerView_Adapter;
import com.example.wurkout.Custom_RecyclerView.RecyclerView_Items;
import com.example.wurkout.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

public class Chart_Main extends RV_Fragment {

    private int id;

    public static Chart_Main newInstance() {
        return new Chart_Main();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        checkData();
        super.buildRecyclerView(v);
        OnItemClickListener();
        return v;
    }

    public void onPause() {
        super.onPause();

        String savedList = new Gson().toJson(zList);
        saveData("Chart", "Chart.txt", savedList);

    }

    public void checkData() {
        String data = super.checkData("Chart", "Chart.txt");
        if (!data.equals("[]")) {
            Gson gson = new Gson();
            Type typeToken = new TypeToken<ArrayList<RecyclerView_Items>>() {
            }.getType();
            zList = gson.fromJson(data, typeToken);
        }
    }

    public void OnItemClickListener() {

        zListAdapter.OnItemClickListener(new RecyclerView_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                startChart(position);

            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }

        });
        zAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNameAddItem(v);

            }
        });

    }

    public void startChart(int position) {
        Intent intent = new Intent(getActivity(), LineChart.class);
        id = zList.get(position).getIdentifier();

        String chartDescription = zList.get(position).getText1();

        intent.putExtra("id", id);
        intent.putExtra("chartDescription", chartDescription);

        startActivity(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public void getNameAddItem(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Chart Name");

        final EditText input = new EditText(v.getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String itemName = input.getText().toString();
                addItem(itemName);

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

    public void addItem(String userInput) {
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date());

        RecyclerView_Items item = new RecyclerView_Items(R.drawable.ic_line_black_24dp,
                R.drawable.ic_remove, userInput, date);
        item.setIdentifier(zList.size());
        item.setImage("");
        zList.add(item);
        zListAdapter.notifyDataSetChanged();
    }

    public void removeItem(int position) {

        id = zList.get(position).getIdentifier();

        super.removeItem(position);

        String subdirectory = "Chart";
        String fileName = "Chart" + id + ".txt";

        deleteData(subdirectory, fileName);

    }
}
