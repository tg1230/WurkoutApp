package com.example.wurkout.Chart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wurkout.R;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;

import lecho.lib.hellocharts.view.LineChartView;

public class LineChart extends AppCompatActivity {

    LineChartView lineChartView;
    private ArrayList<String> xData = new ArrayList<>();
    private ArrayList<Integer> yData = new ArrayList<>();
    private int id;
    private ImageView zAddItem;
    private ImageView zRemoveItem;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_chart);

        Intent i = getIntent();
        id = i.getIntExtra("id", -1);
        String chartDescription = i.getStringExtra("chartDescription");
        final TextView textViewToChange = (TextView) findViewById(R.id.chartDescription);
        textViewToChange.setText(chartDescription);

        zAddItem = findViewById(R.id.imageView3);
        zRemoveItem = findViewById(R.id.imageView4);
        checkData();
        setUpLineChart();
        OnItemClickListener();

    }

    public void onPause() {
        super.onPause();

        saveData();

    }

    public void saveData() {
        String xdata = new Gson().toJson(xData);
        String ydata = new Gson().toJson(yData);

        String xydata = xdata + "!asdc3a3g!" + ydata;
        String fileName = "Chart" + id + ".txt";

        File dir = new File(this.getFilesDir(), "Chart");
        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            File gpxfile = new File(dir, fileName);
            FileWriter writer = new FileWriter(gpxfile, false);
            writer.append(xydata);
            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void checkData() {

        String subdirectory = "Chart";
        String fileName = "Chart" + id + ".txt";

        StringBuilder sb = new StringBuilder();
        String line;
        try {
            File dir = new File(this.getFilesDir(), subdirectory);
            File data = new File(dir, fileName);
            FileInputStream fis = new FileInputStream(data);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            String savedList = sb.toString();

            String[] xy = savedList.split("!asdc3a3g!");
            String x = xy[0];
            String y = xy[1];
            Gson gson = new Gson();
            Type typeToken = new TypeToken<ArrayList<String>>() {
            }.getType();
            xData = gson.fromJson(x, typeToken);

            Gson gson2 = new Gson();
            Type typeToken2 = new TypeToken<ArrayList<Integer>>() {
            }.getType();
            yData = gson2.fromJson(y, typeToken2);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void OnItemClickListener() {


        zRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lineChartView.getSelectedValue().isSet()) {
                    int a = lineChartView.getSelectedValue().getFirstIndex();
                    // point index is second index
                    int b = lineChartView.getSelectedValue().getSecondIndex();
                    removePoint(a, b);
                }
            }
        });

        zAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWeightAndDate();
            }
        });
    }

    public void getWeightAndDate() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (xData.size() < 2) {
            builder.setTitle("Add a Weight and Date below - two points needed for graph to appear");
        } else {
            builder.setTitle("Add a Weight and Date below");
        }
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.chart_add_dialog, null));

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText weight = ((AlertDialog) dialog).findViewById(R.id.chartWeight);
                EditText date = ((AlertDialog) dialog).findViewById(R.id.chartDate);
                if (!weight.getText().toString().equals("") && !date.getText().toString().equals("")) {

                    int w = Integer.parseInt(weight.getText().toString());
                    String d = date.getText().toString();

                    yData.add(w);
                    xData.add(d);
                    setUpLineChart();

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

    public void setUpLineChart() {

        lineChartView = findViewById(R.id.chart);

        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();

        // line will hold values of y axis
        Line line = new Line(yAxisValues).setColor(Color.parseColor("#00574B"));

        // add data to chart given it's x and y axis

        for (int i = 0; i < xData.size(); i++) {
            axisValues.add(i, new AxisValue(i).setLabel(xData.get(i)));
        }

        for (int i = 0; i < yData.size(); i++) {
            yAxisValues.add(new PointValue(i, yData.get(i)));
        }

        // holds all differnet lines of chart
        List lines = new ArrayList();
        lines.add(line);

        // now add the lines to chart
        LineChartData data = new LineChartData();
        data.setLines(lines);

        // set the axis values and position at bottom
        Axis axis = new Axis();
        axis.setValues(axisValues);
        // change text size
        axis.setTextSize(16);
        // change color
        axis.setTextColor(Color.parseColor("#008577"));
        data.setAxisXBottom(axis);
        axis.setName("Date");

        // set yaxis values
        Axis yAxis = new Axis();
        yAxis.setTextColor(Color.parseColor("#008577"));
        yAxis.setTextSize(16);
        // lable yaxis
        yAxis.setName("lbs");

        data.setAxisYLeft(yAxis);

        lineChartView.setLineChartData(data);
        lineChartView.setValueSelectionEnabled(true);

    }

    public void removePoint(final int lineIndex, final int pointIndex) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove Point?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                yData.remove(pointIndex);
                xData.remove(lineIndex);
                setUpLineChart();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        builder.show();
    }


}
