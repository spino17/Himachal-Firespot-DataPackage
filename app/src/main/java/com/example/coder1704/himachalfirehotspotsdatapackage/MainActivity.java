package com.example.coder1704.himachalfirehotspotsdatapackage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int ERROR_DIALOG_REQUEST = 9001;

    dbhelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean ok = isServicesOK();

        mydb = new dbhelper(this);

        getJSON("http://hfdp.000webhostapp.com/readgeog.php");
        //getJSON1("http://hfdp.000webhostapp.com/readdist.php");
        getJSON2("http://hfdp.000webhostapp.com/readabout.php");
        getJSON3("http://hfdp.000webhostapp.com/readnews.php");

        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //
                //Toast.makeText(getApplicationContext(), "hahaha", Toast.LENGTH_SHORT).show();
                //News item = parent.getItemAtPosition(position);
                News newsobj = (News) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this,NewsActivity.class);
                intent.putExtra("headline", newsobj.headline);
                intent.putExtra("content", newsobj.content);
                //based on item add info to intent
                startActivity(intent);
            }
        });
    }

    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] district = new String[jsonArray.length()];
        String[] area = new String[jsonArray.length()];
        String[] dense = new String[jsonArray.length()];
        String[] open = new String[jsonArray.length()];
        String[] moderate = new String[jsonArray.length()];
        String[] total = new String[jsonArray.length()];
        mydb.TruncateTable();
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject obj = jsonArray.getJSONObject(i);
            district[i] = obj.getString("DISTRICT"); // apply insert in this for your database
            Log.d("TAG", district[i]);
            area[i] = obj.getString("AREA"); // apply insert in this for your database
            Log.d("TAG", area[i]);
            dense[i] = obj.getString("DENSE");
            Log.d("TAG", dense[i]);
            open[i] = obj.getString("OPEN");
            Log.d("TAG", open[i]);
            moderate[i] = obj.getString("MODERATE");
            Log.d("TAG", moderate[i]);
            total[i] = obj.getString("TOTAL");
            Log.d("TAG", total[i]);

            Boolean isInserted = mydb.insertDataGeo(district[i], Float.parseFloat(area[i]), Float.parseFloat(dense[i]), Float.parseFloat(open[i]), Float.parseFloat(moderate[i]), Float.parseFloat(total[i]));
            if (isInserted == true) {
                //Toast.makeText(MainActivity.this, "geo data inserted", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(MainActivity.this, "geo data not inserted", Toast.LENGTH_SHORT).show();
            }
                //Toast.makeText(MainActivity.this, "data not inserted", Toast.LENGTH_SHORT).show();
        }
        //Log.d("TAG", district[1]);
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, heroes);
        //listView.setAdapter(arrayAdapter);
    }

    private void getJSON1(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView1(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView1(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] location = new String[jsonArray.length()];
        String[] district = new String[jsonArray.length()];
        ArrayList<String> places = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            location[i] = obj.getString("LOCATION"); // apply insert in this for your database
            Log.d("location", location[i]);
            district[i] = obj.getString("DISTRICT"); // apply insert in this for your database
            Log.d("district", district[i]);
            places.add(location[i]);
            mydb.TruncateTable1();
            Boolean isInserted = mydb.insertDataLoc(location[i], district[i]);
            if (isInserted == true) {
                //Toast.makeText(MainActivity.this, "data inserted", Toast.LENGTH_SHORT).show();
            } else {
                //
            }
                //Toast.makeText(MainActivity.this, "data not inserted", Toast.LENGTH_SHORT).show();
        }
        //Log.d("TAG", district[1]);
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, heroes);
        //listView.setAdapter(arrayAdapter);
    }

    private void getJSON2(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView2(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView2(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] district = new String[jsonArray.length()];
        String[] about = new String[jsonArray.length()];
        mydb.TruncateTable2();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            district[i] = obj.getString("DISTRICT"); // apply insert in this for your database
            Log.d("about: district", district[i]);
            about[i] = obj.getString("ABOUT"); // apply insert in this for your database
            Log.d("about: about", about[i]);

            Boolean isInserted = mydb.insertDataAbout(district[i], about[i]);
            if (isInserted == true) {
                //Toast.makeText(MainActivity.this, "data inserted", Toast.LENGTH_SHORT).show();
            } else {
                //
            }
            //Toast.makeText(MainActivity.this, "data not inserted", Toast.LENGTH_SHORT).show();
        }
        //Log.d("TAG", district[1]);
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, heroes);
        //listView.setAdapter(arrayAdapter);
    }



    private void getJSON3(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView3(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView3(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] headline = new String[jsonArray.length()];
        String[] content = new String[jsonArray.length()];

        ArrayList<News> news = new ArrayList<>();


        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject obj = jsonArray.getJSONObject(i);
            headline[i] = obj.getString("HEADLINE"); // apply insert in this for your database
            Log.d("TAG", headline[i]);
            content[i] = obj.getString("CONTENT"); // apply insert in this for your database
            Log.d("TAG", content[i]);
            News newsobj = new News(headline[i], content[i]);
            news.add(newsobj);

        }

        CustomAdapter adapter = new CustomAdapter(this, news);
        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);

    }

    public void MapActivityBtn(View v) {
        //

        Button button = (Button) findViewById(R.id.btnmap);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean isServicesOK() {

        Log.d(TAG, "isServicesOK: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            // everything is ok
            Log.d(TAG, "isServicesOK: google play services is working!");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isServicesOK: an error occured that is resolvable!");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else {
            Toast.makeText(this, "you can't make this work!", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    public void AdminLogin(View v) {
        Intent intent;
        intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
