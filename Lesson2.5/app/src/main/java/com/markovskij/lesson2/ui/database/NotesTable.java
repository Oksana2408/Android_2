package com.markovskij.lesson2.ui.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class NotesTable {

    private static final String TABLE_NAME = "weather";
    private static final String ID = "_id";
    private static final String CITY_NAME = "city";
    private static final String DATE = "date";
    private static final String TEMPERATURE = "temperature";

//    static void createTable(SQLiteDatabase database){
//        database.execSQL("CREATE TABLE " + TABLE_NAME + " ("
//                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                + CITY_NAME + " TEXT,"
//                + DATE + " TEXT,"
//                + TEMPERATURE +" TEXT);");
//    }
//
//    static void upgrade(SQLiteDatabase database){
//        database.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + CITY_NAME
//                + " TEXT DEFAULT 'Default title'");
//        createTable(database);
//    }

    public static void addCity(String city, SQLiteDatabase database){
        ContentValues values = new ContentValues();
        values.put(CITY_NAME, city);
        database.insert(TABLE_NAME, null, values);
    }

    public static void addDate(String date, SQLiteDatabase database){
        ContentValues values = new ContentValues();
        values.put(DATE, date);
        database.insert(TABLE_NAME, null, values);
    }

    public static void addTemp(String temp, SQLiteDatabase database){
        ContentValues values = new ContentValues();
        values.put(TEMPERATURE, temp);
        database.insert(TABLE_NAME, null, values);
    }

    public static void getAllInfo(SQLiteDatabase database){
        Cursor cursor = database.query("SELECT * FROM" + TABLE_NAME,
                null, null, null, null, null, null);
        getResultFromCursor(cursor);
    }

    public static void getCities(SQLiteDatabase database){
        Cursor cursor = database.query("SELECT * FROM" + TABLE_NAME, new String [] {CITY_NAME},
                null, null, null, null, null);
        getResultFromCursor(cursor);
    }

    private static List<Integer> getResultFromCursor(Cursor cursor){
        List<Integer> result = null;
        if(cursor != null && cursor.moveToFirst()){
            result = new ArrayList<>(cursor.getCount());
            int cityIndex = cursor.getColumnIndex(CITY_NAME);
            do{
                result.add(cursor.getInt(cityIndex));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return result == null?new ArrayList<Integer>(0):result;
    }
}
