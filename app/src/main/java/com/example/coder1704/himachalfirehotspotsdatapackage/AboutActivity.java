package com.example.coder1704.himachalfirehotspotsdatapackage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {

    EditText editdistrict, editabout;
    dbhelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        editdistrict = (EditText)findViewById(R.id.editText8);
        editabout = (EditText)findViewById(R.id.editText4);

        mydb = new dbhelper(this);
    }

    public void AddBtnAbout(View v) {
        //
        boolean isinserted = mydb.insertDataAbout(editdistrict.getText().toString(), editabout.getText().toString());
        if (isinserted == true)
            Toast.makeText(AboutActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(AboutActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
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
