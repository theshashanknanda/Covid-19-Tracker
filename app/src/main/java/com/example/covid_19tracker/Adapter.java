package com.example.covid_19tracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends ArrayAdapter {

    public List<Model> modelList;
    public Context context;

    public Adapter(@NonNull Context context, List<Model> modelList) {
        super(context, R.layout.custom_view, modelList);

        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view, null, false);
        TextView textView = view.findViewById(R.id.countryName);
        ImageView imageView = view.findViewById(R.id.flagImageView);

        textView.setText(modelList.get(position).getCountry());
        Glide.with(context).load(modelList.get(position).getFlag()).into(imageView);

        return view;
    }
}

