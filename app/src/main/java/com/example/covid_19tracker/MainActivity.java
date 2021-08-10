package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    public Button button;
    public TextView totalCases;
    public TextView recovered;
    public TextView critical;
    public TextView active;
    public TextView casesToday;
    public TextView totalDeaths;
    public TextView todayDeaths;
    public TextView affectedContries;

    public ProgressBar progressBar;
    public RelativeLayout layout;
    public PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progresBar);
        layout = findViewById(R.id.layout);
        pieChart = findViewById(R.id.pieChart);

        totalCases = findViewById(R.id.totalCases);
        recovered = findViewById(R.id.recovered);
        critical = findViewById(R.id.critical);
        active = findViewById(R.id.active);
        casesToday = findViewById(R.id.casesToday);
        totalDeaths = findViewById(R.id.totalDeaths);
        todayDeaths = findViewById(R.id.todayDeaths);
        affectedContries = findViewById(R.id.affectedContries);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AffectedCountries.class));
            }
        });

        fetchData();
    }

    public void fetchData(){

        String url = "https://corona.lmao.ninja/v2/all/";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());

                            progressBar.setVisibility(View.INVISIBLE);
                            layout.setVisibility(View.VISIBLE);

                            totalCases.setText(jsonObject.getString("cases"));
                            recovered.setText(jsonObject.getString("recovered"));
                            critical.setText(jsonObject.getString("critical"));
                            active.setText(jsonObject.getString("active"));
                            casesToday.setText(jsonObject.getString("todayCases"));
                            totalDeaths.setText(jsonObject.getString("deaths"));
                            todayDeaths.setText(jsonObject.getString("todayDeaths"));
                            affectedContries.setText(jsonObject.getString("affectedCountries"));

                            pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(recovered.getText().toString()), Color.parseColor("#66BB6A")));
                            pieChart.addPieSlice(new PieModel("Deaths", Integer.parseInt(totalDeaths.getText().toString()), Color.parseColor("#EF5350")));
                            pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(active.getText().toString()),Color.parseColor("#29B6F6")));
                            pieChart.startAnimation();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("Mytag", error.getMessage());
            }
        });

        RequestQueue VolleyRequestqueue = Volley.newRequestQueue(MainActivity.this);
        VolleyRequestqueue.add(request);
    }
}

