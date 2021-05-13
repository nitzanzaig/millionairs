package com.example.millionairs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    AnyChartView anyChartView;
    String[] months = {"Jan", "Feb", "Mar"};
    int[] earnings = {500, 600, 800};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anyChartView = findViewById(R.id.any_chart_view);
        setupPiechart();
    }

    public void setupPiechart() {
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
        for (int i=0; i<months.length; i++){
            dataEntries.add(new ValueDataEntry(months[i],earnings[i]));

        }
        pie.data(dataEntries);
        anyChartView.setChart(pie);
    }


    public void move(View view){
        //Intent intent = new Intent(getApplicationContext(), personaldetails.class);
        //startActivity(intent);
        Intent intent = new Intent(getApplicationContext(), logActivity.class);
        startActivity(intent);
    }

    public void income(View view){
        //Intent intent = new Intent(getApplicationContext(), income.class);
        //startActivity(intent);
        Intent intent = new Intent(getApplicationContext(), incomeActivity.class);
        startActivity(intent);
    }
    public void budget(View view){
        //Intent intent = new Intent(getApplicationContext(), budget.class);
        //startActivity(intent);
        Intent intent = new Intent(getApplicationContext(), logActivity.class);
        startActivity(intent);
    }
    public void expenses(View view){
        //Intent intent = new Intent(getApplicationContext(), budget.class);
        //startActivity(intent);
        Intent intent = new Intent(getApplicationContext(), income_expensesActivity.class);
        startActivity(intent);
    }

    public void bot(View view){
        //Intent intent = new Intent(getApplicationContext(), bot.class);
        //startActivity(intent);
        Intent intent = new Intent(getApplicationContext(), logActivity.class);
        startActivity(intent);
    }

}