package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AffectedCountries extends AppCompatActivity {

    public EditText editText;
    public ListView listView;
    public ProgressBar progressBar;
    public ImageView imageView;

    public List<Model> modelList;
    public List<Model> modelList2;
    public Model model;
    public Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_countries);

        editText = findViewById(R.id.searchEditText);
        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progresBar2);
        imageView = findViewById(R.id.imageView);

        modelList = new ArrayList<>();
        modelList2 = new ArrayList<>();

        getSupportActionBar().setTitle("Affected Countries");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fetchData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Model model = modelList.get(position);
                Intent intent = new Intent(AffectedCountries.this, DetailedActivity.class);
                intent.putExtra("country",model.getCountry());
                intent.putExtra("totalCases",model.getCases());
                intent.putExtra("recovered",model.getRecovered());
                intent.putExtra("critical",model.getCritical());
                intent.putExtra("active",model.getActive());
                intent.putExtra("casesToday",model.getTodayCases());
                intent.putExtra("totalDeaths",model.getDeaths());
                intent.putExtra("todayDeaths",model.getTodayDeaths());

                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        adapter = new Adapter(AffectedCountries.this, modelList);
        listView.setAdapter(adapter);
    }

    public void fetchData(){
        String url = "https://corona.lmao.ninja/v2/countries/";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray  = new JSONArray(response);

                            for(int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                JSONObject object = jsonObject.getJSONObject("countryInfo");

                                model = new Model();
                                model.setFlag(object.getString("flag"));

                                model.setCountry(jsonObject.getString("country"));
                                model.setCases(jsonObject.getString("cases"));
                                model.setTodayCases(jsonObject.getString("todayCases"));
                                model.setDeaths(jsonObject.getString("deaths"));
                                model.setTodayDeaths(jsonObject.getString("todayDeaths"));
                                model.setRecovered(jsonObject.getString("recovered"));
                                model.setActive(jsonObject.getString("active"));
                                model.setCritical(jsonObject.getString("critical"));

                                modelList.add(model);
                            }
                            progressBar.setVisibility(View.INVISIBLE);
                            listView.setVisibility(View.VISIBLE);

                            adapter = new Adapter(AffectedCountries.this, modelList);
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Mytag",error.getMessage());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(AffectedCountries.this);
        requestQueue.add(stringRequest);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                modelList2.clear();

                for(Model model2: modelList)
                {
                    if(model2.getCountry().toLowerCase().trim().equals(editText.getText().toString().toLowerCase().trim())){
                        modelList2.add(model2);
                        Adapter adapter = new Adapter(AffectedCountries.this, modelList2);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Model model = modelList2.get(position);
                        Intent intent = new Intent(AffectedCountries.this, DetailedActivity.class);
                        intent.putExtra("country",model.getCountry());
                        intent.putExtra("totalCases",model.getCases());
                        intent.putExtra("recovered",model.getRecovered());
                        intent.putExtra("critical",model.getCritical());
                        intent.putExtra("active",model.getActive());
                        intent.putExtra("casesToday",model.getTodayCases());
                        intent.putExtra("totalDeaths",model.getDeaths());
                        intent.putExtra("todayDeaths",model.getTodayDeaths());

                        startActivity(intent);
                    }
                });
            }
        });
    }
}
