package com.example.wurkout.Custom_RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wurkout.Custom_RecyclerView.Custom_ItemTouchHelper.ItemTouchHelperCallback;
import com.example.wurkout.Custom_RecyclerView.Custom_ItemTouchHelper.OnStartDragListener;

import com.example.wurkout.GlobalApplication;
import com.example.wurkout.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import java.util.ArrayList;

/*
 All lists extend this class
 In the future, better planning out each different child class and their common attributes
 would create a cleaner parent class
 */

public class RV_Fragment extends Fragment implements OnStartDragListener {

    protected ArrayList<RecyclerView_Items> zList = new ArrayList<RecyclerView_Items>();
    protected RecyclerView zListView;
    protected RecyclerView_Adapter zListAdapter;
    protected ImageView zAddItem;
    private ItemTouchHelper zItemTouchHelper;

    public RV_Fragment() {
        // Required empty public constructor
    }

    // where values could be initialized from bundle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void onPause() {

        super.onPause();

    }

    public void saveData(String directory, String fileName, String data) {
        File file = new File(getContext().getFilesDir(),directory);
        if(!file.exists()){
            file.mkdir();
        }

        try{
            File gpxfile = new File(file, fileName);
            FileWriter writer = new FileWriter(gpxfile, false);
            writer.append(data);
            writer.flush();
            writer.close();

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public String checkData(String subdirectory, String fileName) {

        StringBuilder sb = new StringBuilder();
        String line;
        String savedList = "[]";
        try {
            File dir = new File(getContext().getFilesDir(), subdirectory);
            File data = new File(dir, fileName);
            FileInputStream fis = new FileInputStream(data);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            savedList = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return savedList;
    }

    public void deleteData(String directory, String fileToBeDeleted) {
        File dir = new File(getContext().getFilesDir(),directory);
        File data = new File(dir, fileToBeDeleted);
        boolean deleted = data.delete();
    }

    // where graphical interface initialized
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.recycler_list, container, false);
    }

    public void buildRecyclerView(View v) {

        zListView = v.findViewById(R.id.recycler_list);
        zListView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        zListAdapter = new RecyclerView_Adapter(zList, this);
        zListView.setAdapter(zListAdapter);
        zAddItem = v.findViewById(R.id.imageView3);

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(zListAdapter);
        zItemTouchHelper = new ItemTouchHelper(callback);
        zItemTouchHelper.attachToRecyclerView(zListView);

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        zItemTouchHelper.startDrag(viewHolder);
    }

    // This is overwritten for most of the child lists because the title needed to be different
    // Having the child lists pass a string is the obvious solution to this redundancy
    // Need to be more aware of silly redundancies like this
    public void getNameAddItem(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Workout Name");
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
        RecyclerView_Items item = new RecyclerView_Items(R.drawable.ic_fitness_black,
                R.drawable.ic_remove, userInput, "");
        item.setIdentifier(zList.size());
        item.setImage("");
        zList.add(item);
        zListAdapter.notifyDataSetChanged();
    }

    public void removeItem(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Remove " + "\"" + zList.get(position).getText1() + "\""+ "?");

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Context context = GlobalApplication.getAppContext();
                Toast.makeText(context, "\"" + zList.get(position).getText1() +
                        "\"" + " removed", Toast.LENGTH_LONG).show();

                zList.remove(position);
                zListAdapter.notifyItemRemoved(position);
                zListAdapter.notifyItemRangeChanged(position, zList.size());

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

    // make sure something is entered and there isn't already a description
    public void changeItem(final int position, View v) {
        if (zList.get(position).getWorkoutDescription() == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Enter Description");
            LayoutInflater inflater = this.getLayoutInflater();

            builder.setView(inflater.inflate(R.layout.workout_description_dialog, null));

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    EditText workoutDescription = ((AlertDialog) dialog).findViewById(R.id.workoutDescription);
                    if (workoutDescription.getText() != null) {
                        zList.get(position).setWorkoutDescription(workoutDescription.getText().toString());
                        updateItem(position);

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
    }


    public void updateItem(int position) {
        String newText1 = zList.get(position).getText1() + " - " +
                zList.get(position).getWorkoutDescription();
        zList.get(position).changeText1(newText1);
        zListAdapter.notifyItemChanged(position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
}
