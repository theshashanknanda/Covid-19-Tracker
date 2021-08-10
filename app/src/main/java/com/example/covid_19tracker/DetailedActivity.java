package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class DetailedActivity extends AppCompatActivity {

    public TextView totalCases;
    public TextView recovered;
    public TextView critical;
    public TextView active;
    public TextView casesToday;
    public TextView totalDeaths;
    public TextView todayDeaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        totalCases = findViewById(R.id.totalCases_id);
        recovered = findViewById(R.id.recovered_id);
        critical = findViewById(R.id.critical_id);
        active = findViewById(R.id.active_id);
        casesToday = findViewById(R.id.casesToday_id);
        totalDeaths = findViewById(R.id.totalDeaths_id);
        todayDeaths = findViewById(R.id.todayDeaths_id);

        getData();
    }

    public void getData(){
        String countryName = getIntent().getStringExtra("country");
        String totalCasesString = getIntent().getStringExtra("totalCases");
        String recoveredString = getIntent().getStringExtra("recovered");
        String criticalString = getIntent().getStringExtra("critical");
        String activeString = getIntent().getStringExtra("active");
        String casesTodayString = getIntent().getStringExtra("casesToday");
        String totalDeathsString = getIntent().getStringExtra("totalDeaths");
        String todayDeathsString = getIntent().getStringExtra("todayDeaths");

        getSupportActionBar().setTitle(countryName);

        totalCases.setText(totalCasesString);
        recovered.setText(recoveredString);
        critical.setText(criticalString);
        active.setText(activeString);
        casesToday.setText(casesTodayString);
        totalDeaths.setText(totalDeathsString);
        todayDeaths.setText(todayDeathsString);
    }
}
