package com.example.coder1704.himachalfirehotspotsdatapackage;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    EditText editusername, editpassword;
    TextView errormessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editusername = (EditText)findViewById(R.id.editText);
        editpassword = (EditText)findViewById(R.id.editText2);
        errormessage= (TextView)findViewById(R.id.textView15);
    }

    public void LoginBtn(View v) {
        //
        if(editusername.getText().toString().equals("admin") && editpassword.getText().toString().equals("password")) {
            Log.d("message", "LoginBtn: matched!");
            Toast.makeText(getApplicationContext(), "redirecting...", Toast.LENGTH_LONG);
            Intent intent = new Intent(this, DataActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_LONG);
            errormessage.setText("incorrect username or password");
        }
    }

    public void CancelBtn(View v) {
        //
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
