package com.example.coder1704.himachalfirehotspotsdatapackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import java.util.ArrayList;

public class dbhelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "datahotspot.db";

    public static final String TABLE1_NAME = "geographic";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "DISTRICT";
    public static final String COL_3 = "AREA";
    public static final String COL_4 = "DENSE";
    public static final String COL_5 = "OPEN";
    public static final String COL_6 = "MODERATE";
    public static final String COL_7 = "TOTAL";

    public static final String TABLE2_NAME = "locdistrict";
    public static final String COL_8 = "LOCATION";
    public static final String COL_9 = "DISTRICT";

    public dbhelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE1_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, DISTRICT TEXT, AREA FLOAT, DENSE FLOAT, OPEN FLOAT, MODERATE FLOAT, TOTAL FLOAT)");
        db.execSQL("create table " + TABLE2_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, LOCATION TEXT, DISTRICT TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        onCreate(db);
    }

    public boolean insertDataGeo(String district, float area, float dense, float open, float moderate, float total) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, district);
        contentValues.put(COL_3, area);
        contentValues.put(COL_4, dense);
        contentValues.put(COL_5, open);
        contentValues.put(COL_6, moderate);
        contentValues.put(COL_7, total);
        long result = db.insert(TABLE1_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertDataLoc(String location, String district) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_8, location);
        contentValues.put(COL_9, district);
        long result = db.insert(TABLE2_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public String ShowData() {
        //
        String result = "";
        String query = "Select * FROM " + TABLE1_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            String result_2 = cursor.getString(2);
            String result_3 = cursor.getString(3);
            String result_4 = cursor.getString(4);
            String result_5 = cursor.getString(5);
            //result += String.valueOf(result_0) + "  " + result_1 + "  " + result_2 + "  " + result_3 +
              //      " " + result_4 + " " + System.getProperty("line.separator");
            result += String.valueOf(result_5) + " " + System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }

    public ArrayList<String> PlaceData() {
        Log.d("TAG", "PlaceData called!");
        ArrayList<String> placelist = new ArrayList<>();
        String query = "Select * FROM " + TABLE1_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            placelist.add(cursor.getString(1));
            Log.d("TAG", "item added in list!");
        }
        cursor.close();
        db.close();
        return placelist;
    }

    public GeoClass GeoAllData(String district) {

        String query = "Select * FROM " + TABLE1_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        GeoClass geoobj = null;
        while (cursor.moveToNext()) {

            String result_1 = cursor.getString(1); // name
            if (result_1.equals(district)) {
                float result_2 = cursor.getFloat(2); // district
                float result_3 = cursor.getFloat(3); // economy
                float result_4 = cursor.getFloat(4); // pine availablity
                float result_5 = cursor.getFloat(5); // about
                float result_6 = cursor.getFloat(6);
                geoobj = new GeoClass(result_1, result_2, result_3, result_4, result_5, result_6);
                break;
            }

        }
        cursor.close();
        db.close();
        return geoobj;
    }

    public void DeleteEntry(String district) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Delete FROM " + TABLE1_NAME + " WHERE DISTRICT='" + district + "'";
        db.execSQL(query);
        db.close();
    }
}
