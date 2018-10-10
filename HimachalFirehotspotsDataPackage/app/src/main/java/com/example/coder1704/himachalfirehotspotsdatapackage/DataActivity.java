package com.example.coder1704.himachalfirehotspotsdatapackage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DataActivity extends AppCompatActivity {

    EditText editdistrict, editarea, editdense, editopen, editmoderate, edittotal;
    //TextView textabout;
    dbhelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        mydb = new dbhelper(this);

        editdistrict = (EditText)findViewById(R.id.editText8);
        editarea = (EditText)findViewById(R.id.editText11);
        editdense = (EditText) findViewById(R.id.editText10);
        editopen = (EditText)findViewById(R.id.editText9);
        editmoderate = (EditText)findViewById(R.id.editText19);
        edittotal = (EditText)findViewById(R.id.editText20);
        //textabout = (TextView)findViewById(R.id.textView);
    }

    public void AddBtnGeo(View v) {
        //
        boolean isinserted = mydb.insertDataGeo(editdistrict.getText().toString(), Float.parseFloat(editarea.getText().toString()), Float.parseFloat(editdense.getText().toString()), Float.parseFloat(editopen.getText().toString()), Float.parseFloat(editmoderate.getText().toString()), Float.parseFloat(edittotal.getText().toString()));
        if (isinserted == true)
            Toast.makeText(DataActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(DataActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
    }

    public void Show(View v) {
        //
        String data = mydb.ShowData();
        ///TextView display = (TextView) findViewById(R.id.textView2);
        //display.setText(data);
    }

    public void DeleteBt(View v) {

        mydb.DeleteEntry(editdistrict.getText().toString());
    }
}
