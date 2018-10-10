package com.example.coder1704.himachalfirehotspotsdatapackage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainInfo extends AppCompatActivity {

    TextView editf1, editf2, editf3, editf4, editf5, editf6;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_info);

        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("message"); // district name

        TextView titletext = (TextView)findViewById(R.id.textView);
        titletext.setText(title);

        editf1 = (TextView) findViewById(R.id.textView3);
        editf2 = (TextView) findViewById(R.id.textView5);
        editf3 = (TextView) findViewById(R.id.textView7);
        editf4 = (TextView) findViewById(R.id.textView9);
        editf5 = (TextView) findViewById(R.id.textView11);
        editf6 = (TextView) findViewById(R.id.textView13);

        editf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform when the textview is clicked!
                String option1 = editf1.getText().toString();
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.putExtra("field", option1); // field chosen here it's geographic
                intent.putExtra("district", title);
                startActivity(intent);
            }
        });

        editf2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform when the textview is clicked!
            }
        });

        editf3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform when the textview is clicked!
            }
        });

        editf4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform when the textview is clicked!
            }
        });
    }
}
