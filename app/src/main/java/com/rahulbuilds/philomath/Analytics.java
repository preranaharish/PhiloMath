package com.rahulbuilds.philomath;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Analytics extends AppCompatActivity {
HorizontalBarChart horizontalChart;
String synonym1,synonym2,synonym3,synonym4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            synonym1 = extras.getString("synonyms_array1");
            synonym2=extras.getString("synonyms_array2");
            synonym3=extras.getString("synonyms_array3");
            synonym4=extras.getString("synonyms_array4");
        }
        horizontalChart = (HorizontalBarChart)findViewById(R.id.barchart);
        BarDataSet barDataSet = new BarDataSet(getData(), "Words");
        barDataSet.setBarBorderWidth(0.9f);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData = new BarData(barDataSet);
        XAxis xAxis = horizontalChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        final String[] months = new String[]{synonym1, synonym2, synonym3,synonym4};
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(months);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        horizontalChart.setData(barData);
        horizontalChart.setNoDataTextColor(2);
        horizontalChart.setFitBars(true);
        horizontalChart.getAxisLeft().setTextColor(getResources().getColor(R.color.thumb_inactive));
        horizontalChart.getAxisLeft().setAxisLineColor(getResources().getColor(R.color.thumb_inactive));
        horizontalChart.getAxisRight().setTextColor(getResources().getColor(R.color.thumb_inactive));
        horizontalChart.getAxisRight().setAxisLineColor(getResources().getColor(R.color.thumb_inactive));
        horizontalChart.getXAxis().setTextColor(getResources().getColor(R.color.thumb_inactive));
horizontalChart.getXAxis().setTextSize(30);
        horizontalChart.animateXY(5000, 5000);
        horizontalChart.invalidate();
    }

    private ArrayList getData(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        return entries;
    }
}