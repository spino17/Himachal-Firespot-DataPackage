package com.example.coder1704.himachalfirehotspotsdatapackage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NewsActivity extends AppCompatActivity {

    TextView textheadline, textcontent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Bundle bundle = getIntent().getExtras();
        String headline = bundle.getString("headline"); // district name

        Bundle bundle1 = getIntent().getExtras();
        String content = bundle.getString("content"); // district name

        textheadline = (TextView)findViewById(R.id.textView20);
        textheadline.setText(headline);

        textcontent = (TextView)findViewById(R.id.textView21);
        textcontent.setText(content);
    }
}
