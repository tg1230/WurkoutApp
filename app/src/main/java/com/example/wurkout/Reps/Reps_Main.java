package com.example.wurkout.Reps;

import android.content.Context;
import android.os.Bundle;

import android.text.InputType;
import android.view.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.wurkout.Custom_RecyclerView.RecyclerView_Items;
import com.example.wurkout.Custom_RecyclerView.RecyclerView_Adapter;
import com.example.wurkout.R;
import com.example.wurkout.Custom_RecyclerView.RV_Fragment;
import com.example.wurkout.Reps.RepFragments.MainRepsActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Date;
import java.text.SimpleDateFormat;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;


public class Reps_Main extends RV_Fragment {

    // image overlay for list
    private ImageView zAddItem;

    public static Reps_Main newInstance() {

        return new Reps_Main();
    }

    @Override
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

        super.saveData("Reps", "Reps.txt", savedList);

    }

    public void checkData() {
        String data = super.checkData("Reps", "Reps.txt");
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
                if (zList.get(position).getMaxRep() == 0) {
                    getMaxRep(position);
                }
                else {
                    Intent intent = new Intent(getActivity(), MainRepsActivity.class);
                    Bundle b = new Bundle();

                    b.putDouble("weight", zList.get(position).getMaxWeight());
                    b.putDouble("reps", zList.get(position).getMaxRep());
                    intent.putExtras(b);
                    startActivity(intent);
                }
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



    // good templates for implementing dialogs in the future
    public void getMaxRep(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Enter max above and reps below to calculate percentages");
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.weight_rep_dialog, null));

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {

                    // for future reference - findViewByID requires context or it will return null
                    EditText maxWeight = ((AlertDialog) dialog).findViewById(R.id.maxweight);
                    EditText maxRep = ((AlertDialog) dialog).findViewById(R.id.maxreps);
                    if (maxWeight.getText() != null && maxRep.getText() != null) {

                        zList.get(position).setMaxWeight(Double.parseDouble(maxWeight.getText().toString()));
                        zList.get(position).setMaxRep(Double.parseDouble(maxRep.getText().toString()));
                        // if update or add items outside of this it doesn't update them properly
                        // unsure why or how to fix
                        changeItem(position);
                    }

                }
                // Integer.parseInt returns exception if non-int used
                catch (Exception NumberFormatException) {
                    zList.get(position).setMaxWeight(0);
                    zList.get(position).setMaxRep(0);
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

    // override as needed
    public void changeItem(int position) {
        String newText1 = zList.get(position).getText1() + " - " +
                zList.get(position).getMaxWeight() + " lbs for " + (int)Math.rint(zList.get(position).getMaxRep()) + " reps";
        zList.get(position).changeText1(newText1);
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date());
        zList.get(position).changeText2(date);
        zListAdapter.notifyItemChanged(position);
    }

    public void getNameAddItem(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Exercise Name");
        // Set up the input - interesting creation of EditText without file_paths
        final EditText input = new EditText(v.getContext());
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
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

        RecyclerView_Items item = new RecyclerView_Items(R.drawable.ic_child_care_black_24dp,
                R.drawable.ic_remove, userInput, date);
        item.setIdentifier(zList.size());
        item.setImage("");
        zList.add(item);
        zListAdapter.notifyDataSetChanged();
    }

    public void removeItem(int position) {
        super.removeItem(position);

    }


        @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

}
