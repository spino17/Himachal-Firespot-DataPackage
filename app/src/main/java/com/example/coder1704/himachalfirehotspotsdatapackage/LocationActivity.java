package com.example.coder1704.himachalfirehotspotsdatapackage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LocationActivity extends AppCompatActivity {


    EditText editlocation, editdistrict;
    dbhelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mydb = new dbhelper(this);

        editlocation = (EditText)findViewById(R.id.editText8);
        editdistrict = (EditText)findViewById(R.id.editText11);
    }

    public void AddBtnLoc(View v) {
        //
        boolean isinserted = mydb.insertDataLoc(editlocation.getText().toString(), editdistrict.getText().toString());
        if (isinserted == true)
            Toast.makeText(LocationActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(LocationActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
    }

    public void Show(View v) {
        //
        String data = mydb.ShowData();
        ///TextView display = (TextView) findViewById(R.id.textView2);
        //display.setText(data);
    }

    public void DeleteBt(View v) {

        mydb.DeleteEntry(editlocation.getText().toString());
    }
}
