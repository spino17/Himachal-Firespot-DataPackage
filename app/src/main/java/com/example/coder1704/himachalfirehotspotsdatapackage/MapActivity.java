package com.example.coder1704.himachalfirehotspotsdatapackage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private Boolean mLocationPermissionGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationCLient;

    private static final float DEFAULT_ZOOM = 7f;

    dbhelper mydb;
    SQLiteDatabase db;

    ArrayList<String> places;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mydb = new dbhelper(this);

        getLocationPermission();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // method to make changes and additions to the displayed map
        Toast.makeText(this, "Map is Ready!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready!");
        mMap = googleMap;

        if (mLocationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            //places = new ArrayList<>(); // would call a function from database to get the location
            /*places.add("kamand");
            places.add("udaipur");
            places.add("ahmedabad");*/
            //places = mydb.PlaceData();
            //places = new ArrayList<>();
            //places = mydb.PlaceData();
            //places.add("jamla");

            getJSON1("http://hfdp.000webhostapp.com/readdist.php");

            //init(places); // pass the list of places to add the marker to the locations and make marker clickable

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Log.d(TAG, "marker is clicked!");
                    String placetitle = marker.getTitle();
                    Log.d(TAG, "clicked location name is " + placetitle);
                    Intent intent;
                    intent = new Intent(getApplicationContext(), MainInfo.class);
                    intent.putExtra("message", placetitle);
                    startActivity(intent);
                }
            });
        }
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
            places.add(district[i]);

            mydb.TruncateTable1();
            Boolean isInserted = mydb.insertDataLoc(location[i], district[i]);
            if (isInserted == true) {
                //Toast.makeText(MapActivity.this, "data inserted", Toast.LENGTH_SHORT).show();
            } else {
                //
            }
                //Toast.makeText(MapActivity.this, "data not inserted", Toast.LENGTH_SHORT).show();
        }

        init(places);
        //Log.d("TAG", district[1]);
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, heroes);
        //listView.setAdapter(arrayAdapter);
    }

    private void init(ArrayList<String> places) {
        //
        Log.d(TAG, "init: intializing!");
        geoLocate(places);
    }

    private void geoLocate(ArrayList<String> places) {
        //
        Log.d(TAG, "geoLocate: geolocating the string!");
        String placestring;
        //Log.d("TAG", places.get(0));
        Geocoder geocoder = new Geocoder(MapActivity.this);

        /*// start from here
        String query = "Select * FROM " + "locdistrict";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            placestring = cursor.getString(2);
            Log.d("TAG", "item added in list!");
            List<Address> list = new ArrayList<>();
            try {
                //
                list = geocoder.getFromLocationName(placestring, 1);
                Log.d("TAG", "try!");

            }catch (IOException e) {
                //
                Log.d(TAG, "geolocate: IOexception: " + e.getMessage());
            }
            Log.d("TAG", "size of the string " + Integer.toString(list.size()));
            if (list.size() > 0) {

                //Address address = list.get(0);
                //Log.d("TAG", "postal address" + address.getAdminArea());
                for (int j = 0; j < list.size(); j++) {
                    Address address = list.get(j);
                    if (address.getAdminArea().equals("Himachal Pradesh")) {
                        //
                        Log.d(TAG, "geoLocate: found" + address.toString());
                        moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, placestring);
                        break;
                    }
                }
                //Log.d(TAG, "geoLocate: found" + address.toString());

                //moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, placestring);
            }
        }
        cursor.close();
        db.close();




        // end here*/

       for (int i = 0; i < places.size(); i++) {
            Log.d("TAG", "inside the loop");
            placestring = places.get(i);
            List<Address> list = new ArrayList<>();
            try {
                //
                list = geocoder.getFromLocationName(placestring, 1);
                Log.d("TAG", "try!");

            }catch (IOException e) {
                //
                Log.d(TAG, "geolocate: IOexception: " + e.getMessage());
            }
            Log.d("TAG", "size of the string " + Integer.toString(list.size()));
            if (list.size() > 0) {

                Address address = list.get(0);
                Log.d("TAG", "postal address" + address.getAdminArea());
                /*for (int j = 0; j < list.size(); j++) {
                    Address address = list.get(j);
                    if (address.getAdminArea().equals("Himachal Pradesh")) {
                        //
                        Log.d(TAG, "geoLocate: found" + address.toString());
                        moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, placestring);
                        break;
                    }
                }*/
                Log.d(TAG, "geoLocate: found" + address.toString());

                moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, placestring);
            }
        }

       /* List<Address> list = new ArrayList<>();
        try {
            //
            list = geocoder.getFromLocationName(placestring, 1);

        }catch (IOException e) {
            //
            Log.d(TAG, "geolocate: IOexception: " + e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);
            Log.d(TAG, "geoLocate: found a location!" + address.toString());

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));
        }*/

        // we would also move the camera.
    }


    private void getDeviceLocation() {
        Log.d(TAG, "getting the current device location!");
        mFusedLocationCLient = LocationServices.getFusedLocationProviderClient(this);
        try {
            //
            if(mLocationPermissionGranted){
                Task location = mFusedLocationCLient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        //
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location)task.getResult();
                            Log.d(TAG, "hshahsh: " + Integer.toString((int) currentLocation.getTime()));
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM, "my location");
                        }else {
                            Log.d(TAG, "onComplete current location is null!");
                            Toast.makeText(MapActivity.this, "unable to get the current location!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        //
        Log.d(TAG, "moveCamera: moving the camera to : Lat " + latLng.latitude + ", Lng " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(title);
        Marker marker = mMap.addMarker(options);
    }



    private void initMap() {
        Log.d(TAG, "initMap: initialize map!");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting loction permission!");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //
                mLocationPermissionGranted = true;
                initMap();

            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }  else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequetPermissionResult: called!");
        mLocationPermissionGranted = false;

        switch(requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionResult: permission granted!");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionGranted: permission granted!");
                    mLocationPermissionGranted = true;
                    // initialoze our map
                    initMap();
                }
            }
        }
    }
}
