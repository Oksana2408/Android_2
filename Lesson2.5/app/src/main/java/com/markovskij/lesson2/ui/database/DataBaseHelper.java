package com.markovskij.lesson2.ui.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "weather.db";
    private static final int DATABASE_VERSION = 1;
    private static String DATABASE_PATH; //путь к базе данных
    private Context myContext;

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        DATABASE_PATH = context.getFilesDir().getPath() + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    //метод для работы с существующей БД

    public void createDatabase(){
        InputStream myInput = null;
        OutputStream myOutput = null;
        try{
            File file = new File(DATABASE_PATH);  //путь к новой БД
            if(!file.exists()){
                this.getReadableDatabase(); //получаем локальный БД как поток
                myInput = myContext.getAssets().open(DATABASE_NAME); //путь к новой БД
                String outFileName = DATABASE_PATH;
                //побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0){
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //метод открытия БД

    public SQLiteDatabase open(){
        return SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
