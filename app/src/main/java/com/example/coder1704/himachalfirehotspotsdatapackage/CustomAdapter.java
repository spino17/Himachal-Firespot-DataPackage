package com.example.coder1704.himachalfirehotspotsdatapackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<News> {

    public CustomAdapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_layout, parent, false);
        }
        // Lookup view for data population
        TextView textheadline = (TextView) convertView.findViewById(R.id.textView2);
        TextView textcontent = (TextView) convertView.findViewById(R.id.textView16);
        // Populate the data into the template view using the data object
        textheadline.setText(news.headline);
        textcontent.setText(news.content);
        // Return the completed view to render on screen
        return convertView;

    }

    @Nullable
    @Override
    public News getItem(int position) {
        return super.getItem(position);
    }
}
