package com.example.coder1704.himachalfirehotspotsdatapackage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {
    dbhelper mydb;
    TextView subtitle1, subtitle2, subtitle3, subtitle4, subtitle5, subtitle6;
    TextView subtitle7, subtitle8, subtitle9, subtitle10, subtitle11, subtitle12;
    TextView view18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        mydb = new dbhelper(this);

        // set the data to the view onCreate
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("field"); // geographics

        Bundle bundle1 = getIntent().getExtras();
        String district = bundle.getString("district"); // district clicked

        Log.d("TAG", district);

        TextView texttitle = (TextView)findViewById(R.id.textView);
        texttitle.setText(title);

        if (title.equals("Geographics")) {
            //
            subtitle1 = (TextView)findViewById(R.id.textView3);
            subtitle1.setText("District");

            subtitle2 = (TextView)findViewById(R.id.textView5);
            subtitle2.setText("Geographical Area");

            subtitle3 = (TextView)findViewById(R.id.textView7);
            subtitle3.setText("Dense Forest");

            subtitle4 = (TextView)findViewById(R.id.textView9);
            subtitle4.setText("Open Forest");

            subtitle5 = (TextView)findViewById(R.id.textView11);
            subtitle5.setText("Moderate forest");

            subtitle6 = (TextView)findViewById(R.id.textView13);
            subtitle6.setText("Total");


            GeoClass myobj = mydb.GeoAllData(district);

            TextView textdistrict = (TextView)findViewById(R.id.textView4);
            textdistrict.setText(myobj.getDistrict());

            TextView textarea = (TextView)findViewById(R.id.textView6);
            textarea.setText(Float.toString(myobj.getArea()));

            TextView textdense = (TextView)findViewById(R.id.textView8);
            textdense.setText(Float.toString(myobj.getDense()));

            TextView textopen = (TextView)findViewById(R.id.textView10);
            textopen.setText(Float.toString(myobj.getOpen()));

            TextView textmoderate = (TextView)findViewById(R.id.textView12);
            textmoderate.setText(Float.toString(myobj.getModerate()));

            TextView texttotal = (TextView)findViewById(R.id.textView14);
            texttotal.setText(Float.toString(myobj.getTotal()));
        }

        else if (title.equals("About")) {
            //
            subtitle1 = (TextView)findViewById(R.id.textView3);
            subtitle1.setText(district);

            subtitle2 = (TextView)findViewById(R.id.textView5);
            subtitle2.setText("");

            subtitle3 = (TextView)findViewById(R.id.textView7);
            subtitle3.setText("");

            subtitle4 = (TextView)findViewById(R.id.textView9);
            subtitle4.setText("");

            subtitle5 = (TextView)findViewById(R.id.textView11);
            subtitle5.setText("");

            subtitle6 = (TextView)findViewById(R.id.textView13);
            subtitle6.setText("");

            subtitle7 = (TextView)findViewById(R.id.textView6);
            subtitle7.setText("");

            subtitle8 = (TextView)findViewById(R.id.textView8);
            subtitle8.setText("");

            subtitle9 = (TextView)findViewById(R.id.textView10);
            subtitle9.setText("");

            subtitle10 = (TextView)findViewById(R.id.textView12);
            subtitle10.setText("");

            subtitle11 = (TextView)findViewById(R.id.textView14);
            subtitle11.setText("");

            view18 = (TextView)findViewById(R.id.textView18);
            view18.setText("");

            AboutInfo myobj = mydb.AboutAllData(district);


            TextView textabout = (TextView)findViewById(R.id.textView4);
            textabout.setText(myobj.getAbout());

        }

    }
}
